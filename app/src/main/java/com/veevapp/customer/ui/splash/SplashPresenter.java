package com.veevapp.customer.ui.splash;

import android.os.Handler;

import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.local.PreferenceManager;

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

    }

    private void checkToken() {
        splashView.showMainPageUI();
    }
}
