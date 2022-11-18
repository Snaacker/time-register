package com.snaacker.timeregister.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snaacker.timeregister.persistent.ScheduleType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {
    @JsonProperty("schedule_type")
    private ScheduleType scheduleType;

    @JsonProperty("special_date_name")
    private String specialDateName;

    @JsonProperty("special_date")
    private Date specialDate; // date only don't care about time here

    @JsonProperty("opening_hour") // save as secondOfDay
    private int openingHour;

    @JsonProperty("closing_hour")
    private int closingHour;
}
