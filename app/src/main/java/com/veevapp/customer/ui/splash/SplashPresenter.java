package com.veevapp.customer.ui.splash;

import android.os.Handler;
import android.util.Log;

import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.local.PreferenceManager;
import com.veevapp.customer.data.models.Category;
import com.veevapp.customer.data.models.Customer;
import com.veevapp.customer.data.models.SubCategory;
import com.veevapp.customer.data.remote.response.CategoriesResponse;
import com.veevapp.customer.data.remote.response.SubCategoriesResponse;

import java.util.List;

public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View splashView;
    private PreferenceManager preferenceManager;
    private DataRepository dataRepository;
    private int successSubCategoryCount = 0;

    public SplashPresenter(DataRepository dataRepository, PreferenceManager preferenceManager, SplashContract.View splashView) {
        this.dataRepository = dataRepository;
        this.preferenceManager = preferenceManager;
        this.splashView = splashView;
    }

    @Override
    public void start() {
        Handler handler = new Handler();
        handler.postDelayed(() -> syncCategories(), 1000);
    }

    private void syncCategories() {
        dataRepository.getAllCategories(new DataSource.GetCategoriesCallback() {
            @Override
            public void onSuccess(List<Category> categoryList) {
                preferenceManager.putCategories(new CategoriesResponse(categoryList));
                syncSubCategories(categoryList);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onNetworkFailure() {

            }
        });
    }

    private void syncSubCategories(List<Category> categoryList) {
        for (Category category : categoryList) {
            dataRepository.getAllSubCategories(category.getId(), new DataSource.GetSubCategoriesCallback() {
                @Override
                public void onSuccess(List<SubCategory> subCategoryList) {

                    preferenceManager.putSubCategory(category.getId(), new SubCategoriesResponse(subCategoryList));

                    successSubCategoryCount++;
                    if (successSubCategoryCount == categoryList.size()) {
                        syncSellerInfo();
                    }
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

    private void syncSellerInfo() {
        dataRepository.getCustomerInfo(new DataSource.GetCustomerInfoCallback() {

            @Override
            public void onResponse(Customer customer) {
                preferenceManager.putCustomerInfo(customer);
                if (customer.sentInfo()) {
                    splashView.showMainPageUI();
                } else {
                    splashView.showRegisterUI();
                }
            }

            @Override
            public void onFailure() {
                Log.d("TAG", "onFailure");
            }

            @Override
            public void onNetworkFailure() {
                Log.d("TAG", "onNetworkFailure");
            }
        });
    }

    private void checkToken() {
        if (preferenceManager.getTokenResponse() != null) {
            syncSellerInfo();
        } else {
            splashView.showEnterMobileUI();
        }
    }
}
