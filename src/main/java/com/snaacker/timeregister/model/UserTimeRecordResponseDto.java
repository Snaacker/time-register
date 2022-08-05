package com.snaacker.timeregister.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTimeRecordResponseDto {
  UserResponseDto user;
  List<TimeRecordResponseDto> timeRecord;
}
