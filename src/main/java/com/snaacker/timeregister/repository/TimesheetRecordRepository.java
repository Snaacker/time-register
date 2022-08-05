package com.snaacker.timeregister.repository;

import com.snaacker.timeregister.persistent.TimesheetRecord;
import com.snaacker.timeregister.persistent.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimesheetRecordRepository  extends JpaRepository<TimesheetRecord, Long> {
    //TODO: finish this
    @Query(value = "SELECT t FROM TimesheetRecord  t WHERE t.users = ?", nativeQuery = true)
    List<TimesheetRecord> getTimeRecordOnSavedMonthOfUser(User user);
}
