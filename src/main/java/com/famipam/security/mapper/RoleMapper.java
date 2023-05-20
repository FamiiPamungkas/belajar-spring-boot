package com.famipam.security.mapper;

import com.famipam.security.dto.RoleDTO;
import com.famipam.security.entity.Role;

import java.util.function.Function;
import java.util.stream.Collectors;

public class RoleMapper implements Function<Role, RoleDTO> {

    private final ViewMapper viewMapper = new ViewMapper();

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
                role.getName(),
                role.getDescription(),
                role.getViews()
                        .stream()
                        .map(viewMapper)
                        .collect(Collectors.toList())
        );
    }
}
