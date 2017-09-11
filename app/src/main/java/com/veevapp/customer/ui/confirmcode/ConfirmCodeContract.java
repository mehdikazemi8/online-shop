package com.veevapp.customer.ui.confirmcode;


import com.veevapp.customer.BasePresenter;
import com.veevapp.customer.BaseView;

public class ConfirmCodeContract {

    interface View extends BaseView<Presenter> {

        void showEmptyCodeError();

        void showProgressDialog();

        void hideProgressDialog();

        void showFailureError();

        void showNetworkFailureError();

        void showRegisterUI();

        void showMainPageUI();
    }

    interface Presenter extends BasePresenter {
        void sendCodeToServer(String code);
    }
}
