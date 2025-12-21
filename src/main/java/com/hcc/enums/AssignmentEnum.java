package com.hcc.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AssignmentEnum {
    ASSIGNMENT_1(1, "jungle"),
    ASSIGNMENT_2(2, "canvas"),
    ASSIGNMENT_3(3, "update"),
    ASSIGNMENT_4(4, "degree"),
    ASSIGNMENT_5(5, "harass"),
    ASSIGNMENT_6(6, "reward"),
    ASSIGNMENT_7(7, "Europe"),
    ASSIGNMENT_8(8, "impact"),
    ASSIGNMENT_9(9, "appear"),
    ASSIGNMENT_10(10, "ensure"),
    ASSIGNMENT_11(11, "ladder"),
    ASSIGNMENT_12(12, "desire"),
    ASSIGNMENT_13(13, "matter"),
    ASSIGNMENT_14(14, "cellar");

    private int assignmentNumber;
    private String assignmentName;

    AssignmentEnum(int assignmentNumber, String assignmentName) {
        this.assignmentNumber = assignmentNumber;
        this.assignmentName = assignmentName;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public int getAssignmentNumber() {
        return assignmentNumber;
    }
}
