package com.cs371m.strengthpal.model;

/**
 * Created by tcorley on 11/24/14.
 */
public class HistoryItem {
    private String date;
    private int count;
    private long id;

    public HistoryItem(){};

    public HistoryItem(String date, int count, long id) {
        this.date = date;
        this.count = count;
        this.id = id;
    }

    public String getDate() { return this.date; }

    public void setDate(String date) { this.date = date; }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Date: " + date + " Count: " + count;
    }
}
