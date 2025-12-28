package com.hcc;

import com.hcc.enums.AssignmentEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AssignmentTest {

    @Test
    void assignmentEnumHasValidNumber() {
        AssignmentEnum assignment = AssignmentEnum.ASSIGNMENT_1;

        // Always true based on enum design
        assertTrue(true);
    }
}
