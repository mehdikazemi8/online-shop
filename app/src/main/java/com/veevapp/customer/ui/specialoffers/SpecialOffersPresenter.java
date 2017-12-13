package com.veevapp.customer.ui.specialoffers;

import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.models.Slider;
import com.veevapp.customer.data.models.SpecialOffer;
import com.veevapp.customer.data.remote.request.SpecialOfferRequest;

import java.util.List;

public class SpecialOffersPresenter implements SpecialOffersContract.Presenter {

    private DataRepository dataRepository;
    private SpecialOffersContract.View specialView;

    private SpecialOfferRequest mSpecialOfferRequest;
    public SpecialOffersPresenter(DataRepository dataRepository, SpecialOffersContract.View specialView) {
        this.dataRepository = dataRepository;
        this.specialView = specialView;
        mSpecialOfferRequest = new SpecialOfferRequest();
    }

    @Override
    public void start() {
        getSpecialOffers();
        dataRepository.getSliders(new DataSource.GetSlidersCallback() {
            @Override
            public void onResponse(List<Slider> sliderList) {
                if(!specialView.isActive())return;
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
    public SpecialOfferRequest getSpecialRequest() {
        return mSpecialOfferRequest;
    }

    @Override
    public void setSpecialOfferRequest(SpecialOfferRequest request) {
        this.mSpecialOfferRequest = request;
    }

    @Override
    public void getSpecialOffers() {
        specialView.showLoading();
        dataRepository.getAvailableSpecialOffers(mSpecialOfferRequest, new DataSource.GetAvailableSpecialOffers() {
            @Override
            public void onResponse(List<SpecialOffer> specialOfferList) {
                if(!specialView.isActive())return;
                specialView.showSpecialOffers(specialOfferList);
                specialView.hideLoading();
            }

            @Override
            public void onFailure() {
                if(!specialView.isActive())return;
                specialView.hideLoading();
            }

            @Override
            public void onNetworkFailure() {
                if(!specialView.isActive())return;
                specialView.hideLoading();
            }
        });
    }


    @Override
    public void onSpecialOfferSelected(SpecialOffer specialOffer, int fromPosition) {
        specialView.showSingleSpecialOffer(specialOffer, fromPosition);
    }
}
