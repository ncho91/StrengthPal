package com.cs371m.strengthpal;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class ListPage extends Fragment {

    public ListPage() {}

    double[][] coordinates;
    String[] names;
    String[] addresses;
    String[] images;
    String[] ids;
    Location currentLocation;
    ListView gymList;
    ArrayList<GymListItem> gyms;
    GymListAdapter gymAdapter;
    String gymDistances[];
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list, container, false);

        gyms = new ArrayList<GymListItem>();
        gymDistances = new String[25];
        gymAdapter = new GymListAdapter(getActivity(), R.layout.gym_list_item, gyms);
        gymList = (ListView) v.findViewById(R.id.listGyms);

        bundle = this.getArguments();

        if(bundle != null) {
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
                    gyms.add(new GymListItem(names[i], addresses[i], distances[0]));
                    double dist = (double) distances[0];
                    dist = dist * 0.000621371;
                    dist = (double) Math.round(dist * 100) / 100;
                    gymDistances[i] = Double.toString(dist) + " mi";
                }
            }

            View gymHeader = getActivity().getLayoutInflater().inflate(R.layout.gym_list_header, null);
            gymList.addHeaderView(gymHeader);
            gymList.setAdapter(gymAdapter);

            gymList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    gymClick(position);}});
        }

        return v;
    }

    private void gymClick (int position) {
        DetailDialog detailDialog = new DetailDialog(getActivity(), coordinates, names, ids, images, gymDistances);
        detailDialog.expand(position);
    }
}
