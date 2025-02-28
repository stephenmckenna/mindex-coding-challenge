package com.mindex.challenge.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
    Using Lombok annotations to include setters, getters, and necessary constructors
    allows for cleaner, more concise code
 */

@Data
@AllArgsConstructor
public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String position;
    private String department;
    private List<Employee> directReports;

    public Employee() {
        this.directReports = new ArrayList<>();
    }

}
