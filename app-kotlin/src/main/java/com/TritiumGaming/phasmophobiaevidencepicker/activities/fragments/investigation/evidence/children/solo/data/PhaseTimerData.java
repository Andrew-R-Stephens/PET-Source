package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data;

public class PhaseTimerData {

    private final DifficultyCarouselData difficultyCarouselData;

    private boolean isPaused = true;
    private long timeRemaining = -1L;

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

    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public boolean isSetupPhase() {
        return timeRemaining > 0L;
    }

    public void reset() {
        isPaused = true;
        timeRemaining = difficultyCarouselData.getCurrentDifficultyTime();
    }
}
