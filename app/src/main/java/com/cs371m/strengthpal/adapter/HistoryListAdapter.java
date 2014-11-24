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

        TextView stuffText = (TextView) convertView.findViewById(R.id.stuff);
        stuffText.setText(historyItems.get(position).getStuff());

        return convertView;
    }
}
