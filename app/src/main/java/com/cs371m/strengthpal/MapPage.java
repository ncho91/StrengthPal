package com.cs371m.strengthpal;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapPage extends Fragment {

    public MapPage() {}

    Location currentLocation;
    double[][] coordinates;
    String[] names;
    String[] addresses;
    String[] ids;
    String[] images;
    String[] gymDistances;
    GoogleMap map;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);

        bundle = this.getArguments();

        gymDistances = new String[25];

        if(bundle != null){
            currentLocation = bundle.getParcelable("currentLocation");
            coordinates = (double[][]) bundle.getSerializable("coordinates");
            names = bundle.getStringArray("names");
            addresses = bundle.getStringArray("addresses");
            images = bundle.getStringArray("images");
            ids = bundle.getStringArray("ids");

            float distances[] = new float[3];
            for (int i = 0; i < 25; i++) {
                if (coordinates[i][0] != 0) {
                    Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(), coordinates[i][0], coordinates[i][1], distances);
                    double dist = (double) distances[0];
                    dist = dist * 0.000621371;
                    dist = (double) Math.round(dist * 100) / 100;
                    gymDistances[i] = Double.toString(dist) + " mi";
                }
            }

            LatLng userLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13));

            for (int i = 0; i < 25; i++) {
                if (coordinates[i][0] != 0) {
                    map.addMarker(new MarkerOptions().position(new LatLng(coordinates[i][0],
                            coordinates[i][1])).title(names[i])
                            .snippet(addresses[i]));
                }
            }

            map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    int position = Integer.parseInt(marker.getId().substring(1));
                    DetailDialog detailDialog = new DetailDialog(getActivity(), coordinates, names, ids, images, gymDistances);
                    detailDialog.expand(position + 1);
                }
            });
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