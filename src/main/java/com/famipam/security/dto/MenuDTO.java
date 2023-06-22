package com.famipam.security.dto;

public record MenuDTO(
        long id,
        long parentId,
        String authority,
        String name,
        String description,
        String link,
        boolean isMenu
) {
}
