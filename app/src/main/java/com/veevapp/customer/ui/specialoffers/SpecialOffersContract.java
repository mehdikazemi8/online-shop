package com.veevapp.customer.ui.specialoffers;

import com.veevapp.customer.BasePresenter;
import com.veevapp.customer.BaseView;
import com.veevapp.customer.data.models.SpecialOffer;

import java.util.List;

public class SpecialOffersContract {

    public interface View extends BaseView<Presenter> {

        void showSpecialOffers(List<SpecialOffer> specialOfferList);

        void showSingleSpecialOffer(SpecialOffer specialOffer, int fromPosition);
    }

    public interface Presenter extends BasePresenter {

        void onSpecialOfferSelected(SpecialOffer specialOffer, int fromPosition);

    }
}
