package com.hcc.controllers;

import com.hcc.entities.Assignment;
import com.hcc.model.AssignmentResponseDto;
import com.hcc.services.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping
    public List<AssignmentResponseDto> fetchUserAssignments() {
        return assignmentService.getAssignmentsByUser();
    }

    @GetMapping("/{assignmentId}")
    public AssignmentResponseDto fetchAssignmentById(
            @PathVariable Long assignmentId) {
        return assignmentService.getAssignmentById(assignmentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Assignment createAssignment(
            @RequestBody Assignment assignment) {
        return assignmentService.addAssignment(assignment);
    }

    @PutMapping("/{assignmentId}")
    public AssignmentResponseDto updateAssignment(
            @PathVariable Long assignmentId,
            @RequestBody Assignment assignment) {
        return assignmentService.updateAssignment(assignment, assignmentId);
    }
}