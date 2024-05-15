package com.TritiumGaming.phasmophobiaevidencepicker.data.controllers;

import androidx.annotation.NonNull;

/**
 * ReviewTrackingData class
 *
 * @author TritiumGamingStudios
 */
public class ReviewTrackingData {

    // Checks if the Review was already requested
    protected boolean wasRequested = false;
    // Current time app was alive / target time to trigger review request
    protected long timeActive, TARGET_TIME_ACTIVE = 1200000;
    // Count of times app was opened / target count to trigger review request
    protected int timesOpened;
    protected final int TARGET_TIMES_OPENED = 5;

    public ReviewTrackingData(boolean wasRequested, long timeActive, int timesOpened) {
        setWasRequested(wasRequested);
        setTimeActive(timeActive);
        setTimesOpened(timesOpened);
    }

    public void setTimeActive(long timeActive) {
        this.timeActive = timeActive;
    }

    public long getTimeActive() {
        return timeActive;
    }

    public void setTimesOpened(int timesOpened) {
        this.timesOpened = timesOpened;
    }

    public void incrementTimesOpened() {
        this.timesOpened += 1;
    }

    public int getTimesOpened() {
        return timesOpened;
    }

    public void setWasRequested(boolean wasRequested) {
        this.wasRequested = wasRequested;
    }

    public boolean getWasRequested() {
        return wasRequested;
    }

    public boolean canRequestReview() {
        return (!wasRequested) && /*(timeActive >= TARGET_TIME_ACTIVE
        ||*/ (timesOpened >= TARGET_TIMES_OPENED);
    }

    @NonNull
    public String toString() {
        return "Time Active: " + getTimeActive() + "; Times Opened: " + getTimesOpened() +
                "; Can Request Review: " + canRequestReview();
    }

}
