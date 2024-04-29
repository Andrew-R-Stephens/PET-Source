package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.children.solo.views;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.children.solo.data.DifficultyCarouselData;
import com.TritiumGaming.phasmophobiaevidencepicker.listeners.CompositeListener;
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.SanityWarningView;

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
    private SanitySeekBarView sanityProgressBar;
    private SanityWarningView warnTextView_warn, warnTextView_setup, warnTextView_action;

    private CompositeListener compositeListenerPrev, compositeListenerNext;


    /**
     * DifficultySelectControl parameterized constructor
     */
    public void init(
            DifficultyCarouselData difficultyCarouselData,
            PhaseTimerView timerView,
            PhaseTimerControlView timerControlView,
            @NonNull AppCompatImageButton prevButton,
            @NonNull AppCompatImageButton nextButton,
            AppCompatTextView difficultyNameView,
            SanityWarningView warnTextView_warn,
            SanityWarningView warnTextView_setup,
            SanityWarningView warnTextView_action,
            SanitySeekBarView sanityProgressBar) {

        this.difficultyCarouselData = difficultyCarouselData;

        this.timerControlView = timerControlView;
        this.difficultyNameView = difficultyNameView;
        this.timerView = timerView;
        this.sanityProgressBar = sanityProgressBar;
        this.warnTextView_warn = warnTextView_warn;
        this.warnTextView_setup = warnTextView_setup;
        this.warnTextView_action = warnTextView_action;

        setPrev(prevButton);
        setNext(nextButton);

    }

    /**
     * setPrev method
     *
     * @param prev -
     */
    private void setPrev(@NonNull AppCompatImageButton prev) {
        prev.setOnClickListener(v -> {

            if (difficultyCarouselData.decrementDifficulty()) {
                updateRelatedComponents();
                compositeListenerPrev.onClick(v);
            }
        });
    }

    /**
     * setNext method
     *
     * @param next
     */
    private void setNext(@NonNull AppCompatImageButton next) {
        next.setOnClickListener(v -> {

            if (difficultyCarouselData.incrementDifficulty()) {
                updateRelatedComponents();
                compositeListenerNext.onClick(v);
            }
        });
    }

    private void updateRelatedComponents() {
        difficultyCarouselData.resetSanityData();
        if(difficultyCarouselData.getDifficultyIndex() == 4) {
            difficultyCarouselData.getEvidenceViewModel().getSanityData().setInsanityActual(25f);
        }
        sanityProgressBar.updateProgress();
        difficultyNameView.setText(difficultyCarouselData.getCurrentDifficultyName());
        timerControlView.pause();
        createTimerView();

        warnTextView_warn.reset();
        warnTextView_setup.reset();
        warnTextView_action.reset();
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

    public void registerListener(
            CompositeListener compositeListenerPrev,
            CompositeListener compositeListenerNext
    ) {

        this.compositeListenerPrev = compositeListenerPrev;
        this.compositeListenerNext = compositeListenerNext;
    }

    /**
     * reset method
     */
    public void reset() {
        setIndex(0);
        createTimerView();
    }

}
