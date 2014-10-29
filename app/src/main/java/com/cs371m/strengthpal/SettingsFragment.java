package com.cs371m.strengthpal;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.DatePicker;

/**
 * Created by Tyler_iMac on 10/23/14.
 */
public class SettingsFragment extends PreferenceFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load the preferences from resource file
        addPreferencesFromResource(R.xml.preferences);
        getPreferenceManager().setSharedPreferencesName("sp_prefs");
        final SharedPreferences prefs = getActivity().getSharedPreferences("sp_prefs", getActivity().MODE_PRIVATE);

        Preference btnDateFilter = findPreference("btnDateFilter");
        btnDateFilter.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showDateDialog();
                return false;
            }
        });

        final Preference birthdayLabelPref = findPreference("btnDateFilter");
        String birthday = prefs.getString("birthday", "01-01-1990");
        birthdayLabelPref.setSummary(birthday);

        // Set up showing the weight text selected in the summary
        final EditTextPreference weightPref = (EditTextPreference) findPreference("weight");
        String weightMessage = prefs.getString("weight", "None Yet");
        String units = (prefs.getString("units", "imperial") == "imperial") ? " lbs" : " kg";
        weightPref.setSummary(weightMessage + units);

        weightPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String units = (prefs.getString("units", "imperial") == "imperial") ? " lbs" : " kg";
                weightPref.setSummary(newValue + units);

                // save the pref
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("weight", newValue.toString());
                ed.commit();
                return true;
            }
        });

        // Set up showing the height text selected in the summary
        final EditTextPreference heightPref = (EditTextPreference) findPreference("height");
        String heightMessage = prefs.getString("height", "None Yet");
        String unitsHeight = (prefs.getString("units", "imperial") == "imperial") ? " in" : " cm";
        heightPref.setSummary(heightMessage + unitsHeight);

        heightPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String units = (prefs.getString("units", "imperial") == "imperial") ? " in" : " cm";
                heightPref.setSummary(newValue + units);

                // save the pref
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("height", newValue.toString());
                ed.commit();
                return true;
            }
        });

        // Set up showing the who's next text in the summary
        final ListPreference unitPref = (ListPreference) findPreference("units");
        String next = prefs.getString("units", "imperial");
        unitPref.setSummary(next);

        unitPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                unitPref.setSummary((CharSequence) newValue);

                // Since we are handling the pref, we must save it
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString("units", newValue.toString());
                ed.commit();

                boolean is_imperial = prefs.getString("units", "Imperial").equals("Imperial");
                Log.v("Hai", ""+is_imperial);
                heightPref.setSummary(prefs.getString("height", "NaN") + ((is_imperial) ? " in" : " cm"));
                weightPref.setSummary(prefs.getString("weight", "NaN") + ((is_imperial) ? " lbs" : " kg"));
                return true;
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
        Log.i("dasd", String.format("%02d", i2)+"-"+String.format("%02d", i3)+"-"+String.format("%04d", i));

        SharedPreferences.Editor ed = getActivity().getSharedPreferences("sp_prefs", getActivity().MODE_PRIVATE).edit();
        String storeString = String.format("%02d", i2)+"-"+String.format("%02d", i3)+"-"+String.format("%04d", i);
        String viewString = String.format("%02d", i2 + 1)+"-"+String.format("%02d", i3)+"-"+String.format("%04d", i);
        ed.putString("birthday", storeString);
        ed.commit();
        findPreference("btnDateFilter").setSummary(viewString);
    }

    private void showDateDialog(){
        final SharedPreferences prefs = getActivity().getSharedPreferences("sp_prefs", getActivity().MODE_PRIVATE);
        String birthday = prefs.getString("birthday", "01-01-1990");
        int month = Integer.parseInt(birthday.substring(0,2));
        int day = Integer.parseInt(birthday.substring(3,5));
        int year = Integer.parseInt(birthday.substring(6,10));
        Log.v("dasd", ""+month+"-"+day+"-"+year);
        new DatePickerDialog(getActivity(),this, year, month, day).show();

    }


}
