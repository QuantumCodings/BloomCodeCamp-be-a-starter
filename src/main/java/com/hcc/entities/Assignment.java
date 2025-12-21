package com.hcc.entities;
import javax.persistence.*;

@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private Integer number;
    private String githubUrl;
    private String branch;
    private String reviewVideoUrl;

    @ManyToOne
    private User user;

    @ManyToOne
    private User codeReviewer;

    public Assignment() {}

    public Assignment(String status, Integer number, String githubUrl, String branch, String reviewVideoUrl, User user) {
        this.status = status;
        this.number = number;
        this.githubUrl = githubUrl;
        this.branch = branch;
        this.reviewVideoUrl = reviewVideoUrl;
        this.user = user;
    }
}