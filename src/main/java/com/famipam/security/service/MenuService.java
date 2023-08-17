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

    public List<String> findGroupList(){
        return repository.findGroupList();
    }

    public boolean existsByAuthority(String authority, long id) {
        return repository.existsByAuthority(authority, id);
    }

    public int getParentLastSequence(Menu parent) {
        return repository.getParentLastSequence(parent);
    }
}
