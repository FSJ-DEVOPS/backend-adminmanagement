package com.kce.admin.service;

import com.kce.admin.dto.UserDTO;

import java.util.List;

public interface UserService {

    // Fetch all registered users
    List<UserDTO> fetchAllRegisteredUsers();

    // Fetch all sellers
    List<UserDTO> fetchAllSellers();

    // Fetch single user by ID
    UserDTO fetchUserById(Long id);

    // Fetch single user by Email
    UserDTO fetchUserByEmail(String email);
}