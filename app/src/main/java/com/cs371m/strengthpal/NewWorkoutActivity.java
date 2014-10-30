package com.cs371m.strengthpal;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class NewWorkoutActivity extends Activity {

    //private LinearLayout mLayout;
    private EditText mEditText;
    private Button mButton;
    RoutineHelper routine;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        SharedPreferences sharedPreferences = getSharedPreferences("workout_prefs", MODE_PRIVATE);
        String workout = sharedPreferences.getString("workout_plan", "none");

        mEditText = (EditText) findViewById(R.id.enter_exercise_text);
        mButton = (Button) findViewById(R.id.add_exercise_button);

        //checked the shared preferences for workout_plan, if workout plan is chosen then populate journal, if not leave it blank
        if (workout.equals("Starting Strength")){
            Log.v("blah", "workout.equals = " + workout);
            Toast.makeText(context, "Starting Strength selected", Toast.LENGTH_SHORT).show();
            populateJournal(this, workout);
            add(this, mButton);
        }
        else {
            add(this, mButton);
        }



        //LinearLayout linearLayoutView = new LinearLayout(this);

    }

    public static void add(final Activity activity, Button button) {
        final LinearLayout linearLayoutForm = (LinearLayout) activity.findViewById(R.id.main_linearlayout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout newView = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.new_workout_row_detail, null);

                newView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                EditText enterExerciseText = (EditText) newView.findViewById(R.id.enter_exercise_text);
                EditText enterWeightText = (EditText) newView.findViewById(R.id.enter_weight_text);
                EditText enterRepsText = (EditText) newView.findViewById(R.id.enter_reps_text);
                EditText enterSetsText = (EditText) newView.findViewById(R.id.enter_sets_text);

                Button mButtonRemove = (Button) newView.findViewById(R.id.remove_exercise_button);

                mButtonRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linearLayoutForm.removeView(newView);
                    }
                });

                linearLayoutForm.addView(newView);

            }
        });
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
