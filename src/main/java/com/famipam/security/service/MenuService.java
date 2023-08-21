package com.famipam.security.service;

import com.famipam.security.dto.MenuDTO;
import com.famipam.security.entity.Menu;
import com.famipam.security.exception.ExpectedException;
import com.famipam.security.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@SuppressWarnings({"rawtypes", "unchecked"})
public class MenuService extends BaseRepoService<Menu> {

    private MenuRepository repository;

    @Override
    public JpaRepository repository() {
        return repository;
    }

    public List<Menu> findAllForParent() {
        return repository.findAllForParent();
    }

    public List<String> findGroupList(){
        return repository.findGroupList();
    }

    public boolean existsByAuthority(String authority, long id) {
        return repository.existsByAuthority(authority, id);
    }

    public int getParentLastSequence(Menu parent) {
        return repository.getParentLastSequence(parent);
    }

    public Set<Menu> createMenusFromDtos(List<MenuDTO> menuDTOS) {
        Set<Menu> menus = new LinkedHashSet<>();
        Menu menu;
        for (MenuDTO menuDTO : menuDTOS) {
            menu = this.findById(menuDTO.id())
                    .orElseThrow(() -> new ExpectedException("Menu with id [" + menuDTO.id() + "] not found"));
            menus.add(menu);
        }
        return menus;
    }
}
