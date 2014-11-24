package com.cs371m.strengthpal;

/**
 * Created by Cho on 11/23/14.
 */

/*
    this class will contain data that will be put into the database
 */
public class WorkoutDBEntry {
    private long id;
    private String date;
    private String exercise;
    private double weight;
    private int reps;
    private int sets;

    public WorkoutDBEntry(){}
    public WorkoutDBEntry(long id, String date, String exercise, double weight, int reps, int sets) {
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

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
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
}
