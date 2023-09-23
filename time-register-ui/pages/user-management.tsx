import { UserAddOutlined } from '@ant-design/icons';
import { Avatar, Button, List, PageHeader } from 'antd';
import React, { useEffect, useState } from 'react';
import { getUsers } from '../src/api-manager/user';
import { User } from '../src/type/User';
import { useRouter } from "next/router";
import NavBar from "../src/components/nav/nav-bar";



function UserManagement() {
  const router = useRouter()
  const [userList, setUserList] = useState<User[]>()

  const fetchUserList = async () => {
    const userList = await getUsers();
    console.log(userList)
    setUserList(userList)
    return userList
  }


  useEffect(() => {
    fetchUserList()
  }, [])



  return (
    <div className='row justify-content-center'>
      <NavBar></NavBar>
      <PageHeader className="site-page-header" title="User Management" extra={[
        <Button key="1" type='primary' icon={<UserAddOutlined />} onClick={() => router.push("/create-user")}>Add User</Button>
      ]} />
      <div className=" col-lg-4 col-md-6 col-sm-10 col-xs-12 align-self-center">
        <List
          itemLayout="horizontal"
          dataSource={userList}
          renderItem={user => (
            <List.Item>
              <List.Item.Meta
                avatar={<Avatar src="https://joeschmoe.io/api/v1/random" />}
                title={<a onClick={() => router.push("user-profile/?userId=" + user.id)}>{user.first_name} {user.last_name}</a>}
                description={user.role_name}
              />
            </List.Item>
          )}
        />
      </div>

    </div>

  )
}

export default UserManagement;