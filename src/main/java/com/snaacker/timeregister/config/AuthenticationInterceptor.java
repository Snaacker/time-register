package com.snaacker.timeregister.config;

import com.snaacker.timeregister.AllowAnonymous;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

  @Autowired UserService userService;

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    final AllowAnonymous allowAnonymous =
        ((HandlerMethod) handler).getMethod().getAnnotation((AllowAnonymous.class));

    if (allowAnonymous != null) {
      return true;
    }
    if (request.getHeader("Authorization") == null) {
      log.error("Authorization not sent.");
      log.error("Validation NOK.");
      return false;
    }
    // TODO: fix authentication + authorization here
    else if (request.getHeader("Authorization").equals("Time-register-test")) {
      log.info("Validation OK.");
      return true;
    } else if (request.getHeader("Authorization") != null) {
      JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
      String username = null;
      String jwtToken = null;
      // JWT Token is in the form "Bearer token". Remove Bearer word and get
      // only the Token
      jwtToken = request.getHeader("Authorization").substring(7);
      try {
        username = jwtTokenUtil.getUsernameFromToken(jwtToken);
      } catch (IllegalArgumentException e) {
        System.out.println("Unable to get JWT Token");
      } catch (ExpiredJwtException e) {
        System.out.println("JWT Token has expired");
      }
      // TODO: this is wrong, should be DTO class instead
      User authenticatedUser = userService.getUserByName(username);
      jwtTokenUtil.validateToken(jwtToken, authenticatedUser);
      return true;
    } else {
      log.info("Validation NOK.");
      return false;
    }
  }
}