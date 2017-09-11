package com.veevapp.customer.ui.confirmcode;


import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.local.PreferenceManager;
import com.veevapp.customer.data.models.Customer;
import com.veevapp.customer.data.remote.request.ConfirmationCodeRequest;
import com.veevapp.customer.data.remote.response.TokenResponse;

public class ConfirmCodePresenter implements ConfirmCodeContract.Presenter {

    private DataRepository dataRepository;
    private ConfirmCodeContract.View confirmCodeView;
    private PreferenceManager preferenceManager;
    private boolean mustGetSellerInfo = false;
    private String mobile = null;

    public ConfirmCodePresenter(
            PreferenceManager preferenceManager,
            DataRepository dataRepository,
            ConfirmCodeContract.View confirmCodeView,
            boolean mustGetSellerInfo,
            String mobile
    ) {
        this.preferenceManager = preferenceManager;
        this.dataRepository = dataRepository;
        this.confirmCodeView = confirmCodeView;
        this.mustGetSellerInfo = mustGetSellerInfo;
        this.mobile = mobile;
    }

    @Override
    public void start() {

    }

    private void decideGetInfoOrSubmitInfo() {
        if (mustGetSellerInfo) {
            getUserInfo();
        } else {
            confirmCodeView.showRegisterUI();
        }
    }

    private void getUserInfo() {
        dataRepository.getCustomerInfo(new DataSource.GetCustomerInfoCallback() {

            @Override
            public void onResponse(Customer customer) {
                preferenceManager.putCustomerInfo(customer);
                if (customer.sentInfo()) {
                    confirmCodeView.showMainPageUI();
                } else {
                    confirmCodeView.showRegisterUI();
                }
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onNetworkFailure() {

            }
        });
    }

    @Override
    public void sendCodeToServer(String code) {
        if (code == null || code.isEmpty()) {
            confirmCodeView.showEmptyCodeError();
            return;
        }

        confirmCodeView.showProgressDialog();

        dataRepository.submitConfirmationCode(
                ConfirmationCodeRequest.builder().mobile(mobile).confirmationCode(Integer.parseInt(code)).build(),
                new DataSource.ConfirmationCodeCallback() {
                    @Override
                    public void onSuccess(TokenResponse tokenResponse) {
                        if (confirmCodeView.isActive()) {
                            confirmCodeView.hideProgressDialog();
                        }

                        preferenceManager.putTokenResponse(tokenResponse);
                        dataRepository.prepareDataSource();
//                        decideGetInfoOrSubmitInfo();
                        getUserInfo();
                    }

                    @Override
                    public void onFailure() {
                        if (confirmCodeView.isActive()) {
                            confirmCodeView.hideProgressDialog();
                        }
                        confirmCodeView.showFailureError();
                    }

                    @Override
                    public void onNetworkFailure() {
                        if (confirmCodeView.isActive()) {
                            confirmCodeView.hideProgressDialog();
                        }
                        confirmCodeView.showNetworkFailureError();
                    }
                }
        );
    }
}
