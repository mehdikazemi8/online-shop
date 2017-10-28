package com.veevapp.customer.data.models;

import com.veevapp.customer.view.adapter.BaseRecyclerAdapter;

public class Slider implements BaseRecyclerAdapter.IDiff {
    private String photoURL;
    private SpecialOffer specialOffer;

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public SpecialOffer getSpecialOffer() {
        return specialOffer;
    }

    public void setSpecialOffer(SpecialOffer specialOffer) {
        this.specialOffer = specialOffer;
    }

    // todo implement this
    @Override
    public boolean areContentsTheSame(Object thisType) {
        return false;
    }

    @Override
    public boolean areItemsTheSame(Object thisType) {
        return false;
    }
}
