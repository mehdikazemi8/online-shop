package com.veevapp.customer.ui.specialoffers;

import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.models.Slider;
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

        dataRepository.getSliders(new DataSource.GetSlidersCallback() {
            @Override
            public void onResponse(List<Slider> sliderList) {
                specialView.showSliders(sliderList);
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
    public void onSpecialOfferSelected(SpecialOffer specialOffer, int fromPosition) {
        specialView.showSingleSpecialOffer(specialOffer, fromPosition);
    }
}
