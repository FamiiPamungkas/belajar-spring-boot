package com.famipam.security.dto;

import java.util.List;

public record RoleDTO(
        long id,
        String authority,
        String name,
        String description,
        List<MenuDTO> menus
) {
}
