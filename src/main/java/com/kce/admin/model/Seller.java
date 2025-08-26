package com.kce.admin.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;


public class Seller {

    @Id
    private String id;

    private String storeName;
    private String sellerName;
    private String email;
    private String phone;
    private String companyName;
    private String businessType;
    private String country;
    private String city;
    private String registrationNumber;
    private String address;
    private String state;
    private String zipCode;
    private String image;
    private String sellerPhoto;

    private int uploaded;
    private int sales;
    private int returned;
    private int cancelled;



    @JsonInclude(JsonInclude.Include.ALWAYS)
    private List<DailyStat> dailyStats = new ArrayList<>();

    // Default constructor
    public Seller() {}

    // All-args constructor
    public Seller(String id, String storeName, String sellerName, String email, String phone,
                  String companyName, String businessType, String country, String city,
                  String registrationNumber, String address, String state, String zipCode,
                  String image, String sellerPhoto, int uploaded, int sales, int returned, int cancelled,
                  List<DailyStat> dailyStats) {
        this.id = id;
        this.storeName = storeName;
        this.sellerName = sellerName;
        this.email = email;
        this.phone = phone;
        this.companyName = companyName;
        this.businessType = businessType;
        this.country = country;
        this.city = city;
        this.registrationNumber = registrationNumber;
        this.address = address;
        this.state = state;
        this.zipCode = zipCode;
        this.image = image;
        this.sellerPhoto = sellerPhoto;
        this.uploaded = uploaded;
        this.sales = sales;
        this.returned = returned;
        this.cancelled = cancelled;

        this.dailyStats = dailyStats;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }

    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }



    public int getUploaded() { return uploaded; }
    public void setUploaded(int uploaded) { this.uploaded = uploaded; }

    public int getSales() { return sales; }
    public void setSales(int sales) { this.sales = sales; }

    public int getReturned() { return returned; }
    public void setReturned(int returned) { this.returned = returned; }

    public int getCancelled() { return cancelled; }
    public void setCancelled(int cancelled) { this.cancelled = cancelled; }


    public List<DailyStat> getDailyStats() { return dailyStats; }
    public void setDailyStats(List<DailyStat> dailyStats) { this.dailyStats = dailyStats; }
}