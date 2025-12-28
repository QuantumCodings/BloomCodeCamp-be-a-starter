package com.hcc.controllers;

import com.hcc.entities.Assignment;
import com.hcc.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping
    public List<Assignment> getAssignmentsByUser(@AuthenticationPrincipal UserDetails userDetails) {
        return assignmentService.getAssignmentsByUser(userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public Assignment getAssignmentById(@PathVariable Long id) {
        return assignmentService.getAssignmentById(id);
    }

    @PutMapping("/{id}")
    public Assignment updateAssignment(@PathVariable Long id, @RequestBody Assignment assignment) {
        return assignmentService.updateAssignment(id, assignment);
    }

    @PostMapping
    public Assignment createAssignment(@RequestBody Assignment assignment) {
        return assignmentService.createAssignment(assignment);
    }
}