package com.famipam.security.service;

import com.famipam.security.entity.Product;
import com.famipam.security.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository repository;

    public Optional<Product> findById(long id) {
        return repository.findById(id);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public boolean existsByCode(String name, long excludeId) {
        return repository.existsByCode(name, excludeId);
    }

    public void save(Product product) {
        repository.save(product);
    }
}
