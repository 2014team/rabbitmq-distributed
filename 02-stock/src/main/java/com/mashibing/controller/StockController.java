package com.mashibing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    private static int stock = 10;

    @GetMapping("/decr")
    public void decr() throws InterruptedException {
        Thread.sleep(400);
        stock--;
        if(stock < 0){
            throw new RuntimeException("商品库存不足！");
        }
        System.out.println("扣减库存成功！");
    }
}