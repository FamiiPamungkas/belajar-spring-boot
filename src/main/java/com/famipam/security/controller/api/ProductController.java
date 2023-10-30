package com.famipam.security.controller.api;

import com.famipam.security.dto.ProductDTO;
import com.famipam.security.entity.Product;
import com.famipam.security.exception.ExpectedException;
import com.famipam.security.exception.NotFoundException;
import com.famipam.security.mapper.ProductMapper;
import com.famipam.security.model.ApiResponse;
import com.famipam.security.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController extends BaseController {

    private final ProductService productService;
    private final ProductMapper productMapper = new ProductMapper();

    @GetMapping
    public ResponseEntity<List<ProductDTO>> list() {
        List<ProductDTO> users = productService.findAll()
                .stream()
                .map(productMapper)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> product(
            @PathVariable long id
    ) {
        Product product = productService.findById(id).orElseThrow(() -> new NotFoundException("Product [" + id + "] not found."));
        return ResponseEntity.ok(productMapper.apply(product));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> storeProduct(
            @RequestBody ProductDTO productDTO
    ) {

        boolean exists = productService.existsByName(productDTO.name(), productDTO.id());
        if (exists) {
            throw new ExpectedException("Product [" + productDTO.name() + "] is already exists");
        }

        Product product = Product.builder()
                .code(productDTO.code())
                .name(productDTO.name())
                .category(productDTO.category())
                .price(productDTO.price())
                .build();

        productService.save(product);
        return ResponseEntity.ok(ApiResponse.builder()
                .status(SUCCESS_CODE)
                .message(SUCCESS)
                .data(productMapper.apply(product))
                .build()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateProduct(
            @RequestBody ProductDTO productDTO
    ) {
        Product product = productService.findById(productDTO.id())
                .orElseThrow(() -> new NotFoundException("Product [" + productDTO.id() + "] not found."));

        boolean exists = productService.existsByName(productDTO.name(), productDTO.id());
        if (exists) {
            throw new ExpectedException("Product [" + productDTO.name() + "] is already exists");
        }

        product.setName(productDTO.name());
        product.setCode(productDTO.code());
        product.setCategory(productDTO.category());
        product.setPrice(productDTO.price());
        productService.save(product);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(SUCCESS_CODE)
                .message(SUCCESS)
                .data(productMapper.apply(product))
                .build()
        );
    }

    @GetMapping("/test")
    public ResponseEntity<ApiResponse> test(
    ) {
        Product product1 = productService.findById(1L).orElse(null);
        Product product2 = productService.findById(2L).orElse(null);
        String diff = "";
        if (product1 != null && product2 != null) {
            Map<String, Object> map1 = product1.toMap();
            Map<String, Object> map2 = product2.toMap();

            diff = map1.entrySet().stream()
                    .filter(m -> !m.getValue().equals(map2.get(m.getKey())))
                    .map(v -> v.getKey() + " = " + map2.get(v.getKey()).toString() + " -> " + v.getValue().toString())
                    .collect(Collectors.joining(";"));
            System.out.println("-> diff = " + diff);
        }

        return ResponseEntity.ok(ApiResponse.builder()
                .status(SUCCESS_CODE)
                .message(diff)
                .build()
        );
    }

}
