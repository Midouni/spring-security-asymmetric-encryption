package com.midouni.app.user;

import com.midouni.app.common.BaseEntity;
import com.midouni.app.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "USER")
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Min(value = 8)
    @Column(name = "PASSWORD",nullable = false)
    private String password;

    @Column(name = "PHONE_NUMNER", nullable = false,unique = true)
    private String phoneNumber;

    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    @Column(name = "ENABLED")
    private boolean enabled;

    @Column(name = "IS_ACCOUNT_LOCKED")
    private boolean locked;

    @Column(name = "IS_CREDENTIAL_EXPIRED")
    private boolean credentialsExpired;

    @Column(name = "IS_ACCOUNT_EXPIRED")
    private boolean expired;

    @Column(name = "IS_PHONE_VERIFIED")
    private boolean phoneVerified;

    @Column(name = "IS_EMAIL_VERIFIED")
    private boolean emailVerified;

    @ManyToMany(
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLES_ID")
            }
    )
    private List<Role> roles;
    //private Set<Role> roles = new HashSet<>(); // to avoid duplicate




    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.expired;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
