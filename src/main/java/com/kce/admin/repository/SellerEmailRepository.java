package com.kce.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kce.admin.model.SellerResponse;

import java.util.List;
import java.util.Optional;

public interface SellerEmailRepository extends MongoRepository<SellerResponse, String> {
    List<SellerResponse> findByStatus(String status);
    Optional<SellerResponse> findByEmail(String email);

    Optional<SellerResponse> findByEmailAndStatus(String email, String status);
    boolean existsByEmailAndStatus(String email, String status);
}