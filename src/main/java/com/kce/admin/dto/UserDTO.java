package com.kce.admin.dto;

import java.time.LocalDate;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Long phoneNumber;
    private String role;
    private String panNumber;
    private String panCard;
    private String panName;
    private String uniqueId;
    private LocalDate dob;
    private boolean subs;
    private String status;

    // Full constructor with all fields
    public UserDTO(Long id, String username, String email, Long phoneNumber, String role,
                   String panNumber, String panCard, String panName, String uniqueId,
                   LocalDate dob, boolean subs,String status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.panNumber = panNumber;
        this.panCard = panCard;
        this.panName = panName;
        this.uniqueId = uniqueId;
        this.dob = dob;
        this.subs = subs;
        this.status=status;
    }

    // No-args constructor
    public UserDTO() {}

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Long getPhoneNumber() { return phoneNumber; }
    public String getRole() { return role; }
    public String getPanNumber() { return panNumber; }
    public String getPanCard() { return panCard; }
    public String getPanName() { return panName; }
    public String getUniqueId() { return uniqueId; }
    public LocalDate getDob() { return dob; }
    public boolean isSubs() { return subs; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(Long phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setRole(String role) { this.role = role; }
    public void setPanNumber(String panNumber) { this.panNumber = panNumber; }
    public void setPanCard(String panCard) { this.panCard = panCard; }
    public void setPanName(String panName) { this.panName = panName; }
    public void setUniqueId(String uniqueId) { this.uniqueId = uniqueId; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public void setSubs(boolean subs) { this.subs = subs; }
}