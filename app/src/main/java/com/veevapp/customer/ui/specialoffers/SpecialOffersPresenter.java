package com.veevapp.customer.ui.specialoffers;

import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.models.SpecialOffer;

import java.util.List;

public class SpecialOffersPresenter implements SpecialOffersContract.Presenter {

    private DataRepository dataRepository;
    private SpecialOffersContract.View specialView;

    public SpecialOffersPresenter(DataRepository dataRepository, SpecialOffersContract.View specialView) {
        this.dataRepository = dataRepository;
        this.specialView = specialView;
    }

    @Override
    public void start() {

        dataRepository.getAvailableSpecialOffers(new DataSource.GetAvailableSpecialOffers() {
            @Override
            public void onResponse(List<SpecialOffer> specialOfferList) {
                specialView.showSpecialOffers(specialOfferList);
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
    public void onSpecialOfferSelected(SpecialOffer specialOffer) {
        specialView.showSingleSpecialOffer(specialOffer);
    }
}
