package com.cs371m.strengthpal;

/**
 * Created by Cho on 11/23/14.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Cho on 10/26/14.
 */
public class WorkoutDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "WorkoutDB.db";
    public static final String WORKOUT_TABLE_NAME = "workouts";
    public static final String WORKOUT_COLUMN_ID = "_id";
    public static final String WORKOUT_COLUMN_DATE = "date";
    public static final String WORKOUT_COLUMN_EXERCISE = "exercise";
    public static final String WORKOUT_COLUMN_WEIGHT = "weight";
    public static final String WORKOUT_COLUMN_REPS = "reps";
    public static final String WORKOUT_COLUMN_SETS = "sets";


    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table workout_history " +
            "(id integer primary key, date datetime, exercise text, weight text, reps text, sets text)";
    public WorkoutDB(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(WorkoutDB.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_TABLE_NAME);
        onCreate(db);
    }

//    public boolean insertWorkout (String date, String exercise, String weight, String reps, String sets) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put("date", date);
//        contentValues.put("exercise", exercise);
//        contentValues.put("weight", weight);
//        contentValues.put("reps", reps);
//        contentValues.put("sets", sets);
//
//        db.insert("workout_history", null, contentValues);
//        return true;
//    }

    //add a new workout
    public void addWorkout(WorkoutDBEntry workout) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", workout.getDate());//do something for datetime conversion
        values.put("exercise", workout.getExercise());
        values.put("weight", workout.getWeight());
        values.put("reps", workout.getReps());
        values.put("sets", workout.getSets());

        db.insert(WORKOUT_TABLE_NAME, null, values);
        db.close();
    }
}
