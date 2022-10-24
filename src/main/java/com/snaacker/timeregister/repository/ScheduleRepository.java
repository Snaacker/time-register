package com.snaacker.timeregister.repository;

import com.snaacker.timeregister.persistent.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {}
