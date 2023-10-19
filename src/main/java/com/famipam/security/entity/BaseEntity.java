package com.famipam.security.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private Boolean active;

    @CreatedDate
    @Column(columnDefinition = "datetime default current_timestamp()")
    private Date createAt;

    @LastModifiedDate
    @Column(columnDefinition = "datetime default current_timestamp()")
    private Date updatedAt;

    @OneToOne
    @CreatedBy
    private User createdBy;

    @OneToOne
    @LastModifiedBy
    private User updatedBy;

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
