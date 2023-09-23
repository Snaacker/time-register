import React, { useContext } from "react";
import { Button, Checkbox, Form, Input, Alert } from "antd";
import Card from "antd/lib/card/Card";
import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { useRouter } from "next/router";
import { User } from "../src/type/User";
import { UserContext } from "./appWrapper";
import { useEffect } from "react";
import { useState } from "react";
import { encyptLoginData, handlerUserAuthRequest } from "../src/services/authentication";

function Login() {
  const { state, update } = useContext(UserContext)
  // 0 = Init , 1 = Failed , 2 = Success 
  const [userAuthStatus, setUserAuthStatus] = useState<Number>(0)
  const router = useRouter();
  const onFinish = async (input: { username: String, password: String }) => {

    if (await handlerUserAuthRequest(encyptLoginData(input.username + ":" + input.password))) {
      // TODO : get user props like id, role etc... 
      let _user: User = { id: "12", role_name: "ADMIN", user_name: input.username, isAuthenticated: true }
      update(_user)
      setUserAuthStatus(2)
    } else {
      setUserAuthStatus(1)
    }
  };

  useEffect(() => {
    if (state.isAuthenticated) {
      router.push("home")
    }
  }, [state, userAuthStatus])

  return (
    <div className="vh-100 row justify-content-center">

      <div className=" col-lg-4 col-md-6 col-sm-10 col-xs-12 align-self-center">
        <Card title="Login" >
          {userAuthStatus === 1 ?
            <div className="pb-3">
              <Alert message="Username or password is incorrect." type="error" />
            </div> : null
          }
          <Form
            name="normal_login"
            className="login-form"
            initialValues={{ remember: true }}
            onFinish={onFinish}
          >
            <Form.Item
              name="username"
              rules={[
                { required: true, message: "Please input your Username!" },
              ]}
            >
              <Input
                prefix={<UserOutlined className="site-form-item-icon" />}
                placeholder="Username"
              />
            </Form.Item>
            <Form.Item
              name="password"
              rules={[
                { required: true, message: "Please input your Password!" },
              ]}
            >
              <Input
                prefix={<LockOutlined className="site-form-item-icon" />}
                type="password"
                placeholder="Password"
              />
            </Form.Item>
            <Form.Item>
              <Form.Item name="remember" valuePropName="checked" noStyle>
                <Checkbox>Remember me</Checkbox>
              </Form.Item>
            </Form.Item>

            <Form.Item>
              <Button
                type="primary"
                htmlType="submit"
                className="login-form-button"
              >
                Log in
              </Button>
              Or <a href="/signup">register now!</a>
            </Form.Item>
          </Form>
        </Card>
      </div>


    </div>
  );
}

export default Login;
