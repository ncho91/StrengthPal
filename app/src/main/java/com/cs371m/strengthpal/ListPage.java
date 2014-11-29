package com.cs371m.strengthpal;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListPage extends Fragment {

    public ListPage() {}

    LocationManager locationManager;
    List<String> networkProviders;
    Location currentLocation;
    ListView gymList;
    ArrayList<GymListItem> gyms;
    GymListAdapter gymAdapter;
    getJSON jsonGetter;
    String gymDistances[];
    ImageView gymPopupImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list, container, false);

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

            jsonGetter = new getJSON();
            jsonGetter.execute(query);

            try {
                jsonGetter.get(1000000, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                // do nothing
            }

            gyms = new ArrayList<GymListItem>();

            float distances[] = new float[3];
            gymDistances = new String[25];

            for (int i = 0; i < 25; i++) {
                if (jsonGetter.coordinates[i][0] != 0) {
                    Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(), jsonGetter.coordinates[i][0], jsonGetter.coordinates[i][1], distances);
                    gyms.add(new GymListItem(jsonGetter.names[i], jsonGetter.addresses[i], distances[0]));
                    double dist = (double) distances[0];
                    dist = dist * 0.000621371;
                    dist = (double) Math.round(dist * 100) / 100;
                    gymDistances[i] = Double.toString(dist) + " mi";
                }
            }

            gymAdapter = new GymListAdapter(getActivity(), R.layout.gym_list_item, gyms);
            gymList = (ListView) v.findViewById(R.id.listGyms);
            View gymHeader = getActivity().getLayoutInflater().inflate(R.layout.gym_list_header, null);
            gymList.addHeaderView(gymHeader);
            gymList.setAdapter(gymAdapter);

            final String stockImage = "http://maps.gstatic.com/mapfiles/place_api/icons/fitness-71.png";

            gymList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Dialog dialog = new Dialog(getActivity());
                    LayoutInflater inflater  = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflater.inflate(R.layout.gym_popup, null);
                    gymPopupImage = (ImageView) v.findViewById(R.id.gymPopupImage);
                    getGymImage getImage = new getGymImage();
                    if (jsonGetter.images[position - 1].equalsIgnoreCase("none")) {
                        getImage.execute(stockImage);
                    }
                    else {
                        String imageURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400" +
                                "&photoreference=" + jsonGetter.images[position - 1] +
                                "&key=AIzaSyAgAA0SJSFBydGuJpbJ8LlnUPoQx9CM4PU";

                        getImage.execute(imageURL);
                    }
                    TextView popupName = (TextView) v.findViewById(R.id.gymPopupName);
                    popupName.setText(jsonGetter.names[position - 1]);
                    TextView popupAddress = (TextView) v.findViewById(R.id.gymPopupAddress);
                    popupAddress.setText(jsonGetter.addresses[position - 1]);
                    TextView popupDistance = (TextView) v.findViewById(R.id.gymPopupDistance);
                    popupDistance.setText(gymDistances[position - 1]);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(v);
                    dialog.show();
                }
            });
        }

        return v;
    }

    private class getJSON extends AsyncTask<String, Integer, String> {

        double[][] coordinates  = new double[25][2];
        String[] names = new String[25];
        String[] addresses = new String[25];
        String[] images = new String[25];

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
            }
            return "success";
        }
    }

    private class getGymImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap image = null;
            InputStream inStream = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;

            try {
                URL url = new URL(params[0]);
                URLConnection connection = url.openConnection();

                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();

                    if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        inStream = httpURLConnection.getInputStream();
                    }
                }
                catch (Exception error) {
                    // do nothing
                }

                image = BitmapFactory.decodeStream(inStream, null, options);
                inStream.close();
            }
            catch (Exception e) {
                // do nothing
            }

            return image;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            gymPopupImage.setImageBitmap(image);
        }
    }
}
