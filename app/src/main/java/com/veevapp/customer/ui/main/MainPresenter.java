package com.veevapp.customer.ui.main;

import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.local.PreferenceManager;

public class MainPresenter implements MainContract.Presenter {
    private PreferenceManager preferenceManager;
    private DataRepository dataRepository;
    private MainContract.View mainView;

    public MainPresenter(PreferenceManager preferenceManager, DataRepository dataRepository, MainContract.View mainView) {
        this.preferenceManager = preferenceManager;
        this.dataRepository = dataRepository;
        this.mainView = mainView;
    }

    @Override
    public void start() {

    }
}
