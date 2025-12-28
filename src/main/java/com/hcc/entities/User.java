package com.hcc.entities;

import com.hcc.enums.AuthorityEnum;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    // Getters and setters
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private LocalDate cohortStartDate;

    @Getter
    @Column(unique = true)
    private String username;

    @Getter
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Authority> authorities = new ArrayList<>();

    public User() {}

    public User(LocalDate cohortStartDate, String username, String password) {
        this.cohortStartDate = cohortStartDate;
        this.username = username;
        this.password = password;
    }

    public User(Date cohortStartDate, String username, String encode) {


    }

    public void setId(Long id) { this.id = id; }

    public void setCohortStartDate(LocalDate cohortStartDate) { this.cohortStartDate = cohortStartDate; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }
    public List<Authority> getAuthoritiesList() { return authorities; }
    public void setAuthorities(List<Authority> authorities) { this.authorities = authorities; }

    // UserDetails Overrides
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        for (Authority auth : authorities) {
            roles.add(auth);
        }
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
