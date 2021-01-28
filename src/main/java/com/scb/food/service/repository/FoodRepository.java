package com.scb.food.service.repository;

import com.scb.food.service.entity.FoodDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<FoodDetails, Long> {

    List<FoodDetails> findByItemNameContaining(String itemName);
    FoodDetails findByItemName(String itemName);

}
