package com.veevapp.customer.data.remote.request;

import com.google.auto.value.AutoValue;
import com.veevapp.customer.data.models.BaseModel;

//@AutoValue
public class ConfirmationCodeRequest extends BaseModel {

    private String mobile;
    private Integer confirmationCode;

    public ConfirmationCodeRequest(String mobile, Integer confirmationCode) {
        this.mobile = mobile;
        this.confirmationCode = confirmationCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(Integer confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    //    public abstract String mobile();
//
//    public abstract Integer confirmationCode();
//
//    public static Builder builder() {
//        return new AutoValue_ConfirmationCodeRequest.Builder();
//    }
//
//    @AutoValue.Builder
//    public abstract static class Builder {
//
//        public abstract Builder mobile(String mobile);
//
//        public abstract Builder confirmationCode(Integer confirmationCode);
//
//        public abstract ConfirmationCodeRequest build();
//    }
}