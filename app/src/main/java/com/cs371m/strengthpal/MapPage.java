package com.cs371m.strengthpal;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapPage extends Fragment {

    public MapPage() {}

    Location currentLocation;
    double[][] coordinates;
    String[] names;
    String[] addresses;
    GoogleMap map;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);

        bundle = this.getArguments();

        if(bundle != null){
            currentLocation = bundle.getParcelable("currentLocation");
            coordinates = (double[][]) bundle.getSerializable("coordinates");
            names = bundle.getStringArray("names");
            addresses = bundle.getStringArray("addresses");

            LatLng userLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13));

            for (int i = 0; i < 25; i++) {
                if (coordinates[i][0] != 0) {
                    map.addMarker(new MarkerOptions().position(new LatLng(coordinates[i][0],
                            coordinates[i][1])).title(names[i])
                            .snippet(addresses[i]));
                }
            }
        }

        return view;
    }

    @Override
    public void onDestroyView() {

        Fragment mapFragment = getFragmentManager().findFragmentById(R.id.map);
        getFragmentManager().beginTransaction().remove(mapFragment).commit();

        super.onDestroyView();
    }
}