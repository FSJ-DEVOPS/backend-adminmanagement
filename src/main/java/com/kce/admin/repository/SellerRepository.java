package com.kce.admin.repository;

import com.kce.admin.model.Seller;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends MongoRepository<Seller, String> {
    // You can add custom query methods here if needed, e.g.:
    // Optional<Seller> findByEmail(String email);
}