package com.cs371m.strengthpal;

public class GymListItem {

    public String name;
    public String address;
    public String distance;

    public GymListItem() {
        super();
    }

    public GymListItem(String name, String address, float distance) {
        super();
        this.name = name;
        this.address = address;
        double dist = distance;
        dist = dist * 0.000621371;
        dist = (double) Math.round(dist * 100) / 100;
        this.distance = Double.toString(dist) + " mi";
    }

}
