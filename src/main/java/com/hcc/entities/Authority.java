package com.hcc.entities;

import com.hcc.enums.AuthorityEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authority;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Authority() {}

    public Authority(String authority) {
        this.authority = authority;
    }

    public Authority(String name, User learner) {

    }
}
