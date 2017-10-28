package com.veevapp.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.local.LocalDataSource;
import com.veevapp.customer.data.local.PreferenceManager;
import com.veevapp.customer.data.models.Customer;
import com.veevapp.customer.data.remote.RemoteDataSource;
import com.veevapp.customer.ui.oneoffer.OneOfferController;
import com.veevapp.customer.ui.splash.SplashController;
import com.veevapp.customer.util.AppConstants;
import com.veevapp.customer.util.NetworkHelper;

import pl.aprilapps.easyphotopicker.EasyImage;

public class MainActivity extends AppCompatActivity {

    private Router router;

    private void init() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        EasyImage.configuration(this).setImagesFolderName("my_app_images");

        DataRepository.init(
                RemoteDataSource.getInstance(PreferenceManager.getInstance(this)),
                new LocalDataSource(),
                new NetworkHelper(this)
        );
    }

    void testAutoValue() {
        PreferenceManager.getInstance(this).getCustomerInfo();

        DataRepository.getInstance().getCustomerInfo(new DataSource.GetCustomerInfoCallback() {
            @Override
            public void onResponse(Customer customer) {
                PreferenceManager.getInstance(MainActivity.this).putCustomerInfo(customer);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onNetworkFailure() {

            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

//        testAutoValue();

        pushRootController(savedInstanceState);
    }

    private void pushRootController(Bundle savedInstanceState) {
        ViewGroup container = (ViewGroup) findViewById(R.id.controller_container);
        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new SplashController()));
        }
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        router.onActivityResult(requestCode, resultCode, data);
    }

    void checkIntent(Intent intent) {
        try {
            Log.d("TAG", "abcd onNewIntent " + intent.getAction());
            Log.d("TAG", "abcd onNewIntent " + intent.getData().getScheme());
            Log.d("TAG", "abcd onNewIntent " + intent.getData().getHost());
            Log.d("TAG", "abcd onNewIntent " + intent.getData().getPath());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        checkNewOffer(intent);
    }

    void checkNewOffer(Intent intent) {
        try {
            String buyRequestID = intent.getExtras().getString(AppConstants.BUY_REQUEST_ID);
            String offerID = intent.getExtras().getString(AppConstants.OFFER_ID);

            Log.d("TAG", "hhh " + buyRequestID + " " + offerID);

            if (offerID != null) {
                router.pushController(
                        RouterTransaction.with(OneOfferController.newInstance(offerID))
                                .pushChangeHandler(new FadeChangeHandler())
                                .popChangeHandler(new FadeChangeHandler())
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
