package com.snaacker.timeregister.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeRecordResponseDto {
  private Date workingDate;

  private int fromTime;

  private int toTime;
}
