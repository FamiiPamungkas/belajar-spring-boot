package com.famipam.security.dto;

public record ViewDTO(
        long id,
        long parentId,
        String authority,
        String name,
        String description,
        String link,
        boolean isMenu
) {
}
