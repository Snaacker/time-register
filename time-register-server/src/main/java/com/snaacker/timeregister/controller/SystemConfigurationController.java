package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.model.request.SystemConfigurationRequest;
import com.snaacker.timeregister.model.response.SystemConfigurationResponse;
import com.snaacker.timeregister.service.SystemConfigurationService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/configuration")
public class SystemConfigurationController {

    @Autowired SystemConfigurationService systemConfigurationService;

    @GetMapping("")
    public ResponseEntity<Set<SystemConfigurationResponse>> getAllConfiguration() {
        return new ResponseEntity<>(systemConfigurationService.getAllConfigure(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<SystemConfigurationResponse> createSystemConfiguration(
            @RequestBody SystemConfigurationRequest systemConfigurationRequest) {
        return new ResponseEntity<>(
                systemConfigurationService.createSystemConfigure(systemConfigurationRequest),
                HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<SystemConfigurationResponse> updateSystemConfiguration(
            @RequestBody SystemConfigurationRequest systemConfigurationRequest) {
        return new ResponseEntity<>(
                systemConfigurationService.updateSystemConfigure(systemConfigurationRequest),
                HttpStatus.ACCEPTED);
    }
}
