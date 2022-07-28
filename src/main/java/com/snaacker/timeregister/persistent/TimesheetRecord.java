package com.snaacker.timeregister.persistent;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "timesheet_record")
public class TimesheetRecord extends BaseObject {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "working_date", unique = true)
  @NotNull
  private Date workingDate;

  @Column(name = "from_time")
  private int fromTime;

  @Column(name = "to_time")
  private int toTime;

  @ManyToOne(fetch = FetchType.LAZY)
  private User users;
}
