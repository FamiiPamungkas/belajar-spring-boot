package com.famipam.security.service;

import com.famipam.security.entity.User;
import com.famipam.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repository;

    public Optional<User> findByUsername(String username, long excludeId) {
        return repository.findUserByUsernameAndIdNot(username, excludeId);
    }

    public User findUserByUsername(String username, long excludeId) {
        return findByUsername(username, excludeId).orElse(null);
    }

    public boolean existsByUsername(String username, long excludeId) {
        return repository.existsByUsername(username, excludeId);
    }


}
