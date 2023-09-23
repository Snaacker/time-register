import React, { useEffect, useState } from "react";
import { Button, Form, Input, Select, PageHeader } from "antd";
import Card from "antd/lib/card/Card";
import { User } from "../src/type/User";
import { editUser } from "../src/api-manager/user";
import { useRouter } from "next/router";

function EditUser() {
  const { Option } = Select;
  const [loading, setLoading] = useState<boolean>(false);
  const router = useRouter();
  const { user } = router.query
  const [currentUser, setUser] = useState<User>(JSON.parse(user));


  const onFinish = async (values: User) => {
    console.log(currentUser.id)
    try {
      setLoading(true)
      const editUserResponse = await editUser(currentUser.id,values)
      router.push("/user-management")
      console.log(editUserResponse)
      if (editUserResponse !== undefined) {
        setLoading(false)
      }
    } catch (error) {
      setLoading(false)
    }
  };

  const onFinishFailed = (errorInfo: any) => {
    console.log("Failed:", errorInfo);
  };

  useEffect(() => {
    
    console.log(currentUser)
  }, [user])


  return (
    <div className=" row justify-content-center">
      <PageHeader className="site-page-header" title="Edit User" onBack={() => router.back()} />
      <div className=" col-lg-4 col-md-6 col-sm-10 col-xs-12 align-self-center">
        <Card >
          <Form
            name="basic"
            initialValues={{ remember: true }}
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            autoComplete="off"
            labelCol={{ span: 6 }}
            wrapperCol={{ span: 16 }}
          >
            <Form.Item
              label="User role"
              name="role_name"
              rules={[
                { required: true, message: "Please input your role!" },
              ]}
              initialValue={currentUser?.role_name}
            >
              <Select>
                <Option value="MANAGER">Manager</Option>
                <Option value="ADMIN">Admin</Option>
                <Option value="EMPLOYEE">Employee</Option>
              </Select>
            </Form.Item>
            <Form.Item
              label="First name"
              name="first_name"
              rules={[
                { required: true, message: "Please input your first name!" },
              ]}
              initialValue={currentUser?.first_name}
            >
              <Input />
            </Form.Item>
            <Form.Item
              label="Last name"
              name="last_name"
              rules={[
                { required: true, message: "Please input your last name!" },
              ]}
              initialValue={currentUser?.last_name}
            >
              <Input />
            </Form.Item>
            <Form.Item
              label="Username"
              name="username"
              rules={[
                { required: true, message: "Please input your username!" },
              ]}
              initialValue={currentUser?.user_name}
            >
              <Input />
            </Form.Item>

            <Form.Item
              label="Password"
              name="password"
              rules={[
                { required: true, message: "Please input your password!" },
              ]}
            >
              <Input.Password />
            </Form.Item>
            <Form.Item
              label="Address"
              name="address"
              rules={[{ required: true, message: "Please input your address" }]}
              initialValue={currentUser?.address}
            >
              <Input />
            </Form.Item>
            <Form.Item
              label="Phone number"
              name="phone_number"
              rules={[
                { required: true, message: "Please input your phone number" },
              ]}
              initialValue={currentUser?.phone_number}
            >
              <Input type={"number"} />
            </Form.Item>
            <Form.Item
              label="Email"
              name="email"
              rules={[{ required: true, message: "Please input your Email" }]}
              initialValue={currentUser?.email}
            >
              <Input type={"email"} />
            </Form.Item>
            <Form.Item className="float-right">
              <Button type="primary" htmlType="submit" loading={loading}>
                Submit
              </Button>
            </Form.Item>
          </Form>
        </Card>
      </div>
    </div>
  );
}

export default EditUser;
