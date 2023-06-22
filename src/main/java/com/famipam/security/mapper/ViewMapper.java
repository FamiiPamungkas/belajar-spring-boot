package com.famipam.security.mapper;

import com.famipam.security.dto.ViewDTO;
import com.famipam.security.entity.View;

import java.util.function.Function;

public class ViewMapper implements Function<View, ViewDTO> {


    /**
     * Applies this function to the given argument.
     *
     * @param view the function argument
     * @return the function result
     */
    @Override
    public ViewDTO apply(View view) {
        long parentId = view.getParent() != null ? view.getParent().getId() : 0;
        return new ViewDTO(
                view.getId(),
                parentId,
                view.getAuthority(),
                view.getName(),
                view.getDescription(),
                view.getLink(),
                view.isMenu()
        );
    }
}
