package com.snaacker.timeregister.service;

import com.snaacker.timeregister.model.request.SystemConfigurationRequest;
import com.snaacker.timeregister.model.response.SystemConfigurationResponse;
import com.snaacker.timeregister.persistent.SystemConfiguration;
import com.snaacker.timeregister.repository.SystemConfigurationRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigurationService {

    @Autowired SystemConfigurationRepository systemConfigurationRepository;

    public Set<SystemConfigurationResponse> getAllConfigure() {
        return systemConfigurationRepository.findAll().stream()
                .map(x -> new SystemConfigurationResponse(x))
                .collect(Collectors.toSet());
    }

    public SystemConfigurationResponse createSystemConfigure(
            SystemConfigurationRequest systemConfigurationRequest) {
        SystemConfiguration systemConfiguration =
                new SystemConfiguration(
                        systemConfigurationRequest.getConfigurationName(),
                        systemConfigurationRequest.getValue());
        return new SystemConfigurationResponse(
                systemConfigurationRepository.save(systemConfiguration));
    }

    public SystemConfigurationResponse updateSystemConfigure(
            SystemConfigurationRequest systemConfigurationRequest) {
        SystemConfiguration requestConfiguration =
                systemConfigurationRepository.findAll().stream()
                        .filter(
                                configuration ->
                                        configuration
                                                .getName()
                                                .equals(
                                                        systemConfigurationRequest
                                                                .getConfigurationName()))
                        .findFirst()
                        .get();
        requestConfiguration.setValue(systemConfigurationRequest.getValue());

        return new SystemConfigurationResponse(
                systemConfigurationRepository.save(requestConfiguration));
    }
}
