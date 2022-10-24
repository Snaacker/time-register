package com.snaacker.timeregister.repository;

import com.snaacker.timeregister.persistent.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {}
