package com.famipam.security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "sequence")
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;

    @ManyToMany
    Set<View> views;

}
