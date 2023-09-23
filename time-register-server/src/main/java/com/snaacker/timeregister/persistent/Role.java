package com.snaacker.timeregister.persistent;

import com.snaacker.timeregister.utils.Constants;

public enum Role {
    ADMIN(Constants.ADMIN),
    MANAGER(Constants.MANAGER),
    EMPLOYEE(Constants.EMPLOYEE),
    ANONYMOUS(Constants.ANONYMOUS);

    String value;

    Role(String value) {
        this.value = value;
    }

    public String getRoleValue() {
        return value;
    }
}
