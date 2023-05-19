package com.famipam.security.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

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
