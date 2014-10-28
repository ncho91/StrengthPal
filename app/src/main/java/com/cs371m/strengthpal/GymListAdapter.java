package com.cs371m.strengthpal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class GymListAdapter extends ArrayAdapter<GymListItem> {

    Context context;
    int layoutResourceId;
    ArrayList<GymListItem> items = null;

    public GymListAdapter(Context context, int layoutResourceId, ArrayList<GymListItem> items) {
        super(context, layoutResourceId, items);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup group) {
        View row = convertView;
        GymInfo gymInfo;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, group, false);
            gymInfo = new GymInfo();
            gymInfo.name = (TextView) row.findViewById(R.id.listName);
            gymInfo.address = (TextView) row.findViewById(R.id.listAddress);
            gymInfo.distance = (TextView) row.findViewById(R.id.listDistance);
            row.setTag(gymInfo);
        }
        else {
            gymInfo = (GymInfo) row.getTag();
        }

        GymListItem gymListItem = items.get(position);
        gymInfo.name.setText(gymListItem.name);
        gymInfo.address.setText(gymListItem.address);
        gymInfo.distance.setText(gymListItem.distance);

        return row;
    }

    static class GymInfo {
        TextView name;
        TextView address;
        TextView distance;
    }
}