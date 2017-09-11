package com.veevapp.customer.ui.main;

import android.util.Log;

import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.DataSource;
import com.veevapp.customer.data.local.PreferenceManager;
import com.veevapp.customer.data.remote.request.FCMRequest;

public class MainPresenter implements MainContract.Presenter {
    private PreferenceManager preferenceManager;
    private DataRepository dataRepository;
    private MainContract.View mainView;

    public MainPresenter(PreferenceManager preferenceManager, DataRepository dataRepository, MainContract.View mainView) {
        this.preferenceManager = preferenceManager;
        this.dataRepository = dataRepository;
        this.mainView = mainView;
    }

    @Override
    public void start() {
        sendFcmIDToServer();
    }

    private void sendFcmIDToServer() {
        FCMRequest fcmRequest = preferenceManager.getFcmID();
        if (fcmRequest != null && fcmRequest.sendToServer()) {
            DataRepository.getInstance().sendFcmIDToServer(fcmRequest.fcmID(), new DataSource.SendFcmIDCallback() {
                @Override
                public void onSuccess() {
                    Log.d("TAG", "Refreshed token hhhh main presenter: " + fcmRequest.fcmID());
//                    fcmRequest.sendToServer(false);
                    preferenceManager.putFcmID(FCMRequest.builder().fcmID(fcmRequest.fcmID()).sendToServer(false).build());
                }

                @Override
                public void onFailure() {

                }

                @Override
                public void onNetworkFailure() {

                }
            });
        }
    }
}
