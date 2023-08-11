package com.famipam.security.dto.user;

public record SimpleUser(
        long id,
        String username,
        String fullname,
        boolean active
) {
}
