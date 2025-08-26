// File: ShopverseAdmin/src/main/java/com/kce/admin/service/AdminSellerServiceImpl.java
package com.kce.admin.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.kce.admin.model.Seller;
import com.kce.admin.model.SellerResponse;
import com.kce.admin.repository.SellerEmailRepository;
import com.kce.admin.repository.SellerRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kce.admin.dto.DashboardSellerDTO;
import com.kce.admin.dto.SellerAnalyticsDTO;
import com.kce.admin.dto.SellerOrderDTO;
import com.kce.admin.dto.UserDTO;
import com.kce.admin.feign.OrderServiceFeign;
import com.kce.admin.feign.SellerProfileFeign;
import com.kce.admin.feign.UserAuthClient;
import com.kce.admin.model.DailyStat;
import com.shopverse.sellerprofile.entity.Book;

@Service
public class AdminSellerServiceImpl implements AdminSellerService {
    //@Autowired
    // private static final SellerRepository sellerRepository;
    private static final Logger logger = LoggerFactory.getLogger(AdminSellerServiceImpl.class);
    private final UserAuthClient userAuthClient;
    private final SellerProfileFeign sellerProfileFeign;
    private final OrderServiceFeign orderServiceFeign;
    private  SellerRepository sellerRepository;
    @Autowired
    private SellerEmailRepository sellerEmailRepository; // MongoDB repository
    @Autowired
    public AdminSellerServiceImpl(UserAuthClient userAuthClient, SellerProfileFeign sellerProfileFeign, OrderServiceFeign orderServiceFeign) {
        this.userAuthClient = userAuthClient;
        this.sellerProfileFeign = sellerProfileFeign;
        this.orderServiceFeign = orderServiceFeign;
    }
    @Override
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    @Override
    public List<DashboardSellerDTO> getAllSellersForAdmin() {

        List<UserDTO> sellersFromAuth = userAuthClient.getAllUsers()
                .stream()
                .filter(user -> "SELLER".equalsIgnoreCase(user.getRole()))
                .toList();

        return sellersFromAuth.stream().map(sellerAuth -> {
                    try {
                        SellerAnalyticsDTO sellerProfile = sellerProfileFeign.getSellerProfileByEmail(sellerAuth.getEmail());

                        DashboardSellerDTO finalView = new DashboardSellerDTO();
                        BeanUtils.copyProperties(sellerProfile, finalView);

                        finalView.setSellerName(sellerAuth.getUsername());
                        finalView.setSellerEmail(sellerAuth.getEmail());
                        finalView.setUniqueId(sellerAuth.getUniqueId());
                        if (sellerAuth.getPhoneNumber() != null) {
                            finalView.setSellerPhone(sellerAuth.getPhoneNumber().toString());
                        }

                        return finalView;
                    } catch (Exception e) {
                        System.err.println("Could not fetch profile for seller: " + sellerAuth.getEmail());
                        return null;
                    }
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }

    @Override
    public SellerAnalyticsDTO getSellerAnalytics(String sellerId) {
        logger.debug("🔍 Starting getSellerAnalytics for sellerId: {}", sellerId);

        logger.debug("📞 Calling UserAuthClient.getUserByUniqueId() for sellerId: {}", sellerId);
        UserDTO user = userAuthClient.getUserByUniqueId(sellerId);
        logger.debug("📋 UserAuthClient response - user: {}", user);

        if (user == null) {
            logger.error("❌ User not found for sellerId: {}", sellerId);
            throw new RuntimeException("User not found for sellerId: " + sellerId);
        }
        logger.debug("✅ User found - email: {}, username: {}", user.getEmail(), user.getUsername());

        logger.debug("📞 Calling SellerProfileFeign.getSellerProfileByEmail() for email: {}", user.getEmail());
        SellerAnalyticsDTO profile = sellerProfileFeign.getSellerProfileByEmail(user.getEmail());
        logger.debug("📋 SellerProfileFeign response - profile: {}", profile);

        if (profile == null) {
            logger.error("❌ Seller profile not found for email: {}", user.getEmail());
            throw new RuntimeException("Seller profile not found for email: " + user.getEmail());
        }
        logger.debug("✅ Seller profile found - sellerId: {}, storeName: {}", profile.getSellerId(), profile.getStoreName());

        try {

            logger.debug("📞 Calling OrderServiceFeign.getOrdersBySellerId() for sellerId: {}", sellerId);
            List<SellerOrderDTO> orders = orderServiceFeign.getOrdersBySellerId(sellerId);
            logger.debug("📋 OrderServiceFeign response - orders count: {}, orders: {}",
                    orders != null ? orders.size() : 0, orders);

            // Enrich orders with usernames
            logger.debug("🔧 Enriching orders with usernames");
            if (orders != null && !orders.isEmpty()) {
                List<UserDTO> allUsers = userAuthClient.getAllUsers();

                for (SellerOrderDTO order : orders) {
                    if (order.getUserId() != null) {
                        // Find username by userId
                        UserDTO orderUser = allUsers.stream()
                                .filter(u -> order.getUserId().equals(String.valueOf(u.getId())))
                                .findFirst()
                                .orElse(null);

                        if (orderUser != null) {
                            order.setUsername(orderUser.getUsername());
                            order.setUserEmail(orderUser.getEmail());
                            logger.debug("✅ Set username '{}' and email '{}' for userId '{}'",
                                    orderUser.getUsername(), orderUser.getEmail(), order.getUserId());
                        } else {
                            order.setUsername("Unknown User");
                            order.setUserEmail("No email");
                            logger.warn("⚠️ Could not find username for userId: {}", order.getUserId());
                        }
                    } else {
                        order.setUsername("No User ID");
                        order.setUserEmail("No email");
                        logger.warn("⚠️ Order {} has null userId", order.getOrderId());
                    }
                }
            }
            logger.debug("✅ Completed username enrichment for orders");

            logger.debug("🔧 Creating and populating SellerAnalyticsDTO");
            SellerAnalyticsDTO analyticsDTO = new SellerAnalyticsDTO();

            analyticsDTO.setSellerId(profile.getSellerId());
            analyticsDTO.setStoreName(profile.getStoreName());
            analyticsDTO.setSellerName(user.getUsername());
            analyticsDTO.setSellerEmail(user.getEmail());
            analyticsDTO.setSellerPhone(profile.getSellerPhone());
            analyticsDTO.setAddress(profile.getAddress());

            List<Book> uploadedBooks = sellerProfileFeign.getBooksBySellerId(sellerId);
            analyticsDTO.setUploadedProducts(uploadedBooks != null ? uploadedBooks.size() : 0);

            analyticsDTO.setSellerPhoto(profile.getSellerPhoto());
            logger.debug("✅ Populated basic seller info - sellerId: {}, storeName: {}, sellerName: {}",
                    analyticsDTO.getSellerId(), analyticsDTO.getStoreName(), analyticsDTO.getSellerName());

            analyticsDTO.setOrders(orders);
            logger.debug("✅ Set orders in analyticsDTO - orders count: {}", orders != null ? orders.size() : 0);

            double totalSales = (orders != null) ? orders.stream().mapToDouble(SellerOrderDTO::getTotalPrice).sum() : 0.0;
            analyticsDTO.setTotalSales(totalSales);
            analyticsDTO.setReturned(0);
            analyticsDTO.setCancelled(0);
            logger.debug("✅ Calculated aggregate stats - totalSales: {}", totalSales);

            logger.debug("🔧 Calculating daily sales summary");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            Map<LocalDate, List<SellerOrderDTO>> salesByDate = (orders != null) ?
                    orders.stream()
                            .filter(order -> order.getOrderDate() != null)
                            .collect(Collectors.groupingBy(order -> {
                                try {

                                    String dateStr = order.getOrderDate().substring(0, 10);
                                    logger.debug("🗓️ Parsing date: {}", dateStr);
                                    return LocalDate.parse(dateStr, formatter);
                                } catch (Exception e) {
                                    logger.warn("⚠️ Failed to parse date: {}, error: {}", order.getOrderDate(), e.getMessage());

                                    return LocalDate.now();
                                }
                            })) : new HashMap<>();
            logger.debug("📊 Sales grouped by date - entries count: {}", salesByDate.size());

            List<DailyStat> dailyStats = salesByDate.entrySet().stream()
                    .map(entry -> new DailyStat(entry.getKey(), entry.getValue().size(), 0, 0)) // (date, sales, returns, cancelled)
                    .collect(Collectors.toList());
            analyticsDTO.setSalesSummary(dailyStats);
            logger.debug("✅ Created daily stats - dailyStats count: {}", dailyStats.size());

            logger.debug("🎉 Successfully created SellerAnalyticsDTO for sellerId: {}", sellerId);
            return analyticsDTO;

        } catch(Exception e) {
            logger.error("❌ Error in getSellerAnalytics for sellerId: {}, error: {}", sellerId, e.getMessage(), e);
            throw new RuntimeException("Error in analytics "+ e);
        }
    }
    private String getSellerEmailById(String sellerId) {

        UserDTO user = userAuthClient.getAllUsers().stream()
                .filter(u -> sellerId.equals(u.getUniqueId()))
                .findFirst()
                .orElse(null);
        return (user != null) ? user.getEmail() : null;
    }

    //    @Override
//    public Optional<Seller> getSellerById(String id) {
//        return sellerRepository.findById(id);
//    }
//
    //@Override
    //public Seller saveSeller(Seller seller) {
    //   return sellerRepository.save(seller);
    // }
    @Override
    public List<UserDTO> fetchAllSellers() {
        try {
            // Get sellers directly from UserAuth service
            List<UserDTO> allSellers = userAuthClient.fetchAllSellers();

            // Get already present seller IDs from MongoDB
            Set<String> existingSellerIds = sellerEmailRepository.findAll()
                    .stream()
                    .map(SellerResponse::getId) // match your MongoDB entity
                    .collect(Collectors.toSet());

            // Filter out sellers already present in MongoDB
            return allSellers.stream()
                    .filter(seller -> !existingSellerIds.contains(seller.getId()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error fetching sellers from UserAuth service: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    //
//    @Override
//    public void deleteSeller(String id) {
//        sellerRepository.deleteById(id);
//    }
//
//    @Override
//    public List<SellerWithOrdersDTO> getAllSellersWithOrders() {
//        System.out.println("🔍 Calling Feign client to fetch all sellers...");
//
//        List<DashboardSellerDTO> dashboardSellers;
//        try {
//            dashboardSellers = sellerDashboardClient.getAllSellers();
//            System.out.println("✅ Fetched " + dashboardSellers.size() + " sellers from dashboard.");
//        } catch (Exception e) {
//            System.out.println("❌ Error fetching sellers from dashboard: " + e.getMessage());
//            e.printStackTrace();
//            return List.of();
//        }
//
//        return dashboardSellers.stream().map(dto -> {
//            Seller seller = new Seller();
//            seller.setId(dto.getSellerId());
//            seller.setSellerName(dto.getSellerName());
//            seller.setStoreName(dto.getStoreName());
//            seller.setEmail(dto.getSellerEmail());
//            seller.setPhone(dto.getSellerPhone());
//            seller.setAddress(dto.getAddress());
//            seller.setCity(dto.getCity());
//            seller.setState(dto.getState());
//            seller.setCountry(dto.getCountry());
//            seller.setZipCode(dto.getPostalCode());
//
//            return new SellerWithOrdersDTO(seller); // Empty order list
//        }).collect(Collectors.toList());
//    }
//
//    @Override
//    public SellerWithOrdersDTO getSellerWithOrders(String sellerId) {
//        Seller seller = sellerRepository.findById(sellerId)
//                .orElseThrow(() -> new RuntimeException("Seller not found"));
//
//        try {
//            DashboardSellerDTO dto = sellerDashboardClient.getSellerByEmail(seller.getEmail()); // FIXED
//            seller.setSellerName(dto.getSellerName());
//            seller.setStoreName(dto.getStoreName());
//            seller.setPhone(dto.getSellerPhone());
//            seller.setAddress(dto.getAddress());
//            seller.setCity(dto.getCity());
//            seller.setState(dto.getState());
//            seller.setCountry(dto.getCountry());
//            seller.setZipCode(dto.getPostalCode());
//        } catch (Exception e) {
//            System.out.println("⚠ Could not fetch profile for email: " + seller.getEmail());
//        }
//
//        return new SellerWithOrdersDTO(seller); // Empty order list
//    }
    @Override
    public List<SellerResponse> getSellersByStatus(String status) {
        return sellerEmailRepository.findByStatus(status);
    }

    @Override
    @Transactional
    public SellerResponse saveSellerResponse(SellerResponse response) {
        logger.info("Attempting to save seller response for email: {} with status: {}", response.getEmail(), response.getStatus());

        Optional<SellerResponse> existingResponse = sellerEmailRepository.findByEmailAndStatus(response.getEmail(), response.getStatus());

        if (existingResponse.isPresent()) {
            logger.warn("Duplicate entry detected. Seller response for email: {} and status: {} already exists. Skipping save.", response.getEmail(), response.getStatus());
            return existingResponse.get();
        }

        SellerResponse savedResponse = sellerEmailRepository.save(response);
        logger.info("Successfully saved new seller response with ID: {}", savedResponse.getId());
        return savedResponse;
    }

    @Override
    public Optional<SellerResponse> findSellerResponseByEmailAndStatus(String email, String status) {
        return sellerEmailRepository.findByEmailAndStatus(email, status);
    }
}