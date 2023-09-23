package com.snaacker.timeregister.persistent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Getter
@Setter
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
@RestResource(exported = false)
public class Account extends BaseObject {

    @Column(unique = true, name = "account_id")
    private String accountId;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(name = "is_first_login")
    private boolean isFirstLogin;

    @Column(name = "latest_login_ip")
    private String latestLoginIP;

    @Column(name = "account_name", nullable = false, unique = true)
    private String accountName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "isActive")
    private boolean isActive;
}
