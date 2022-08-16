package com.snaacker.timeregister.model;

import com.snaacker.timeregister.persistent.Schedule;

import java.util.Date;

public class ScheduleResponse {
    private Date scheduleTime;

    public ScheduleResponse(Schedule schedule) {
        this.scheduleTime = schedule.getScheduleTime();
    }

    public ScheduleResponse(){
        super();
    }
}
