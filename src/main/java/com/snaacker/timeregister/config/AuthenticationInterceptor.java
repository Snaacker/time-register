package com.snaacker.timeregister.config;

import com.snaacker.timeregister.annotation.AllowAdmin;
import com.snaacker.timeregister.annotation.AllowAnonymous;
import com.snaacker.timeregister.exception.TimeRegisterBadRequestException;
import com.snaacker.timeregister.model.UserResponse;
import com.snaacker.timeregister.persistent.Role;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
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
  @Autowired JwtTokenConfiguration jwtTokenConfiguration;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws TimeRegisterBadRequestException {
    // TODO: dev only - this should be remove on production deploy/introduce dev profile
    if (request.getRequestURI().contains("localhost")
        && (request.getRequestURI().contains("swagger-resources")
            || request.getRequestURI().contains("swagger-ui")
            || request.getRequestURI().contains("v2/api-docs")
            || request.getRequestURI().contains(".ico")
            || request.getRequestURI().contains("/error")
            || request.getRequestURI().contains("/webjars"))) {
      return true;
    }
    final AllowAnonymous allowAnonymous =
        ((HandlerMethod) handler).getMethod().getAnnotation((AllowAnonymous.class));

    final AllowAdmin allowAdmin =
        ((HandlerMethod) handler).getMethod().getAnnotation((AllowAdmin.class));

    if (allowAnonymous != null || request.getRequestURI().contains("api/v1/authentication")) {
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
      // if already authenticated
      if (request.getHeader("Authorization").startsWith("Bearer ")) {
        String username = null;
        String jwtToken = request.getHeader("Authorization").substring(7);
        try {
          username = jwtTokenConfiguration.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
          System.out.println("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
          System.out.println("JWT Token has expired");
        }
        // TODO: this is wrong, should be DTO class instead
        UserResponse authenticatedUser = userService.getUserByName(username);
        jwtTokenConfiguration.validateToken(jwtToken, authenticatedUser);

        // TODO: check authorization here
        if (allowAdmin != null && !authenticatedUser.getRoleName().equals(Role.ADMIN)) {
          throw new TimeRegisterBadRequestException("Permission denied");
        }
        return true;
      } else {
        return false;
      }
    } else {
      log.info("Validation Not OK.");
      return false;
    }
  }
}
