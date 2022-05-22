package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.model.EmployeeDto;
import com.snaacker.timeregister.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public EmployeeDto getEmployee(@PathVariable Long id){
        return employeeService.getEmployeeById(id);
    }
//    @GetMapping("/{id}")
//    public EmployeeDto getEmployeeById(@PathVariable Long id){
//        // Not implemented yet
//        return null;
//    }
    @GetMapping("")
    public List<EmployeeDto> getListEmployee(){
        // Not implemented yet
        return null;
    }
    @PutMapping("")
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employee){
        return employeeService.createEmployee(employee);
    }

    @PostMapping("")
    public EmployeeDto editEmployee(@RequestBody EmployeeDto user){
        // Not implemented yet
        return null;
    }

    @DeleteMapping("/{id}")
    public List<EmployeeDto> deleteEmployee(@PathVariable Long id){
        // TODO: Not implemented yet
        return null;
    }
}
