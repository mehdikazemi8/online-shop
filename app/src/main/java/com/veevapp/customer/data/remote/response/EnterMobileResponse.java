package com.veevapp.customer.data.remote.response;

public class EnterMobileResponse {

    private String confirmationCode;

    public EnterMobileResponse() {
    }

    public EnterMobileResponse(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }
}
