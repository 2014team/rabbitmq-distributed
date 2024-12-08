package com.mashibing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "stock")
public interface StockClient {

    @GetMapping("decr")
    public void decr();
}
