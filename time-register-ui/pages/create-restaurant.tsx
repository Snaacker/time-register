import React, {useState} from "react";
import {Button, Form, Input, Select} from "antd";
import Card from "antd/lib/card/Card";
import { createRestaurant } from "../src/api-manager/restaurant";
import { useRouter } from "next/router";
import {Restaurant} from "../src/type/Restaurant";

function CreateRestaurant() {
  const { Option } = Select;
  const [loading, setLoading] = useState<boolean>(false);
  const router = useRouter();
  const onFinish = async (values: Restaurant) => {
    try {
      setLoading(true)
      const createRestaurantResponse = await createRestaurant(values)
      router.push("/restaurant-management")
      console.log(createRestaurantResponse)
      if (createRestaurantResponse !== undefined) {
        setLoading(false)
      }
    } catch (error) {
      setLoading(false)
    }
  };

  const onFinishFailed = (errorInfo: any) => {
    console.log("Failed:", errorInfo);
  };

  return (
    <div className="vh-100 row justify-content-center">
      <div className=" col-lg-6 col-md-8 col-sm-10 col-xs-12 align-self-center">
        <Card title="Create a new restaurant ">
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
              label="Name"
              name="name"
              rules={[{ required: true, message: "Name is required" }]}
            >
              <Input />
            </Form.Item>
            <Form.Item
              label="Address"
              name="address"
              rules={[{ required: true, message: "Address is required" }]}
            >
              <Input />
            </Form.Item>
            <Form.Item
                label="Email"
                name="email"
                rules={[{ required: true, message: "Email is required" }]}
            >
              <Input />
            </Form.Item>
            <Form.Item
                label="Phone number"
                name="phone_number"
                rules={[{ required: true, message: "Phone number is required" }]}
            >
              <Input />
            </Form.Item>
            <Form.Item>
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

export default CreateRestaurant;
