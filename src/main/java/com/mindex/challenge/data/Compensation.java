package com.mindex.challenge.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Compensation {
    @NonNull
    private String employeeId;
    @NonNull
    private Double salary;
    @NonNull
    private LocalDate effectiveDate;

}
