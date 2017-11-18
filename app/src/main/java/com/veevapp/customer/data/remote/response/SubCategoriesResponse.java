package com.veevapp.customer.data.remote.response;

import com.veevapp.customer.data.models.BaseModel;
import com.veevapp.customer.data.models.SubCategory;

import java.util.List;

public class SubCategoriesResponse extends BaseModel {

    public SubCategoriesResponse(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    private List<SubCategory> subCategories = null;

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

}
