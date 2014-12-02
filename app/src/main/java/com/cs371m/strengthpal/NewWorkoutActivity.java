package com.cs371m.strengthpal;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cs371m.strengthpal.model.HistoryItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class NewWorkoutActivity extends Activity {

    private EditText mLastEntry;
    private Context context = this;
    private Date date;
    private ArrayList<HistoryItem> historyItems;
    private ArrayList<WorkoutDBEntry> workoutDBEntries;
    private int workout_id;
    private SQLiteDatabase db;
    private WorkoutDB wdb;

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

        //checked the shared preferences for workout_plan, if workout plan is chosen then populate journal, if not leave it blank
        if(workout.equals("none")) {
            Log.v("blah", "no workout selected");
        }
        else if (workout.equals("Starting Strength")){
            Log.v("blah", "workout.equals = " + workout);
            Toast.makeText(context, "Starting Strength selected", Toast.LENGTH_SHORT).show();
            populateJournal(this, workout);
            //add(mButton);
        }

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

        wdb = new WorkoutDB(this);
        Log.v("Database", "This should've created the db by now");
        // this is not picking up the correct information here
        db = wdb.getWritableDatabase();

    }

    public void addEntryAction(View view) {
        EditText exercise = (EditText)findViewById(R.id.enter_exercise_text_2);
        EditText weight = (EditText)findViewById(R.id.enter_weight_text_2);
        EditText reps = (EditText)findViewById(R.id.enter_reps_text_2);
        EditText sets = (EditText)findViewById(R.id.enter_sets_text_2);

        if(exercise.getText().toString().matches("") || weight.getText().toString().matches("") || reps.getText().toString().matches("") || sets.getText().toString().matches("")) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
        }
        else {
            // Create the new entry in the table
            final LinearLayout newHistoryItem = (LinearLayout) getLayoutInflater().inflate(R.layout.new_workout_row_detail, null);
            ((EditText) newHistoryItem.findViewById(R.id.enter_exercise_text)).setText(exercise.getText().toString());
            ((EditText) newHistoryItem.findViewById(R.id.enter_weight_text)).setText(weight.getText().toString());
            ((EditText) newHistoryItem.findViewById(R.id.enter_reps_text)).setText(reps.getText().toString());
            ((EditText) newHistoryItem.findViewById(R.id.enter_sets_text)).setText(sets.getText().toString());
            ((LinearLayout) findViewById(R.id.main_linearlayout)).addView(newHistoryItem);

            // clear out old entries
            exercise.setText("");
            weight.setText("");
            reps.setText("");
            sets.setText("");
            // set the focus to the first EditText
            exercise.requestFocus();
        }
    }

    // NEEDS NEW IMPLEMENTATION
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
            case R.id.save_workout:
                saveWorkout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //function for button to save to database
    public void saveWorkout() {
        LinearLayout main = (LinearLayout)findViewById(R.id.main_linearlayout);
        for (int i = 0; i < main.getChildCount(); i++) {
            LinearLayout e = (LinearLayout)main.getChildAt(i);
            EditText exercise = (EditText)e.findViewById(R.id.enter_exercise_text);
            EditText weight = (EditText)e.findViewById(R.id.enter_weight_text);
            EditText reps = (EditText)e.findViewById(R.id.enter_reps_text);
            EditText sets = (EditText)e.findViewById(R.id.enter_sets_text);
            workoutDBEntries.add(new WorkoutDBEntry(workout_id,date,
                    exercise.getText().toString(),
                    Integer.parseInt(weight.getText().toString()),
                    Integer.parseInt(reps.getText().toString()),
                    Integer.parseInt(sets.getText().toString())));
        }
        Toast.makeText(this, "Workout with " + workoutDBEntries.size() + " exercise" + ((workoutDBEntries.size() > 1) ?"s":"") + " saved!", Toast.LENGTH_SHORT).show();
        for (WorkoutDBEntry e: workoutDBEntries){
            Log.v("Exercises", e.toString());
        }
        //increment the workout_id
        getSharedPreferences("workout_prefs", MODE_PRIVATE)
                .edit()
                .putInt("running_workout_id", ++workout_id)
                .commit();

        //GOD SAVE THE QUEEN
        addWorkout();

        //Is this the way back to history?
        NavUtils.navigateUpFromSameTask(this);
    }

    public void removeEntry(View view) {
        //view.getParent() refers to the linear layout that holds the line
        ((LinearLayout)findViewById(R.id.main_linearlayout)).removeView((View) view.getParent());
        //Also remove from the arraylist
    }

    public void addWorkout() {
        if(workoutDBEntries.isEmpty()) {
            Log.v("Database", "No values to insert");
        }
        else {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //get the id and the date from the first item
            long id = workoutDBEntries.get(0).getId();
            Date date = workoutDBEntries.get(0).getDate();
            try {
                db.beginTransaction();
                for (WorkoutDBEntry e : workoutDBEntries) {
                    ContentValues values = new ContentValues();
                    values.put("id", id);
                    values.put("date", sdf.format(date));
                    values.put("exercise", e.getExercise());
                    values.put("weight", e.getWeight());
                    values.put("reps", e.getReps());
                    values.put("sets", e.getSets());
                    db.insert(wdb.WORKOUT_TABLE_NAME, null, values);
                }
                db.setTransactionSuccessful();
                Log.v("Database", "It works!");
            }
            catch (SQLiteException e) {
                Log.v("Database", "Error inserting into database: " + e);
            }
            finally {
                db.endTransaction();
            }
        }


    }

}
