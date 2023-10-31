package com.famipam.security.repository;

import com.famipam.security.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.code = :code AND p.id <> :excludeId")
    boolean existsByCode(String code, long excludeId);
}
