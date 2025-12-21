package com.hcc.dto;

import java.util.List;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String username;
    private List<String> roles;

    private JwtResponse(Builder builder) {
        this.token = builder.token;
        this.username = builder.username;
        this.roles = builder.roles;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String token;
        private String username;
        private List<String> roles;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder roles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public JwtResponse build() {
            return new JwtResponse(this);
        }
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
