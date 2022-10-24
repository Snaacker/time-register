package com.snaacker.timeregister.model.response;

import com.snaacker.timeregister.persistent.Schedule;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
