package com.kce.admin.controller;

import com.kce.admin.dto.UserDTO;
import com.kce.admin.feign.UserAuthClient;
import com.kce.admin.model.Seller;
import com.kce.admin.model.SellerResponse;
import com.kce.admin.repository.SellerEmailRepository;
import com.kce.admin.repository.SellerRepository;
import com.kce.admin.service.AdminSellerService;
import com.kce.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin(origins = "http://localhost:5173 ")
@RequestMapping("/api/admin")
public class UserController {



    private final SellerEmailRepository sellerEmailRepository;

    private final UserService userService;
    private final UserAuthClient userAuthClient;
    //  @Autowired
    private final AdminSellerService adminSellerService;

    public UserController(AdminSellerService adminSellerService,SellerEmailRepository sellerEmailRepository,UserService userService, UserAuthClient userAuthClient) {
        this.userService = userService;
        this.userAuthClient = userAuthClient;
        this.sellerEmailRepository = sellerEmailRepository;
        this.adminSellerService = adminSellerService;
    }

    @Autowired
    private SellerRepository sellerRepository;
    @GetMapping("/sellers/by-email")
    public SellerResponse getSellerByEmail(@RequestParam String email) {
        return sellerEmailRepository.findByEmail(email)
                .orElse(null); // or throw exception if not found
    }


    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userAuthClient.getAllUsers();
    }
    // Fetch all sellers
    @GetMapping("/users/sellers")
    public List<UserDTO> getAllSellers() {
        return userService.fetchAllSellers();
    }
    @GetMapping("/sellers/status/{status}")
    public List<SellerResponse> getSellersByStatus(@PathVariable String status) {
        return adminSellerService.getSellersByStatus(status);
    }




    @GetMapping("/{id:\\d+}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userAuthClient.getUserById(id);
    }


    // FEIGN: Fetch user by email
    @GetMapping("/email/{email}")
    public UserDTO getUserByEmail(@PathVariable String email) {
        return userAuthClient.getUserByEmail(email);
    }

    @GetMapping("/users/sellers-with-status")
    public List<UserDTO> getAllSellersWithStatus() {
        List<UserDTO> sellers = userService.fetchAllSellers();

        // Fetch all processed seller responses from MongoDB
        List<SellerResponse> responses = sellerEmailRepository.findAll();

        // Map email -> status
        Map<String, String> statusMap = responses.stream()
                .collect(Collectors.toMap(SellerResponse::getEmail, SellerResponse::getStatus, (a, b) -> b));

        // Set status in seller DTOs
        return sellers.stream().map(s -> {
            String status = statusMap.getOrDefault(s.getEmail(), "Pending");
            s.setStatus(status); // Add status field in UserDTO if not present
            return s;
        }).collect(Collectors.toList());
    }

    /*@GetMapping("/sellers/status")
    public ResponseEntity<Map<String, List<SellerResponse>>> getSellersByStatus() {
        return ResponseEntity.ok(userService.fetchSellersByStatus());
    }*/

}