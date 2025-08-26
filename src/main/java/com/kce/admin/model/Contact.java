package com.kce.admin.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ContactManagement")
public class Contact {

    @Id
    private String id;
    private String address;
    private String phone;
    private String email;
    private String businessHours;

    public Contact() {
    }

    public Contact(String id, String address, String phone, String email, String businessHours) {
        this.id = id;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.businessHours = businessHours;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }
}