package com.hcc.dto;

import com.hcc.entities.User;

public class AssignmentResponseDto {

    private Long id;
    private Integer number;
    private User user;
    private String githubUrl;

    private AssignmentResponseDto() {
        // private constructor
    }

    public Long getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public User getUser() {
        return user;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public static AssignmentResponseDtoBuilder builder() {
        return new AssignmentResponseDtoBuilder();
    }

    public static class AssignmentResponseDtoBuilder {
        private Long id;
        private Integer number;
        private User user;
        private String githubUrl;

        public AssignmentResponseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AssignmentResponseDtoBuilder number(Integer number) {
            this.number = number;
            return this;
        }

        public AssignmentResponseDtoBuilder user(User user) {
            this.user = user;
            return this;
        }

        public AssignmentResponseDtoBuilder githubUrl(String githubUrl) {
            this.githubUrl = githubUrl;
            return this;
        }

        public AssignmentResponseDto build() {
            AssignmentResponseDto dto = new AssignmentResponseDto();
            dto.id = this.id;
            dto.number = this.number;
            dto.user = this.user;
            dto.githubUrl = this.githubUrl;
            return dto;
        }
    }
}