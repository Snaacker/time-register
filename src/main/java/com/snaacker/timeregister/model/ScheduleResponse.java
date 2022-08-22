package com.snaacker.timeregister.model;

import com.snaacker.timeregister.persistent.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class ScheduleResponse {
    private Date scheduleDate;
    private Date fromTime;
    private Date toTime;
    public ScheduleResponse(Schedule schedule) {
        this.scheduleDate = schedule.getScheduleDate();
        this.fromTime = schedule.getFromTime();
        this.toTime = schedule.getToTime();
    }

}
