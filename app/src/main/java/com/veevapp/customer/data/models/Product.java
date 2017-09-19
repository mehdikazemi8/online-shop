package com.veevapp.customer.data.models;

import java.util.ArrayList;
import java.util.List;

public class Product extends BaseModel {

    private String id;
    private String categoryID;
    private String subCategory;
    private String status;
    private String name;
    private String guarantee;
    private String price;
    private String currency;
    private List<String> photoURLs = null;
    private List<Property> properties = null;

    public void addPhoto(String base64Photo) {
        if (photoURLs == null) {
            photoURLs = new ArrayList<>();
        }

        photoURLs.add(base64Photo);
    }

    public Product(String categoryID, String subCategory, String name) {
        this.categoryID = categoryID;
        this.subCategory = subCategory;
        this.name = name;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<String> getPhotoURLs() {
        return photoURLs;
    }

    public void setPhotoURLs(List<String> photoURLs) {
        this.photoURLs = photoURLs;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

}