package com.cs371m.strengthpal;

import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.FragmentManager;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GymFragment extends Fragment implements View.OnClickListener {

    public GymFragment() {}

    Fragment mapFragment;
    Fragment listFragment;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gym, container, false);

        Button mapButton = (Button) v.findViewById(R.id.map_button);
        mapButton.setOnClickListener(this);
        Button listButton = (Button) v.findViewById(R.id.list_button);
        listButton.setOnClickListener(this);

        mapFragment = new MapPage();
        listFragment = new ListPage();

        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, mapFragment).commit();

        /*
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
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

            String query = "https://maps.googleapis.com/maps/api/place/search/json" +
                    "?location=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() +
                    "&rankby=distance" +
                    "&types=gym" +
                    "&key=AIzaSyAgAA0SJSFBydGuJpbJ8LlnUPoQx9CM4PU";

            getJSON jsonGetter = new getJSON();
            jsonGetter.execute(query);

            try {
                jsonGetter.get(1000000, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                // do nothing
            }

            for (int i = 0; i < 25; i++) {
                if (jsonGetter.coordinates[i][0] != 0) {
                    map.addMarker(new MarkerOptions().position(new LatLng(jsonGetter.coordinates[i][0], jsonGetter.coordinates[i][1])).title(jsonGetter.names[i]).snippet(jsonGetter.addresses[i]));
                }
            }
        }*/

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_button:
                fragmentManager.beginTransaction().replace(R.id.content_frame, mapFragment).commit();
                break;
            case R.id.list_button:
                fragmentManager.beginTransaction().replace(R.id.content_frame, listFragment).commit();
                break;
        }
    }
}

/*

public class GymFragment extends Fragment {

    public GymFragment() {}


    LocationManager locationManager;
    List<String> networkProviders;
    Location currentLocation;
    GoogleMap map;


    ViewGroup cont;
    Button listButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        /*
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
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

            String query = "https://maps.googleapis.com/maps/api/place/search/json" +
                    "?location=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() +
                    "&rankby=distance" +
                    "&types=gym" +
                    "&key=AIzaSyAgAA0SJSFBydGuJpbJ8LlnUPoQx9CM4PU";

            getJSON jsonGetter = new getJSON();
            jsonGetter.execute(query);

            try {
                jsonGetter.get(1000000, TimeUnit.MILLISECONDS);
            }
            catch (Exception e) {
                // do nothing
            }

            for (int i = 0; i < 25; i++) {
                if (jsonGetter.coordinates[i][0] != 0) {
                    map.addMarker(new MarkerOptions().position(new LatLng(jsonGetter.coordinates[i][0], jsonGetter.coordinates[i][1])).title(jsonGetter.names[i]).snippet(jsonGetter.addresses[i]));
                }
            }
        }


        //LayoutInflater inflateList = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        //inflateList.inflate(R.layout.fragment_list, container, false);

        /*cont = container;
        listButton = (Button) getView().findViewById(R.id.list_button);
        listButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LayoutInflater inflateList = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
                inflateList.inflate(R.layout.fragment_map, cont, false);
            }
        });

        return rootView;
    }

    private class getJSON extends AsyncTask<String, Integer, String> {

        double[][] coordinates  = new double[25][2];
        String[] names = new String[25];
        String[] addresses = new String[25];

        @Override
        protected String doInBackground(String... params) {

            String strJson = null;
            try {
                DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(params[0]);
                HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                strJson = EntityUtils.toString(httpEntity);
            }
            catch (Exception e) {
                // do nothing
            }


            JSONObject json = null;
            try {
                json = new JSONObject(strJson);
            }
            catch (Exception e) {
                // do nothing
            }

            JSONArray results = null;
            if (json != null) {
                try {
                    results = json.getJSONArray("results");
                }
                catch (Exception e) {
                    // do nothing
                }
            }

            if (results != null) {
                for (int i = 0; i < results.length(); i++) {
                    try {
                        JSONObject item = results.getJSONObject(i);
                        coordinates[i][0] = item.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                        coordinates[i][1] = item.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                        names[i] = item.getString("name");
                        addresses[i] = item.getString("vicinity");
                        if (i == 24)
                            break;
                    } catch (Exception e) {
                        // do nothing
                    }
                }
            }
            return "success";
        }
    }
}*/