package com.veevapp.customer.data.models;

import java.util.List;

public class Seller extends BaseModel {
    private String id;

    private String name;
    private String family;
    private String mobile;
    private String telegram;
    private String sellerPhotoUrl;
    private Boolean sentInfo;

    private String shopName;
    private String shopPhoneNumber;
    private String shopAddress;
    private List<Double> location;
    private String licencePhotoUrl;

    private float rate;
    private String timeframeHours;

    private String categoryID;

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public Boolean getSentInfo() {
        return sentInfo;
    }

    public void setSentInfo(Boolean sentInfo) {
        this.sentInfo = sentInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getShopPhoneNumber() {
        return shopPhoneNumber;
    }

    public void setShopPhoneNumber(String shopPhoneNumber) {
        this.shopPhoneNumber = shopPhoneNumber;
    }

    public String getSellerMobileNumber() {
        return mobile;
    }

    public void setSellerMobileNumber(String sellerMobileNumber) {
        this.mobile = sellerMobileNumber;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getSellerPhotoUrl() {
        return sellerPhotoUrl;
    }

    public void setSellerPhotoUrl(String sellerPhotoUrl) {
        this.sellerPhotoUrl = sellerPhotoUrl;
    }

    public String getLicencePhotoUrl() {
        return licencePhotoUrl;
    }

    public void setLicencePhotoUrl(String licencePhotoUrl) {
        this.licencePhotoUrl = licencePhotoUrl;
    }

    public String getTimeframeHours() {
        return timeframeHours;
    }

    public void setTimeframeHours(String timeframeHours) {
        this.timeframeHours = timeframeHours;
    }
}
