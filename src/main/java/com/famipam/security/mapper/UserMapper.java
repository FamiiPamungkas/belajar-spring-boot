package com.famipam.security.mapper;

import com.famipam.security.dto.UserDTO;
import com.famipam.security.entity.User;
import com.famipam.security.util.DateUtils;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserMapper implements Function<User, UserDTO> {

    private final RoleMapper roleMapper = new RoleMapper();

    /**
     * Applies this function to the given argument.
     *
     * @param user the function argument
     * @return the function result
     */
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstname(),
                Optional.ofNullable(user.getLastname()).orElse(""),
                DateUtils.formatDate(user.getBirthdate()),
                user.getUsername(),
                user.getEmail(),
                user.getRoles()
                        .stream()
                        .map(roleMapper)
                        .collect(Collectors.toList())
        );
    }
}
