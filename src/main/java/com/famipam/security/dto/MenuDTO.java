package com.famipam.security.dto;

import java.util.Set;

public record MenuDTO(
        long id,
        String authority,
        String name,
        String description,
        String link,
        String group,
        boolean showOnNav,
        String icon,
        Set<MenuDTO> children,
        Set<String> authorities,
        int seq
) {
}
