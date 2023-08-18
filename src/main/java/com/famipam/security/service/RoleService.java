package com.famipam.security.service;

import com.famipam.security.entity.Role;
import com.famipam.security.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@SuppressWarnings({"rawtypes", "unchecked"})
public class RoleService extends BaseRepoService<Role> {

    private RoleRepository repository;

    @Override
    public JpaRepository repository() {
        return repository;
    }

    public Optional<Role> findById(long id) {
        return repository.findById(id);
    }

    public boolean existsByAuthority(String authority, long excludeId) {
        return repository.existsByAuthority(authority, excludeId);
    }

}
