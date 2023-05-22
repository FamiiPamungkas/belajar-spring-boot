package com.famipam.security.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new LinkedHashSet<>();
        for (Role role : getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getAuthority()));
            for (View view : role.getViews()) {
                authorities.add(new SimpleGrantedAuthority(view.getAuthority()));
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
}
