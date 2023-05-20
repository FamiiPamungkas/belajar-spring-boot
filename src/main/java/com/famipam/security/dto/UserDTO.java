package com.famipam.security.dto;

import java.util.List;

public record UserDTO(
        long id,
        String firstname,
        String lastname,
        String username,
        List<RoleDTO> roles
) {
}