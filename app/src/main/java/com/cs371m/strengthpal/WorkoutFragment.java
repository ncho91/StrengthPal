package com.cs371m.strengthpal;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Tyler_iMac on 10/23/14.
 */
public class WorkoutFragment extends Fragment {

    public WorkoutFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workout, container, false);


        /*
            fill out workout entries with default values as per program specification
         */
        final Button startingStrengthbutton = (Button) rootView.findViewById(R.id.starting_strength_button);
        startingStrengthbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            }
        });

        final Button strongLiftbutton = (Button) rootView.findViewById(R.id.strong_lifts_button);
        strongLiftbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            }
        });

        final Button wendlerButton = (Button) rootView.findViewById(R.id.wendler_button);
        wendlerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            }
        });

        final Button phatButton = (Button) rootView.findViewById(R.id.phat_button);
        phatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            }
        });

        return rootView;
    }
}
