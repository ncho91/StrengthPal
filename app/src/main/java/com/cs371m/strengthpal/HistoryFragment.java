package com.cs371m.strengthpal;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Tyler_iMac on 10/23/14.
 */
public class HistoryFragment extends Fragment {

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

        return rootView;
    }
}
