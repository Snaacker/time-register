package com.snaacker.timeregister.model.response;

import com.snaacker.timeregister.persistent.Schedule;
import com.snaacker.timeregister.persistent.ScheduleType;
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
    private long id;
    private ScheduleType scheduleType;
    private String specialDateName;
    private Date specialDate; // date only don't care about time here
    private int openingHour;
    private int closingHour;

    public ScheduleResponse(Schedule schedule) {
        this.id = schedule.getId();
        this.scheduleType = schedule.getScheduleType();
        this.specialDate = schedule.getSpecialDate();
        this.specialDateName = schedule.getSpecialDateName();
        this.openingHour = schedule.getOpeningHour();
        this.closingHour = schedule.getClosingHour();
    }
}
