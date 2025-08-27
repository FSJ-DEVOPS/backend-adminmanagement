// File: ShopverseAdmin/src/main/java/com/kce/admin/dto/BookDTO.java

package com.kce.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String id;
    private String title;
    private String author;
    private String sellerId;
    private String genre;
    private String isbn;
    private double price;
    private int stock;
    private String sellerName;
}