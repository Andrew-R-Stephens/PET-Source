package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.DifficultyCarouselData;

/**
 * DifficultySelectControl class
 *
 * @author TritiumGamingStudios
 */
public class DifficultyCarouselView {

    private final DifficultyCarouselData difficultyCarouselData;

    private final PhaseTimerView timerView;
    private final PhaseTimerControlView timerControlView;
    private final AppCompatTextView difficultyNameView;
    private final SanitySeekBarView sanityProgressBar;
    private final WarnTextView warnTextView;


    /**
     * DifficultySelectControl parameterized constructor
     */
    public DifficultyCarouselView(
            DifficultyCarouselData difficultyCarouselData,
            PhaseTimerView timerView,
            PhaseTimerControlView timerControlView,
            AppCompatImageButton prevButton,
            AppCompatImageButton nextButton,
            AppCompatTextView difficultyNameView,
            WarnTextView warnTextView,
            SanitySeekBarView sanityProgressBar) {

        this.difficultyCarouselData = difficultyCarouselData;

        this.timerControlView = timerControlView;
        this.difficultyNameView = difficultyNameView;
        this.timerView = timerView;
        this.sanityProgressBar = sanityProgressBar;
        this.warnTextView = warnTextView;

        setPrev(prevButton);
        setNext(nextButton);

    }

    /**
     * setPrev method
     *
     * @param prev -
     */
    private void setPrev(AppCompatImageButton prev) {
        prev.setOnClickListener(v -> {

            if (difficultyCarouselData.decrementDifficulty()) {
                difficultyCarouselData.resetSanityData();
                sanityProgressBar.updateProgress();
                difficultyNameView.setText(difficultyCarouselData.getCurrentDifficultyName());
                timerControlView.pause();
                createTimerView();
                warnTextView.invalidate();
            }

        });
    }

    /**
     * setNext method
     *
     * @param next
     */
    private void setNext(AppCompatImageButton next) {
        next.setOnClickListener(v -> {

            if (difficultyCarouselData.incrementDifficulty()) {
                difficultyCarouselData.resetSanityData();
                sanityProgressBar.updateProgress();
                difficultyNameView.setText(difficultyCarouselData.getCurrentDifficultyName());
                timerControlView.pause();
                createTimerView();
                warnTextView.invalidate();
            }

        });
    }

    /**
     * createTimer method
     */
    private void createTimerView() {
        timerView.createTimer(false, difficultyCarouselData.getCurrentDifficultyTime(), 1000L);
    }

    /**
     * getState method
     *
     * @return index of difficulty array
     */
    public int getIndex() {
        return difficultyCarouselData.getDifficultyIndex();
    }

    /**
     * setState method
     *
     * @param state -
     */
    public void setIndex(int state) {
        difficultyCarouselData.setDifficultyIndex(state);
        difficultyNameView.setText(difficultyCarouselData.getCurrentDifficultyName());
    }

    /**
     * reset method
     */
    public void reset() {
        setIndex(0);
        createTimerView();
    }

}
