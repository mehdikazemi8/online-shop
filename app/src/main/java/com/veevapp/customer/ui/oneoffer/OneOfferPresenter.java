package com.veevapp.customer.ui.oneoffer;

import com.veevapp.customer.BasePresenter;

public class OneOfferPresenter implements OneOfferContract.Presenter {

    private OneOfferContract.View oneOfferView;

    public OneOfferPresenter(OneOfferContract.View oneOfferView) {
        this.oneOfferView = oneOfferView;
    }

    @Override
    public void start() {

    }
}
