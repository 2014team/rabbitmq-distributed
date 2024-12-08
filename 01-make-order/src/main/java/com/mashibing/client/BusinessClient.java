package com.mashibing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "business")
public interface BusinessClient {

    @GetMapping("notifyBusiness")
    public void notifyBusiness();
}
