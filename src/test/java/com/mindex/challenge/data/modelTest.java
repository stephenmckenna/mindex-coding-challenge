package com.mindex.challenge.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class modelTest {
    @Test
    public void testReportingStructure() {
        //no args constructor, toString
        ReportingStructure rs = new ReportingStructure();
        assertEquals("ReportingStructure(employee=null, numberOfReports=null)", rs.toString());

        // test setters and getters
        Employee e = new Employee();
        rs.setEmployee(e);
        rs.setNumberOfReports(1L);

        assertEquals(e.toString(), rs.getEmployee().toString());
        assertEquals(1L, rs.getNumberOfReports());

    }

    @Test
    public void testEmployee() {
        //no args constructor, toString
        Employee e = new Employee();
            //get id
                String id = e.getEmployeeId();
        assertEquals("Employee(employeeId="+id+", firstName=null, lastName=null, position=null, department=null, directReports=[])", e.toString());

        //test setters and getters
        e.setFirstName("Stephen");
        e.setLastName("McKenna");
        e.setPosition("Candidate");
        e.setDepartment("Engineering");
        assertEquals("Stephen", e.getFirstName());
        assertEquals("McKenna", e.getLastName());
        assertEquals("Candidate", e.getPosition());
        assertEquals("Engineering", e.getDepartment());

    }

    @Test
    public void testCompensation() {
        //All args constructor, toString
        Compensation comp = new Compensation("TestId", 12345.0, LocalDate.now());
        assertEquals("Compensation(employeeId=TestId, salary=12345.0, effectiveDate=2025-03-03)", comp.toString());
        assertEquals("TestId", comp.getEmployeeId());
        assertEquals(12345.0, comp.getSalary());
        assertEquals(LocalDate.now(), comp.getEffectiveDate());
    }
}
