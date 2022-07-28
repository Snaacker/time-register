package com.snaacker.timeregister.model;

import com.snaacker.timeregister.persistent.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  private String username;

  private String password;

  private String accountId;

  private String firstName;

  private String lastName;

  private String email;

  private String phoneNumber;

  private String address;

  private Role roleName;
}
