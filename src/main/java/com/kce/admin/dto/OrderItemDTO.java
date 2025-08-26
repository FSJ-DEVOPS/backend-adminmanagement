package com.kce.admin.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private String productId;
    private String sellerId;
    private String title;
    private String productImage;
    private int quantity;
    private double price;
}