package com.TritiumGaming.phasmophobiaevidencepicker.data.persistent;

/**
 * ReviewTrackingData class
 *
 * TODO
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
     * ReviewTrackingData constructor
     *
     * TODO
     *
     * @param wasRequested
     * @param timeActive
     * @param timesOpened
     */
    public ReviewTrackingData(boolean wasRequested, long timeActive, int timesOpened){
        setWasRequested(wasRequested);
        setTimeActive(timeActive);
        setTimesOpened(timesOpened);
    }

    /**
     * setTimeActive
     *
     * TODO
     *
     * @param timeActive
     */
    public void setTimeActive(long timeActive){
        this.timeActive = timeActive;
    }

    /**
     * addTimeActive
     *
     * TODO
     *
     * @param timeActive
     */
    public void addTimeActive(long timeActive){
        this.timeActive += timeActive;
    }

    /**
     * getTimeActive
     *
     * TODO
     *
     * @return
     */
    public long getTimeActive(){
        return timeActive;
    }

    /**
     * setTimesOpened
     *
     * TODO
     *
     * @param timesOpened
     */
    public void setTimesOpened(int timesOpened){
        this.timesOpened = timesOpened;
    }

    /**
     * addTimesOpened
     *
     * TODO
     *
     * @param timesOpened
     */
    public void addTimesOpened(int timesOpened){
        this.timesOpened += timesOpened;
    }

    /**
     * getTimesOpened
     *
     * TODO
     *
     * @return
     */
    public int getTimesOpened(){
        return timesOpened;
    }

    /**
     * setWasRequested
     *
     * TODO
     *
     * @param wasRequested
     */
    public void setWasRequested(boolean wasRequested){
        this.wasRequested = wasRequested;
    }

    /**
     * getWasRequested
     *
     * TODO
     *
     * @return
     */
    public boolean getWasRequested(){
        return wasRequested;
    }

    /**
     * canRequestReview
     *
     * TODO
     *
     * @return
     */
    public boolean canRequestReview() {
        return (!wasRequested) && /*(timeActive >= TARGET_TIME_ACTIVE ||*/ (timesOpened >= TARGET_TIMES_OPENED);
    }

}
