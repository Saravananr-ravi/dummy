package com.scb.food.service.repository;

import com.scb.food.service.entity.FoodDetails;
import com.scb.food.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
