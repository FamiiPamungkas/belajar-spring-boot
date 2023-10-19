package com.famipam.security.repository;

import com.famipam.security.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.name = :name AND p.id <> :excludeId")
    boolean existsByName(String name, long excludeId);
}
