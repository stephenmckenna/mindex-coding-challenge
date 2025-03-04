package com.mindex.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;


import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;



import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
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

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompensationControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private Compensation compBody;

    @InjectMocks
    private CompensationController compensationController;

    @Mock
    CompensationService compensationService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(compensationController).build();

        mapper = new ObjectMapper();
        mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        compBody = new Compensation("testId", 12345.0, this.mapper.convertValue("2000-10-08", LocalDate.class));
    }

    @Test
    public void testCreateCompensation() throws Exception {
        when(compensationService.create(any())).thenReturn(compBody);

        this.mockMvc.perform(post("/compensation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(compBody)))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadCompensation() throws Exception {
        when(compensationService.read(any())).thenReturn(compBody);

        this.mockMvc.perform(get("/compensation/{}","testId"))
                .andExpect(status().isOk());
    }
}
