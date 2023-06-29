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
    @Column(columnDefinition = "datetime default current_timestamp()")
    private Date createAt;
    @Column(columnDefinition = "datetime default current_timestamp()")
    private Date updatedAt;

    @PrePersist
    protected void prePersist() {
        updatedAt = new Date();

        if (active == null) {
            active = true;
        }
        if (createAt == null) {
            createAt = new Date();
        }
    }
}
