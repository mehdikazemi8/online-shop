package com.veevapp.customer.data.models;

import com.google.gson.annotations.SerializedName;
import com.veevapp.customer.view.adapter.BaseRecyclerAdapter;

public class SpecialOffer extends BaseModel implements BaseRecyclerAdapter.IDiff {
    private Product product;
    @SerializedName("owner")
    private Seller seller;

    private Integer duration;
    private Integer suggestedPrice;
    private String expirationTime;
    @SerializedName("creation_date")
    private String creationDate;
    private Double remainingTime;

    public long createdObjTimestamp = 0;
    public SpecialOffer(){
        createdObjTimestamp = System.currentTimeMillis();
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

    @Override
    public boolean areContentsTheSame(Object thisType) {
        return this.equals(thisType);
    }

    @Override
    public boolean areItemsTheSame(Object thisType) {
        return this.product.getId().equals(((SpecialOffer)thisType).getProduct().getId());
    }
}
