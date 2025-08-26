package com.kce.admin.service;

import com.kce.admin.dto.UserDTO;
import com.kce.admin.feign.UserAuthClient;
import com.kce.admin.model.SellerResponse;
import com.kce.admin.repository.SellerEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserAuthClient userAuthClient;

    @Override
    public List<UserDTO> fetchAllRegisteredUsers() {
        try {
            // Fetch all users from UserAuth service and filter only BUYERS
            return userAuthClient.getAllUsers()
                    .stream()
                    .filter(user -> "BUYER".equalsIgnoreCase(user.getRole()))
                    .toList();
        } catch (Exception e) {
            System.err.println("Error fetching users from userAuth service: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Autowired
    private SellerEmailRepository sellerEmailRepository; // MongoDB repo

    public List<UserDTO> fetchAllSellers() {
        try {
            // 1. Fetch all SELLER users from UserAuth
            List<UserDTO> allSellers = userAuthClient.getAllUsers()
                    .stream()
                    .filter(user -> "SELLER".equalsIgnoreCase(user.getRole()))
                    .collect(Collectors.toList());

            // 2. Get all seller emails already in MongoDB
            List<String> existingEmails = sellerEmailRepository.findAll()
                    .stream()
                    .map(SellerResponse::getEmail) // Updated here
                    .collect(Collectors.toList());

            // 3. Filter out sellers already in MongoDB
            return allSellers.stream()
                    .filter(seller -> !existingEmails.contains(seller.getEmail()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error fetching sellers: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    @Override
    public UserDTO fetchUserById(Long id) {
        return userAuthClient.getUserById(id);
    }

    @Override
    public UserDTO fetchUserByEmail(String email) {
        return userAuthClient.getUserByEmail(email);
    }
}