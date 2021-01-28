package com.scb.food.service.repository;

import com.scb.food.service.entity.OrderDetails;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {

}
