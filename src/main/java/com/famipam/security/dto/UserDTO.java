package com.famipam.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;

public record UserDTO(
        long id,
        @NotBlank(message = "Firstname is required")
        String firstname,
        String lastname,
        @NotBlank(message = "Birthdate is required")
        String birthdate,
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Email is required")
        @Email(message = "Must be a well-formed email address")
        String email,
        List<RoleDTO> roles,
        Set<MenuDTO> treeMenus
) {
}
