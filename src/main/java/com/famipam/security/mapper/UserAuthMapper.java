package com.famipam.security.mapper;

import com.famipam.security.dto.UserAuthDTO;
import com.famipam.security.entity.User;
import com.famipam.security.util.DateUtils;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserAuthMapper implements Function<User, UserAuthDTO> {

    private final RoleMapper roleMapper = new RoleMapper();
    private final MenuMapper menuMapper = new MenuMapper();

    /**
     * Applies this function to the given argument.
     *
     * @param user the function argument
     * @return the function result
     */
    @Override
    public UserAuthDTO apply(User user) {
        return new UserAuthDTO(
                user.getId(),
                user.getFirstname(),
                Optional.ofNullable(user.getLastname()).orElse(""),
                DateUtils.formatDate(user.getBirthdate()),
                user.getUsername(),
                user.getEmail(),
                user.getActive(),
                DateUtils.formatDate(user.getCreateAt()),
                DateUtils.formatDate(user.getUpdatedAt()),
                user.getRoles()
                        .stream()
                        .map(roleMapper)
                        .collect(Collectors.toSet()),
                user.getTreeMenus()
                        .stream()
                        .map(menuMapper)
                        .collect(Collectors.toSet())
        );
    }
}
