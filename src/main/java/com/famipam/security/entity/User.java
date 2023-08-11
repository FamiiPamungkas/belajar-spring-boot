package com.famipam.security.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false)
    private String firstname;
    private String lastname;

    @Column(nullable = false, columnDefinition = "DATE")
    private Date birthdate;

    @Column(nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles != null ? roles : new LinkedHashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new LinkedHashSet<>();
        for (Role role : getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getAuthority()));
            for (Menu menu : role.getMenus()) {
                authorities.add(new SimpleGrantedAuthority(menu.getAuthority()));
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getActive();
    }

    private Set<Menu> getMenus() {
        return getRoles().stream()
                .flatMap(role -> role.getMenus().stream().filter(Menu::isVisible))
                .collect(Collectors.toSet());
    }

    public Set<Menu> getTreeMenus() {
        Set<Menu> menus = getMenus().stream().filter(x -> x.getParent() == null).collect(Collectors.toSet());

        for (Menu menu : menus) {
            menu.setChildren(getMenus().stream()
                    .filter(x -> menu.equals(x.getParent()))
                    .collect(Collectors.toSet())
            );

            for (Menu child : menu.getChildren()) {
                child.setChildren(getMenus().stream()
                        .filter(x -> child.equals(x.getParent()))
                        .collect(Collectors.toSet())
                );
            }
        }

        for (Menu menu : menus) {
            Set<String> authorities = menu.getAuthorities();
            for (Menu child : menu.getChildren()) {
                Set<String> childAuthorities = child.getAuthorities();
                authorities.add(child.getAuthority());
                for (Menu subChild : child.getChildren()) {
                    authorities.add(subChild.getAuthority());
                    childAuthorities.add(subChild.getAuthority());
                }
                child.setAuthorities(childAuthorities);
            }
            menu.setAuthorities(authorities);
        }

        return menus;
    }
}
