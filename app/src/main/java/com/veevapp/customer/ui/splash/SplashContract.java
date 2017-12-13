package com.veevapp.customer.ui.splash;

import com.veevapp.customer.BasePresenter;
import com.veevapp.customer.BaseView;

public class SplashContract {

    interface View extends BaseView<Presenter> {

        void showEnterMobileUI();

        void showMainPageUI();

        void showRegisterUI(String mobile);
    }

    interface Presenter extends BasePresenter {

    }
}
