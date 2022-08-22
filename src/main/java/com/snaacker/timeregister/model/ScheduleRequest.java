package com.snaacker.timeregister.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {
    @JsonProperty("schedule_date")
    private Date scheduleDate;
    @JsonProperty("from_time")
    private Date fromTime;
    @JsonProperty("to_time")
    private Date toTime;
}
