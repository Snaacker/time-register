package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.annotation.AllowAdmin;
import com.snaacker.timeregister.exception.TimeRegisterBadRequestException;
import com.snaacker.timeregister.exception.TimeRegisterException;
import com.snaacker.timeregister.exception.TimeRegisterObjectNotFoundException;
import com.snaacker.timeregister.model.request.EmployeeRequest;
import com.snaacker.timeregister.model.request.TimeRecordRequest;
import com.snaacker.timeregister.model.response.EmployeeResponse;
import com.snaacker.timeregister.model.response.TimeRecordResponse;
import com.snaacker.timeregister.model.response.TimeRegisterGenericResponse;
import com.snaacker.timeregister.model.response.UserMultipleTimeRecordResponse;
import com.snaacker.timeregister.model.response.UserSingleTimeRecordResponse;
import com.snaacker.timeregister.service.EmployeeService;
import com.snaacker.timeregister.service.TimesheetRecordService;
import com.snaacker.timeregister.utils.Constants;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final TimesheetRecordService timesheetRecordService;

    @Autowired
    public EmployeeController(
            final EmployeeService employeeService,
            final TimesheetRecordService timesheetRecordService) {
        this.employeeService = employeeService;
        this.timesheetRecordService = timesheetRecordService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getUserById(@PathVariable Long id)
            throws TimeRegisterObjectNotFoundException {
        return new ResponseEntity<>(employeeService.getUserById(id), HttpStatus.OK);
    }

    @AllowAdmin
    @GetMapping("")
    public ResponseEntity<TimeRegisterGenericResponse<EmployeeResponse>> getListUser(
            @RequestParam(name = "startPage", defaultValue = Constants.DEFAULT_START_PAGE + "")
                    int startPage,
            @RequestParam(name = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE + "")
                    int pageSize) {
        return new ResponseEntity<>(
                employeeService.getListEmployee(startPage, pageSize), HttpStatus.OK);
    }

    @AllowAdmin
    @PutMapping("")
    public ResponseEntity<EmployeeResponse> createUser(@RequestBody EmployeeRequest user)
            throws TimeRegisterBadRequestException {
        return new ResponseEntity<>(employeeService.createEmployee(user), HttpStatus.CREATED);
    }

    @AllowAdmin
    @PostMapping("/{id}")
    public ResponseEntity<EmployeeResponse> editUser(
            @PathVariable long id, @RequestBody EmployeeRequest user)
            throws TimeRegisterBadRequestException {
        return new ResponseEntity<>(employeeService.editUser(id, user), HttpStatus.OK);
    }

    @AllowAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id)
            throws TimeRegisterBadRequestException {
        return new ResponseEntity<>(employeeService.deleteUser(id), HttpStatus.OK);
    }

    @AllowAdmin
    // TODO: return list registed time of user
    @GetMapping("/{id}/registration")
    public ResponseEntity<TimeRegisterGenericResponse<TimeRecordResponse>>
            getAllRegisteredTimeByUser(
                    @RequestParam(
                                    name = "startPage",
                                    defaultValue = Constants.DEFAULT_START_PAGE + "")
                            int startPage,
                    @RequestParam(
                                    name = "pageSize",
                                    defaultValue = Constants.DEFAULT_PAGE_SIZE + "")
                            int pageSize,
                    @PathVariable long id,
                    @RequestParam Date fromDate,
                    @RequestParam Date toDate)
                    throws TimeRegisterObjectNotFoundException {

        return new ResponseEntity<>(
                timesheetRecordService.getTimeRecordByUserId(
                        id, fromDate, toDate, startPage, pageSize),
                HttpStatus.OK);
    }

    @PutMapping("/{id}/registration")
    public ResponseEntity<UserMultipleTimeRecordResponse> addTimeRecord(
            @PathVariable Long id, @RequestBody TimeRecordRequest timeRecordRequest)
            throws TimeRegisterException {
        return new ResponseEntity<>(
                employeeService.addTimeRecord(id, timeRecordRequest), HttpStatus.CREATED);
    }

    @PostMapping("/{user_id}/time_record/{record_id}")
    public ResponseEntity<UserSingleTimeRecordResponse> editTimeRecord(
            @PathVariable Long user_id,
            @PathVariable Long record_id,
            @RequestBody TimeRecordRequest timeRecord) {
        return new ResponseEntity<>(
                employeeService.editTimeRecord(user_id, record_id, timeRecord),
                HttpStatus.ACCEPTED);
    }

    @AllowAdmin
    @PostMapping("/{user_id}/confirmation")
    public ResponseEntity<String> approveTimeSheet(
            @PathVariable Long user_id, @RequestBody List<Long> listTimeSheetId) {
        employeeService.approvedTimesheet(user_id, listTimeSheetId);
        // TODO: return user response
        return new ResponseEntity<>("Success approved", HttpStatus.OK);
    }

    @AllowAdmin
    @PostMapping("/{user_id}/restaurant/{restaurant_id}")
    public ResponseEntity<EmployeeResponse> assignRestaurant(
            @PathVariable Long user_id, @PathVariable Long restaurant_id) {
        return new ResponseEntity<>(
                employeeService.assignRestaurant(user_id, restaurant_id), HttpStatus.OK);
    }
}
