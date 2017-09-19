package com.veevapp.customer.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.veevapp.customer.data.models.BaseModel;
import com.veevapp.customer.data.models.Customer;
import com.veevapp.customer.data.remote.request.FCMRequest;
import com.veevapp.customer.data.remote.response.TokenResponse;

import java.lang.reflect.Type;

// todo, for all operations use asynctask
public class PreferenceManager {
    private enum Key {
        TOKEN,
        CUSTOMER_INFO,
        FCM_ID
    }

    public static PreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new PreferenceManager(context);
        }

        return instance;
    }

    private PreferenceManager(Context context) {
        init(context);
    }

    private static PreferenceManager instance = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public void init(Context context) {
        sharedPreferences = context.getSharedPreferences("veevapp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void put(Key key, Integer value) {
        editor.putInt(key.toString(), value);
        editor.apply();
    }

    private void put(Key key, String value) {
        editor.putString(key.toString(), value);
        editor.apply();
    }

    private void put(Key key, Float value) {
        editor.putFloat(key.toString(), value);
        editor.apply();
    }

    private void put(Key key, Boolean value) {
        editor.putBoolean(key.toString(), value);
        editor.apply();
    }

    private void put(Key key, BaseModel object) {
        editor.putString(key.toString(), object.serialize());
        editor.apply();
    }

    private String getString(Key key) {
        return sharedPreferences.getString(key.toString(), null);
    }

    private Integer getInt(Key key) {
        return sharedPreferences.getInt(key.toString(), 0);
    }

    private Boolean getBoolean(Key key) {
        return sharedPreferences.getBoolean(key.toString(), false);
    }

    private Float getFloat(Key key) {
        return sharedPreferences.getFloat(key.toString(), 0f);
    }

    private <T> T get(Key key, Type type) {
        String json = sharedPreferences.getString(key.toString(), null);
        if (json == null) {
            return null;
        }

        return BaseModel.deserialize(json, type);
    }

    private void remove(Key key) {
        editor.remove(key.toString());
        editor.apply();
    }

    //*************************************************************

    public void clear() {
        editor.clear().apply();
    }

    //*************************************************************

    public void putTokenResponse(TokenResponse tokenResponse) {
        put(Key.TOKEN, tokenResponse);
    }

    public TokenResponse getTokenResponse() {
        return get(Key.TOKEN, TokenResponse.class);
    }

    public void removeTokenResponse() {
        remove(Key.TOKEN);
    }

    //*************************************************************

    public String getAuthorization() {
        TokenResponse tokenResponse = getTokenResponse();
        if (tokenResponse == null) {
            return null;
        }
        return tokenResponse.accessToken();
    }

    //*************************************************************

    public void putFcmID(FCMRequest fcmRequest) {
        put(Key.FCM_ID, fcmRequest);
    }

    public FCMRequest getFcmID() {
        return get(Key.FCM_ID, FCMRequest.class);
    }

    //*************************************************************

    public void putCustomerInfo(Customer customer) {
        put(Key.CUSTOMER_INFO, customer);
    }

    public void removeCustomerInfo() {
        remove(Key.CUSTOMER_INFO);
    }

    public Customer getCustomerInfo() {
        return get(Key.CUSTOMER_INFO, Customer.class);
    }


}

