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

    private DifficultyCarouselData difficultyCarouselData;

    private PhaseTimerView timerView;
    private PhaseTimerControlView timerControlView;
    private AppCompatTextView difficultyNameView;


    /**
     * DifficultySelectControl parameterized constructor
     */
    public DifficultyCarouselView(
            DifficultyCarouselData difficultyCarouselData,
            PhaseTimerView timerView,
            AppCompatImageButton prevButton,
            AppCompatImageButton nextButton,
            AppCompatTextView difficultyNameView) {

        setDifficultyCarouselData(difficultyCarouselData);

        setDifficultyNameView(difficultyNameView);
        setTimerView(timerView);
        setPrev(prevButton);
        setNext(nextButton);

    }

    /**
     * init method
     */
    public void init(DifficultyCarouselData difficultyCarouselData) {
        this.difficultyCarouselData = difficultyCarouselData;
    }

    public void setDifficultyCarouselData(DifficultyCarouselData difficultyCarouselData) {
        this.difficultyCarouselData = difficultyCarouselData;
    }

    /**
     * setPrev method
     *
     * @param prev -
     */
    private void setPrev(AppCompatImageButton prev) {
        prev.setOnClickListener(v -> {

            if (difficultyCarouselData.decrementDifficulty())
                createTimerView();

        });
    }

    /**
     * setNext method
     *
     * @param next
     */
    private void setNext(AppCompatImageButton next) {
        next.setOnClickListener(v -> {

            if (difficultyCarouselData.incrementDifficulty())
                createTimerView();

        });
    }

    /**
     * setTimerControl method
     *
     * @param stateControl
     */
    public void setTimerControl(PhaseTimerControlView stateControl) {
        this.timerControlView = stateControl;
    }

    /**
     * createTimer method
     */
    private void createTimerView() {
        timerView.createTimer(difficultyCarouselData.getCurrentDifficultyTime(), 1000L);
        difficultyNameView.setText(difficultyCarouselData.getCurrentDifficultyName());
        timerControlView.setPaused();
    }

    /**
     * setTimerView method
     *
     * @param timer
     */
    public void setTimerView(PhaseTimerView timer) {
        this.timerView = timer;
    }

    /**
     * setDifficultyName method
     *
     * @param difficultyNameView
     */
    private void setDifficultyNameView(AppCompatTextView difficultyNameView) {
        this.difficultyNameView = difficultyNameView;
    }

    /**
     * getState method
     *
     * @return
     */
    public int getState() {
        return difficultyCarouselData.getDifficultyIndex();
    }

    /**
     * setState method
     *
     * @param state
     */
    public void setState(int state) {
        difficultyCarouselData.setDifficulty(state);
        difficultyNameView.setText(difficultyCarouselData.getCurrentDifficultyName());
    }

    /**
     * reset method
     */
    public void reset() {
        setState(0);
    }

}
