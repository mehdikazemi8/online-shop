package com.veevapp.customer.data.remote.response;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.veevapp.customer.data.models.BaseModel;

@AutoValue
public abstract class TokenResponse extends BaseModel {

    public abstract String accessToken();

    public abstract String tokenType();

    public abstract String refreshToken();

    public static Builder builder() {
        return new AutoValue_TokenResponse.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder accessToken(String accessToken);

        public abstract Builder tokenType(String tokenType);

        public abstract Builder refreshToken(String refreshToken);

        public abstract TokenResponse build();
    }

    public static TypeAdapter<TokenResponse> typeAdapter(Gson gson) {
        return new AutoValue_TokenResponse.GsonTypeAdapter(gson);
    }

}
