package com.snaacker.timeregister.persistent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "contract")
public class Contract extends BaseObject {

    @OneToOne(mappedBy = "contract")
    private Employee employee;

    @Column(name = "contract_type")
    private ContractType contractType;

    @Column(name = "maximum_working_hours_per_week")
    private int maximumWorkingHoursPerWeek;

    @Column(name = "maximum_working_hours_per_month")
    private int maximumWorkingHoursPerMonth;
}

enum ContractType {
    LONG_TERM,
    SHORT_TERM,
    STUDENT,
    FIX_TIME
}
