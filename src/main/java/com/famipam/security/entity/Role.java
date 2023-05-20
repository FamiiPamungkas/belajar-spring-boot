package com.famipam.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Role extends BaseEntity {

    @Column(nullable = false)
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<View> views;

}
