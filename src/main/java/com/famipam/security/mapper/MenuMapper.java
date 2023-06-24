package com.famipam.security.mapper;

import com.famipam.security.dto.MenuDTO;
import com.famipam.security.entity.Menu;

import java.util.function.Function;
import java.util.stream.Collectors;

public class MenuMapper implements Function<Menu, MenuDTO> {

    /**
     * Applies this function to the given argument.
     *
     * @param menu the function argument
     * @return the function result
     */
    @Override
    public MenuDTO apply(Menu menu) {
        return new MenuDTO(
                menu.getId(),
                menu.getAuthority(),
                menu.getName(),
                menu.getDescription(),
                menu.getLink(),
                menu.getGroup(),
                menu.isShowOnNav(),
                menu.getIcon(),
                menu.getChildren()
                        .stream()
                        .map(this)
                        .collect(Collectors.toSet()),
                menu.getSeq()
        );
    }
}
