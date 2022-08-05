package com.snaacker.timeregister.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeRecordRequestDto implements Serializable {
    private Date fromTime;
    private Date toTime;
}
