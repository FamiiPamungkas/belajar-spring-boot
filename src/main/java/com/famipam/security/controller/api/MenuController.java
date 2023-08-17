package com.famipam.security.controller.api;

import com.famipam.security.dto.RoleDTO;
import com.famipam.security.dto.SimpleMenu;
import com.famipam.security.entity.Menu;
import com.famipam.security.exception.ExpectedException;
import com.famipam.security.exception.NotFoundException;
import com.famipam.security.mapper.RoleMapper;
import com.famipam.security.mapper.SimpleMenuMapper;
import com.famipam.security.model.ApiResponse;
import com.famipam.security.repository.RoleRepository;
import com.famipam.security.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController extends BaseController {

    private final MenuService menuService;
    private final RoleRepository roleRepository;
    private final SimpleMenuMapper simpleMenuMapper = new SimpleMenuMapper();
    private final RoleMapper roleMapper = new RoleMapper();

    @GetMapping
    public ResponseEntity<List<SimpleMenu>> getMenuList() {
        List<SimpleMenu> menus = menuService.findAll()
                .stream()
                .map(simpleMenuMapper)
                .collect(Collectors.toList());
        return ResponseEntity.ok(menus);
    }

    @GetMapping("{id}")
    public ResponseEntity<SimpleMenu> getMenu(
            @PathVariable long id
    ) {
        Menu menu = menuService.findById(id).orElseThrow(() -> new NotFoundException("Menu [" + id + "] not found."));
        return ResponseEntity.ok(simpleMenuMapper.apply(menu));
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<List<RoleDTO>> findMenuRoles(
            @PathVariable long id
    ) {
        Menu menu = menuService.findById(id)
                .orElseThrow(() -> new NotFoundException("Menu [" + id + "] not found."));
        return ResponseEntity.ok(
                roleRepository.findByMenu(menu)
                        .stream()
                        .map(roleMapper)
                        .toList()
        );
    }

    @GetMapping("/for-parent")
    public ResponseEntity<List<SimpleMenu>> getMenuForParent() {
        List<SimpleMenu> menus = menuService.findAllForParent()
                .stream()
                .map(simpleMenuMapper)
                .collect(Collectors.toList());
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/groups")
    public ResponseEntity<List<String>> findGroupList() {
        return ResponseEntity.ok(menuService.findGroupList());
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('user-management')")
    public ResponseEntity<ApiResponse> newMenu(
            @Valid @RequestBody SimpleMenu body
    ) {
        boolean exists = menuService.existsByAuthority(body.authority(), body.id());
        if (exists) {
            throw new ExpectedException("Menu with this authority [" + body.authority() + "] is already exists");
        }

        Menu parent = null;
        int seq = 0;
        if (body.parent() != null) {
            parent = menuService.findById(body.parent().id()).orElseThrow(() -> new NotFoundException("Parent not found!"));
            seq = menuService.getParentLastSequence(parent);
        }

        Menu menu = Menu.builder()
                .name(body.name())
                .authority(body.authority())
                .description(body.description())
                .group(body.group())
                .link(body.link())
                .parent(parent)
                .visible(body.visible())
                .icon(body.icon())
                .seq(seq + 1)
                .build();

        menuService.save(menu);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(SUCCESS_CODE)
                .message(SUCCESS)
                .data(menu)
                .build()
        );
    }

    @PutMapping
    @PreAuthorize(value = "hasAuthority('user-management')")
    public ResponseEntity<ApiResponse> updateMenu(
            @Valid @RequestBody SimpleMenu body
    ) {

        Menu menu = menuService.findById(body.id()).orElseThrow(()-> new NotFoundException("Menu not found."));
        if (menuService.existsByAuthority(body.authority(), body.id())) {
            throw new ExpectedException("Menu with this authority [" + body.authority() + "] is already exists");
        }

        Menu parent = null;
        if (body.parent() != null) {
            parent = menuService.findById(body.parent().id()).orElseThrow(() -> new NotFoundException("Parent with id ["+body.parent().id()+"] found!"));
        }

        menu.setName(body.name());
        menu.setAuthority(body.authority());
        menu.setDescription(body.description());
        menu.setGroup(body.group());
        menu.setLink(body.link());
        menu.setParent(parent);
        menu.setVisible(body.visible());
        menu.setIcon(body.icon());

        menuService.save(menu);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(SUCCESS_CODE)
                .message(SUCCESS)
                .data(menu)
                .build()
        );

    }

}
