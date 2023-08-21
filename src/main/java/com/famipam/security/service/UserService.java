package com.famipam.security.service;

import com.famipam.security.entity.Role;
import com.famipam.security.entity.User;
import com.famipam.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@SuppressWarnings({"rawtypes", "unchecked"})
public class UserService extends BaseRepoService<User> {

    private UserRepository repository;

    @Override
    public JpaRepository repository() {
        return repository;
    }

    public Optional<User> findByUsername(String username, long excludeId) {
        return repository.findUserByUsernameAndIdNot(username, excludeId);
    }

    public User findUserByUsername(String username, long excludeId) {
        return findByUsername(username, excludeId).orElse(null);
    }

    public boolean existsByUsername(String username, long excludeId) {
        return repository.existsByUsername(username, excludeId);
    }

    public List<User> findByRole(Role role) {
        return repository.findByRole(role);
    }

    public Page<User> findByFilter(String search, Long roleId, Boolean active, Pageable pageable) {
        System.out.println("SEARCH ="+search);
        System.out.println("roleId ="+roleId);
        System.out.println("active ="+active);
        return repository.findByFilter(search, roleId, active, pageable);
    }

}
