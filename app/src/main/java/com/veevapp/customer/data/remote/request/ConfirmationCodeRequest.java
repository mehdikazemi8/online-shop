package com.veevapp.customer.data.remote.request;

import com.google.auto.value.AutoValue;
import com.veevapp.customer.data.models.BaseModel;

@AutoValue
public abstract class ConfirmationCodeRequest extends BaseModel {

    public abstract String mobile();

    public abstract Integer confirmationCode();

    public static Builder builder() {
        return new AutoValue_ConfirmationCodeRequest.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder mobile(String mobile);

        public abstract Builder confirmationCode(Integer confirmationCode);

        public abstract ConfirmationCodeRequest build();
    }
}