package com.veevapp.customer.ui.offer;

import android.util.Log;

import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.models.BuyRequestOffer;

import java.util.List;

public class OffersPresenter implements OffersContract.Presenter {

    private DataRepository dataRepository;
    private OffersContract.View offersView;
    private BuyRequest buyRequest;

    public OffersPresenter(BuyRequest buyRequest, DataRepository dataRepository, OffersContract.View offersView) {
        this.buyRequest = buyRequest;
        this.dataRepository = dataRepository;
        this.offersView = offersView;
    }

    @Override
    public void start() {
        offersView.showLoading();
        dataRepository.getOffersOfOneBuyRequest(buyRequest.getId(), new DataSource.GetOffersCallback() {
            @Override
            public void onResponse(List<BuyRequestOffer> offerList) {
                if(!offersView.isActive()) {
                    return;
                }
                offersView.hideLoading();

                offersView.showOffers(offerList);
            }

            @Override
            public void onFailure() {
                if(!offersView.isActive()) {
                    return;
                }
                offersView.hideLoading();
            }

            @Override
            public void onNetworkFailure() {
                if(!offersView.isActive()) {
                    return;
                }
                offersView.hideLoading();
            }
        });

    }

    @Override
    public void onSelectOffer(BuyRequestOffer offer) {
        Log.d("TAG", "onSelectOffer" + offer.getSuggestedPrice());
    }
}
