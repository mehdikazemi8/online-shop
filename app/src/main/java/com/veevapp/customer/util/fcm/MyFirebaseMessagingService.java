package com.veevapp.customer.util.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.veevapp.customer.util.fcm.action.BaseNotificationAction;
import com.veevapp.customer.util.fcm.action.ShowOfferAction;

import java.util.ArrayList;
import java.util.List;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TELEGRAM_PACKAGE = "org.telegram.messenger";
    public static final String INSTAGRAM_PACKAGE = "com.instagram.android";
    public static final String CAFEBAZAAR_PACKAGE = "com.farsitel.bazaar";
    public static final String CHROME_PACKAGE = "com.android.chrome";

    public static final String SHOW_OFFER = "showOffer";

    private List<BaseNotificationAction> availableActions = null;

    private void prepareActions() {
        if (availableActions != null) {
            return;
        }

        availableActions = new ArrayList<>();
        availableActions.add(new ShowOfferAction());
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        prepareActions();

        String action = remoteMessage.getData().get("action");

        if (action == null) {
            return;
        }

        for (BaseNotificationAction baseNotificationAction : availableActions) {
            if (baseNotificationAction.getAction().equals(action)) {
                baseNotificationAction.run(getApplicationContext(), remoteMessage);
                break;
            }
        }
    }
}