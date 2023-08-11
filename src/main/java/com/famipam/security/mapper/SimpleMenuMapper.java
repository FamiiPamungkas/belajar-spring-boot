package com.famipam.security.mapper;

import com.famipam.security.dto.SimpleMenu;
import com.famipam.security.entity.Menu;
import com.famipam.security.util.DateUtils;

import java.util.function.Function;

public class SimpleMenuMapper implements Function<Menu, SimpleMenu> {

    /**
     * Applies this function to the given argument.
     *
     * @param menu the function argument
     * @return the function result
     */
    @Override
    public SimpleMenu apply(Menu menu) {
        if (menu == null) return null;

        return apply(menu, 1);
    }

    private SimpleMenu apply(Menu menu, int layer) {
        if (menu == null) return null;

        return new SimpleMenu(
                menu.getId(),
                menu.getAuthority(),
                menu.getName(),
                menu.getDescription(),
                menu.getLink(),
                menu.getGroup(),
                menu.isVisible(),
                menu.getIcon(),
                menu.getActive(),
                DateUtils.formatDate(menu.getCreateAt()),
                DateUtils.formatDate(menu.getUpdatedAt()),
                (layer > 3) ? null : this.apply(menu.getParent(), (layer + 1)),
                menu.getSeq()
        );
    }
}
