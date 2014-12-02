package com.cs371m.strengthpal.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cs371m.strengthpal.R;
import com.cs371m.strengthpal.WorkoutDBEntry;
import com.cs371m.strengthpal.model.HistoryItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by TyleriMac on 11/30/14.
 */
public class WorkoutDetailListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<WorkoutDBEntry> exerciseItems;

    public WorkoutDetailListAdapter(Context context, ArrayList<WorkoutDBEntry> exerciseItems) {
        this.context = context;
        this.exerciseItems = exerciseItems;
    }

    @Override
    public int getCount() {
        return exerciseItems.size();
    }

    @Override
    public Object getItem(int i) {
        return exerciseItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.history_detail_item, null);
        }

        TextView ex = (TextView) convertView.findViewById(R.id.exercise);
        ex.setText(exerciseItems.get(position).getExercise());
        TextView wt = (TextView) convertView.findViewById(R.id.weight);
        wt.setText("" + exerciseItems.get(position).getWeight());
        TextView rep = (TextView) convertView.findViewById(R.id.reps);
        rep.setText("" + exerciseItems.get(position).getReps());
        TextView set = (TextView) convertView.findViewById(R.id.sets);
        set.setText("" + exerciseItems.get(position).getSets());

        return convertView;
}
}
