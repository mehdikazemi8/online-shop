package com.veevapp.customer.data.models;

import com.google.gson.annotations.SerializedName;

public class SpecialOffer extends BaseModel {
    private Product product;
    @SerializedName("owner")
    private Seller seller;

    private Integer duration;
    private Integer suggestedPrice;
    private String expirationTime;
    @SerializedName("creation_date")
    private String creationDate;
    private Double remainingTime;

    public SpecialOffer() {
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getSuggestedPrice() {
        return suggestedPrice;
    }

    public void setSuggestedPrice(Integer suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Double getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(Double remainingTime) {
        this.remainingTime = remainingTime;
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
