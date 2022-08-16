package com.snaacker.timeregister.persistent;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@RestResource(exported = false)
public class Schedule extends BaseObject{

    private Date scheduleTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;
}
