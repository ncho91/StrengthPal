package com.cs371m.strengthpal;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class GymFragment extends Fragment implements View.OnClickListener {

    public GymFragment() {}

    Fragment mapFragment;
    Fragment listFragment;
    Fragment emptyFragment;
    FragmentManager fragmentManager;
    Button switchView;
    Boolean onMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gym, container, false);

        fragmentManager = getFragmentManager();

        mapFragment = new MapPage();
        listFragment = new ListPage();
        emptyFragment = new EmptyPage();

        fragmentManager
                .beginTransaction()
                .replace(R.id.content_frame, emptyFragment)
                .commit();

        onMap = true;
        switchView = (Button) view.findViewById(R.id.switchViewsButton);
        switchView.setText("List Results");
        switchView.setOnClickListener(this);

        GymFinder gymFinder = new GymFinder();
        gymFinder.execute();

        return view;
    }

    @Override
    public void onClick(View v) {
        if (onMap) {
            onMap = false;
            switchView.setText("View on Map");
            fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                    .replace(R.id.content_frame, listFragment)
                    .commit();
        }
        else {
            onMap = true;
            switchView.setText("List Results");
            fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                    .replace(R.id.content_frame, mapFragment)
                    .commit();
        }
    }

    public void onDestroyView()
    {
        if (onMap) {
            fragmentManager.beginTransaction().remove(mapFragment).commit();
        }
        super.onDestroyView();
    }

    private class GymFinder extends AsyncTask<String, Integer, String> {
        LocationManager locationManager;
        List<String> networkProviders;
        Location currentLocation;

        double[][] coordinates = new double[25][2];
        String[] names = new String[25];
        String[] addresses = new String[25];
        String[] images = new String[25];
        String[] ids = new String[25];

        @Override
        protected String doInBackground(String... params) {

            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            networkProviders = locationManager.getProviders(true);
            currentLocation = null;

            for (int i = networkProviders.size() - 1; i >= 0; i--) {
                currentLocation = locationManager.getLastKnownLocation(networkProviders.get(i));
                if (currentLocation != null)
                    break;
            }

            if (currentLocation != null) {
                String query = "https://maps.googleapis.com/maps/api/place/search/json" +
                        "?location=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() +
                        "&rankby=distance" +
                        "&types=gym" +
                        "&key=AIzaSyAgAA0SJSFBydGuJpbJ8LlnUPoQx9CM4PU";

                String strJson = null;
                try {
                    DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(query);
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
                            ids[i] = item.getString("place_id");
                            try {
                                images[i] = item.getJSONArray("photos").getJSONObject(0).getString("photo_reference");
                            }
                            catch (Exception noImage) {
                                images[i] = "none";
                            }
                            if (i == 24)
                                break;
                        } catch (Exception e) {
                            // do nothing
                        }
                    }

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("currentLocation", currentLocation);
                    bundle.putSerializable("coordinates", coordinates);
                    bundle.putStringArray("names", names);
                    bundle.putStringArray("addresses", addresses);
                    bundle.putStringArray("images", images);
                    bundle.putStringArray("ids", ids);

                    mapFragment.setArguments(bundle);
                    listFragment.setArguments(bundle);

                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                            .replace(R.id.content_frame, mapFragment)
                            .commit();
                }
                else {
                    // No results were found
                }
            }
            else {
                // Location was unable to be determined
            }

            return "Success";
        }
    }
}