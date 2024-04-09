package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;

import java.text.NumberFormat;

/**
 * SanityData class
 *
 * @author TritiumGamingStudios
 */
public class SanityData {

    private final EvidenceViewModel evidenceViewModel;

    private long startTime = -1L;

    private final float MAX_SANITY = 100;
    private float insanityActual = 0L;

    private final double[] difficultyRate = {1, 1.5, 2};

    private final double[] dropRate_setup = {.09, .05, .03};
    private final double[] dropRate_normal = {.12, .08, .05};

    private boolean canWarn = true;
    private boolean isPaused = false;

    private long flashTimeoutMax = -1;
    private long flashTimeoutStart = -1;

    /**
     * SanityData
     *
     * @param evidenceViewModel - The EvidenceViewModel created at Activity creation
     */
    public SanityData(EvidenceViewModel evidenceViewModel) {
        this.evidenceViewModel = evidenceViewModel;
    }

    /**
     * getDifficultyRate method
     * <p>
     * Returns the difficulty rate multiplier.
     * Defaults if the selected index is out of range of available indexes.
     *
     * @return 1 - default. 0-2 Depending on Map Size.
     */
    public double getDifficultyRate() {
        if (evidenceViewModel == null || !evidenceViewModel.hasDifficultyCarouselData()) {
            return 1;
        }

        int diffIndex = evidenceViewModel.getDifficultyCarouselData().getDifficultyIndex();

        if (difficultyRate != null && (diffIndex >= 0 && diffIndex < difficultyRate.length)) {
            return difficultyRate[diffIndex];
        }

        return 1;
    }

    /**
     * getDropRate method
     * <p>
     * Returns the drop rate multiplier.
     * Based on current map size (Small, Medium, Large) and the stage of the investigation (Setup
     * vs Hunt)
     * Defaults if the selected index is out of range of available indexes.
     *
     * @return 1 - default.
     */
    public double getDropRate() {
        if (evidenceViewModel != null) {

            int currMapSize = evidenceViewModel.getMapCarouselData().getMapCurrentSize();

            if (evidenceViewModel.getPhaseTimerData().getTimeRemaining() <= 0L) {
                return getNormalDrainRate(currMapSize);
            }

            return getSanityDrainRate(currMapSize);
        }

        return 1;
    }

    public double getNormalDrainRate(int mapSize) {
        return dropRate_normal[mapSize];
    }

    public double getSanityDrainRate(int mapSize) { return dropRate_setup[mapSize]; }

    /**
     * setStartTime method
     *
     * @param startTime - The Sanity Drain starting time, whenever the play button is activated.
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void resetStartTime() {
        setStartTime(-1);
    }

    public void initStartTime() {
        setStartTime(System.currentTimeMillis());
    }

    /**
     * getStartTime method
     *
     * @return The Sanity drain start time.
     */
    public long getStartTime() {
        return startTime;
    }

    public boolean isNewCycle() {
        return startTime == -1;
    }

    public void setFlashTimeoutMax(int flashTimeoutMax) {
        this.flashTimeoutMax = flashTimeoutMax;
    }

    /**
     * setFlashTimeoutStart
     *
     * @param timeout - The moment when the Warning began to flash.
     */
    public void setFlashTimeoutStart(long timeout) {
        flashTimeoutStart = timeout;
    }

    public void resetFlashTimeoutStart() {
        setFlashTimeoutStart(-1);
    }

    /**
     * getSanityActual
     *
     * @return The Sanity level between 0 and 100. Levels outside those extremes are constrained.
     */
    public long getSanityActual() {
        long insanityActualTemp = (int) (insanityActual);

        return Math.max(Math.min(insanityActualTemp, 100L), 0L);
    }

    /**
     * setInsanityActual
     *
     * @param insanityActual - The decimal form of the sanity level.
     */
    public void setInsanityActual(float insanityActual) {
        final long SANITY_MAX = 100L;
        this.insanityActual = Math.min(insanityActual, SANITY_MAX);
    }

    /**
     * getInsanityActual
     * <p>
     * The level can be between 0 and 100. Levels outside those extremes are constrained.
     *
     * @return The sanity level that's missing. MAX_SANITY - insanityActual.
     */
    public long getInsanityActual() {
        long insanityActualTemp = (int) (MAX_SANITY - insanityActual);

        final long SANITY_MAX = 100L, SANITY_MIN = 0L;
        return Math.max(Math.min(insanityActualTemp, SANITY_MAX), SANITY_MIN);

    }

    /**
     * getInsanityPercent
     *
     * @return the sanity level missing, in percent form.
     */
    public float getInsanityPercent() {
        return (float) (getInsanityActual() * .01);
    }

