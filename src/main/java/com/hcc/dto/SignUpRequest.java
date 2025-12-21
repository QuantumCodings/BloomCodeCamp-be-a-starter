package com.hcc.dto;

import java.util.Date;
import java.util.Set;

public class SignUpRequest {

    private Long id;
    private String username;
    private String password;
    private Date cohortStartDate;
    private Set<String> roles;

    public SignUpRequest() {
        // Default constructor
    }

    public SignUpRequest(Long id, String username, String password, Date cohortStartDate, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cohortStartDate = cohortStartDate;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCohortStartDate() {
        return cohortStartDate;
    }

    public void setCohortStartDate(Date cohortStartDate) {
        this.cohortStartDate = cohortStartDate;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
