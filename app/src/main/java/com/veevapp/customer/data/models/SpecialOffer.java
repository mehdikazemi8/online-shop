package com.veevapp.customer.data.models;

public class SpecialOffer extends BaseModel {
    private Product product;
    private Seller seller;

    public SpecialOffer() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
