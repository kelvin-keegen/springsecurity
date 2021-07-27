package com.keegan.springsecurity.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Entity
@Table(name = "profiles")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AppUser implements UserDetails {

    @SequenceGenerator(
            name = "profiles_sequence",
            sequenceName = "profiles_sequence",
            allocationSize = 1
    )

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "profiles_sequence"
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String time;
    @Enumerated(EnumType.STRING)
    private UserRoles userRoles;
    private Boolean none_locked = true;
    private Boolean none_expired = true;
    private Boolean enabled = false;

    public AppUser(String firstName, String lastName, String email, String password, UserRoles userRoles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userRoles = userRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRoles.name());

        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return firstName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return none_expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return none_locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
