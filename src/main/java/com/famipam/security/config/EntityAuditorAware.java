package com.famipam.security.config;

import com.famipam.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@RequiredArgsConstructor
public class EntityAuditorAware implements AuditorAware<User> {


    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = Optional.empty();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof User) {
                user = Optional.of((User) authentication.getPrincipal());
            }
        }
        return user;
    }
}
