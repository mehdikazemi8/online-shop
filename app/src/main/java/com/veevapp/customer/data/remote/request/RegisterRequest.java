package com.veevapp.customer.data.remote.request;

import com.veevapp.customer.data.models.BaseModel;

public class RegisterRequest extends BaseModel {

    private String name;
    private String family;
    private String mobile;

    public RegisterRequest(String name, String family, String mobile) {
        this.name = name;
        this.family = family;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}