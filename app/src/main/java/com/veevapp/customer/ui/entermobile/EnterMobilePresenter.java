package com.veevapp.customer.ui.entermobile;

import android.support.annotation.NonNull;

import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.util.FormUtils;


public class EnterMobilePresenter implements EnterMobileContract.Presenter {

    DataRepository dataRepository;

    @NonNull
    private final EnterMobileContract.View enterMobileView;

    public EnterMobilePresenter(DataRepository dataRepository, @NonNull EnterMobileContract.View enterMobileView) {
        this.dataRepository = dataRepository;
        this.enterMobileView = enterMobileView;
    }

    @Override
    public void start() {

    }

    @Override
    public void sendMobileToServer(String mobileNumber) {

        if (mobileNumber == null || mobileNumber.isEmpty()) {
            enterMobileView.showEmptyMobileNumberError();
            return;
        }

        if (!FormUtils.validMobileNumber(mobileNumber)) {
            enterMobileView.showInvalidMobileNumberError();
            return;
        }

        enterMobileView.showProgressDialog();

        dataRepository.submitMobileNumber(mobileNumber, new DataSource.SubmitMobileNumberCallback() {
            @Override
            public void onMustLogin(String confirmationCode) {
                if(!enterMobileView.isActive())return;
                enterMobileView.hideProgressDialog();
                enterMobileView.showConfirmCodeUI(true, confirmationCode);
            }

            @Override
            public void onMustRegister(String confirmationCode) {
                if(!enterMobileView.isActive())return;
                enterMobileView.hideProgressDialog();
                enterMobileView.showConfirmCodeUI(false, confirmationCode);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onNetworkFailure() {
                if(!enterMobileView.isActive())return;
                enterMobileView.hideProgressDialog();
            }
        });
    }
}
