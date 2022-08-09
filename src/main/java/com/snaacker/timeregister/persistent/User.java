package com.snaacker.timeregister.persistent;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
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

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
  private List<TimesheetRecord> timesheetRecord;

  @ManyToOne(fetch = FetchType.LAZY)
  private Restaurant restaurant;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
  private List<UserConfigureData> userConfigureData;
}
