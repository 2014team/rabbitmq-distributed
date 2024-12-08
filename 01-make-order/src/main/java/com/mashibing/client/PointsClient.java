package com.mashibing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "points")
public interface PointsClient {

    @GetMapping("up")
    public void up();
}
