package com.veevapp.customer.data.remote.response;

import com.veevapp.customer.data.models.BaseModel;
import com.veevapp.customer.data.models.Category;

import java.util.List;

public class CategoriesResponse extends BaseModel {
    List<Category> categories;

    public CategoriesResponse(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
