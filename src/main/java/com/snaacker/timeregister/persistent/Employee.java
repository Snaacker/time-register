package com.snaacker.timeregister.persistent;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Data
@Entity
@Table(name="employee")
public class Employee extends BaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, name = "account_id")
    private String accountId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="address")
    private String address;

    @Column(name="role_name")
    private Role roleName;

    @Column(name="is_admin")
    private boolean isAdmin;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<TimesheetRecord> timesheetRecord;
}
