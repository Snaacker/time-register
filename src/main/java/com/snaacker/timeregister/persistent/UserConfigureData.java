package com.snaacker.timeregister.persistent;

import lombok.Getter;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Service;

import javax.persistence.*;

@Entity
@Getter
@Service
@Table(name = "user_configure_data")
@RestResource(exported = false)
public class UserConfigureData extends BaseObject{
    @Column(name = "maximum_working_hours")
    private int maximumWorkingHours;
    @Column(name = "manager_notice")
    private String managerNotice;
    @ManyToOne(fetch = FetchType.LAZY)
    private User users;
}
