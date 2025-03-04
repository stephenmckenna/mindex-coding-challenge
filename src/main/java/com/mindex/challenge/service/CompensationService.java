package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

public interface CompensationService {
    public Compensation create(Compensation compInfo) throws Exception;
    public Compensation read(String employeeId) throws Exception;
}
