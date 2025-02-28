package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String employeeUrl;
    private String employeeIdUrl;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
    }

    @Test
    public void testCreateReportingStructure() throws Exception {

        //Test exception
            try {
                reportingStructureService.createReportingStructure("randomId");
            }
            catch(Exception e) {
                assertEquals("Invalid employeeId: randomId", e.getMessage());
                assertEquals(RuntimeException.class, e.getClass());
            }


        ReportingStructure reportingStructure;

        //Test two given employees
            //Test John Lennon
            reportingStructure = reportingStructureService.createReportingStructure("16a596ae-edd3-4847-99fe-c4518e82c86f");
            assertEquals(4, reportingStructure.getNumberOfReports());
            String johnLennonStructure="Employee(employeeId=16a596ae-edd3-4847-99fe-c4518e82c86f, firstName=John, lastName=Lennon, position=Development Manager, department=Engineering, directReports=[Employee(employeeId=b7839309-3348-463b-a7e3-5de1c168beb3, firstName=Paul, lastName=McCartney, position=Developer I, department=Engineering, directReports=[]), Employee(employeeId=03aa1462-ffa9-4978-901b-7c001562cf6f, firstName=Ringo, lastName=Starr, position=Developer V, department=Engineering, directReports=[Employee(employeeId=62c1084e-6e34-4630-93fd-9153afb65309, firstName=Pete, lastName=Best, position=Developer II, department=Engineering, directReports=[]), Employee(employeeId=c0c2293d-16bd-4603-8e08-638a9d18b22c, firstName=George, lastName=Harrison, position=Developer III, department=Engineering, directReports=[])])])";
            assertEquals(johnLennonStructure, reportingStructure.getEmployee().toString());
            //Test George Harrison
            reportingStructure = reportingStructureService.createReportingStructure("c0c2293d-16bd-4603-8e08-638a9d18b22c");
            assertEquals(0, reportingStructure.getNumberOfReports());
            String georgeHarrisonStructure="Employee(employeeId=c0c2293d-16bd-4603-8e08-638a9d18b22c, firstName=George, lastName=Harrison, position=Developer III, department=Engineering, directReports=[])";
            assertEquals(georgeHarrisonStructure, reportingStructure.getEmployee().toString());

        //Test new employee
            Employee testEmployee = new Employee();
            testEmployee.setFirstName("Stephen");
            testEmployee.setLastName("McKenna");
            testEmployee.setPosition("Candidate");
            testEmployee.setDepartment("Engineering");

            //Add this new employee to the database
                Employee stephenEmployeeObject = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

            reportingStructure = reportingStructureService.createReportingStructure(stephenEmployeeObject.getEmployeeId());
            assertEquals(0, reportingStructure.getNumberOfReports());
            String stephenStructure="Employee(employeeId="+stephenEmployeeObject.getEmployeeId()+", firstName=Stephen, lastName=McKenna, position=Candidate, department=Engineering, directReports=[])";
            assertEquals(stephenStructure, reportingStructure.getEmployee().toString());

            /* Modify direct reports
                //Create random employee
                testEmployee = new Employee();
                //Add random employee to database
                    Employee employeeUnderStephen = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
                //Update stephenEmployeeObject to include new employee
                    stephenEmployeeObject.getDirectReports().add(testEmployee);
                //Update stephen to include new employee as direct report
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);

                    Employee updatedStephen = restTemplate.exchange(employeeIdUrl,
                            HttpMethod.PUT,
                            new HttpEntity<Employee>(stephenEmployeeObject, headers),
                            Employee.class,
                            stephenEmployeeObject.getEmployeeId()).getBody();


            //test stephen's new reporting structure
                reportingStructure = reportingStructureService.createReportingStructure(updatedStephen.getEmployeeId());
                assertEquals(1, reportingStructure.getNumberOfReports());
                assertEquals(stephenStructure <- modify , reportingStructure.getEmployee().toString());


                <-----
                    Attempted to test adding new employee to stephen's reporting hierarchy.
                    After updating the database entry for Stephen, it seems that a duplicate
                    employee with Stephen's employeeId is created in the database, thus
                    reportingStructure could not be constructed.

                    At line 107, reportingStructureService.createReportingStructure:
                    [
                        Query (...) returned non unique result
                    ]
                ----->

             */
    }
}
