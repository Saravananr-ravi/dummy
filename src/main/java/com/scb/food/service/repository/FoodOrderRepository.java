package com.scb.food.service.repository;

import com.scb.food.service.entity.FoodOrder;
import com.scb.food.service.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodOrderRepository extends JpaRepository<FoodOrder,Long> {

    List<FoodOrder> findByUserId(long userId);
}
