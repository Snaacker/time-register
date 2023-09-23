package com.snaacker.timeregister.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.snaacker.timeregister.persistent.TimesheetRecord;
import com.snaacker.timeregister.persistent.User;
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
    UserResponse user;

    @JsonProperty("time_record")
    TimeRecordResponse timeRecordResponse;

    public UserSingleTimeRecordResponse(User user, TimesheetRecord timesheetRecord) {
        this.user = new UserResponse(user);
        this.timeRecordResponse = new TimeRecordResponse(timesheetRecord);
    }
}
