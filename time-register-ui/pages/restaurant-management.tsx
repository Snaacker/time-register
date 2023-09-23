import {Avatar, Button, List, PageHeader} from 'antd';
import React, {useEffect, useState} from 'react';
import {UserAddOutlined} from "@ant-design/icons";
import { Restaurant } from '../src/type/Restaurant';
import { useRouter } from "next/router";
import {getRestaurants} from "../src/api-manager/restaurant";
import NavBar from "../src/components/nav/nav-bar";

function RestaurantManagement () {
  const router = useRouter()
  const [restaurantList, setRestaurantList] = useState<Restaurant[]>()

  const fetchRestaurantList = async () => {
    const restaurantList = await getRestaurants();
    console.log(restaurantList)
    setRestaurantList(restaurantList)
    return restaurantList
  }


  useEffect(() => {
    fetchRestaurantList()
  }, [])


  return (
    <div className='row justify-content-center'>
      <NavBar></NavBar>
    <PageHeader className="site-page-header" title="Restaurant Management" extra={[
      <Button key="1" type='primary' icon={<UserAddOutlined />} onClick={() => router.push("/create-restaurant")}>Add Restaurant</Button>
    ]} />
    <div className=" col-lg-4 col-md-6 col-sm-10 col-xs-12 align-self-center">
    <List
    itemLayout="horizontal"
    dataSource={restaurantList}
    renderItem={restaurant => (
      <List.Item>
        <List.Item.Meta
          avatar={<Avatar src="https://joeschmoe.io/api/v1/random" />}
          title={<a onClick={() => router.push("restaurant-detail/?restaurantId=" + restaurant.restaurant_id)}>{restaurant.name}</a>}
          description={restaurant.name}
        />
      </List.Item>
    )}
  />
    </div>
    </div>

)
}

export default RestaurantManagement;
