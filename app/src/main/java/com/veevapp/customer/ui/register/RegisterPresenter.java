package com.veevapp.customer.ui.register;


import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.remote.request.RegisterRequest;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View registerView;
    private DataRepository dataRepository;

    public RegisterPresenter(RegisterContract.View registerView, DataRepository dataRepository) {
        this.registerView = registerView;
        this.dataRepository = dataRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void registerCustomer(RegisterRequest registerRequest) {
        registerView.showProgressBar();

        dataRepository.registerCustomer(registerRequest, new DataSource.RegisterCustomerCallback() {
            @Override
            public void onSuccess() {
                if (!registerView.isActive()) {
                    return;
                }
                registerView.hideProgressBar();

                registerView.showMainPageUI();
            }

            @Override
            public void onFailure() {
                if (!registerView.isActive()) {
                    return;
                }
                registerView.hideProgressBar();

            }

            @Override
            public void onNetworkFailure() {
                if (!registerView.isActive()) {
                    return;
                }
                registerView.hideProgressBar();

            }
        });
    }
}
