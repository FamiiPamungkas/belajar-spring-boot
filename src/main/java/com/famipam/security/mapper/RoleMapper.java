package com.famipam.security.mapper;

import com.famipam.security.dto.RoleDTO;
import com.famipam.security.entity.Role;
import com.famipam.security.util.DateUtils;

import java.util.function.Function;
import java.util.stream.Collectors;

public class RoleMapper implements Function<Role, RoleDTO> {

    private final MenuMapper menuMapper = new MenuMapper();

    /**
     * Applies this function to the given argument.
     *
     * @param role the function argument
     * @return the function result
     */
    @Override
    public RoleDTO apply(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getAuthority(),
                role.getName(),
                role.getDescription(),
                role.getActive(),
                DateUtils.formatDate(role.getCreateAt()),
                DateUtils.formatDate(role.getUpdatedAt()),
                role.getMenus()
                        .stream()
                        .map(menuMapper)
                        .collect(Collectors.toList())
        );
    }
}
