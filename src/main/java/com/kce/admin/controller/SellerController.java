package com.kce.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kce.admin.dto.DashboardSellerDTO;
import com.kce.admin.dto.SellerAnalyticsDTO;
import com.kce.admin.service.AdminSellerService;

@RestController
@RequestMapping("/api/admin/seller")
public class SellerController {

    private static final Logger logger = LoggerFactory.getLogger(SellerController.class);
    private final AdminSellerService sellerService;

    @Autowired
    public SellerController(AdminSellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping
    public ResponseEntity<List<DashboardSellerDTO>> getAllSellersForAdmin() {
        List<DashboardSellerDTO> sellers = sellerService.getAllSellersForAdmin();
        System.out.println(sellers.toString());
        return ResponseEntity.ok(sellers);
    }

    @GetMapping("/{sellerId}/analytics")
    public ResponseEntity<SellerAnalyticsDTO> getSellerAnalytics(@PathVariable String sellerId) {
        logger.debug("Starting seller analytics request for sellerId: {}", sellerId);
        try {
            logger.debug("Calling sellerService.getSellerAnalytics() for sellerId: {}", sellerId);
            SellerAnalyticsDTO analytics = sellerService.getSellerAnalytics(sellerId);
            logger.debug("Successfully retrieved analytics for sellerId: {}, analytics: {}", sellerId, analytics);
            return ResponseEntity.ok(analytics);
        } catch (Exception e) {
            logger.error("Error in getSellerAnalytics for sellerId: {}, error: {}", sellerId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


//    @GetMapping("/all")
//    public ResponseEntity<List<SellerWithOrdersDTO>> getAllSellersWithOrders() {
//        try {
//            List<SellerWithOrdersDTO> sellersWithOrders = sellerService.getAllSellersWithOrders();
//            return ResponseEntity.ok(sellersWithOrders);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(null);
//        }
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getSeller(@PathVariable String id) {
//        Optional<Seller> seller = sellerService.getSellerById(id);
//        if (seller.isPresent()) {
//            return ResponseEntity.ok(seller.get());
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("❌ Seller not found with ID: " + id);
//        }
//    }
//
//    @GetMapping("/{id}/with-orders")
//    public ResponseEntity<?> getSellerWithOrders(@PathVariable String id) {
//        try {
//            SellerWithOrdersDTO dto = sellerService.getSellerWithOrders(id);
//            if (dto == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body("❌ Seller with orders not found for ID: " + id);
//            }
//            return ResponseEntity.ok(dto);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("⚠ Error retrieving seller with orders: " + e.getMessage());
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) {
//        Seller savedSeller = sellerService.saveSeller(seller);
//        return ResponseEntity.ok(savedSeller);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteSeller(@PathVariable String id) {
//        Optional<Seller> seller = sellerService.getSellerById(id);
//        if (seller.isPresent()) {
//            sellerService.deleteSeller(id);
//            return ResponseEntity.ok("✅ Seller deleted successfully with ID: " + id);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("❌ Cannot delete. Seller not found with ID: " + id);
//        }
//    }
}