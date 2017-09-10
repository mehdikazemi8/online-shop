package com.veevapp.customer.data.remote.response;

import com.veevapp.customer.data.models.SpecialOffer;

import java.util.List;

public class SpecialOffersResponse {
    private List<SpecialOffer> specialOffers;

    public List<SpecialOffer> getSpecialOffers() {
        return specialOffers;
    }

    public void setSpecialOffers(List<SpecialOffer> specialOffers) {
        this.specialOffers = specialOffers;
    }
}
