package com.veevapp.customer.data.remote.response;

import com.veevapp.customer.data.models.Category;

import java.util.List;

public class CategoriesResponse {
    List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
