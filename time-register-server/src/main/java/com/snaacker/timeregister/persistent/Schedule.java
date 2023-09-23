package com.snaacker.timeregister.persistent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

@Getter
@Setter
@Entity
@Table(name = "schedule")
@RestResource(exported = false)
public class Schedule extends BaseObject {

    @Column(name = "schedule_type", nullable = false, unique = true)
    private ScheduleType scheduleType;

    @Column(name = "special_date_name")
    private String specialDateName;

    @Column(name = "special_date")
    private Date specialDate; // date only don't care about time here

    @Column(name = "opening_hour") // save as secondOfDay
    private int openingHour;

    @Column(name = "closing_hour")
    private int closingHour;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;
}
