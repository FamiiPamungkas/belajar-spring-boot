package com.famipam.security.dto;

public record SimpleMenu(
        long id,
        String authority,
        String name,
        String description,
        String link,
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
