package com.snaacker.timeregister.persistent;

import com.snaacker.timeregister.model.UserRequest;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Getter
@Setter
@Table(name = "users")
@AllArgsConstructor
@RestResource(exported = false)
public class User extends BaseObject {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(unique = true, name = "account_id")
    private String accountId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "role_name")
    private Role roleName;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(name = "maximum_working_hours")
    private int maximumWorkingHours;

    @Column(name = "manager_note")
    private String managerNote;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<TimesheetRecord> timesheetRecord;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<UserRestaurant> userRestaurants;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Message> message;

    public User(
            String username,
            String password,
            String accountId,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String address,
            Role roleName) {
        this.username = username;
        this.password = password;
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.roleName = roleName;
        this.maximumWorkingHours = maximumWorkingHours;
        this.isAdmin = isAdmin;
        this.managerNote = managerNote;
        this.roleName = roleName;
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    public User(UserRequest userRequest) {
        this.username = userRequest.getUserName();
        this.password = userRequest.getPassword();
        this.accountId = userRequest.getAccountId();
        this.firstName = userRequest.getFirstName();
        this.lastName = userRequest.getLastName();
        this.email = userRequest.getEmail();
        this.phoneNumber = userRequest.getPhoneNumber();
        this.address = userRequest.getAddress();
        this.roleName = userRequest.getRoleName();
        this.maximumWorkingHours = userRequest.getMaximumWorkingHours();
        this.isAdmin = userRequest.isAdmin();
        this.managerNote = userRequest.getManagerNote();
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    public User() {
        super();
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }
}
