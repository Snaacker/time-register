import axios from "axios";
import { getLocalApiUrl, getRequestHeader } from "./request-helper";
import { User } from "../type/User";

export async function getUsers() {
  try {
    const { data, status } = await axios.get(getLocalApiUrl() + "user", {
      params:{
        startPage:0,
        pageSize:10
      },
      headers: getRequestHeader(),
    });

    console.log("response status is: ", status);
    return data.genericObject
  } catch (error) {
    console.log(error);
  }
}

export async function getUserById(id: String) {
  try {
    const { data, status } = await axios.get<User>(
      getLocalApiUrl() + "user/" + id,
      {
        headers: getRequestHeader(),
      }
    );

    console.log("response status is: ", status);
    console.log(data);
    return data
  } catch (error) {
    console.log(error);
  }
}

export async function createUser(user: User) {
  try {
    const { data, status } = await axios.put<User>(
      getLocalApiUrl() + "user",
      {
        account_id: user.account_id,
        address: user.address,
        email: user.email,
        first_name: user.first_name,
        last_name: user.last_name,
        password: user.password,
        phone_number: user.phone_number,
        role_name: user.role_name,
        user_name: user.user_name,
      },
      {
        headers: getRequestHeader(),
      }
    );

    console.log("response status is: ", status);
    console.log(data);
    return status
  } catch (error) {
    console.log(error);
  }
}

export async function editUser(userId:String, user: User) {
  try {
    const { data, status } = await axios.post<User>(
      getLocalApiUrl() + "user/" + userId,
      {
        account_id: user.account_id,
        address: user.address,
        email: user.email,
        first_name: user.first_name,
        last_name: user.last_name,
        password: user.password,
        phone_number: user.phone_number,
        role_name: user.role_name,
        user_name: user.user_name,
      },
      {
        headers: getRequestHeader(),
      }
    );

    console.log("response status is: ", status);
    console.log(data);
  } catch (error) {
    console.log(error);
  }
}

export async function deleteUser(userId: String) {
  try {
    const { data, status } = await axios.delete<User>(
      getLocalApiUrl() + "user/" + userId,
      {
        headers: getRequestHeader(),
      }
    );

    console.log("response status is: ", status);
    console.log(data);
    return status
  } catch (error) {
    console.log(error);
  }
}
