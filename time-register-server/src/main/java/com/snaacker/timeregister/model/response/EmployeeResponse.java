package com.snaacker.timeregister.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.snaacker.timeregister.persistent.Employee;
import com.snaacker.timeregister.persistent.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(
        value = {"hibernateLazyInitializer", "handler"},
        ignoreUnknown = true)
public class EmployeeResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("address")
    private String address;

    @JsonProperty("role_name")
    private Role roleName;

    @JsonProperty("maximum_working_hours")
    private int maximumWorkingHours;

    @JsonProperty("email")
    private String email;

    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.accountId = employee.getAccountId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.phoneNumber = employee.getPhoneNumber();
        this.address = employee.getAddress();
        this.roleName = employee.getRoleName();
        this.email = employee.getEmail();
        this.maximumWorkingHours = employee.getContract().getMaximumWorkingHoursPerMonth();
    }
}
