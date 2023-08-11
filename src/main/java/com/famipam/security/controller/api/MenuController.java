package com.famipam.security.controller.api;

import com.famipam.security.dto.RoleDTO;
import com.famipam.security.dto.SimpleMenu;
import com.famipam.security.entity.Menu;
import com.famipam.security.exception.NotFoundException;
import com.famipam.security.mapper.RoleMapper;
import com.famipam.security.mapper.SimpleMenuMapper;
import com.famipam.security.model.SimpleOption;
import com.famipam.security.repository.MenuRepository;
import com.famipam.security.repository.RoleRepository;
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
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController extends BaseController {

    private final MenuRepository menuRepository;
    private final RoleRepository roleRepository;
    private final SimpleMenuMapper simpleMenuMapper = new SimpleMenuMapper();
    private final RoleMapper roleMapper = new RoleMapper();

    @GetMapping
    public ResponseEntity<List<SimpleMenu>> getRoleList() {
        List<SimpleMenu> menus = menuRepository.findAll()
                .stream()
                .map(simpleMenuMapper)
                .collect(Collectors.toList());
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/options")
    public ResponseEntity<List<SimpleOption>> getRoleAsOptions() {
        List<SimpleOption> menus = menuRepository.findAll()
                .stream()
                .map(menu -> new SimpleOption(
                        String.valueOf(menu.getId()),
                        menu.getName())
                ).toList();
        return ResponseEntity.ok(menus);
    }

    @GetMapping("{id}")
    public ResponseEntity<SimpleMenu> getRole(
            @PathVariable long id
    ) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new NotFoundException("Menu [" + id + "] not found."));
        return ResponseEntity.ok(simpleMenuMapper.apply(menu));
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<List<RoleDTO>> findRoleUsers(
            @PathVariable long id
    ) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Menu [" + id + "] not found."));
        return ResponseEntity.ok(
                roleRepository.findByMenu(menu)
                        .stream()
                        .map(roleMapper)
                        .toList()
        );
    }

}
