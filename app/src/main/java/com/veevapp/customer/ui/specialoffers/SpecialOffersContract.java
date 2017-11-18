package com.veevapp.customer.ui.specialoffers;

import com.veevapp.customer.BasePresenter;
import com.veevapp.customer.BaseView;
import com.veevapp.customer.data.models.Slider;
import com.veevapp.customer.data.models.SpecialOffer;
import com.veevapp.customer.data.remote.request.SpecialOfferRequest;

import java.util.List;

public class SpecialOffersContract {

    public interface View extends BaseView<Presenter> {

        void showSpecialOffers(List<SpecialOffer> specialOfferList);

        void showSliders(List<Slider> sliderList);

        void showSingleSpecialOffer(SpecialOffer specialOffer, int fromPosition);

        void showLoading();

        void hideLoading();
    }

    public interface Presenter extends BasePresenter {

        void onSpecialOfferSelected(SpecialOffer specialOffer, int fromPosition);

        void getSpecialOffers();

        SpecialOfferRequest getSpecialRequest();

        void setSpecialOfferRequest(SpecialOfferRequest request);
    }
}