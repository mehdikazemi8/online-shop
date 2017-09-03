package com.veevapp.customer.data.models;

public class BuyRequestOffer {

    private String id;
    private Integer suggestedPrice;
    private String description;

    public BuyRequestOffer(String id, Integer suggestedPrice, String description) {
        this.id = id;
        this.suggestedPrice = suggestedPrice;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSuggestedPrice() {
        return suggestedPrice;
    }

    public void setSuggestedPrice(Integer suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}