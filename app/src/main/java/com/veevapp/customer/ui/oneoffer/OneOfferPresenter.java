package com.veevapp.customer.ui.oneoffer;

import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.models.BuyRequestOffer;

public class OneOfferPresenter implements OneOfferContract.Presenter {

    private DataRepository dataRepository;
    private OneOfferContract.View oneOfferView;
    private String offerID = null;

    public OneOfferPresenter(DataRepository dataRepository, OneOfferContract.View oneOfferView, String offerID) {
        this.dataRepository = dataRepository;
        this.oneOfferView = oneOfferView;
        this.offerID = offerID;
    }

    @Override
    public void start() {
        if(offerID != null) {
            dataRepository.getSingleOffer(offerID, new DataSource.GetSingleOfferCallback() {
                @Override
                public void onResponse(BuyRequestOffer offer) {
                    oneOfferView.showOffer(offer);
                }

                @Override
                public void onFailure() {

                }

                @Override
                public void onNetworkFailure() {

                }
            });
        }

    }
}
