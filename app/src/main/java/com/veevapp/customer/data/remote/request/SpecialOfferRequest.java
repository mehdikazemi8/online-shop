package com.veevapp.customer.data.remote.request;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class SpecialOfferRequest {
    private String categoryID;
    private String subCategoryID;
    private Integer priceFrom;
    private Integer priceTo;
    private String sort;


    public String getCategoryID() {
        return categoryID;
    }
    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getSubCategoryID() {
        return subCategoryID;
    }
    public void setSubCategoryID(String subCategoryID) {
        this.subCategoryID = subCategoryID;
    }

    public Integer getPriceFrom() {
        return priceFrom;
    }
    public void setPriceFrom(Integer priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Integer getPriceTo() {
        return priceTo;
    }
    public void setPriceTo(Integer priceTo) {
        this.priceTo = priceTo;
    }

    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
}