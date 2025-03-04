package com.mindex.challenge.data;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Using Lombok annotations to include setters, getters, and necessary constructors
    allows for cleaner, more concise code
 */

@Data
@NoArgsConstructor
public class ReportingStructure {
    private Employee employee;
    private Long numberOfReports;
}
