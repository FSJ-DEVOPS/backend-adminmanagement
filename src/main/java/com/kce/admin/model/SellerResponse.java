package com.kce.admin.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "RequestManagement")
public class SellerResponse {

    @Id
    private String id;

    private String name;
    private String email;
    private String status;
    private LocalDateTime respondedDate;

    public SellerResponse() {}

    public SellerResponse(String name, String email, String status, LocalDateTime respondedDate) {
        this.name = name;
        this.email = email;
        this.status = status;
        this.respondedDate = respondedDate;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getStatus() { return status; }
    public LocalDateTime getRespondedDate() { return respondedDate; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setStatus(String status) { this.status = status; }
    public void setRespondedDate(LocalDateTime respondedDate) { this.respondedDate = respondedDate; }
}