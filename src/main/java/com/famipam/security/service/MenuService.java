package com.famipam.security.service;

import com.famipam.security.entity.Menu;
import com.famipam.security.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Menu> searchGroupMenu(String name){
        return repository.searchGroupMenu(name);
    }

}
