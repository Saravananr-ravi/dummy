package com.scb.food.service.service.impl;

import com.scb.food.service.config.OrderServiceProxy;
import com.scb.food.service.entity.FoodDetails;
import com.scb.food.service.entity.FoodOrder;
import com.scb.food.service.entity.OrderDetails;
import com.scb.food.service.entity.User;
import com.scb.food.service.exception.InvalidFoodNameException;
import com.scb.food.service.exception.OutOfStockException;
import com.scb.food.service.exception.TransactionFailureException;
import com.scb.food.service.exception.UserNotFoundException;
import com.scb.food.service.repository.FoodOrderRepository;
import com.scb.food.service.repository.FoodRepository;
import com.scb.food.service.repository.OrderDetailsRepository;
import com.scb.food.service.repository.UserRepository;
import com.scb.food.service.service.FoodOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodOrderService {

    @Autowired
    OrderServiceProxy orderServiceProxy;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    FoodOrderRepository foodOrderRepository;


    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    UserRepository userRepository;


    @Value("${vendor.account.number}")
    private long vendorAccountNumber;


    public List<FoodDetails> getFoodItem(String foodItem) {
        List<FoodDetails> foodDetails=foodRepository.findByItemNameContaining(foodItem);
        if(!Optional.ofNullable(foodDetails).isPresent()){
            throw new InvalidFoodNameException();
        }
         return foodDetails;
    }
    public FoodOrder placeOrder(FoodOrder order) {
        long fromAccountNo = order.getFromAccNo();
        FoodOrder foodOrder=new FoodOrder();
        foodOrder.setToAccNo(vendorAccountNumber);
        foodOrder.setFromAccNo(fromAccountNo);
        foodOrder.setUserId(order.getUserId());
        if (fromAccountNo==vendorAccountNumber) {
            throw new UserNotFoundException();
        }

        Optional<User> userRepo=userRepository.findById(foodOrder.getUserId());
        if (!userRepo.isPresent()) {
            throw new UserNotFoundException();
        }
        Set<OrderDetails> orderDetailsSet = order.getOrderDetailsSet();

        float totalAmt = 0;
        for (OrderDetails orderDetails : orderDetailsSet) {
            int qty = orderDetails.getQty();
            String itemName = orderDetails.getItemName();
            FoodDetails foodDetails = foodRepository.findByItemName(itemName);
            if(!Optional.ofNullable(foodDetails).isPresent()){
                throw new InvalidFoodNameException();
            }
            if (foodDetails.getQty() == 0) {
                throw new OutOfStockException();
            }
            //updateQty(foodDetails,qty);
            //qtyMap.put(itemName,foodDetails.getQty()-qty);
            float actualPrice = foodDetails.getPrice() * qty;
            orderDetails.setPrice(foodDetails.getPrice());
            totalAmt += actualPrice;
            persistOrderDetails(orderDetails,foodOrder);
        }
        String response=orderServiceProxy.fundTransfer(fromAccountNo, vendorAccountNumber, totalAmt);
        if(!response.equalsIgnoreCase("Transaction succeeded")){
            throw new TransactionFailureException();
        }
        foodOrder.setOrderDateTime(LocalDateTime.now());
        foodOrder.setIsPaymentSuccess("Y");
        foodOrder.setTotalAmount(totalAmt);
        //foodOrder.setOrderDetailsSet(orderDetailsSet);
        foodOrderRepository.save(foodOrder);
        return foodOrder;
    }
    @Transactional
    private void updateQty(FoodDetails foodDetails,int qty){
        foodDetails.setQty(foodDetails.getQty()-qty);
        foodRepository.saveAndFlush(foodDetails);
    }
    @Transactional
    private void persistOrderDetails(OrderDetails orderDetails,FoodOrder foodOrder){
        orderDetailsRepository.save(orderDetails);
        orderDetails.setFoodOrder(foodOrder);
    }
    public List<FoodOrder> getOrderHistory(@RequestParam Long userId) {
        List<FoodOrder> foodOrder = foodOrderRepository.findByUserId(userId);
        if(!Optional.ofNullable(foodOrder).isPresent()){
            throw new UserNotFoundException();
        }
        List<FoodOrder> sortedList = foodOrder.stream()
                .sorted(Comparator.comparing(FoodOrder::getOrderDateTime)).limit(5)
                .collect(Collectors.toList());
        return sortedList;
    }

}
