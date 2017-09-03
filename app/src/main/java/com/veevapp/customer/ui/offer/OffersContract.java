package com.veevapp.customer.ui.offer;

import com.veevapp.customer.BasePresenter;
import com.veevapp.customer.BaseView;
import com.veevapp.customer.data.models.BuyRequestOffer;

import java.util.List;

public class OffersContract {

    public interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void showOffers(List<BuyRequestOffer> offerList);

        void showOneOfferUI(BuyRequestOffer offer);
    }

    public interface Presenter extends BasePresenter {

        void onSelectOffer(BuyRequestOffer offer);
    }
}
