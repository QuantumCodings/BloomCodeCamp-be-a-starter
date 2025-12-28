package com.hcc.services;

import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.repositories.AssignmentRepository;
import com.hcc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepo;

    @Autowired
    private UserRepository userRepo;

    public List<Assignment> getAssignmentsByUser(String username) {
        Optional<User> userOpt = userRepo.findByUsername(username);
        return userOpt.map(assignmentRepo::findByUser).orElse(List.of());
    }

    public Assignment getAssignmentById(Long id) {
        return assignmentRepo.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found"));
    }

    public Assignment updateAssignment(Long id, Assignment updatedAssignment) {
        Assignment assignment = getAssignmentById(id);
        assignment.setStatus(updatedAssignment.getStatus());
        assignment.setGithubUrl(updatedAssignment.getGithubUrl());
        assignment.setBranch(updatedAssignment.getBranch());
        assignment.setReviewVideoUrl(updatedAssignment.getReviewVideoUrl());
        assignment.setCodeReviewer(updatedAssignment.getCodeReviewer());
        return assignmentRepo.save(assignment);
    }

    public Assignment createAssignment(Assignment assignment) {
        return assignmentRepo.save(assignment);
    }
}