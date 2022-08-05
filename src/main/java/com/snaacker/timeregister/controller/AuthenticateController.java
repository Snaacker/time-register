package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.exception.TimeRegisterException;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.repository.UserRepository;
import com.snaacker.timeregister.utils.AllowAnonymous;
import com.snaacker.timeregister.utils.JwtTokenUtil;
import com.snaacker.timeregister.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticateController {
  @Autowired private UserRepository userRepository;

  // TODO: Add exception handler for project
  @AllowAnonymous
  @PostMapping("")
  public ResponseEntity<String> authenticate(@RequestHeader("Authorization") String credential)
          throws TimeRegisterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
    byte[] decodedBytes = Base64.getDecoder().decode(credential);
    String decodedString = new String(decodedBytes);
    String[] userCredential = decodedString.split(":");
    User requestUser = userRepository.findByUsername(userCredential[0]);
    if (null == requestUser) {
      throw new TimeRegisterException("Bad credential");
    }
    String requestPassword = requestUser.getPassword();
    if (!Utilities.decrypt(requestPassword).equals(userCredential[1])){
      throw new TimeRegisterException("Bad credential");
    } else {
      JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
      return new ResponseEntity<>(jwtTokenUtil.generateToken(requestUser), HttpStatus.ACCEPTED);
    }
  }
}
