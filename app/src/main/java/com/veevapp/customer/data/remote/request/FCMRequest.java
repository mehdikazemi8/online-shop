package com.veevapp.customer.data.remote.request;

public class FCMRequest {
    String fcmID;

    public FCMRequest(String fcmID) {
        this.fcmID = fcmID;
    }

    public String getFcmID() {
        return fcmID;
    }

    public void setFcmID(String fcmID) {
        this.fcmID = fcmID;
    }
}
