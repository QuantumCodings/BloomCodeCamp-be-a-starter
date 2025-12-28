package com.hcc.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AssignmentEnum {
    ASSIGNMENT_1(1, "Spring Boot Service"),
    ASSIGNMENT_2(2, "Spring Boot Data JPA"),
    ASSIGNMENT_3(3, "Spring Boot Postgresql"),
    ASSIGNMENT_4(4, "Docker Compose Setup"),
    ASSIGNMENT_5(5, "React Frontend Hooks"),
    ASSIGNMENT_6(6, "Python Problems"),
    ASSIGNMENT_7(7, "Assembly Boot Sector Hello World"),
    ASSIGNMENT_8(8, "CTF Buffer Overflow"),
    ASSIGNMENT_9(9, "Docker Build"),
    ASSIGNMENT_10(10, "AWS Buckets"),
    ASSIGNMENT_11(11, "Hashmaps and Sets"),
    ASSIGNMENT_12(12, "Computational Counting"),
    ASSIGNMENT_13(13, "Data Driven Websites"),
    ASSIGNMENT_14(14, "SQL Reporting");

    private final int assignmentNumber;
    private final String assignmentName;

    AssignmentEnum(int assignmentNumber, String assignmentName) {
        this.assignmentNumber = assignmentNumber;
        this.assignmentName = assignmentName;
    }

}
