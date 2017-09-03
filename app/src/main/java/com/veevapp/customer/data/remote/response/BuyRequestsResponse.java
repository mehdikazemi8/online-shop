package com.veevapp.customer.data.remote.response;

import com.veevapp.customer.data.models.BuyRequest;

import java.util.List;

public class BuyRequestsResponse {
    List<BuyRequest> buyRequests;

    public BuyRequestsResponse() {
    }

    public List<BuyRequest> getBuyRequests() {
        return buyRequests;
    }

    public void setBuyRequests(List<BuyRequest> buyRequests) {
        this.buyRequests = buyRequests;
    }
}
