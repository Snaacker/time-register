package com.snaacker.timeregister.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registration")
public class TimeRegistrationController {

  // TODO: return all registed time of all employee (pagination)
  @GetMapping("")
  public List<Object> getAllRegistedTime() {
    return null;
  }
}
