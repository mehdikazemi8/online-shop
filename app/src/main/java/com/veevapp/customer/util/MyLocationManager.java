package com.veevapp.customer.util;

import android.content.Context;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.directions.route.AbstractRouting;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.veevapp.customer.MainActivity;

import java.util.ArrayList;

public class MyLocationManager {
    public static void displayLocationSettingsRequest(Context context) {

        GoogleApiClient googleApiClient = null;
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {

                        }
                    })
                    .addOnConnectionFailedListener(connectionResult -> {
                    }).build();
            googleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            // **************************
            builder.setAlwaysShow(true); // this is the key ingredient
            // **************************

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result
                            .getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can
                            // initialize location
                            // requests here.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be
                            // fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling
                                // startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult((MainActivity) context, 1000);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have
                            // no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });
        }
    }


    public static void zoomOnLatLongs(Context ctx,GoogleMap mMap,LatLng... latLngs) {
        if(latLngs==null || latLngs.length==0)return;
        if(mMap==null)return;

        if(latLngs.length>1) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for (LatLng latLng : latLngs) {
                builder.include(latLng);
            }
            LatLngBounds bounds = builder.build();
            int padding = DpHandler.dpToPx(ctx, 40); // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);
        }else{
            CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(latLngs[0],15);
            mMap.animateCamera(cu);
        }
    }

    public static void showRoute(GoogleMap mMap,int color,LatLng... latLngs) {
        if(latLngs==null || latLngs.length==0)return;

        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(new RoutingListener() {
                    @Override
                    public void onRoutingFailure(com.directions.route.RouteException e) {
                        Log.d("SS","onRoutingFailure : " + e.toString());
                    }

                    @Override
                    public void onRoutingStart(){
                        Log.d("SS","onRoutingStart");
                    }

                    @Override
                    public void onRoutingSuccess(ArrayList<com.directions.route.Route> route, int ss) {

                        ArrayList<Polyline> currentRoute = new ArrayList<>();
                        //add route(s) to the map.
                        for (int i = 0; i < route.size(); i++){
                            PolylineOptions polyOptions = new PolylineOptions();
                            polyOptions.color(color);
                            polyOptions.width(10 + i * 3);
                            polyOptions.addAll(route.get(i).getPoints());
                            currentRoute.add(i,mMap.addPolyline(polyOptions));
                        }
                    }

                    @Override
                    public void onRoutingCancelled(){
                        Log.d("SS","onRoutingCancelled");
                    }
                })
                .waypoints(latLngs)
                .key("AIzaSyBGjAjpFnzpeudJ4tLfqtqfvUK4ZQZe95k")
                .build();
        routing.execute();
    }

    /*public static void removeRoute(){
        if(currentRoute==null || currentRoute.size()==0)return;

        for(Polyline pl : currentRoute){
            pl.remove();
        }

        currentRoute = null;
    }*/
}
