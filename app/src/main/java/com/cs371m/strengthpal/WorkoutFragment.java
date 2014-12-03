package com.cs371m.strengthpal;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Tyler_iMac on 10/23/14.
 */
public class WorkoutFragment extends Fragment {


    public WorkoutFragment(){}
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workout, container, false);
        context = container.getContext();

        final SharedPreferences prefs = getActivity().getSharedPreferences("workout_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("workout_plan", "none").commit();
        /*
            fill out workout entries with default values as per program specification
         */

        final Button startingStrengthButton = (Button) rootView.findViewById(R.id.chest_triceps);
        startingStrengthButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("workout_plan", "Chest and Triceps").commit();
                Toast.makeText(context, "Chest and Triceps selected", Toast.LENGTH_SHORT).show();

            }
        });

        final Button strongLiftsButton = (Button) rootView.findViewById(R.id.back_biceps);
        strongLiftsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("workout_plan", "Back and Biceps").commit();
                Toast.makeText(context, "Back and Biceps selected", Toast.LENGTH_SHORT).show();
            }
        });

        final Button wendlerButton = (Button) rootView.findViewById(R.id.legs_abs);
        wendlerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("workout_plan", "Legs and Abs").commit();
                Toast.makeText(context, "Legs and Abs selected", Toast.LENGTH_SHORT).show();
            }
        });

        final Button phatButton = (Button) rootView.findViewById(R.id.shoulder_arms);
        phatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("workout_plan", "Shoulders and Arms").commit();
                Toast.makeText(context, "Shoulers and Arms selected", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
