package com.kce.admin.dto;

import java.util.List;

import lombok.Data;

@Data
public class SellerOrderDTO {
    private String orderId;
    private String userId;
    private String username; // Added field for displaying user's name
    private String userEmail; // Added field for displaying user's email
    private double totalPrice;
    private String orderStatus;
    private String address;
    private String orderDate;
    private List<OrderItemDTO> products;



}
