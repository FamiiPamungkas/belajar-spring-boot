package com.famipam.security.dto;

import jakarta.validation.constraints.NotBlank;

public record SimpleMenu(
        long id,
        @NotBlank(message = "Authority is required")
        String authority,
        @NotBlank(message = "Name is required")
        String name,
        String description,
        String link,
        @NotBlank(message = "Group is required")
        String group,
        boolean visible,
        String icon,
        boolean active,
        String createdAt,
        String updatedAt,
        SimpleMenu parent,
        int seq
) {
}
