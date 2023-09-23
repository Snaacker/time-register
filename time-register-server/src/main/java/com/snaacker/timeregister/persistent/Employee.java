package com.snaacker.timeregister.persistent;

import com.snaacker.timeregister.model.request.EmployeeRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Getter
@Setter
@Table(name = "employee")
@AllArgsConstructor
@RestResource(exported = false)
public class Employee extends Account {

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "role_name")
    private Role roleName;

    @Column(name = "manager_note")
    private String managerNote;

    @OneToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private Contract contract;

    @Column(name = "last_working_date")
    private Date lastWorkingDate;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<TimesheetRecord> timesheetRecord;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<EmployeeRestaurant> employeeRestaurants;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<Message> message;

    public Employee(String phoneNumber, String address, Role roleName) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.roleName = roleName;
    }

    public Employee(EmployeeRequest employeeRequest) {
        this.phoneNumber = employeeRequest.getPhoneNumber();
        this.address = employeeRequest.getAddress();
        this.roleName = employeeRequest.getRoleName();
        this.managerNote = employeeRequest.getManagerNote();
    }

    public Employee() {
        super();
    }
}
