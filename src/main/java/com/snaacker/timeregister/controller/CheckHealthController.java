package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.utils.AllowAnonymous;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CheckHealthController {
  @AllowAnonymous
  @GetMapping("/health_check")
  public ResponseEntity<String> healthCheck() {
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }
}
