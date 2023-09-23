package com.snaacker.timeregister.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserRequest {

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("address")
    private String address;

    @JsonProperty("role_name")
    private Role roleName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("is_admin")
    private boolean isAdmin;

    @JsonProperty("maximum_working_hours_per_week")
    private int maximumWorkingHoursPerWeek;

    @JsonProperty("manager_note")
    private String managerNote;

    @JsonProperty("restaurant_name")
    private String restaurantName;
}
