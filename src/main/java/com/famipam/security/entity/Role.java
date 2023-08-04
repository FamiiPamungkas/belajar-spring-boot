package com.famipam.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String authority;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Menu> menus;

    @Override
    protected void prePersist() {
        super.prePersist();
        authority = StringUtils.upperCase(StringUtils.replace(name, " ", "_"));
        authority = authority.replaceAll("[^A-Za-z0-9_]", "");
    }
}
