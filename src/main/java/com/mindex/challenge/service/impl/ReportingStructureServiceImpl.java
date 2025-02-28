package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure createReportingStructure(String employeeId) throws Exception {
        //Extract all employee data from the database, given the employee's ID
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        //Check if the employee exists, throw an exception if not
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }
        return calculateReports(employee);
    }

    /**
     * This method fills a ReportingStructure object for the employee in question, including their direct reports'
     * direct reports, and does so recursively so it will be scalable should any new entries enter the chain of
     * command. This function also takes into account the number of direct reports for the initial employee,
     * incrementing that value recursively as well.
     * @param employee
     * @return completely filled ReportingStructure object
     */
    private ReportingStructure calculateReports(Employee employee) {
        // Initialize return object
        ReportingStructure filledReportingStructure = new ReportingStructure();
        // Set number of reports for this employee
        filledReportingStructure.setNumberOfReports(Long.valueOf(employee.getDirectReports().size()));

        // Fill current employee's information
        filledReportingStructure.setEmployee(employee);
        // Get current employee's direct reports
        List<Employee> dirReports = employee.getDirectReports();
        // Fill direct report information to account for ONLY employee IDs being filled
        dirReports = dirReports.stream().map(x -> employeeRepository.findByEmployeeId(x.getEmployeeId())).collect(Collectors.toList());

        // For each direct report, find THEIR reports using recursion, simultaneously incrementing the number of total reports found
        for(Employee report: dirReports)
            filledReportingStructure.setNumberOfReports(filledReportingStructure.getNumberOfReports() + calculateReports(report).getNumberOfReports());

        /*
          Set direct reports of the initial employee AFTER the recursion, to allow for tail recursion.
          This was required because I am actively building the report structure from the bottom up, which
          makes it easier to keep track of direct reports and store employee information.
         */
        employee.setDirectReports(dirReports);

        return filledReportingStructure;
    }

}
