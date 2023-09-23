package com.snaacker.timeregister.repository;

import com.snaacker.timeregister.persistent.SystemConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration, Long> {}
