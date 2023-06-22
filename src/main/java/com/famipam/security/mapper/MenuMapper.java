package com.famipam.security.mapper;

import com.famipam.security.dto.MenuDTO;
import com.famipam.security.entity.Menu;

import java.util.function.Function;

public class MenuMapper implements Function<Menu, MenuDTO> {


    /**
     * Applies this function to the given argument.
     *
     * @param menu the function argument
     * @return the function result
     */
    @Override
    public MenuDTO apply(Menu menu) {
        long parentId = menu.getParent() != null ? menu.getParent().getId() : 0;
        return new MenuDTO(
                menu.getId(),
                parentId,
                menu.getAuthority(),
                menu.getName(),
                menu.getDescription(),
                menu.getLink(),
                menu.isMenu()
        );
    }
}
