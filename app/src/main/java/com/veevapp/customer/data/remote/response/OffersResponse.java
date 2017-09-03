package com.veevapp.customer.data.remote.response;

import com.veevapp.customer.data.models.BuyRequestOffer;

import java.util.List;

public class OffersResponse {
    List<BuyRequestOffer> offers;

    public List<BuyRequestOffer> getOffers() {
        return offers;
    }

    public void setOffers(List<BuyRequestOffer> offers) {
        this.offers = offers;
    }
}
