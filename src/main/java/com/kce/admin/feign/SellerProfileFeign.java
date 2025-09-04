// File: ShopverseAdmin/src/main/java/com/kce/admin/feign/SellerProfileFeign.java

package com.kce.admin.feign;

import com.kce.admin.config.FeignConfig;
import com.kce.admin.dto.SellerAnalyticsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Map;

@FeignClient(name = "sellerprofile", url = "https://backend-sellerprofile.onrender.com", configuration = FeignConfig.class)
public interface SellerProfileFeign {
    @GetMapping("/api/seller/profile/{email}")
    SellerAnalyticsDTO getSellerProfileByEmail(@PathVariable("email") String email);

    @GetMapping("/api/seller/books/{sellerId}")
    List<Map<String, Object>> getBooksBySellerId(@PathVariable("sellerId") String sellerId);
}
