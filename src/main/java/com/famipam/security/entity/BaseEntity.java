package com.famipam.security.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "sequence")
    @EqualsAndHashCode.Include
    private Long id;

    private Boolean active;
    private Date createAt;
    private Date updatedAt;

    @PrePersist
    private void prePersist() {
        updatedAt = new Date();

        if (active == null) {
            active = true;
        }
        if (createAt == null) {
            createAt = new Date();
        }
    }
}
