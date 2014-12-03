package com.cs371m.strengthpal;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Tyler_iMac on 10/23/14.
 */
public class WorkoutFragment extends Fragment {


    public WorkoutFragment(){}
    private Context context;
    SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workout, container, false);
        context = container.getContext();

        prefs = getActivity().getSharedPreferences("workout_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("workout_plan", "none").commit();
        /*
            fill out workout entries with default values as per program specification
         */

        final Button startingStrengthButton = (Button) rootView.findViewById(R.id.chest_triceps);
        startingStrengthButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                SharedPreferences.Editor ed = prefs.edit();
//                ed.putString("workout_plan", "Chest and Triceps").commit();
                //Toast.makeText(context, "Chest and Triceps selected", Toast.LENGTH_SHORT).show();
                showDetails("Chest and Triceps");
            }
        });

        final Button strongLiftsButton = (Button) rootView.findViewById(R.id.back_biceps);
        strongLiftsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                SharedPreferences.Editor ed = prefs.edit();
//                ed.putString("workout_plan", "Back and Biceps").commit();
//                Toast.makeText(context, "Back and Biceps selected", Toast.LENGTH_SHORT).show();
                showDetails("Back and Biceps");
            }
        });

        final Button wendlerButton = (Button) rootView.findViewById(R.id.legs_abs);
        wendlerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                SharedPreferences.Editor ed = prefs.edit();
//                ed.putString("workout_plan", "Legs and Abs").commit();
//                Toast.makeText(context, "Legs and Abs selected", Toast.LENGTH_SHORT).show();
                showDetails("Legs and Abs");
            }
        });

        final Button phatButton = (Button) rootView.findViewById(R.id.shoulder_arms);
        phatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                SharedPreferences.Editor ed = prefs.edit();
//                ed.putString("workout_plan", "Shoulders and Arms").commit();
//                Toast.makeText(context, "Shoulers and Arms selected", Toast.LENGTH_SHORT).show();
                showDetails("Shoulders and Arms");
            }
        });

        return rootView;
    }

    private void showDetails(final String workout) {
        new AlertDialog.Builder(getActivity())
                .setPositiveButton("Get these gains, brah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Navigate to workout page and auto-populate
                        SharedPreferences.Editor ed = prefs.edit();
                        ed.putString("workout_plan", workout).commit();
                        Toast.makeText(context, "Selected " + workout + "!", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(getActivity(), NewWorkoutActivity.class);
                        startActivityForResult(myIntent, 0);
                    }
                })
                .setNegativeButton("Cancel", null)
                .setMessage(getWorkoutNames(workout))
                .show();
    }

    private String getWorkoutNames(String workoutLabel) {
        ArrayList<String> w;
        if(workoutLabel.equals("Chest and Triceps")) {
            w = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.chest_triceps)));
        }
        else if(workoutLabel.equals("Back and Biceps")) {
            w = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.back_bicep)));
        }
        else if(workoutLabel.equals("Legs and Abs")) {
            w = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.leg_abs)));
        }
        else {
            w = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.shoulder_arms)));
        }

        String result = workoutLabel + " includes the following workouts: ";
        for(String s: w) {
            result += s + ", ";
        }
        result = result.substring(0, result.length()-2) + ".";
        return result;
    }
}
