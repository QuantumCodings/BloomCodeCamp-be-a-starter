package com.hcc.entities;

import javax.persistence.*;

@Entity
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;

    @ManyToOne
    private User user;

    public Authority() {}

    public Authority(String authority) {
        this.authority = authority;
    }
}
