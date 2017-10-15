package com.veevapp.customer.data.models;

public class BuyRequest extends BaseModel {

    private String id;
    private String description;
    private Product product;
    private String color;
    private int offerCount;
    private int count;
    private String status;
    private String creation_date;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getOfferCount() {
        return offerCount;
    }

    public void setOfferCount(int count) {
        this.offerCount = count;
    }

}
