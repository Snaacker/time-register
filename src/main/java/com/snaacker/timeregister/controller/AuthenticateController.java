package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.exception.TimeRegisterBadRequestException;
import com.snaacker.timeregister.exception.TimeRegisterException;
import com.snaacker.timeregister.exception.TimeRegisterUnauthorizedException;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.repository.UserRepository;
import com.snaacker.timeregister.annotation.AllowAnonymous;
import com.snaacker.timeregister.config.JwtTokenConfiguration;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticateController {
  private UserRepository userRepository;

  private JwtTokenConfiguration jwtTokenConfiguration;

  @Autowired
  public AuthenticateController(
      final UserRepository userRepository, final JwtTokenConfiguration jwtTokenConfiguration) {
    this.userRepository = userRepository;
    this.jwtTokenConfiguration = jwtTokenConfiguration;
  }

  @AllowAnonymous
  @PostMapping("")
  public ResponseEntity<String> authenticate(@RequestHeader("Authorization") String credential)
      throws TimeRegisterUnauthorizedException, TimeRegisterBadRequestException {
    byte[] decodedBytes = Base64.getDecoder().decode(credential);
    String decodedString = new String(decodedBytes);
    String[] userCredential = decodedString.split(":");
    User requestUser = userRepository.findByUsername(userCredential[0]);
    if (null == requestUser) {
      throw new TimeRegisterUnauthorizedException("Bad credential");
    }
    String requestPassword = requestUser.getPassword();
    String hashRequestPassword = "";
    try {
      hashRequestPassword =
          Base64.getEncoder().encodeToString(Utilities.encryptToByte(userCredential[1]));
    } catch (NoSuchPaddingException
        | NoSuchAlgorithmException
        | InvalidKeyException
        | IllegalBlockSizeException
        | BadPaddingException e) {
      throw new TimeRegisterBadRequestException(e);
    }
    if (!requestPassword.equals(hashRequestPassword)) {
      throw new TimeRegisterUnauthorizedException("Bad credential");
    } else {
      return new ResponseEntity<>(
          jwtTokenConfiguration.generateToken(requestUser), HttpStatus.ACCEPTED);
    }
  }
}
