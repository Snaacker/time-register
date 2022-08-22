package com.snaacker.timeregister.persistent;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@RestResource(exported = false)
public class Schedule extends BaseObject{

    @Column(name = "schedule_date", nullable = false, unique = true)
    private Date scheduleDate;

    @Column(name = "from_time")
    private Date fromTime;

    @Column(name = "to_time")
    private Date toTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;
}
