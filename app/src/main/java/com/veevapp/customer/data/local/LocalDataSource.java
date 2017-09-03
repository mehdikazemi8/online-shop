package com.veevapp.customer.data.local;

import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.models.BuyRequest;

public class LocalDataSource extends DataSource {

    @Override
    public void addBuyRequest(BuyRequest request, AddBuyRequestCallback callback) {

    }

    @Override
    public void getAllCategories(GetCategoriesCallback callback) {

    }

    @Override
    public void getAllSubCategories(String categoryID, GetSubCategoriesCallback callback) {

    }

    @Override
    public void getBuyRequests(GetBuyRequestsCallback callback) {

    }

    @Override
    public void getOffersOfOneBuyRequest(String buyRequestID, GetOffersCallback callback) {

    }
}
