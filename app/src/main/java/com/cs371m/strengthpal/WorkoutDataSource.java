package com.cs371m.strengthpal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * Created by Cho on 11/23/14.
 */

public class WorkoutDataSource {

    //database fields
    private SQLiteDatabase db;
    private WorkoutDB workoutDbHelper;
    private String[] allColumns = {WorkoutDB.WORKOUT_COLUMN_ID, WorkoutDB.WORKOUT_COLUMN_DATE, WorkoutDB.WORKOUT_COLUMN_EXERCISE,
        WorkoutDB.WORKOUT_COLUMN_WEIGHT, WorkoutDB.WORKOUT_COLUMN_REPS, WorkoutDB.WORKOUT_COLUMN_SETS};

    public WorkoutDataSource(Context context) {
        workoutDbHelper = new WorkoutDB(context);
    }

    public void open() throws SQLiteException {
        db = workoutDbHelper.getWritableDatabase();
    }

    public void close() {
        workoutDbHelper.close();
    }

    public WorkoutDBEntry createEntry(String exercise, double weight, int reps, int sets){
        ContentValues values = new ContentValues();
        values.put(WorkoutDB.WORKOUT_COLUMN_EXERCISE, exercise);
        values.put(WorkoutDB.WORKOUT_COLUMN_WEIGHT, exercise);
        values.put(WorkoutDB.WORKOUT_COLUMN_REPS, reps);
        values.put(WorkoutDB.WORKOUT_COLUMN_SETS, sets);
        return null;
    }

}
