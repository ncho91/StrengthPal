package com.cs371m.strengthpal;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class DetailDialog {

    Context context;
    final String stockImage = "http://maps.gstatic.com/mapfiles/place_api/icons/fitness-71.png";
    double[][] coordinates;
    String[] names;
    String[] images;
    String[] ids;
    String gymDistances[];
    String phoneStr;
    Bundle bundle;
    ImageView gymPopupImage;
    TextView gymPopupPhone;
    TextView gymPopupAddress;
    TextView gymPopupMonday;
    TextView gymPopupTuesday;
    TextView gymPopupWednesday;
    TextView gymPopupThursday;
    TextView gymPopupFriday;
    TextView gymPopupSaturday;
    TextView gymPopupSunday;

    public DetailDialog(Context activity, double[][] coord, String[] nam, String[] id, String[] img, String[] gymDist) {
        context = activity;
        coordinates = coord;
        names = nam;
        ids = id;
        images = img;
        gymDistances = gymDist;
    }

    public void expand(int position) {
        Dialog dialog = new Dialog(context);
        LayoutInflater inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.gym_popup, null);

        gymPopupImage = (ImageView) v.findViewById(R.id.gymPopupImage);

        getGymImage getImage = new getGymImage();

        if (images[position - 1].equalsIgnoreCase("none")) {
            getImage.execute(stockImage);
        }
        else {
            String imageURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400" +
                    "&photoreference=" + images[position - 1] +
                    "&key=AIzaSyAgAA0SJSFBydGuJpbJ8LlnUPoQx9CM4PU";

            getImage.execute(imageURL);
        }

        TextView popupName = (TextView) v.findViewById(R.id.gymPopupName);
        popupName.setText(names[position - 1]);
        gymPopupAddress = (TextView) v.findViewById(R.id.gymPopupAddress);
        TextView popupDistance = (TextView) v.findViewById(R.id.gymPopupDistance);
        popupDistance.setText(gymDistances[position - 1]);
        gymPopupPhone = (TextView) v.findViewById(R.id.gymPopupPhone);

        GetDetails detailGetter = new GetDetails();
        String detailsRequest = "https://maps.googleapis.com/maps/api/place/details/json?" +
                "placeid=" + ids[position - 1] +
                "&key=AIzaSyAgAA0SJSFBydGuJpbJ8LlnUPoQx9CM4PU";

        gymPopupMonday = (TextView) v.findViewById(R.id.gymPopupMonday);
        gymPopupTuesday = (TextView) v.findViewById(R.id.gymPopupTuesday);
        gymPopupWednesday = (TextView) v.findViewById(R.id.gymPopupWednesday);
        gymPopupThursday = (TextView) v.findViewById(R.id.gymPopupThursday);
        gymPopupFriday = (TextView) v.findViewById(R.id.gymPopupFriday);
        gymPopupSaturday = (TextView) v.findViewById(R.id.gymPopupSaturday);
        gymPopupSunday = (TextView) v.findViewById(R.id.gymPopupSunday);

        detailGetter.execute(detailsRequest);

        Button callButton = (Button) v.findViewById(R.id.gymPopupButtonCall);
        callButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String uri = phoneStr.trim();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(uri));
                context.startActivity(callIntent);
            }
        });

        final String destination = "http://maps.google.com/maps" +
                "?daddr=" + coordinates[position - 1][0] + "," + coordinates[position - 1][1];

        final Button directionButton = (Button) v.findViewById(R.id.gymPopupButtonDirections);
        directionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent directionIntent = new Intent(Intent.ACTION_VIEW);
                directionIntent.setData(Uri.parse(destination));
                context.startActivity(directionIntent);
            }
        });

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(v);
        dialog.show();
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

    private class GetDetails extends AsyncTask<String, Integer, String> {

        String hours[] = new String[7];
        String address;

        @Override
        protected String doInBackground(String... params) {
            String phone = "None";
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

            JSONObject result = null;
            if (json != null) {
                try {
                    result = json.getJSONObject("result");
                } catch (Exception e) {
                    // do nothing
                }
            }

            if (result != null) {
                try {
                    phone = result.getString("formatted_phone_number");
                }
                catch (Exception e) {
                    phone = "Number Unavailable";
                }
                try {
                    address = result.getString("formatted_address");
                }
                catch (Exception e) {
                    address = "Address Unavailable";
                }
                try {
                    JSONArray openingHours = result.getJSONObject("opening_hours").getJSONArray("weekday_text");
                    for (int i = 0; i < 7; i++) {
                        hours[i] = openingHours.getString(i);
                    }
                }
                catch (Exception e) {
                    hours[0] = "Monday: hours unavailable";
                    hours[1] = "Tuesday: hours unavailable";
                    hours[2] = "Wednesday: hours unavailable";
                    hours[3] = "Thursday: hours unavailable";
                    hours[4] = "Friday: hours unavailable";
                    hours[5] = "Saturday: hours unavailable";
                    hours[6] = "Sunday: hours unavailable";
                }
            }

            return phone;
        }

        @Override
        protected void onPostExecute(String phone) {
            phoneStr = "tel:" + phone;
            gymPopupPhone.setText(phone);
            gymPopupAddress.setText(address);
            gymPopupMonday.setText(hours[0]);
            gymPopupTuesday.setText(hours[1]);
            gymPopupWednesday.setText(hours[2]);
            gymPopupThursday.setText(hours[3]);
            gymPopupFriday.setText(hours[4]);
            gymPopupSaturday.setText(hours[5]);
            gymPopupSunday.setText(hours[6]);
        }
    }
}