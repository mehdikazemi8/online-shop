package com.veevapp.customer.ui.entermobile;

import com.veevapp.customer.BasePresenter;
import com.veevapp.customer.BaseView;

public class EnterMobileContract {

    interface View extends BaseView<Presenter> {

        void showInvalidMobileNumberError();

        void showEmptyMobileNumberError();

        void showProgressDialog();

        void hideProgressDialog();

        void showConfirmCodeUI(boolean mustGetSellerInfo, String confirmationCode);
    }

    interface Presenter extends BasePresenter {

        void sendMobileToServer(String mobileNumber);

    }

}
