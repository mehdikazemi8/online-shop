package com.veevapp.customer.ui.buyrequests;

import com.veevapp.customer.BasePresenter;
import com.veevapp.customer.BaseView;
import com.veevapp.customer.data.models.BuyRequest;

import java.util.List;

public class BuyRequestsContract {

    public interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void showBuyRequests(List<BuyRequest> buyRequestList);
    }

    public interface Presenter extends BasePresenter {

    }
}
