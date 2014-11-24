package com.cs371m.strengthpal.model;

/**
 * Created by tcorley on 11/24/14.
 */
public class HistoryItem {
    private String stuff;

    public HistoryItem(){};

    public HistoryItem(String stuff) {
        this.stuff = stuff;
    }

    public String getStuff() { return this.stuff; }

    public void setStuff(String stuff) { this.stuff = stuff; }
}
