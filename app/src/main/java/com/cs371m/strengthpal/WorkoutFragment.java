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


        /*
            fill out workout entries with default values as per program specification
         */
        final Button startingStrengthbutton = (Button) rootView.findViewById(R.id.starting_strength_button);
        startingStrengthbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("workout_plan", "Starting Strength").commit();
                Toast.makeText(context, "Starting Strength selected", Toast.LENGTH_SHORT).show();

            }
        });

        final Button strongLiftsbutton = (Button) rootView.findViewById(R.id.strong_lifts_button);
        strongLiftsbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("workout_plan", "Strong Lifts").commit();
                Toast.makeText(context, "Strong Lifts selected", Toast.LENGTH_SHORT).show();
            }
        });

        final Button wendlerButton = (Button) rootView.findViewById(R.id.wendler_button);
        wendlerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("workout_plan", "Wendler 5/3/1").commit();
                Toast.makeText(context, "Wendler 5/3/1 selected", Toast.LENGTH_SHORT).show();
            }
        });

        final Button phatButton = (Button) rootView.findViewById(R.id.phat_button);
        phatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("workout_plan", "Layne Norton P.H.A.T.").commit();
                Toast.makeText(context, "Layne Norton P.H.A.T. selected", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
