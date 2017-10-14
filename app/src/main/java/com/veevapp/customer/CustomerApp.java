package com.veevapp.customer;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class CustomerApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Make sure we use vector drawables
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
