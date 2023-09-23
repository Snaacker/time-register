import {
  Avatar,
  Button,
  Descriptions,
  Form,
  Input,
  List,
  message,
  notification,
  PageHeader,
  Popconfirm,
  Select,
  Tabs
} from 'antd';
import Card from "antd/lib/card/Card";
import React, { useEffect, useState } from 'react';
import { EditOutlined, UserDeleteOutlined } from '@ant-design/icons';
import { useRouter } from "next/router";
import { deleteRestaurant, getRestaurantById, getUsersOfRestaurant } from '../src/api-manager/restaurant';
import { Restaurant } from '../src/type/Restaurant';
import Paragraph from 'antd/lib/typography/Paragraph';
import {User} from "../src/type/User";
import NavBar from "../src/components/nav/nav-bar";

function RestaurantDetail() {
  const { Option } = Select;
  const router = useRouter()
  const { restaurantId } = router.query
  const [restaurant, setRestaurant] = useState<Restaurant>()
  const [userList, setUserList] = useState<User[]>()

  async function fetchRestaurantData() {
    let restaurant = await getRestaurantById(restaurantId)
    setRestaurant(restaurant)
  }

  async function fetchUserRestaurantData() {
    let userList = await getUsersOfRestaurant(restaurantId)
    setUserList(userList)
  }

  const openMessage = () => {
    message.success('Restaurant has been deleted successfully ');
  };

  async function callDeleteRestaurant(restaurantId: String) {
    await deleteRestaurant(restaurantId)
    openMessage()
    router.back()
  }

  useEffect(() => {
    console.log(restaurantId)
    fetchRestaurantData()
  }, [restaurantId])

  useEffect( () => {
    fetchUserRestaurantData()
  }, [restaurantId])

  return (
    <div>
      <NavBar></NavBar>
      {restaurant !== undefined ?
        <div className='row justify-content-center'>
          <PageHeader className="site-page-header" title={restaurant.name} onBack={() => router.back()} avatar={{ size: 'large', src: "https://joeschmoe.io/api/v1/random" }}
            extra={[
              <Button key="2" type='primary' icon={<EditOutlined />} onClick={() => router.push({ pathname: "/edit-restaurant", query: { restaurant: JSON.stringify(restaurant) } })}>Edit Restaurant</Button>,
              <Popconfirm
                placement="bottom"
                title="Are you sure about it ?"
                onConfirm={() => callDeleteRestaurant(restaurantId)}
                okText="Yes"
                cancelText="No"
              >
                <Button key="1" danger icon={<UserDeleteOutlined />} >Delete Restaurant</Button>
              </Popconfirm>
            ]}>
            <Descriptions size="middle" column={{ xs: 1, sm: 2, md: 3, lg: 4, xl: 5 }}>
              <Descriptions.Item label="Phone Number">{restaurant.phone_number}</Descriptions.Item>
              <Descriptions.Item label="Address">{restaurant.address}</Descriptions.Item>
              <Descriptions.Item label="Email">{restaurant.email}</Descriptions.Item>
              <Descriptions.Item label="Manager">{restaurant.manager}</Descriptions.Item>
            </Descriptions>
          </PageHeader>
          <div className=" col-lg-6 col-md-8 col-sm-10 col-xs-12 align-self-center">
            <List
                itemLayout="horizontal"
                dataSource={userList}
                renderItem={user => (
                    <List.Item>
                      <List.Item.Meta
                          avatar={<Avatar src="https://joeschmoe.io/api/v1/random" />}
                          //TODO: return user id on request
                          title={<a onClick={() => router.push("user-profile/?userId=" + user.id)}>{user.first_name} {user.last_name}</a>}
                          description={user.role_name}
                      />
                    </List.Item>
                )}
            />
          </div>
        </div>
        : null}
    </div>

  )
}

export default RestaurantDetail;
