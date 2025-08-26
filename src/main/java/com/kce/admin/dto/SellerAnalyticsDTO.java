package com.kce.admin.dto;

import com.kce.admin.model.DailyStat;
import lombok.Data;
import java.util.List;

@Data
public class SellerAnalyticsDTO {
    private String sellerId;
    private String uniqueId;
    private String storeName;
    private String sellerName;
    private String sellerEmail;
    private String sellerPhone;
    private String address;
    private int uploadedProducts;
    private List<SellerOrderDTO> orders;
    private List<DailyStat> salesSummary;
    private double totalSales;
    private int returned;
    private int cancelled;
    private String sellerPhoto;


}