package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.utils.AllowAnonymous;
import com.snaacker.timeregister.config.JwtTokenUtil;
import com.snaacker.timeregister.exception.TimeRegisterException;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticateController {
  @Autowired private UserRepository userRepository;

  // TODO: Add exception handler for project
  @AllowAnonymous
  @PostMapping("")
  public String authenticate(@RequestHeader("Authorization") String credential)
      throws TimeRegisterException {
    byte[] decodedBytes = Base64.getDecoder().decode(credential);
    String decodedString = new String(decodedBytes);
    String[] user = decodedString.split(":");
    User requestUser = userRepository.findByUsername(user[0]);
    if (null == requestUser) throw new TimeRegisterException();
    JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
    return jwtTokenUtil.generateToken(requestUser);
  }
}
