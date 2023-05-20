package com.famipam.security.controller.api;

import com.famipam.security.dto.UserDTO;
import com.famipam.security.mapper.UserMapper;
import com.famipam.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper = new UserMapper();

    @GetMapping
    @PostAuthorize("hasAuthority('users')")
    public ResponseEntity<List<UserDTO>> getUsers(Authentication auth){
        System.out.println("### Get User");
        for (GrantedAuthority authority : auth.getAuthorities()) {
            System.out.println("Auth = "+authority);
        }
        List<UserDTO> users = userRepository.findAll()
                .stream()
                .map(userMapper)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
}
