package com.mashibing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CouponController {

    @GetMapping("/coupon")
    public void coupon() throws InterruptedException {
        Thread.sleep(400);
        System.out.println("优惠券预扣除成功！");
    }

}