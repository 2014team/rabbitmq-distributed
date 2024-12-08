package com.mashibing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessController {

    @GetMapping("/notifyBusiness")
    public void notifyBusiness() throws InterruptedException {
        Thread.sleep(400);
        System.out.println("通知商家成功！！");
    }

}