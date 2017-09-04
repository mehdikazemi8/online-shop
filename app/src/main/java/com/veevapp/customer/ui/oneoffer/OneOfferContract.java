package com.veevapp.customer.ui.oneoffer;

import com.veevapp.customer.BasePresenter;
import com.veevapp.customer.BaseView;
import com.veevapp.customer.data.models.BuyRequestOffer;

public class OneOfferContract {

    public interface View extends BaseView<Presenter> {

        void showOffer(BuyRequestOffer offer);
    }

    public interface Presenter extends BasePresenter {

    }
}
