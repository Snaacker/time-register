package com.snaacker.timeregister.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.snaacker.timeregister.persistent.Role;
import com.sun.istack.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class EmployeeDto {
    @NotNull
    @JsonProperty("id")
    private Long id;
    @JsonProperty("account_id")
    private String accountId;
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
    @JsonProperty("created_date")
    private Date createdDate;
    @JsonProperty("updated_date")
    private Date updatedDate;
}
