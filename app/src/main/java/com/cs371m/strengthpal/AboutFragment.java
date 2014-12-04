package com.cs371m.strengthpal;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tyler_iMac on 10/23/14.
 */
public class AboutFragment extends Fragment {

    public AboutFragment(){}

    ShakeListener mShaker;
    ArrayList<String> aQuotes;
    Random rand;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Vibrator vibe = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        aQuotes = new ArrayList<String>();
        String[] q = getResources().getStringArray(R.array.arnold_quotes);
        for(int i = 0; i < q.length; i++) {
            aQuotes.add(q[i]);
        }
        rand = new Random();

        mShaker = new ShakeListener(getActivity());
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener () {
            public void onShake()
            {
                vibe.vibrate(100);
                new AlertDialog.Builder(getActivity())
                        .setPositiveButton(android.R.string.ok, null)
                        .setMessage(randomMessage())
                        .setTitle("Arnold says: ")
                        .show();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView tyler = (ImageView) getActivity().findViewById(R.id.imageView2);
        ImageView noah = (ImageView) getActivity().findViewById(R.id.imageView4);
        ImageView even = (ImageView) getActivity().findViewById(R.id.imageView5);

        int mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_longAnimTime);

        tyler.setAlpha(0f);
        noah.setAlpha(0f);
        even.setAlpha(0f);

        tyler.animate()
                .alpha(1f)
                .setStartDelay(500)
                .setDuration(mShortAnimationDuration)
                .setListener(null);
        noah.animate()
                .alpha(1f)
                .setStartDelay(1000)
                .setDuration(mShortAnimationDuration)
                .setListener(null);
        even.animate()
                .alpha(1f)
                .setStartDelay(1500)
                .setDuration(mShortAnimationDuration)
                .setListener(null);


    }

    @Override
    public void onPause() {
        super.onPause();
        mShaker.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mShaker.resume();
    }

    private String randomMessage() {
        return aQuotes.get(rand.nextInt(aQuotes.size()));
    }
}
