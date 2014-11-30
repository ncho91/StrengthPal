package com.cs371m.strengthpal;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs371m.strengthpal.adapter.HistoryListAdapter;
import com.cs371m.strengthpal.adapter.WorkoutDetailListAdapter;
import com.cs371m.strengthpal.model.HistoryItem;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Tyler_iMac on 10/23/14.
 */
public class HistoryFragment extends Fragment {

    WorkoutDB wdb;
    SQLiteDatabase db;
    private ListView mHistoryList;
    private HistoryListAdapter adapter;
    private ArrayList<HistoryItem> mHistoryItems;

    public HistoryFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        final Button newWorkoutButton = (Button) rootView.findViewById(R.id.new_workout_button);
        newWorkoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), NewWorkoutActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        final Button showCountButton = (Button) rootView.findViewById(R.id.show_count);
        showCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutCount();
            }
        });

        wdb = new WorkoutDB(getActivity());
        db = wdb.getReadableDatabase();

        mHistoryItems= getAllWorkouts();

        // ListView for the History
        mHistoryList = (ListView) rootView.findViewById(R.id.history);
        mHistoryList.setOnItemClickListener(new HistoryItemClickListener());

        adapter = new HistoryListAdapter(getActivity(), mHistoryItems);
        mHistoryList.setAdapter(adapter);

        for(HistoryItem e: mHistoryItems){
            Log.v("Database", e.toString());
        }

        return rootView;
    }

    public void workoutCount() {
        String countQuery = "SELECT  DISTINCT(id) FROM " + wdb.WORKOUT_TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        Toast.makeText(getActivity(), "" + cursor.getCount(), Toast.LENGTH_SHORT).show();
        cursor.close();
    }

    private ArrayList<HistoryItem> getAllWorkouts() {
        ArrayList<HistoryItem> a = new ArrayList<HistoryItem>();

        // Select All Query
        String selectQuery = "SELECT date, count(*), id FROM " + wdb.WORKOUT_TABLE_NAME + " GROUP BY id ORDER BY id";

        Cursor cursor = db.rawQuery(selectQuery, null);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat displaySDF = new SimpleDateFormat("MMM dd @ HH:mm");

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HistoryItem workout = new HistoryItem();
                workout.setId(Integer.parseInt(cursor.getString(2)));
                try {
                    Date d = (sdf.parse(cursor.getString(0)));
                    workout.setDate(displaySDF.format(d));
                }
                catch (ParseException e) {
                    workout.setDate(displaySDF.format(new Date()));
                }
                workout.setCount(cursor.getInt(1));
                // Adding contact to list
                a.add(workout);
            } while (cursor.moveToNext());
        }

        return a;
    }

    private class HistoryItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Dialog dialog = new Dialog(getActivity());
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.popup_workout_detail, null);
            TextView date_label = (TextView) v.findViewById(R.id.date);
            date_label.setText(mHistoryItems.get(i).getDate());
            // Run the query on the history id to get the exercises
            ArrayList<WorkoutDBEntry> items = getExercises(mHistoryItems.get(i).getId());

            // iterate through the items and add them to the ListView
            ListView lv = (ListView) v.findViewById(R.id.exercises);
            lv.setAdapter(new WorkoutDetailListAdapter(getActivity(), items));

            dialog.setContentView(v);
            dialog.show();
        }
    }

    private ArrayList<WorkoutDBEntry> getExercises(long id) {
        ArrayList<WorkoutDBEntry> exercises = new ArrayList<WorkoutDBEntry>();

        String selectQuery = "SELECT * FROM " + wdb.WORKOUT_TABLE_NAME + " WHERE id=" + id;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                WorkoutDBEntry exercise = new WorkoutDBEntry();
                exercise.setExercise(cursor.getString(2));
                exercise.setWeight(cursor.getInt(3));
                exercise.setReps(cursor.getInt(4));
                exercise.setSets(cursor.getInt(5));

                exercises.add(exercise);
            } while (cursor.moveToNext());
        }

        return exercises;
    }
}
