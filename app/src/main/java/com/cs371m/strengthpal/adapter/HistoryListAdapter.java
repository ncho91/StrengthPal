package com.cs371m.strengthpal.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cs371m.strengthpal.R;
import com.cs371m.strengthpal.model.HistoryItem;

import java.util.ArrayList;

/**
 * Created by tcorley on 11/24/14.
 */
public class HistoryListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HistoryItem> historyItems;

    public HistoryListAdapter(Context context, ArrayList<HistoryItem> historyItems) {
        this.context = context;
        this.historyItems = historyItems;
    }

    @Override
    public int getCount() {
        return historyItems.size();
    }

    @Override
    public Object getItem(int position) {
        return historyItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.history_list_item, null);
        }

        TextView date = (TextView) convertView.findViewById(R.id.date);
        date.setText(historyItems.get(position).getDate());

        TextView count = (TextView) convertView.findViewById(R.id.num_exercises);
        count.setText("" + historyItems.get(position).getCount());

        TextView label = (TextView) convertView.findViewById(R.id.label);
        label.setText((historyItems.get(position).getCount() > 1) ? "exercises":"exercise");

        return convertView;
    }
}
