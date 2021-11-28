package com.TritiumGaming.phasmophobiaevidencepicker.data.persistent;

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

    /**
     * ReviewTrackingData parameterized constructor
     *
     * @param wasRequested
     * @param timeActive
     * @param timesOpened
     */
    public ReviewTrackingData(boolean wasRequested, long timeActive, int timesOpened) {
        setWasRequested(wasRequested);
        setTimeActive(timeActive);
        setTimesOpened(timesOpened);
    }

    /**
     * @param timeActive
     */
    public void setTimeActive(long timeActive) {
        this.timeActive = timeActive;
    }

    /**
     * @return
     */
    public long getTimeActive() {
        return timeActive;
    }

    /**
     * @param timesOpened
     */
    public void setTimesOpened(int timesOpened) {
        this.timesOpened = timesOpened;
    }

    /**
     *
     */
    public void incrementTimesOpened() {
        this.timesOpened += 1;
    }

    /**
     * @return
     */
    public int getTimesOpened() {
        return timesOpened;
    }

    /**
     * @param wasRequested
     */
    public void setWasRequested(boolean wasRequested) {
        this.wasRequested = wasRequested;
    }

    /**
     * @return
     */
    public boolean getWasRequested() {
        return wasRequested;
    }

    /**
     * @return
     */
    public boolean canRequestReview() {
        return (!wasRequested) && /*(timeActive >= TARGET_TIME_ACTIVE
        ||*/ (timesOpened >= TARGET_TIMES_OPENED);
    }

}
