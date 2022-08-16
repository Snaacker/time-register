package com.snaacker.timeregister.repository;

import com.snaacker.timeregister.persistent.TimesheetRecord;
import com.snaacker.timeregister.persistent.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public interface TimesheetRecordRepository extends JpaRepository<TimesheetRecord, Long> {
  // TODO: not done yet - add time query
  @Query(
      value = "SELECT * FROM timesheet_record t WHERE t.users_id = :users_id",
      nativeQuery = true)
  List<TimesheetRecord> getTimeRecordOnSavedMonthOfUser(Long users_id);

  List<TimesheetRecord> findByUsers(User user, Pageable pageable);

  @Query(
      value =
          "SELECT COUNT(*) from timesheet_record t WHERE t.users_id = :users_id " +
                  "AND t.from_time > :startOfDate " +
                  "AND t.to_time < :endOfDate",
      nativeQuery = true)
  int findRecordInTimeRange(
      OffsetDateTime startOfDate, OffsetDateTime endOfDate, Long users_id);
}
