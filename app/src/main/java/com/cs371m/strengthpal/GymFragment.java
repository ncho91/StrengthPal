package com.cs371m.strengthpal;

import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class GymFragment extends Fragment {

    public GymFragment() {}

    LocationManager locationManager;
    List<String> networkProviders;
    Location currentLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gym, container, false);

        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        networkProviders = locationManager.getProviders(true);
        currentLocation = null;

        for (int i = networkProviders.size() - 1; i >= 0; i--) {
            currentLocation = locationManager.getLastKnownLocation(networkProviders.get(i));
            if (currentLocation != null)
                break;
        }

        if (currentLocation != null) {
            LatLng coordinates = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 13));
        }

        return rootView;
    }
}