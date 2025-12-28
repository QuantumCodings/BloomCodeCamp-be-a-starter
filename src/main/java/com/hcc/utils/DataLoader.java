package com.hcc.utils;

import com.hcc.entities.Assignment;
import com.hcc.entities.Authority;
import com.hcc.entities.User;
import com.hcc.enums.AssignmentStatusEnum;
import com.hcc.enums.AuthorityEnum;
import com.hcc.repositories.AssignmentRepository;
import com.hcc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AssignmentRepository assignmentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create users
        User learner = new User(LocalDate.of(2025, 1, 1), "learner1", passwordEncoder.encode("password123"));
        User reviewer = new User(LocalDate.of(2025, 1, 1), "reviewer1", passwordEncoder.encode("password123"));
        User admin = new User(LocalDate.of(2025, 1, 1), "admin1", passwordEncoder.encode("password123"));

        userRepo.saveAll(List.of(learner, reviewer, admin));

        // Add authorities
        Authority learnerAuth = new Authority(AuthorityEnum.ROLE_LEARNER.name(), learner);
        Authority reviewerAuth = new Authority(AuthorityEnum.ROLE_REVIEWER.name(), reviewer);
        Authority adminAuth = new Authority(AuthorityEnum.ROLE_ADMIN.name(), admin);

        learner.setAuthorities(List.of(learnerAuth));
        reviewer.setAuthorities(List.of(reviewerAuth));
        admin.setAuthorities(List.of(adminAuth));

        userRepo.saveAll(List.of(learner, reviewer, admin));

        // Create assignments for learner
        Assignment assignment1 = new Assignment(
                AssignmentStatusEnum.PENDING_SUBMISSION.getStatus(),
                1,
                "https://github.com/learner1/assignment1",
                "main",
                null,
                learner
        );

        Assignment assignment2 = new Assignment(
                AssignmentStatusEnum.IN_REVIEW.getStatus(),
                2,
                "https://github.com/learner1/assignment2",
                "dev",
                "https://video.review/assignment2",
                learner
        );

        assignment2.setCodeReviewer(reviewer); // reviewer assigned

        assignmentRepo.saveAll(List.of(assignment1, assignment2));

        System.out.println("✅ DataLoader: Users and assignments initialized successfully!");
    }
}