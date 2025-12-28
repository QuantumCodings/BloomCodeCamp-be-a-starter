package com.hcc.dto;

import com.hcc.entities.Assignment;
import com.hcc.enums.AssignmentEnum;
import com.hcc.enums.AssignmentStatusEnum;
import lombok.Getter;
import lombok.Setter;

public class AssignmentResponseDto {
    @Setter
    @Getter
    private Assignment assignment;
    @Getter
    private final AssignmentEnum[] assignmentEnums = AssignmentEnum.values();
    private final AssignmentStatusEnum[] statusEnums = AssignmentStatusEnum.values();

    public AssignmentResponseDto(Assignment assignment) {
        this.assignment = assignment;
    }

    public AssignmentStatusEnum[] getAssignmentStatusEnums() { return statusEnums; }
}