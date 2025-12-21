package com.hcc.dto;

import com.hcc.entities.User;

public class AuthCredentialRequest {

    private String username;
    private String password;
    private User user;

    private AuthCredentialRequest() {
        // default constructor for frameworks
    }

    private AuthCredentialRequest(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.user = builder.user;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public User getUser() {
        return user;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String username;
        private String password;
        private User user;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public AuthCredentialRequest build() {
            return new AuthCredentialRequest(this);
        }
    }
}