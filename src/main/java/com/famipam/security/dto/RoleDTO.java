package com.famipam.security.dto;

import java.util.List;

public record RoleDTO(
        long id,
        String name,
        String description,
        List<MenuDTO> menus
) {
}
