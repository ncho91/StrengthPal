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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        GymHolder gymHolder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            gymHolder = new GymHolder();
            gymHolder.name = (TextView) row.findViewById(R.id.listName);
            gymHolder.address = (TextView) row.findViewById(R.id.listAddress);
            row.setTag(gymHolder);
        } else {
            gymHolder = (GymHolder) row.getTag();
        }

        GymListItem gymListItem = items.get(position);
        gymHolder.name.setText(gymListItem.name);
        gymHolder.address.setText(gymListItem.address);

        return row;
    }

    static class GymHolder {
        TextView name;
        TextView address;
    }
}