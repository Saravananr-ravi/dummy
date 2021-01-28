package com.scb.food.service.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "http://BANK-SERVICE/bank-app")
public interface OrderServiceProxy {

    @PostMapping("fund_transfer")
    String fundTransfer(@RequestParam long fromAccountNo,
                        @RequestParam long toAccountNo, @RequestParam float amtToTransfer);
}
