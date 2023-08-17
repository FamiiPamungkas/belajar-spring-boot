package com.famipam.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

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
    @Column(columnDefinition = "varchar(255) DEFAULT ''")
    private String description;
    @Column(nullable = false, columnDefinition = "varchar(255) DEFAULT ''")
    private String link;

    // jadi group_name untuk menghindari sql error
    @Column(name = "group_name", nullable = false, columnDefinition = "varchar(255) DEFAULT ''")
    private String group;
    private int seq;
    private boolean visible;

    @Column(columnDefinition = "varchar(64) DEFAULT ''")
    private String icon;

    @ManyToOne
    private Menu parent;

    @Transient
    private Set<Menu> children;
    @Transient
    private Set<String> authorities;

    public Set<Menu> getChildren() {
        return children != null ? children : new LinkedHashSet<>();
    }

    public Set<String> getAuthorities() {
        if (authorities == null) {
            return new LinkedHashSet<>(Collections.singletonList(authority));
        }
        return authorities;
    }

}
