package com.snaacker.timeregister.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.snaacker.timeregister.persistent.Employee;
import com.snaacker.timeregister.persistent.TimesheetRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class UserSingleTimeRecordResponse {
    @JsonProperty("user")
    EmployeeResponse user;

    @JsonProperty("time_record")
    TimeRecordResponse timeRecordResponse;

    public UserSingleTimeRecordResponse(Employee employee, TimesheetRecord timesheetRecord) {
        this.user = new EmployeeResponse(employee);
        this.timeRecordResponse = new TimeRecordResponse(timesheetRecord);
    }
}
