package com.snaacker.timeregister.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(
        value = {"hibernateLazyInitializer", "handler"},
        ignoreUnknown = true)
public class UserMultipleTimeRecordResponse {
    @JsonProperty("user")
    EmployeeResponse user;

    @JsonProperty("time_records")
    TimeRegisterGenericResponse<TimeRecordResponse> timeRecords;
}
