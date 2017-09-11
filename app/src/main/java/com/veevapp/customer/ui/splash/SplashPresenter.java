package com.veevapp.customer.ui.splash;

import android.os.Handler;
import android.util.Log;

import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.local.PreferenceManager;
import com.veevapp.customer.data.models.Customer;

public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View splashView;
    private PreferenceManager preferenceManager;
    private DataRepository dataRepository;

    public SplashPresenter(DataRepository dataRepository, PreferenceManager preferenceManager, SplashContract.View splashView) {
        this.dataRepository = dataRepository;
        this.preferenceManager = preferenceManager;
        this.splashView = splashView;
    }

    @Override
    public void start() {
        Handler handler = new Handler();
        handler.postDelayed(() -> checkToken(), 1000);
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
