package com.mashibing.controller;

import com.mashibing.service.TBOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderManageController {



    @Autowired
    private TBOrderService orderService;

    @GetMapping("create")
    public void create() throws InterruptedException {
        orderService.save();
        System.out.println("创建订单成功！");
    }

}