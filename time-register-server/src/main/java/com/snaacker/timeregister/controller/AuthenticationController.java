package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.annotation.AllowAnonymous;
import com.snaacker.timeregister.config.JwtTokenConfiguration;
import com.snaacker.timeregister.exception.TimeRegisterBadRequestException;
import com.snaacker.timeregister.exception.TimeRegisterUnauthorizedException;
import com.snaacker.timeregister.persistent.Employee;
import com.snaacker.timeregister.repository.EmployeeRepository;
import com.snaacker.timeregister.utils.Utilities;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {
    private final EmployeeRepository employeeRepository;

    private final JwtTokenConfiguration jwtTokenConfiguration;

    @Autowired
    public AuthenticationController(
            final EmployeeRepository employeeRepository,
            final JwtTokenConfiguration jwtTokenConfiguration) {
        this.employeeRepository = employeeRepository;
        this.jwtTokenConfiguration = jwtTokenConfiguration;
    }

    @AllowAnonymous
    @PostMapping("")
    public ResponseEntity<String> authenticate(@RequestHeader("Authorization") String credential)
            throws TimeRegisterUnauthorizedException, TimeRegisterBadRequestException {
        byte[] decodedBytes = Base64.getDecoder().decode(credential);
        String decodedString = new String(decodedBytes);
        String[] userCredential = decodedString.split(":");
        Employee requestEmployee = employeeRepository.findByAccountName(userCredential[0]);
        if (null == requestEmployee) {
            throw new TimeRegisterUnauthorizedException("Bad credential");
        }
        String requestPassword = requestEmployee.getPassword();
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
        }
        return new ResponseEntity<>(
                jwtTokenConfiguration.generateToken(requestEmployee), HttpStatus.ACCEPTED);
    }
}
