package com.famipam.security.service;

import com.famipam.security.entity.Role;
import com.famipam.security.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository repository;

    public Optional<Role> findById(long id) {
        return repository.findById(id);
    }
}
