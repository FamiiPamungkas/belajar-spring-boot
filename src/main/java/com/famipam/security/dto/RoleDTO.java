package com.famipam.security.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record RoleDTO(
        long id,
        @NotBlank
        String authority,
        @NotBlank
        String name,
        String description,
        boolean active,
        String createdAt,
        String updatedAt,
        List<MenuDTO> menus
) {
}
