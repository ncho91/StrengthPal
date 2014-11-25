package com.cs371m.strengthpal;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cs371m.strengthpal.model.HistoryItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class NewWorkoutActivity extends Activity {

    //private LinearLayout mLayout;
    private EditText mLastEntry;
    private Button mButton;
    private Button saveButton;
    RoutineHelper routine;
    private Context context = this;
    private Date date;
    private ArrayList<HistoryItem> historyItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        // ActionBar formatting
        getActionBar().setDisplayHomeAsUpEnabled(true);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        date = new Date();
        getActionBar().setTitle("Workout: " + sdf.format(date));
        // For removing the icon so that we have some more room to work with
        // http://stackoverflow.com/a/17485385
        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        SharedPreferences sharedPreferences = getSharedPreferences("workout_prefs", MODE_PRIVATE);
        String workout = sharedPreferences.getString("workout_plan", "none");

        //mEditText = (EditText) findViewById(R.id.enter_exercise_text);
        //mButton = (Button) findViewById(R.id.add_exercise_button);
        //saveButton = (Button) findViewById(R.id.save_button);

        //checked the shared preferences for workout_plan, if workout plan is chosen then populate journal, if not leave it blank
        if(workout.equals("none")) {
            Log.v("blah", "no workout selected");
            //add(mButton);


        }
        else if (workout.equals("Starting Strength")){
            Log.v("blah", "workout.equals = " + workout);
            Toast.makeText(context, "Starting Strength selected", Toast.LENGTH_SHORT).show();
            populateJournal(this, workout);
            //add(mButton);
        }

        //add(mButton);
        //add blank row
        //final LinearLayout linearLayoutForm = (LinearLayout) findViewById(R.id.main_linearlayout);
        //addRow(linearLayoutForm, false);

        //set listener for the done action on the keyboard
        mLastEntry = (EditText) findViewById(R.id.enter_sets_text_2);

        historyItems = new ArrayList<HistoryItem>();

        mLastEntry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE) {
                    //create history list item
                    String stuff = ((EditText)findViewById(R.id.enter_exercise_text_2)).getText().toString() + "; " +
                            ((EditText)findViewById(R.id.enter_weight_text_2)).getText().toString() + "; " +
                            ((EditText)findViewById(R.id.enter_reps_text_2)).getText().toString() + "; " +
                            ((EditText)findViewById(R.id.enter_sets_text_2)).getText().toString();
                    //populate it
                    historyItems.add(new HistoryItem(stuff));
                    //display in the scrollview
                    final LinearLayout newHistoryItem = (LinearLayout) getLayoutInflater().inflate(R.layout.history_list_item, null);
                    newHistoryItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    ((TextView)newHistoryItem.findViewById(R.id.stuff)).setText(stuff);
                    ((LinearLayout) findViewById(R.id.main_linearlayout)).addView(newHistoryItem);
                }
                return true;
            }
        });

    }

    private void addRow(final LinearLayout linearLayoutForm, boolean shouldShowRemove) {
        final LinearLayout newView = (shouldShowRemove) ? (LinearLayout) getLayoutInflater().inflate(R.layout.new_workout_row_detail, null)
                :  (LinearLayout) getLayoutInflater().inflate(R.layout.new_workout_row_detail_2, null);

        newView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        EditText enterExerciseText = (EditText) newView.findViewById(R.id.enter_exercise_text);
        EditText enterWeightText = (EditText) newView.findViewById(R.id.enter_weight_text);
        EditText enterRepsText = (EditText) newView.findViewById(R.id.enter_reps_text);
        EditText enterSetsText = (EditText) newView.findViewById(R.id.enter_sets_text);

        if(shouldShowRemove) {
            Button mButtonRemove = (Button) newView.findViewById(R.id.remove_exercise_button);

            mButtonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayoutForm.removeView(newView);
                }
            });
        }



        linearLayoutForm.addView(newView);
    }


//    public void add(Button button) {
//        final LinearLayout linearLayoutForm = (LinearLayout) findViewById(R.id.main_linearlayout);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              addRow(linearLayoutForm, true);
//
//
//            }
//        });


//        //insert data into SQLite database whenever "save" is pressed
//        saveButton.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                saveWorkout();
//            }
//        });
    //}

    public void populateJournal(final Activity activity, String workout) {
        String[] exercises = getResources().getStringArray(R.array.starting_strength_list_a);

        final LinearLayout linearLayoutForm = (LinearLayout) activity.findViewById(R.id.main_linearlayout);
        for(int i = 0; i < exercises.length; i++) {
            final LinearLayout newView = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.new_workout_row_detail_2, null);
            newView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            EditText enterExerciseText = (EditText) newView.findViewById(R.id.enter_exercise_text_2);
            EditText enterWeightText = (EditText) newView.findViewById(R.id.enter_weight_text_2);
            EditText enterRepsText = (EditText) newView.findViewById(R.id.enter_reps_text_2);
            EditText enterSetsText = (EditText) newView.findViewById(R.id.enter_sets_text_2);
            enterExerciseText.setText(exercises[i], EditText.BufferType.EDITABLE);
            linearLayoutForm.addView(newView);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.add_exercise:
                addRow((LinearLayout) findViewById(R.id.main_linearlayout), true);
                break;
            case R.id.save_workout:
                Toast.makeText(this, "We should really save here!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //function for button to save to database
    public boolean saveWorkout(View view) {
        Toast.makeText(this, "The Butôn works",Toast.LENGTH_SHORT).show();
        return true;
    }

}
