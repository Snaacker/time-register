export interface UserAuthStatus {
  isAuthenticated?: Boolean
}

type RoleName = "MANAGER" | "ADMIN" | "EMPLOYEE"

export interface User extends UserAuthStatus {
  id: String;
  account_id?: String;
  address?: String;
  email?: String;
  first_name?: String;
  last_name?: String;
  password?: String;
  phone_number?: String;
  role_name: String;
  user_name: String;
  maximum_working_hours?: Number;
  manager_note?:String;
};
