package com.famipam.security.service;

import com.famipam.security.entity.User;
import com.famipam.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repository;

    public User findByUsername(String username){
        Optional<User> user = repository.findByUsername(username);
        return user.orElse(null);
    }

}
