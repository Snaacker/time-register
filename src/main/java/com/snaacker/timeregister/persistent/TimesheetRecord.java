package com.snaacker.timeregister.persistent;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "timesheet_record")
@RestResource(exported = false)
public class TimesheetRecord extends BaseObject {

  @Column(name = "from_time")
  private Date fromTime;

  @Column(name = "to_time")
  private Date toTime;

  @Column(name = "type")
  private TimesheetType timesheetType;
  @Column(name = "is_ready_for_review")
  private boolean isReadyForReview;

  @Column(name = "is_approved")
  private boolean isApproved;

  // This is a stupid issue since user is a keyword in Postgres
  @ManyToOne(fetch = FetchType.LAZY)
  private User users;
}
