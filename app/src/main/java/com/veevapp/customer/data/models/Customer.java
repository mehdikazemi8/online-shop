package com.veevapp.customer.data.models;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Customer extends BaseModel {
    @Nullable
    public abstract String name();

    @Nullable
    public abstract String family();

    @Nullable
    public abstract String mobile();

    @Nullable
    public abstract Boolean sentInfo();

    public static Builder builder() {
        return new AutoValue_Customer.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);

        public abstract Builder family(String family);

        public abstract Builder mobile(String mobile);

        public abstract Builder sentInfo(Boolean sentInfo);

        public abstract Customer build();
    }

    public static TypeAdapter<Customer> typeAdapter(Gson gson) {
        return new AutoValue_Customer.GsonTypeAdapter(gson);
    }
}
