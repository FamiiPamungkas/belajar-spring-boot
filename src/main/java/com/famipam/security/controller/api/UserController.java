package com.famipam.security.controller.api;

import com.famipam.security.dto.user.UserDTO;
import com.famipam.security.dto.user.UserFormRequest;
import com.famipam.security.entity.Role;
import com.famipam.security.entity.User;
import com.famipam.security.exception.ExpectedException;
import com.famipam.security.exception.NotFoundException;
import com.famipam.security.mapper.UserMapper;
import com.famipam.security.model.ApiResponse;
import com.famipam.security.model.BaseResponse;
import com.famipam.security.repository.UserRepository;
import com.famipam.security.service.RoleService;
import com.famipam.security.service.UserService;
import com.famipam.security.util.DateUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserMapper userMapper = new UserMapper();

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUserList() {
        List<UserDTO> users = userRepository.findAll()
                .stream()
                .map(userMapper)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserDetail(
            @PathVariable long id
    ) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User [" + id + "] not found"));
        return ResponseEntity.ok(userMapper.apply(user));
    }

    @DeleteMapping("{id}")
    @PreAuthorize(value = "hasAuthority('user-management')")
    public ResponseEntity<BaseResponse> deleteUser(
            @PathVariable long id
    ) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User [" + id + "] not found"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authUser = userService.findUserByUsername(authentication.getName(), 0);

        if (user.getId().equals(1L)) {
            throw new AccessDeniedException("You cannot delete admin data.");
        }

        if (authUser.equals(user)) {
            throw new AccessDeniedException("You cannot delete your own data.");
        }

//        userRepository.delete(user);
        return ResponseEntity.ok(BaseResponse.builder()
                .status(SUCCESS_CODE)
                .message(SUCCESS)
                .build()
        );
    }

    @PutMapping
    @PreAuthorize(value = "hasAuthority('user-management')")
    public ResponseEntity<ApiResponse> editUser(
            @Valid @RequestBody UserFormRequest userDto
    ) throws ParseException {
        User user = userRepository.findById(userDto.id())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (userRepository.existsByUsername(userDto.username(), user.getId())) {
            throw new ExpectedException("Username Has Been Used");
        }

        Date birthdate = DateUtils.parseISODate(userDto.birthdate());
        user.setFirstname(userDto.firstname());
        user.setLastname(userDto.lastname());
        user.setBirthdate(birthdate);
        user.setEmail(userDto.email());
        user.setUsername(userDto.username());
        if (!userDto.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.password()));
        }
        user.setActive(userDto.active());
        user.setUpdatedAt(new Date());

        Set<Role> roles = roleService.getRolesFromDtos(userDto.roles());
        user.setRoles(roles);

        userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.builder()
                .status(SUCCESS_CODE)
                .message(SUCCESS)
                .data(userMapper.apply(user))
                .build()
        );
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('user-management')")
    public ResponseEntity<ApiResponse> addUser(
            @Valid @RequestBody UserFormRequest userDTO
    ) throws ParseException {
        if (userService.existsByUsername(userDTO.username().trim(), userDTO.id()))
            throw new ExpectedException("Username has been used");

        User user = new User();
        user.setFirstname(userDTO.firstname());
        user.setLastname(userDTO.lastname());
        user.setBirthdate(DateUtils.parseISODate(userDTO.birthdate()));
        user.setEmail(userDTO.email());
        user.setUsername(userDTO.username().trim());
        if (userDTO.password().isEmpty()) {
            throw new ExpectedException("Password is required");
        }
        user.setPassword(passwordEncoder.encode(userDTO.password()));

        Set<Role> roles = roleService.getRolesFromDtos(userDTO.roles());
        user.setRoles(roles);

        userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.builder()
                .status(SUCCESS_CODE)
                .message(SUCCESS)
                .data(userMapper.apply(user))
                .build()
        );
    }


}
