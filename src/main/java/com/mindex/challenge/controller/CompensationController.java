package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compensation")
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    CompensationService compensationService;

    @PostMapping
    public Compensation create(@Valid @RequestBody Compensation compInfo) throws Exception {
        return compensationService.create(compInfo);
    }

    @GetMapping("/{id}")
    public Compensation read(@PathVariable String id) throws Exception {
        return compensationService.read(id);
    }
}
