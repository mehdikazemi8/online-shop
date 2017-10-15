package com.veevapp.customer.ui.showlocation;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.RestoreViewOnCreateController;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.veevapp.customer.R;
import com.veevapp.customer.util.MyLocationManager;

import java.util.List;

import io.nlopez.smartlocation.SmartLocation;
import pub.devrel.easypermissions.EasyPermissions;


public class ShowLocationController extends RestoreViewOnCreateController implements
        ShowLocationContract.View,
        OnMapReadyCallback,
        EasyPermissions.PermissionCallbacks {

    private int RC_LOCATION_PERMISSION = 1367;

    private ShowLocationContract.Presenter presenter;
    private MapView mapView;
    private GoogleMap googleMap = null;


    private double showingLat, showingLon;
    private String showingTitle;

    public static ShowLocationController newInstance(double lat, double lon, String title) {
        ShowLocationController instance = new ShowLocationController();
        instance.showingLat = lat;
        instance.showingLon = lon;
        instance.showingTitle = title;
        return instance;
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedViewState) {
        View rootView = inflater.inflate(R.layout.controller_show_location, container, false);
        mapView = rootView.findViewById(R.id.map);
        mapView.onCreate(savedViewState);
        mapView.getMapAsync(this);

        onViewBound(rootView);

        return rootView;
    }

    private void onViewBound(@Nullable View view) {

        init(view);

        presenter = new ShowLocationPresenter();
        presenter.start();
    }

    private void init(View view) {
    }

    @Override
    public boolean isActive() {
        // todo, is this right
        return isAttached();
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        mapView.onResume();
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        mapView.onPause();
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        mapView.onDestroy();
        SmartLocation.with(getActivity()).location().stop();
    }

    @Override
    protected void onSaveViewState(@NonNull View view, @NonNull Bundle outState) {
        super.onSaveViewState(view, outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(showingLat,showingLon))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title(showingTitle));

        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            checkGPSStatus();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RC_LOCATION_PERMISSION);
//            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    void checkGPSStatus() {
        if (SmartLocation.with(getActivity()).location().state().isGpsAvailable()) {
            registerForLocationUpdates();

            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            registerForLocationUpdates();
            MyLocationManager.displayLocationSettingsRequest(getActivity());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void registerForLocationUpdates() {
        SmartLocation.with(getActivity()).location()
                .start(location -> onLocationChanged(location));
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        checkGPSStatus();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        getRouter().popController(this);
    }

    public void onLocationChanged(Location location) {
        if (!SmartLocation.with(getActivity()).location().state().isGpsAvailable()) {
            return;
        }


        LatLng addressPlace = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(addressPlace)
                .zoom(16)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
