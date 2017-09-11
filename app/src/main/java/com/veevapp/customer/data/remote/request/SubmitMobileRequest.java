package com.veevapp.customer.data.remote.request;

public class SubmitMobileRequest {
    private String mobile;

    public SubmitMobileRequest(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
