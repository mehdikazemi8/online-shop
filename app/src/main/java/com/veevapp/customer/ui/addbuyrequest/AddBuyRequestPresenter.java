package com.veevapp.customer.ui.addbuyrequest;

import android.util.Log;

import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.models.Category;
import com.veevapp.customer.data.models.SubCategory;

import java.util.List;

public class AddBuyRequestPresenter implements AddBuyRequestContract.Presenter {

    private DataRepository dataRepository;
    private AddBuyRequestContract.View addBuyRequestView;

    public AddBuyRequestPresenter(DataRepository dataRepository, AddBuyRequestContract.View addBuyRequestView) {
        this.dataRepository = dataRepository;
        this.addBuyRequestView = addBuyRequestView;
    }

    @Override
    public void start() {
        dataRepository.getAllCategories(new DataSource.GetCategoriesCallback() {
            @Override
            public void onSuccess(List<Category> categoryList) {
                addBuyRequestView.showCategories(categoryList);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onNetworkFailure() {

            }
        });

    }

    @Override
    public void onSubmitBuyRequest(BuyRequest request) {
        dataRepository.addBuyRequest(request, new DataSource.AddBuyRequestCallback() {
            @Override
            public void onSuccess(BuyRequest buyRequest) {
                Log.d("TAG", "buy request " + request.serialize());
                Log.d("TAG", "buy request " + buyRequest.serialize());
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onNetworkFailure() {

            }
        });
    }

    @Override
    public void loadSubCategories(String categoryID) {
        dataRepository.getAllSubCategories(categoryID, new DataSource.GetSubCategoriesCallback() {
            @Override
            public void onSuccess(List<SubCategory> subCategoryList) {
                addBuyRequestView.showSubCategories(subCategoryList);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onNetworkFailure() {

            }
        });
    }
}
