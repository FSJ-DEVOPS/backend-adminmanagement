package com.kce.admin.feign;

import com.kce.admin.config.FeignConfig;
import com.kce.admin.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "userauth", configuration = FeignConfig.class)
public interface UserAuthClient {

    @GetMapping("/api/admin/users")
    List<UserDTO> getAllUsers();

    @GetMapping("/api/admin/users/sellers")
    List<UserDTO> fetchAllSellers();
    @GetMapping("/api/admin/users/sellers/status/{status}")
    List<UserDTO> getSellersByStatus(@PathVariable("status") String status);
    @GetMapping("/users/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);

    @GetMapping("/users/email/{email}")
    UserDTO getUserByEmail(@PathVariable("email") String email);

    @GetMapping("/api/admin/users/unique/{uniqueId}")
    UserDTO getUserByUniqueId(@PathVariable("uniqueId") String uniqueId);
}