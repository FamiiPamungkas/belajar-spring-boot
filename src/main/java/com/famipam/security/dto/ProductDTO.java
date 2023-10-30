package com.famipam.security.dto;

import jakarta.validation.constraints.NotBlank;

public record ProductDTO(
        long id,
        String code,
        @NotBlank
        String name,
        String category,
        double price,
        String createdAt,
        String updatedAt
) {
}
