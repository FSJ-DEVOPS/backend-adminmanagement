package com.kce.admin.service;
import com.kce.admin.dto.DashboardSellerDTO;
import com.kce.admin.dto.SellerAnalyticsDTO;
import com.kce.admin.dto.SellerWithOrdersDTO;
import com.kce.admin.dto.UserDTO;
import com.kce.admin.model.Seller;
import com.kce.admin.model.SellerResponse;

import java.util.List;
import java.util.Optional;

public interface AdminSellerService {
    List<DashboardSellerDTO> getAllSellersForAdmin();
    SellerAnalyticsDTO getSellerAnalytics(String sellerId);
    List<Seller> getAllSellers();
    List<UserDTO> fetchAllSellers();
    List<SellerResponse> getSellersByStatus(String status);

    SellerResponse saveSellerResponse(SellerResponse response);
    Optional<SellerResponse> findSellerResponseByEmailAndStatus(String email, String status);

//    Optional<Seller> getSellerById(String id);
    //Seller saveSeller(Seller seller);
//    void deleteSeller(String id);
//    SellerWithOrdersDTO getSellerWithOrders(String sellerId);
//
//    List<SellerWithOrdersDTO> getAllSellersWithOrders();
}