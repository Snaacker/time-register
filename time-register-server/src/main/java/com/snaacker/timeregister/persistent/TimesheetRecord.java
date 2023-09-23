package com.snaacker.timeregister.persistent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Getter
@Setter
@Table(name = "timesheet_record")
@RestResource(exported = false)
public class TimesheetRecord extends BaseObject {

    @Column(name = "working_date", nullable = false)
    private String workingDate;

    @Column(name = "from_time", nullable = false)
    private Date fromTime;

    @Column(name = "to_time", nullable = false)
    private Date toTime;

    @Column(name = "timesheet_type", nullable = false)
    private TimesheetType timesheetType;

    @Column(name = "is_ready_for_review")
    private boolean isReadyForReview;

    @Column(name = "is_approved")
    private boolean isApproved;

    // This is a stupid issue since user is a keyword in Postgres
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

    @OneToOne private Restaurant restaurant;
}
