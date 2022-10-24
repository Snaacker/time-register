package com.snaacker.timeregister.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.snaacker.timeregister.persistent.TimesheetRecord;
import com.snaacker.timeregister.persistent.TimesheetType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(
        value = {"hibernateLazyInitializer", "handler"},
        ignoreUnknown = true)
public class TimeRecordResponse {
    @JsonProperty("from_time")
    private Date fromTime;

    @JsonProperty("to_time")
    private Date toTime;

    @JsonProperty("timesheet_type")
    private TimesheetType timesheetType;

    @JsonProperty("is_approved")
    private boolean isApproved;

    public TimeRecordResponse(TimesheetRecord timesheetRecord) {
        this.fromTime = timesheetRecord.getFromTime();
        this.toTime = timesheetRecord.getToTime();
        this.timesheetType = timesheetRecord.getTimesheetType();
        this.isApproved = timesheetRecord.isApproved();
    }
}
