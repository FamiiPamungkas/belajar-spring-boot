package com.famipam.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Menu extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String authority;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private String link;
    @Column(name = "group_name")
    private String group;

    private boolean showOnNav;

    @ManyToOne
    private Menu parent;

}
