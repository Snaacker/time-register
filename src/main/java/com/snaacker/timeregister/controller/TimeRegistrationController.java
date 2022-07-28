package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.model.TimeRecordResponseDto;
import com.snaacker.timeregister.model.UserTimeRecordResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registration")
public class TimeRegistrationController {

  // TODO: return all registed time of all employee (pagination)
  @GetMapping("")
  public List<UserTimeRecordResponseDto> getAllTimeRecord() {
    return null;
  }

  @GetMapping("/{userId}")
  public List<TimeRecordResponseDto> getAllTimeRecordByUserId(@PathVariable int userId){
    return null;
  }

  @DeleteMapping("/{id}")
  public List<TimeRecordResponseDto> deleteTimeRecord(@PathVariable int recordId){
    return null;
  }
}
