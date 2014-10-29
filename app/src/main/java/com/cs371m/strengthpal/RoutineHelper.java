package com.cs371m.strengthpal;

/**
 * Created by Cho on 10/28/14.
 */
public class RoutineHelper {

    private enum Routine {startingstrength, stronglifts, wendler, phat};
    private Routine routine;

    public RoutineHelper() {}

    public RoutineHelper(Routine routine) {
        this.routine = routine;
    }

    public Routine getRoutine() {
        return this.routine;
    }
}
