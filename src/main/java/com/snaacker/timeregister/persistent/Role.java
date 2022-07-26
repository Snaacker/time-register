package com.snaacker.timeregister.persistent;

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

  public static class Constants {
    public static final String ADMIN = "Admin";
    public static final String MANAGER = "Manager";
    public static final String EMPLOYEE = "Employee";
    public static final String ANONYMOUS = "Anonymous";
  }
}
