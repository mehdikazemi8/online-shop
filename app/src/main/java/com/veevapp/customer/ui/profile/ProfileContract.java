package com.veevapp.customer.ui.profile;

import com.veevapp.customer.BasePresenter;
import com.veevapp.customer.BaseView;

public class ProfileContract {

    public interface View extends BaseView<Presenter> {
        void showEnterMobileUI();
        void showLogoutConfirmation();
    }

    public interface Presenter extends BasePresenter {
        void onLogoutClicked();
        void onLogoutConfirmed();
    }
}
