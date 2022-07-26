package com.snaacker.timeregister.persistent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

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

  @Column(name = "email")
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
}
