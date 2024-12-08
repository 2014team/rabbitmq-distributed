package com.mashibing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "ordermanage")
public interface OrderManageClient {

    @GetMapping("create")
    public void create();
}
