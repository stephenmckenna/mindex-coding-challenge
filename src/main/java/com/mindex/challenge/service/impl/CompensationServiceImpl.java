package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    CompensationRepository compensationRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Compensation create(Compensation compInfo) throws Exception {
        LOG.info("Looking for employee "+compInfo.getEmployeeId());
        checkEmployeeIsValid(compInfo.getEmployeeId());
        return compensationRepository.insert(compInfo);
    }

    @Override
    public Compensation read(String employeeId) throws Exception {
        LOG.info("Reading compensation data for "+employeeId);
        checkEmployeeIsValid(employeeId);
        Compensation compData = compensationRepository.readByEmployeeId(employeeId);
        if(compData == null) {
            throw new Exception("No compensation data set for employeeId");
        }
        else
            return compData;
    }

    /**
     * This function checks the database to be sure that the employeeId provided is
     * valid and can return a result
     * @param employeeId
     */
    private void checkEmployeeIsValid(String employeeId) {
        if(employeeRepository.findByEmployeeId(employeeId) == null)
            throw new RuntimeException("Invalid Employee Id");
    }
}
