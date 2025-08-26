package com.kce.admin.feign;

import com.kce.admin.config.FeignConfig;
import com.kce.admin.dto.SellerOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "orderservice", configuration = FeignConfig.class)
public interface OrderServiceFeign {
    @GetMapping("/api/order/seller/{sellerId}")
    List<SellerOrderDTO> getOrdersBySellerId(@PathVariable("sellerId") String sellerId);
}
