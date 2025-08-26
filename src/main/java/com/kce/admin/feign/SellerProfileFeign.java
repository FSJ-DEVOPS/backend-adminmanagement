// File: ShopverseAdmin/src/main/java/com/kce/admin/feign/SellerProfileFeign.java
package com.kce.admin.feign;

import com.kce.admin.config.FeignConfig;
import com.kce.admin.dto.DashboardSellerDTO;
import com.kce.admin.dto.SellerAnalyticsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.shopverse.sellerprofile.entity.Book;

import java.util.List;

@FeignClient(name = "sellerprofile", configuration = FeignConfig.class)
public interface SellerProfileFeign {
    @GetMapping("/api/seller/profile/{email}")
    SellerAnalyticsDTO getSellerProfileByEmail(@PathVariable("email") String email);

    @GetMapping("/api/seller/books/{sellerId}")
    List<Book> getBooksBySellerId(@PathVariable("sellerId") String sellerId);

}