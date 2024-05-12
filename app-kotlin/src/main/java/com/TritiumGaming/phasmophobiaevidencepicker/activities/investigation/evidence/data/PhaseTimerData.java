package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data;

public class PhaseTimerData {

    private final DifficultyCarouselData difficultyCarouselData;

    private boolean isPaused = true;

    private final long TIME_DEFAULT = -1L, TIME_MIN = 0L;
    private long timeRemaining = TIME_DEFAULT;

    public PhaseTimerData(DifficultyCarouselData difficultyCarouselData) {
        this.difficultyCarouselData = difficultyCarouselData;

        reset();
    }

    public DifficultyCarouselData getDifficultyCarouselData() {
        return difficultyCarouselData;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public boolean hasTimeRemaining() {
        return timeRemaining < TIME_MIN;
    }

    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public boolean isSetupPhase() {
        return timeRemaining > TIME_MIN;
    }

    public void reset() {
        isPaused = true;
        timeRemaining = difficultyCarouselData.getCurrentDifficultyTime();
    }

}
