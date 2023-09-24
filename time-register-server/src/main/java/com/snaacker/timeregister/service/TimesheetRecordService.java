package com.snaacker.timeregister.service;

import com.snaacker.timeregister.exception.TimeRegisterObjectNotFoundException;
import com.snaacker.timeregister.model.response.TimeRecordResponse;
import com.snaacker.timeregister.model.response.TimeRegisterGenericResponse;
import com.snaacker.timeregister.persistent.Employee;
import com.snaacker.timeregister.persistent.TimesheetRecord;
import com.snaacker.timeregister.repository.EmployeeRepository;
import com.snaacker.timeregister.repository.TimesheetRecordRepository;
import com.snaacker.timeregister.utils.DtoTransformation;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TimesheetRecordService {

    private final TimesheetRecordRepository timesheetRecordRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public TimesheetRecordService(
            final TimesheetRecordRepository timesheetRecordRepository,
            final EmployeeRepository employeeRepository) {
        this.timesheetRecordRepository = timesheetRecordRepository;
        this.employeeRepository = employeeRepository;
    }

    public TimeRegisterGenericResponse getTimeRecordByUserId(
            long userId, Date fromDate, Date toDate, int startPage, int pageSize)
            throws TimeRegisterObjectNotFoundException {
        employeeRepository
                .findById(userId)
                .orElseThrow(() -> new TimeRegisterObjectNotFoundException("User does not exist"));
        Employee employee =
                employeeRepository
                        .findById(userId)
                        .orElseThrow(
                                () -> new TimeRegisterObjectNotFoundException("User not found"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        List<TimeRecordResponse> listTimeRecordResponse =
                timesheetRecordRepository
                        .findRecordInTimeRange(
                                dateFormat.format(fromDate),
                                dateFormat.format(toDate),
                                employee.getId())
                        .stream()
                        .map(
                            DtoTransformation::timesheetRecord2TimeRecordResponse)
                        .collect(Collectors.toList());
        return new TimeRegisterGenericResponse(listTimeRecordResponse, startPage, pageSize);
    }

    public TimeRegisterGenericResponse<TimeRecordResponse> getAllTimeRecord(
            int startPage, int pageSize) {
        Pageable pageable = PageRequest.of(startPage, pageSize);
        List<TimeRecordResponse> listTimeRecordResponse =
                timesheetRecordRepository.findAll(pageable).stream()
                        .map(DtoTransformation::timesheetRecord2TimeRecordResponse)
                        .collect(Collectors.toList());
        return new TimeRegisterGenericResponse<>(listTimeRecordResponse, startPage, pageSize);
    }

    public String deleteTimeRecord(long recordId) throws TimeRegisterObjectNotFoundException {
        TimesheetRecord timesheetRecord =
                timesheetRecordRepository
                        .findById(recordId)
                        .orElseThrow(
                                () ->
                                        new TimeRegisterObjectNotFoundException(
                                                "Record does not exist"));
        timesheetRecordRepository.delete(timesheetRecord);
        return "OK";
    }
}
