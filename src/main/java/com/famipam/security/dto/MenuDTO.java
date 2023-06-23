package com.famipam.security.dto;

import java.util.Set;

public record MenuDTO(
        long id,
        long parentId,
        String authority,
        String name,
        String description,
        String link,
        String group,
        boolean isMenu,
        Set<MenuDTO> children
) {
}
