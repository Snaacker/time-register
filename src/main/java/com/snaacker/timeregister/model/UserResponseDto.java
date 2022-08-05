package com.snaacker.timeregister.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.snaacker.timeregister.persistent.Role;
import com.snaacker.timeregister.persistent.User;
import com.sun.istack.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(
    value = {"hibernateLazyInitializer", "handler"},
    ignoreUnknown = true)
public class UserResponseDto {
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

  public UserResponseDto(User user) {
    this.accountId = user.getAccountId();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.phoneNumber = user.getPhoneNumber();
    this.address = user.getAddress();
    this.roleName = user.getRoleName();
  }
}
