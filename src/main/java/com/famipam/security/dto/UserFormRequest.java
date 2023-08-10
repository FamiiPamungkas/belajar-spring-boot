package com.famipam.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UserFormRequest(
        long id,
        @NotBlank(message = "Firstname is required")
        String firstname,
        String lastname,
        @NotBlank(message = "Birthdate is required")
        String birthdate,
        @NotBlank(message = "Username is required")
        String username,
        String password,
        @NotBlank(message = "Email is required")
        @Email(message = "Must be a well-formed email address")
        String email,
        boolean active,
        @NotEmpty(message = "Role is required")
        Set<RoleDTO> roles
) {
}
