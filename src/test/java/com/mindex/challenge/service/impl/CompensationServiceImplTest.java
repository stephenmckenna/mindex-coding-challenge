package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CompensationServiceImplTest {

    @Autowired
    private CompensationService compensationService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testCreateCompensation() throws Exception {
        Compensation compBody = new Compensation("TestId", 12345.0, LocalDate.now());

        //Include failing checkEmployeeIsValid check
        try {
            compensationService.create(compBody);
        }
        catch (Exception e) {
            assertEquals("Invalid Employee Id", e.getMessage());
            assertEquals(RuntimeException.class, e.getClass());
        }

        //Mock to get past checkEmployeeIsValid check
            when(employeeRepository.findByEmployeeId(any())).thenReturn(new Employee());

        Compensation returnedComp = compensationService.create(compBody);

        assertNotNull(returnedComp);
        assertEquals(compBody.toString(), returnedComp.toString());
    }

    @Test
    public void testReadCompensation() throws Exception {
        //Test no data exception
        /*
            Attempted to add a new employee to the database but was getting errors.
            Would love to talk about + debug it with you all!

        Employee employee = new Employee();
        Employee returnedEmployee = employeeService.create(employee);
        String employeeId = returnedEmployee.getEmployeeId();

        try {
            compensationService.read(employeeId);
        }
        catch (Exception e) {
            assertEquals("No compensation data set for Employee Id", e.getMessage());
            assertEquals(RuntimeException.class, e.getClass());
        }
         */

        //Mock to get past checkEmployeeIsValid check
        //* - checkEmployeeIsValid checked in testCreateCompensation()
        when(employeeRepository.findByEmployeeId(any())).thenReturn(new Employee());

        Compensation compBody = new Compensation("TestId2", 12345.0, LocalDate.now());
        //add comp object to db
        compensationService.create(compBody);

        Compensation returnedComp = compensationService.read("TestId2");
        assertEquals(compBody.toString(), returnedComp.toString());


    }

}
