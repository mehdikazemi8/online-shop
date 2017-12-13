package com.veevapp.customer.ui.register;


import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.local.PreferenceManager;
import com.veevapp.customer.data.models.Customer;
import com.veevapp.customer.data.remote.request.RegisterRequest;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View registerView;
    private DataRepository dataRepository;
    private PreferenceManager preferenceManager;

    public RegisterPresenter(RegisterContract.View registerView, DataRepository dataRepository,PreferenceManager preferenceManager) {
        this.registerView = registerView;
        this.dataRepository = dataRepository;
        this.preferenceManager = preferenceManager;
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
                if (!registerView.isActive()) return;
                registerView.hideProgressBar();

                getUserInfo();
            }

            @Override
            public void onFailure() {
                if (!registerView.isActive()) return;

                // todo remove
                registerView.showMainPageUI();
                registerView.hideProgressBar();

            }

            @Override
            public void onNetworkFailure() {
                if (!registerView.isActive()) return;
                registerView.hideProgressBar();

                // todo remove
                registerView.showMainPageUI();

            }
        });
    }


    private void getUserInfo() {
        registerView.showProgressBar();
        dataRepository.getCustomerInfo(new DataSource.GetCustomerInfoCallback() {
            @Override
            public void onResponse(Customer customer) {
                if(!registerView.isActive())return;

                preferenceManager.putCustomerInfo(customer);

                registerView.hideProgressBar();
                registerView.showMainPageUI();
            }

            @Override
            public void onFailure() {
                registerView.hideProgressBar();
            }

            @Override
            public void onNetworkFailure() {
                registerView.hideProgressBar();
            }
        });
    }
}
