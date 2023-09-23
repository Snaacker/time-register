import React, { useEffect, useState } from "react";
import { Button, Form, Input, Select, PageHeader } from "antd";
import Card from "antd/lib/card/Card";
import { Restaurant } from "../src/type/Restaurant";
import { useRouter } from "next/router";
import {editRestaurant} from "../src/api-manager/restaurant";

function EditRestaurant() {
  const { Option } = Select;
  const [loading, setLoading] = useState<boolean>(false);
  const router = useRouter();
  const { restaurant } = router.query
  const [currentRestaurant, setRestaurant] = useState<Restaurant>(JSON.parse(restaurant));


  const onFinish = async (values: Restaurant) => {
    console.log(currentRestaurant.restaurant_id)
    try {
      setLoading(true)
      const editRestaurantResponse = await editRestaurant(currentRestaurant.restaurant_id, values)
      router.push("/restaurant-management")
      console.log(editRestaurantResponse)
      if (editRestaurantResponse !== undefined) {
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
    
    console.log(currentRestaurant)
  }, [restaurant])


  return (
    <div className=" row justify-content-center">
      <PageHeader className="site-page-header" title="Edit Restaurant" onBack={() => router.back()} />
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
              label="Restaurant name"
              name="name"
              rules={[
                { required: true, message: "Please input restaurant name!" },
              ]}
              initialValue={currentRestaurant?.name}
            >
              <Input />
            </Form.Item>
            <Form.Item
              label="Restaurant address"
              name="address"
              rules={[
                { required: true, message: "Please input restaurant address!" },
              ]}
              initialValue={currentRestaurant?.address}
            >
              <Input />
            </Form.Item>

            <Form.Item
              label="Phone number"
              name="phone_number"
              rules={[
                { required: true, message: "Please input restaurant phone number" },
              ]}
              initialValue={currentRestaurant?.phone_number}
            >
              <Input type={"number"} />
            </Form.Item>
            <Form.Item
              label="Email"
              name="email"
              rules={[{ required: true, message: "Please input restaurant Email" }]}
              initialValue={currentRestaurant?.email}
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

export default EditRestaurant;
