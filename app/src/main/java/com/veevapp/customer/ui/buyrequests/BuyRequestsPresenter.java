package com.veevapp.customer.ui.buyrequests;

import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.models.BuyRequest;

import java.util.List;

public class BuyRequestsPresenter implements BuyRequestsContract.Presenter {

    private BuyRequestsContract.View buyRequestsView;
    private DataRepository dataRepository;

    public BuyRequestsPresenter(BuyRequestsContract.View buyRequestsView, DataRepository dataRepository) {
        this.buyRequestsView = buyRequestsView;
        this.dataRepository = dataRepository;
    }

    @Override
    public void start() {
        buyRequestsView.showLoading();
        dataRepository.getBuyRequests(new DataSource.GetBuyRequestsCallback() {
            @Override
            public void onResponse(List<BuyRequest> buyRequestList) {
                if(!buyRequestsView.isActive()) {
                    return;
                }
                buyRequestsView.hideLoading();

                buyRequestsView.showBuyRequests(buyRequestList);
            }

            @Override
            public void onFailure() {
                if(!buyRequestsView.isActive()) {
                    return;
                }
                buyRequestsView.hideLoading();

            }

            @Override
            public void onNetworkFailure() {
                if(!buyRequestsView.isActive()) {
                    return;
                }
                buyRequestsView.hideLoading();

            }
        });
    }
}
