package com.veevapp.customer.data.remote.response;

import com.veevapp.customer.data.models.SubCategory;

import java.util.List;

public class SubCategoriesResponse {

    private List<SubCategory> subCategories = null;

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

}
