package com.snaacker.timeregister.utils;

import com.snaacker.timeregister.model.EmployeeDto;
import com.snaacker.timeregister.persistent.Employee;

public class Ultilities {

    public static EmployeeDto model2Dto(Employee employee){

        EmployeeDto employeeDto =  new EmployeeDto();
        employeeDto.setAddress(employee.getAddress());
        employeeDto.setAccountId(employee.getAccountId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setPhoneNumber(employee.getPhoneNumber());
        return employeeDto;
    }

    public static Employee dto2Model(EmployeeDto employeeDto) {
        Employee employee =  new Employee();
        employee.setAddress(employeeDto.getAddress());
        employee.setAccountId(employeeDto.getAccountId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        if (null != employeeDto.getId()) {
            employee.setId(employeeDto.getId());
        }
        return employee;
    }
}
