package com.snaacker.timeregister.service;

import com.snaacker.timeregister.FixtureTest;
import com.snaacker.timeregister.repository.EmployeeRepository;
import com.snaacker.timeregister.repository.TimesheetRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class EmployeeServiceTest extends FixtureTest {

    @Autowired EmployeeService employeeService;

    @MockBean static EmployeeRepository employeeRepository;
    @MockBean static TimesheetRecordRepository timesheetRecordRepository;
    //
    //    @Test
    //    public void getUserWithValidParametersShouldReturnSuccess() {
    //        List<Employee> listEmployee =
    //                Arrays.asList(
    //                        new Employee(
    //                                "username1",
    //                                "password1",
    //                                "accoundId1",
    //                                "firstName1",
    //                                "lastName1",
    //                                "email1",
    //                                "phoneNumber1",
    //                                "address1",
    //                                Role.EMPLOYEE),
    //                        new Employee(
    //                                "username2",
    //                                "password2",
    //                                "accountId2",
    //                                "firstName2",
    //                                "lastName2",
    //                                "email2",
    //                                "phoneNumber2",
    //                                "address2",
    //                                Role.EMPLOYEE),
    //                        new Employee(
    //                                "username3",
    //                                "password3",
    //                                "accountId3",
    //                                "firstName3",
    //                                "lastName3",
    //                                "email3",
    //                                "phoneNumber#",
    //                                "address3",
    //                                Role.EMPLOYEE));
    //        when(employeeRepository.findAll((Pageable) any())).thenReturn(new
    // PageImpl<>(listEmployee));
    //        TimeRegisterGenericResponse<EmployeeResponse> returnEmployee =
    //                employeeService.getListEmployee(OFFSET, PAGE_SIZE);
    //        assertThat(returnEmployee.getTotal()).isEqualTo(3);
    //        assertThat(returnEmployee.getOffset()).isEqualTo(OFFSET);
    //        assertThat(returnEmployee.getPageSize()).isEqualTo(PAGE_SIZE);
    //        assertThat(
    //                        returnEmployee.getGenericObject().stream()
    //                                .anyMatch(x -> x.getUsername().equals("username1")))
    //                .isTrue();
    //    }
}
