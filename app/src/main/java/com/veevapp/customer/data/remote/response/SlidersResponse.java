package com.veevapp.customer.data.remote.response;

import com.veevapp.customer.data.models.Slider;

import java.util.List;

public class SlidersResponse {

    private List<Slider> sliders;

    public List<Slider> getSliders() {
        return sliders;
    }

    public void setSliders(List<Slider> sliders) {
        this.sliders = sliders;
    }
}
