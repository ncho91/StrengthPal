package com.cs371m.strengthpal;

import java.util.Date;

/**
 * Created by Cho on 11/23/14.
 */

/*
    this class will contain data that will be put into the database
 */
public class WorkoutDBEntry {
    private long id;
    private Date date;
    private String exercise;
    private int weight;
    private int reps;
    private int sets;

    public WorkoutDBEntry(){}
    public WorkoutDBEntry(long id, Date date, String exercise, int weight, int reps, int sets) {
        this.id = id;
        this.date = date;
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.sets = sets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getExercise() {
        return exercise;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets){
        this.sets = sets;
    }

    public String toString() {
        return "{" + "id: " + id + "\nDate: " + date.toString() + "\nExercise: " + exercise + "\nWeight: " + weight + "\nReps: " + reps + "\nSets: " + sets + "}";
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
