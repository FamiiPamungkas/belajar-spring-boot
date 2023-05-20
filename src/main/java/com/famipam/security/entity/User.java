package com.famipam.security.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@TableGenerator(
        name = "sequence",
        table = "sequence",
        pkColumnName = "sequence_name",
        valueColumnName = "next_val",
        allocationSize = 1
)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false)
    private String firstname;
    private String lastname;

    @Column(nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles()
                .stream()
                .flatMap(role -> role.getViews().stream())
                .map(view ->
                        new SimpleGrantedAuthority(view.getAuthority())
                ).collect(Collectors.toSet());
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
        return true;
    }
}
