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
    private String description;
    @Column(nullable = false)
    private String link;
    @Column(name = "group_name")
    private String group;
    private int seq;
    private boolean showOnNav;

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
