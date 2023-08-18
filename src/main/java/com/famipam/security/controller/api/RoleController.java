package com.famipam.security.controller.api;

import com.famipam.security.dto.MenuDTO;
import com.famipam.security.dto.RoleDTO;
import com.famipam.security.dto.user.SimpleUser;
import com.famipam.security.entity.Menu;
import com.famipam.security.entity.Role;
import com.famipam.security.exception.ExpectedException;
import com.famipam.security.exception.NotFoundException;
import com.famipam.security.mapper.RoleMapper;
import com.famipam.security.mapper.SimpleUserMapper;
import com.famipam.security.model.ApiResponse;
import com.famipam.security.model.SimpleOption;
import com.famipam.security.service.MenuService;
import com.famipam.security.service.RoleService;
import com.famipam.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController extends BaseController {

    private final RoleService roleService;
    private final MenuService menuService;
    private final UserService userService;
    private final RoleMapper roleMapper = new RoleMapper();
    private final SimpleUserMapper userMapper = new SimpleUserMapper();

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getRoleList() {
        List<RoleDTO> users = roleService.findAll()
                .stream()
                .map(roleMapper)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/options")
    public ResponseEntity<List<SimpleOption>> getRoleAsOptions() {
        List<SimpleOption> roles = roleService.findAll()
                .stream()
                .map(role -> new SimpleOption(
                        String.valueOf(role.getId()),
                        role.getName())
                ).toList();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("{id}")
    public ResponseEntity<RoleDTO> getRole(
            @PathVariable long id
    ) {
        Role role = roleService.findById(id).orElseThrow(() -> new NotFoundException("Role [" + id + "] not found."));
        return ResponseEntity.ok(roleMapper.apply(role));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<SimpleUser>> findRoleUsers(
            @PathVariable long id
    ) {
        Role role = roleService.findById(id)
                .orElseThrow(() -> new NotFoundException("Role [" + id + "] not found."));
        return ResponseEntity.ok(
                userService.findByRole(role)
                        .stream()
                        .map(userMapper)
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addRole(
            @RequestBody RoleDTO roleDTO
    ) {

        boolean exists = roleService.existsByAuthority(roleDTO.authority(), roleDTO.id());
        if (exists) {
            throw new ExpectedException("Role with this authority [" + roleDTO.authority() + "] is already exists");
        }

        Set<Menu> menus = new LinkedHashSet<>();
        Menu menu;
        for (MenuDTO menuDTO : roleDTO.menus()) {
            menu = menuService.findById(menuDTO.id())
                    .orElseThrow(() -> new ExpectedException("Menu with id [" + menuDTO.id() + "] not found"));
            menus.add(menu);
        }
        Role role = Role.builder()
                .name(roleDTO.name())
                .authority(roleDTO.authority())
                .description(roleDTO.description())
                .menus(menus)
                .build();

        roleService.save(role);
        return ResponseEntity.ok(ApiResponse.builder()
                .status(SUCCESS_CODE)
                .message(SUCCESS)
                .data(roleMapper.apply(role))
                .build()
        );
    }

}
