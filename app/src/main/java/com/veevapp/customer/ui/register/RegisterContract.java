package com.veevapp.customer.ui.register;


import com.veevapp.customer.BasePresenter;
import com.veevapp.customer.BaseView;
import com.veevapp.customer.data.remote.request.RegisterRequest;

public interface RegisterContract {

    interface View extends BaseView<Presenter> {

        void showProgressBar();

        void hideProgressBar();

        void showMainPageUI();
    }

    interface Presenter extends BasePresenter {

        void registerCustomer(RegisterRequest registerRequest);
    }
}
