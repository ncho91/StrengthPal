package com.cs371m.strengthpal;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


public class NewWorkoutActivity extends Activity {

    //private LinearLayout mLayout;
    private EditText mEditText;
    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);


        mEditText = (EditText) findViewById(R.id.enter_exercise_text);
        mButton = (Button) findViewById(R.id.add_exercise_button);

        add(this, mButton);

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


//    private EditText createNewLinearLayout(String text) {
//        final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        final EditText editTextView = new EditText(this);
//        editTextView.setLayoutParams(lparams);
//        editTextView.setHint("Exercise");
//        return editTextView;
//    }

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
