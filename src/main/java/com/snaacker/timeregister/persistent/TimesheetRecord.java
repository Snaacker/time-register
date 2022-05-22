package com.snaacker.timeregister.persistent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="timesheet_record")
public class TimesheetRecord extends BaseObject{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="from_time")
    private Date fromTime;

    @Column(name="to_time")
    private Date toTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;
}
