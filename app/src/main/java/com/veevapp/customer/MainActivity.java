package com.veevapp.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.veevapp.customer.ui.splash.SplashController;

import pl.aprilapps.easyphotopicker.EasyImage;

public class MainActivity extends AppCompatActivity {

    private Router router;

    private void init() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        EasyImage.configuration(this).setImagesFolderName("my_app_images");

//        DataRepository.init(
//                RemoteDataSource.getInstance(),
//                new LocalDataSource(),
//                new NetworkHelper(this)
//        );
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

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
}
