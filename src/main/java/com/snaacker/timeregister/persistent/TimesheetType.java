package com.snaacker.timeregister.persistent;

import com.snaacker.timeregister.utils.Constants;

public enum TimesheetType {
    AVAILABLE(Constants.AVAILABLE),
    ABSENCE(Constants.ABSENCE),
    SICK_LEAVE(Constants.SICK_LEAVE),
    HOLIDAY(Constants.HOLIDAY);

    String value;

    TimesheetType(String value) {
        this.value = value;
    }

    public String getTimesheetType() {
        return value;
    }
}
