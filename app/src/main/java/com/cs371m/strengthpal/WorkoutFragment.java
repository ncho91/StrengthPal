package com.cs371m.strengthpal;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Tyler_iMac on 10/23/14.
 */
public class WorkoutFragment extends Fragment {

    public WorkoutFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workout, container, false);

        return rootView;
    }
}
