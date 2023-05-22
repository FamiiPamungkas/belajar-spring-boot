package com.famipam.security.controller.api;

import com.famipam.security.dto.UserDTO;
import com.famipam.security.entity.User;
import com.famipam.security.exception.ExpectedException;
import com.famipam.security.exception.ResourceNotFoundException;
import com.famipam.security.mapper.UserMapper;
import com.famipam.security.repository.UserRepository;
import com.famipam.security.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('users')")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper = new UserMapper();

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userRepository.findAll()
                .stream()
                .map(userMapper)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUser(
            @PathVariable long id
    ) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User [" + id + "] not found"));
        return ResponseEntity.ok(userMapper.apply(user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable long id
    ) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User [" + id + "] not found"));
        userRepository.delete(user);
        return ResponseEntity.ok("Delete User Success");
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> editUser(
            @RequestBody UserDTO userDTO
    ) throws ParseException {
        User user = new User();

        if (userRepository.existsByUsername(userDTO.username())) throw new ExpectedException("Username Has Been Used");

        Date birthdate = DateUtils.parseISODate(userDTO.birthdate());

        user.setFirstname(userDTO.firstname());
        user.setLastname(userDTO.lastname());
        user.setBirthdate(birthdate);
        user.setEmail(userDTO.email());
        user.setUsername(userDTO.username());
        user.setPassword(passwordEncoder.encode(DateUtils.formatDefaultPassword(birthdate)));

        userRepository.save(user);

        return ResponseEntity.ok(userDTO);
    }


}
