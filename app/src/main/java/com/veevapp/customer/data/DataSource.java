package com.veevapp.customer.data;

import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.models.Category;
import com.veevapp.customer.data.models.SubCategory;

import java.util.List;

public abstract class DataSource {

    public interface AddBuyRequestCallback {

        void onSuccess(BuyRequest buyRequest);

        void onFailure();

        void onNetworkFailure();
    }

    public interface GetCategoriesCallback {

        void onSuccess(List<Category> categoryList);

        void onFailure();

        void onNetworkFailure();
    }

    public interface GetSubCategoriesCallback {

        void onSuccess(List<SubCategory> subCategoryList);

        void onFailure();

        void onNetworkFailure();
    }

    public interface GetBuyRequestsCallback {

        void onResponse(List<BuyRequest> buyRequestList);

        void onFailure();

        void onNetworkFailure();
    }

    public abstract void addBuyRequest(BuyRequest request, AddBuyRequestCallback callback);

    public abstract void getAllCategories(GetCategoriesCallback callback);

    public abstract void getAllSubCategories(String categoryID, GetSubCategoriesCallback callback);

    public abstract void getBuyRequests(GetBuyRequestsCallback callback);
}
