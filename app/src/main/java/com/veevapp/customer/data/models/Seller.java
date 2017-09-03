package com.veevapp.customer.data.models;

public class Seller extends BaseModel {
    private String id;

    private String name;
    private String family;
    private String sellerMobileNumber;
    private String telegramID;
    private String sellerPhotoUrl;
    private Boolean sentInfo;

    private String shopName;
    private String shopPhoneNumber;
    private String shopAddress;
    private Location location;
    private String licencePhotoUrl;

    private Double rate;
    private String timeframeHours;

    private String categoryID;

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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getShopPhoneNumber() {
        return shopPhoneNumber;
    }

    public void setShopPhoneNumber(String shopPhoneNumber) {
        this.shopPhoneNumber = shopPhoneNumber;
    }

    public String getSellerMobileNumber() {
        return sellerMobileNumber;
    }

    public void setSellerMobileNumber(String sellerMobileNumber) {
        this.sellerMobileNumber = sellerMobileNumber;
    }

    public String getTelegramID() {
        return telegramID;
    }

    public void setTelegramID(String telegramID) {
        this.telegramID = telegramID;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
