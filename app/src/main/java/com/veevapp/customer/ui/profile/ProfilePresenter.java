package com.veevapp.customer.ui.profile;

import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.local.PreferenceManager;

public class ProfilePresenter implements ProfileContract.Presenter {

    private DataRepository dataRepository;
    private PreferenceManager preferenceManager;
    private ProfileContract.View profileView;

    public ProfilePresenter(DataRepository dataRepository, PreferenceManager preferenceManager, ProfileContract.View profileView) {
        this.dataRepository = dataRepository;
        this.preferenceManager = preferenceManager;
        this.profileView = profileView;
    }

    @Override
    public void start() {

    }

    @Override
    public void onLogoutClicked() {
        profileView.showLogoutConfirmation();
    }

    @Override
    public void onLogoutConfirmed() {
        preferenceManager.clear();
        profileView.showEnterMobileUI();
    }
}
