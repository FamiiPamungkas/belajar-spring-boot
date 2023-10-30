package com.famipam.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product implements Serializable {

    public Product(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private String category;
    private double price;

    @CreatedDate
    private Date createAt;

    @LastModifiedDate
    private Date updatedAt;

    @OneToOne
    @CreatedBy
    private User createdBy;

    @OneToOne
    @LastModifiedBy
    private User updatedBy;

    public Map<String, Object> toMap() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", this.getId());
        userMap.put("code", this.getCode());
        userMap.put("name", this.getName());
        userMap.put("category", this.getCategory());
        userMap.put("price", this.getPrice());

        return userMap;
    }
}
