package com.snaacker.timeregister.repository;

import com.snaacker.timeregister.persistent.Employee;
import com.snaacker.timeregister.persistent.TimesheetRecord;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TimesheetRecordRepository extends JpaRepository<TimesheetRecord, Long> {
    // TODO: not done yet - add time query
    @Query(
            value = "SELECT * FROM timesheet_record t WHERE t.users_id = :users_id",
            nativeQuery = true)
    List<TimesheetRecord> getTimeRecordOnSavedMonthOfUser(Long users_id);

    List<TimesheetRecord> findByEmployee(Employee employee, Pageable pageable);

    @Query(
            value =
                    "SELECT * from timesheet_record t WHERE t.users_id = :users_id "
                            + "AND t.from_time > :startOfDate "
                            + "AND t.to_time < :endOfDate",
            nativeQuery = true)
    List<TimesheetRecord> findRecordInTimeRange(
            String startOfDate, String endOfDate, Long users_id);

    @Query(
            value =
                    "SELECT * from timesheet_record t WHERE t.users_id = :userId "
                            + "AND t.working_date = :workingDate ",
            nativeQuery = true)
    List<TimesheetRecord> findRecordByWorkingDateAndUserId(String workingDate, Long userId);
}
