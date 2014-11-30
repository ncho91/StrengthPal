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
    private ArrayList<WorkoutDBEntry> workoutDBEntries;
    private int workout_id;

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
        workout_id = sharedPreferences.getInt("running_workout_id", 0);

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
        workoutDBEntries = new ArrayList<WorkoutDBEntry>();

        mLastEntry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                // On successful entry
                if(i == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    addEntryAction(null);
                }
                return true;
            }


        });

    }

    public void addEntryAction(View view) {
        // fine. We'll extract the values
        EditText exercise = (EditText)findViewById(R.id.enter_exercise_text_2);
        EditText weight = (EditText)findViewById(R.id.enter_weight_text_2);
        EditText reps = (EditText)findViewById(R.id.enter_reps_text_2);
        EditText sets = (EditText)findViewById(R.id.enter_sets_text_2);
//        //create history list item
//        String stuff = exercise.getText().toString() + "; " +
//                weight.getText().toString() + "; " +
//                reps.getText().toString() + "; " +
//                sets.getText().toString();
//        //populate it
//        historyItems.add(new HistoryItem(stuff));
//        workoutDBEntries.add(new WorkoutDBEntry(index,date,
//                exercise.getText().toString(),
//                Double.valueOf(weight.getText().toString()),
//                Integer.getInteger(reps.getText().toString()),
//                Integer.getInteger(sets.getText().toString())));
//        index++;
//        for (WorkoutDBEntry e: workoutDBEntries){
//            Log.v("Exercises", e.toString());
//        }
        //display in the scrollview
//        final LinearLayout newHistoryItem = (LinearLayout) getLayoutInflater().inflate(R.layout.history_list_item, null);
//        newHistoryItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        ((TextView)newHistoryItem.findViewById(R.id.stuff)).setText(stuff);
//        ((LinearLayout) findViewById(R.id.main_linearlayout)).addView(newHistoryItem);
        final LinearLayout newHistoryItem = (LinearLayout) getLayoutInflater().inflate(R.layout.new_workout_row_detail, null);
        ((EditText)newHistoryItem.findViewById(R.id.enter_exercise_text)).setText(exercise.getText().toString());
        ((EditText)newHistoryItem.findViewById(R.id.enter_weight_text)).setText(weight.getText().toString());
        ((EditText)newHistoryItem.findViewById(R.id.enter_reps_text)).setText(reps.getText().toString());
        ((EditText)newHistoryItem.findViewById(R.id.enter_sets_text)).setText(sets.getText().toString());
        ((LinearLayout) findViewById(R.id.main_linearlayout)).addView(newHistoryItem);

        // clear out old entries
        ((EditText)findViewById(R.id.enter_exercise_text_2)).setText("");
        ((EditText)findViewById(R.id.enter_sets_text_2)).setText("");
        ((EditText)findViewById(R.id.enter_weight_text_2)).setText("");
        ((EditText)findViewById(R.id.enter_reps_text_2)).setText("");
        // set the focus to the first EditText
        findViewById(R.id.enter_exercise_text_2).requestFocus();
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
                saveWorkout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //function for button to save to database
    public void saveWorkout() {
        Toast.makeText(this, "The Butôn works",Toast.LENGTH_SHORT).show();
        LinearLayout main = (LinearLayout)findViewById(R.id.main_linearlayout);
        int childCount = main.getChildCount();
        Log.v("AAAH", "No. of children: " + childCount);
        for (int i = 0; i < childCount; i++) {
            LinearLayout e = (LinearLayout)main.getChildAt(i);
            EditText exercise = (EditText)e.findViewById(R.id.enter_exercise_text);
            EditText weight = (EditText)e.findViewById(R.id.enter_weight_text);
            EditText reps = (EditText)e.findViewById(R.id.enter_reps_text);
            EditText sets = (EditText)e.findViewById(R.id.enter_sets_text);
            Log.v("Real Monsters", "E:" + exercise.getText().toString() + "||W: " + weight.getText().toString() + "||R: " + reps.getText().toString() + "||S: " + sets.getText().toString());
            workoutDBEntries.add(new WorkoutDBEntry(workout_id,date,
                    exercise.getText().toString(),
                    Integer.parseInt(weight.getText().toString()),
                    Integer.parseInt(reps.getText().toString()),
                    Integer.parseInt(sets.getText().toString())));
        }
        Toast.makeText(this, "We're saving " + workoutDBEntries.size() + " Exercises in this workout.", Toast.LENGTH_SHORT).show();
        for (WorkoutDBEntry e: workoutDBEntries){
            Log.v("Exercises", e.toString());
        }
        //increment the workout_id
        getSharedPreferences("workout_prefs", MODE_PRIVATE)
                .edit()
                .putInt("running_workout_id", ++workout_id)
                .commit();
    }

    public void removeEntry(View view) {
        //view.getParent() refers to the linear layout that holds the line
        ((LinearLayout)findViewById(R.id.main_linearlayout)).removeView((View) view.getParent());
        //Also remove from the arraylist
    }

}
