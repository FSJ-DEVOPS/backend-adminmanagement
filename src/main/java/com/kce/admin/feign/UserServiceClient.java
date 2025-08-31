package com.kce.admin.feign;

import com.kce.admin.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usermanagement", url = "https://backend-usermanagement.onrender.com", configuration = FeignConfig.class)
public interface UserServiceClient {

    @GetMapping("/api/users/{userId}/name")
    String getUserNameById(@PathVariable("userId") Long userId);
}
