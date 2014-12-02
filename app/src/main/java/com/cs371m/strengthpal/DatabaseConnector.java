package com.cs371m.strengthpal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;


/**
 * Created by Cho on 11/23/14.
 */
public class DatabaseConnector {

    private static final String TAG = "DatabaseConnector";
    private static final String DATABASE_NAME = "WorkoutHistory";
    private SQLiteDatabase database;
    private DatabaseOpenHelper dbOpenHelper;

    public DatabaseConnector(Context context) {
        dbOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
    }

    public void open() throws SQLException {
        database = dbOpenHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null)
            database.close();
    }

    public void insertWorkout(String date, String exercise, double weight, int reps, int sets) {
        ContentValues newWorkout = createWorkout(date, weight, reps, sets);
        newWorkout.put("exercise", exercise);

        //open();
        database.insert("workouts", null, newWorkout);
        close();

    }

    private ContentValues createWorkout(String date, double weight, int reps, int sets) {
        ContentValues workout = new ContentValues();
        workout.put("date", date);
        workout.put("weight", weight);
        workout.put("reps", reps);
        workout.put("sets", sets);
        return workout;
    }

    public void updateWorkout(long id, String date, String exercise, double weight, int reps, int sets) {
        ContentValues editWorkout = createWorkout(date, weight, reps, sets);

        //open();
        database.update("workouts", editWorkout, "_id=" + id, null);
        close();
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper {

        public DatabaseOpenHelper(Context context, String name,
                                  CursorFactory factory, int version) {
            super(context, name, factory, version);
            Log.d(TAG, "in constructor");
        }


        // creates the ratings table when the database is created
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, "in on create");
            // query to create a new table named ratings
            String createQuery = "CREATE TABLE workouts" +
                    "(_id INTEGER PRIMARY KEY autoincrement, " +
                    "exercise TEXT, " +
                    "date TEXT, " +
                    "weight DOUBLE, " +
                    "reps INT, " +
                    "sets INT); ";

            db.execSQL(createQuery);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        { /* nothing to do*/ }
    }
}
