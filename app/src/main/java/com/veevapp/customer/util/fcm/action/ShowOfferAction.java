package com.veevapp.customer.util.fcm.action;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.veevapp.customer.MainActivity;
import com.veevapp.customer.util.fcm.MyFirebaseMessagingService;

import static com.veevapp.customer.util.AppConstants.BUY_REQUEST_ID;
import static com.veevapp.customer.util.AppConstants.OFFER_ID;

public class ShowOfferAction extends BaseNotificationAction {

    public ShowOfferAction() {
        setAction(MyFirebaseMessagingService.SHOW_OFFER);
    }

    @Override
    public void run(Context context, RemoteMessage remoteMessage) {
        super.run(context, remoteMessage);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.putExtra(BUY_REQUEST_ID, remoteMessage.getData().get(BUY_REQUEST_ID));
        notificationIntent.putExtra(OFFER_ID, remoteMessage.getData().get(OFFER_ID));

        showNotification(notificationIntent, remoteMessage);
    }
}
