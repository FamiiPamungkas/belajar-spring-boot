package com.famipam.security.controller.api;

import com.famipam.security.dto.RoleDTO;
import com.famipam.security.dto.user.SimpleUser;
import com.famipam.security.entity.Role;
import com.famipam.security.exception.NotFoundException;
import com.famipam.security.mapper.RoleMapper;
import com.famipam.security.mapper.SimpleUserMapper;
import com.famipam.security.model.SimpleOption;
import com.famipam.security.repository.RoleRepository;
import com.famipam.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final RoleMapper roleMapper = new RoleMapper();
    private final SimpleUserMapper userMapper = new SimpleUserMapper();

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getRoleList() {
        List<RoleDTO> users = roleRepository.findAll()
                .stream()
                .map(roleMapper)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/options")
    public ResponseEntity<List<SimpleOption>> getRoleAsOptions() {
        List<SimpleOption> roles = roleRepository.findAll()
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
        Role role = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role [" + id + "] not found."));
        return ResponseEntity.ok(roleMapper.apply(role));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<SimpleUser>> findRoleUsers(
            @PathVariable long id
    ) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role [" + id + "] not found."));
        return ResponseEntity.ok(
                userService.findByRole(role)
                        .stream()
                        .map(userMapper)
                        .toList()
        );
    }

}
