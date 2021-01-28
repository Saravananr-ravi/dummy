package com.scb.food.service.controller;

import com.scb.food.service.entity.FoodDetails;
import com.scb.food.service.entity.FoodOrder;
import com.scb.food.service.service.impl.FoodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class FoodServiceController {

    @Autowired
    FoodServiceImpl foodServiceImpl;

    @GetMapping("/food")
    public List<FoodDetails> getFoodItem(@RequestParam String foodItem) {
        return foodServiceImpl.getFoodItem(foodItem);
    }

    @PostMapping("/place-order")
    public FoodOrder placeOrder(@RequestBody FoodOrder order) {
        return foodServiceImpl.placeOrder(order);
    }

    @GetMapping("/history")
    public List<FoodOrder> getOrderHistory(@RequestParam Long userId) {
        return foodServiceImpl.getOrderHistory(userId);
    }

}
