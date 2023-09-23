package com.snaacker.timeregister.persistent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "system_configuration")
public class SystemConfiguration extends BaseObject {
    @Column(name = "name", nullable = false)
    ConfigurationName name;

    @Column(name = "value")
    String value;
}
