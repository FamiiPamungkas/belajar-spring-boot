package com.famipam.security.controller.api;

import com.famipam.security.dto.RoleDTO;
import com.famipam.security.dto.UserDTO;
import com.famipam.security.dto.UserFormRequest;
import com.famipam.security.entity.Role;
import com.famipam.security.entity.User;
import com.famipam.security.exception.ExpectedException;
import com.famipam.security.exception.NotFoundException;
import com.famipam.security.mapper.UserMapper;
import com.famipam.security.repository.UserRepository;
import com.famipam.security.service.RoleService;
import com.famipam.security.service.UserService;
import com.famipam.security.util.DateUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
//@PreAuthorize("hasAuthority('user-management')")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User [" + id + "] not found"));
        return ResponseEntity.ok(userMapper.apply(user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable long id
    ) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User [" + id + "] not found"));
        userRepository.delete(user);
        return ResponseEntity.ok("Delete User Success");
    }

    @PutMapping()
    public ResponseEntity<UserDTO> editUser(
            @Valid @RequestBody UserDTO userDTO
    ) throws ParseException {
        User user = userRepository.findById(userDTO.id())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (userRepository.existsByUsername(userDTO.username(), user.getId()))
            throw new ExpectedException("Username Has Been Used");

        Date birthdate = DateUtils.parseISODate(userDTO.birthdate());

        user.setFirstname(userDTO.firstname());
        user.setLastname(userDTO.lastname());
        user.setBirthdate(birthdate);
        user.setEmail(userDTO.email());
        user.setUsername(userDTO.username());

        userRepository.save(user);

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping()
    public ResponseEntity<UserDTO> addUser(
            @Valid @RequestBody UserFormRequest userDTO
    ) throws ParseException {
        if (userService.existsByUsername(userDTO.username(), userDTO.id()))
            throw new ExpectedException("Username Has Been Used");

        User user = new User();

        user.setFirstname(userDTO.firstname());
        user.setLastname(userDTO.lastname());
        user.setBirthdate(DateUtils.parseISODate(userDTO.birthdate()));
        user.setEmail(userDTO.email());
        user.setUsername(userDTO.username());
        user.setPassword(passwordEncoder.encode(userDTO.password()));

        Set<Role> roles = new LinkedHashSet<>();
        for (RoleDTO roleDTO : userDTO.roles()) {
            Role role = roleService.findById(roleDTO.id())
                    .orElseThrow(() -> new NotFoundException("Role [" + roleDTO.id() + "] not found"));
            roles.add(role);
        }
        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity.ok(userMapper.apply(user));
    }


}
