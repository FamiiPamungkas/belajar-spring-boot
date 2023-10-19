package com.famipam.security.dto;

import jakarta.validation.constraints.NotBlank;

public record ProductDTO(
        long id,
        @NotBlank
        String name,
        String createdAt,
        String updatedAt
) {
}
