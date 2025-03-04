package com.mindex.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private EmployeeController controller;

    @Mock
    EmployeeService employeeService;

    @Mock
    ReportingStructureService reportingStructureService;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    Employee testEmployee = new Employee("testId", "Stephen", "McKenna", "Candidate", "Engineering", new ArrayList<>());

    @Test
    public void testCreateEmployee() throws Exception {
        when(employeeService.create(any())).thenReturn(testEmployee);
;
        this.mockMvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content((new ObjectMapper()).writeValueAsString(testEmployee)))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadEmployee() throws Exception {
        when(employeeService.read(any())).thenReturn(testEmployee);

        this.mockMvc.perform(get("/employee/{}", "testId"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        when(employeeService.update(any())).thenReturn(testEmployee);

        this.mockMvc.perform(put("/employee/{}", "testId")
                .contentType(MediaType.APPLICATION_JSON)
                .content((new ObjectMapper()).writeValueAsString(testEmployee)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetReportingStructure() throws Exception {
        when(reportingStructureService.createReportingStructure(any())).thenReturn(new ReportingStructure());

        this.mockMvc.perform(get("/employee/reportingStructure/{}", "testId"))
                .andExpect(status().isOk());
    }

}
