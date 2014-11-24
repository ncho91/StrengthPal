package com.cs371m.strengthpal;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cs371m.strengthpal.adapter.NavDrawerListAdapter;
import com.cs371m.strengthpal.model.NavDrawerItem;

import java.util.ArrayList;


public class MyActivity extends Activity {
    // Variable for the drawer
    private static final String TAG = "StrengthPal";
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mDrawerTitleLabels;
    private TypedArray mDrawerIcons;
    int x;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    // This is used as a way to make sure that we display the drawer only on the first
    // back button press
    private boolean mShouldDisplayToast;
    private WorkoutDB db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mTitle = mDrawerTitle = getTitle();
        mDrawerTitleLabels = getResources().getStringArray(R.array.drawer_options);
        mDrawerIcons = getResources().obtainTypedArray(R.array.drawer_icons);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        //add the items to the array
        //workout history
        navDrawerItems.add(new NavDrawerItem(mDrawerTitleLabels[0], mDrawerIcons.getResourceId(0, -1)));
        //find a gym
        navDrawerItems.add(new NavDrawerItem(mDrawerTitleLabels[1], mDrawerIcons.getResourceId(1, -1)));
        //workouts
        navDrawerItems.add(new NavDrawerItem(mDrawerTitleLabels[2], mDrawerIcons.getResourceId(2, -1)));
        //settings
        navDrawerItems.add(new NavDrawerItem(mDrawerTitleLabels[3], mDrawerIcons.getResourceId(3, -1)));
        //about
        navDrawerItems.add(new NavDrawerItem(mDrawerTitleLabels[4], mDrawerIcons.getResourceId(4, -1)));

        //recycle typed array
        mDrawerIcons.recycle();

        // drawer's list view and a click listener
        // because each title is going to be different, we might need to rethink this line
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // make the icon on the action bar behave as a toggle for the drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // app bar icon, button stuff to make sure it works together
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
                ) {
            public void onDrawerClosed(View drawerView) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        db = new WorkoutDB(this);

        if (savedInstanceState == null) {
            displayView(0);
        }

        mShouldDisplayToast = true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called when we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if the drawer is open, hide action items related to content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //follow this pattern. we won't have a websearch but we might have options pertaining to
        //each fragment. maybe a switch() here?
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open/close the drawer. ActionBarDrawerToggle does it.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /* Implement the click listener for the listview in the drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }
    }

    /**
     * Display the fragment view for the selected nav item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HistoryFragment();
                break;
            case 1:
                fragment = new GymFragment();
                break;
            case 2:
                fragment = new WorkoutFragment();
                break;
            case 3:
                fragment = new SettingsFragment();
                break;
            case 4:
                fragment = new AboutFragment();
                break;

            default:
                break;
        }

        mShouldDisplayToast = true;
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

            //update selected item and title, then close drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mDrawerTitleLabels[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        else {
            // error in creating fragment
            Log.e(TAG, "Error in creating the fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void icons8site(View view) {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://icons8.com/"));
        startActivity(viewIntent);
    }

    public void gpsLink(View view) {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com/legal.html"));
        startActivity(viewIntent);
    }

    @Override
    public void onBackPressed() {
//        if(mShouldDisplayToast) {
//            mDrawerLayout.openDrawer(mDrawerList);
//            mShouldDisplayToast = !mShouldDisplayToast;
//        }
//        else {
//            super.onBackPressed();
//        }
        if(!mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.openDrawer(mDrawerList);
            mShouldDisplayToast = true;
        }
        else {
            if(mShouldDisplayToast) {
                Toast.makeText(this, "Press back once again to exit.", Toast.LENGTH_SHORT).show();
                mShouldDisplayToast = !mShouldDisplayToast;
            }
            else {
                super.onBackPressed();
            }
        }
    }
}
