package com.kce.admin.dto;

import com.kce.admin.model.Seller;
import java.util.List;

public class SellerWithOrdersDTO {
    private Seller seller;

    public SellerWithOrdersDTO(Seller seller) {
        this.seller = seller;
    }



    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }


}