package com.cs371m.strengthpal;

import android.app.Fragment;
import android.os.Bundle;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GymFragment extends Fragment implements View.OnClickListener {

    public GymFragment() {}

    Fragment mapFragment;
    Fragment listFragment;
    FragmentManager fragmentManager;
    Button switchView;
    Boolean onMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gym, container, false);

        onMap = true;
        switchView = (Button) view.findViewById(R.id.switchViewsButton);
        switchView.setText("List Results");
        switchView.setOnClickListener(this);

        mapFragment = new MapPage();
        listFragment = new ListPage();

        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, mapFragment).commit();

        return view;
    }

    @Override
    public void onClick(View v) {
        if (onMap) {
            onMap = false;
            switchView.setText("View on Map");
            fragmentManager.beginTransaction().replace(R.id.content_frame, listFragment).commit();
        }
        else {
            onMap = true;
            switchView.setText("List Results");
            fragmentManager.beginTransaction().replace(R.id.content_frame, mapFragment).commit();
        }
    }

    public void onDestroyView()
    {
        if (onMap) {
            fragmentManager.beginTransaction().remove(mapFragment).commit();
        }
        super.onDestroyView();
    }
}