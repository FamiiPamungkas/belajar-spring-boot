package com.famipam.security.controller.api;

import com.famipam.security.dto.RoleDTO;
import com.famipam.security.dto.SimpleMenu;
import com.famipam.security.entity.Menu;
import com.famipam.security.exception.NotFoundException;
import com.famipam.security.mapper.RoleMapper;
import com.famipam.security.mapper.SimpleMenuMapper;
import com.famipam.security.repository.RoleRepository;
import com.famipam.security.service.MenuService;
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


}
