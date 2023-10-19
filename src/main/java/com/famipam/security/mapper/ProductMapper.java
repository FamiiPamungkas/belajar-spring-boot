package com.famipam.security.mapper;

import com.famipam.security.dto.ProductDTO;
import com.famipam.security.entity.Product;
import com.famipam.security.util.DateUtils;

import java.util.function.Function;

public class ProductMapper implements Function<Product, ProductDTO> {

    /**
     * Applies this function to the given argument.
     *
     * @param o the function argument
     * @return the function result
     */
    @Override
    public ProductDTO apply(Product o) {
        return new ProductDTO(
                o.getId(),
                o.getName(),
                DateUtils.formatDate(o.getCreateAt()),
                DateUtils.formatDate(o.getUpdatedAt())
        );
    }
}
