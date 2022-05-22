package com.snaacker.timeregister.service;

import com.snaacker.timeregister.model.EmployeeDto;
import com.snaacker.timeregister.persistent.Employee;
import com.snaacker.timeregister.repository.EmployeeRepository;
import com.snaacker.timeregister.utils.Ultilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeDto getEmployeeById(long id){
        Employee employee = employeeRepository.getById(id);
        return Ultilities.model2Dto(employee);
    }

    public List<EmployeeDto> getListEmployee(){
        // Not implemented yet
        // TODO: implement pagination + sortable
        return null;
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto){
        Employee employee = employeeRepository.save(Ultilities.dto2Model(employeeDto));
        return Ultilities.model2Dto(employee);
    }

    public EmployeeDto editEmployee(Long id, EmployeeDto user){
        // TODO: Not implemented yet
        return null;
    }

    public void deleteEmployee(long id){
        Employee deleteEmployee = employeeRepository.getById(id);
        employeeRepository.delete(deleteEmployee);
        // TODO: return list of employee at first page (do this after introduce pagination)
    }
}