    /**
     * getInsanityDegree
     *
     * @return the sanity level missing, in degrees.
     */
    public float getInsanityDegree() {
        return (getInsanityPercent() * 360f);
    }

    /**
     * setProgressManually
     * <p>
     * Sets the progress based on preexisting sanity levels
     * Resets the Warning Indicator to start flashing again, if necessary
     * Used upon fragment re-entry, continuing with preexisting data.
     */
    public void setProgressManually() {
        setProgressManually((long) insanityActual);
        resetFlashTimeoutStart();
    }

    /**
     * setProgressManually
     *
     * @param progressOverride Accepts a passed value to specify the progress, either done
     *                         locally or through views
     *                         Resets the Warning Indicator to start flashing again, if necessary
     *                         Sets the Start Time of the Sanity Drain, based on remaining time,
     *                         sanity, difficulty and map size.
     */
    public void setProgressManually(long progressOverride) {
        double newStartTime =
                (System.currentTimeMillis() +
                (MAX_SANITY - progressOverride / getDifficultyRate() / getDropRate() / .001));
        setStartTime((long) newStartTime);
        resetFlashTimeoutStart();
    }

    /**
     * setCanWarn
     *
     * @param canWarn - If the Audio Hunt Warning is allowed to execute
     */
    public void setCanWarn(boolean canWarn) {
        this.canWarn = canWarn;
    }

    /**
     * canWarn
     *
     * @return if the Audio Hunt Warning is allowed to execute
     */
    public boolean canWarn() {
        boolean temp = false;
        if (evidenceViewModel.hasSanityData()) {
            temp = evidenceViewModel.getSanityData().getInsanityPercent() < .7;
        }
        return canWarn && temp;
    }

    /**
     * canFlashWarning
     * <p>
     * Allow the Warning indicator to flash either off or on if:
     * The player's sanity is less than 70%
     * either if the Flash Timeout is infinite
     * or if there is no time remaining on the countdown timer.
     *
     * @return if the Warning indicator can flash
     */
    public boolean canFlashWarning() {
        boolean temp = false;
        if (evidenceViewModel.hasSanityData()) {
            temp = evidenceViewModel.getSanityData().getInsanityPercent() < .7;
        }

        if (temp && flashTimeoutMax == -1) {
            return true;
        }

        if (temp && flashTimeoutStart == -1) {
            setFlashTimeoutStart(System.currentTimeMillis());
        }

        return (System.currentTimeMillis() - flashTimeoutStart) < flashTimeoutMax;
    }

    /**
     * setIsPaused
     * <p>
     * Sets the paused state of the countdown timer.
     *
     * @param isPaused
     */
    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    /**
     * isPaused
     *
     * @return If the countdown timer is paused.
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * tick
     * <p>
     * Reduces player sanity level each tick. Sanity cannot drop below 50% if the clock still has
     * time remaining.
     */
    public void tick() {
        double multiplier = .001;
        final float PERCENT_HALF = .5f;
        final long SANITY_HALF = 50L;

        /*
        Multiplier which drops the rate to appropriate levels
        Algorithm which mimics in-game sanity drop, based on map size, difficulty level and
        investigation phase.
        */
        setInsanityActual(
                (float) ((((System.currentTimeMillis() - (getStartTime() == -1 ?
                System.currentTimeMillis() :
                getStartTime())) * multiplier * getDropRate()) * getDifficultyRate()))
        );

        if (evidenceViewModel != null) {
            /*
            If the Countdown timer still has time, and the player's sanity is less than or
            equal to halfway gone, set the remaining sanity to half.
            */
            if (getInsanityPercent() <= PERCENT_HALF &&
                    evidenceViewModel.getPhaseTimerData().hasTimeRemaining()) {
                setProgressManually(SANITY_HALF);
            }
        }
    }

    /**
     * toPercentString
     *
     * @return the percent format of sanity level
     */
    @NonNull
    public String toPercentString() {

        NumberFormat percentageFormat = NumberFormat.getPercentInstance();
        percentageFormat.setMinimumFractionDigits(0);

        return percentageFormat.format(getInsanityPercent()).replace("%", "");
    }

    /**
     * reset
     * <p>
     * Defaults all persistent data.
     */
    public void reset() {
        resetStartTime();
        resetFlashTimeoutStart();
        setCanWarn(true);
        tick();
        final float SANITY_QUARTER = 25f;
        final long SANITY_EMPTY = 0L;
        setInsanityActual(
                evidenceViewModel.getDifficultyCarouselData().getDifficultyIndex() == 4 ?
                        SANITY_QUARTER : SANITY_EMPTY);
    }

}
