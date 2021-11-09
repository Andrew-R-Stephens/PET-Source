package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;

/**
 * DifficultySelectControl class
 *
 * @author TritiumGamingStudios
 */
public class DifficultyCarouselView {

    private EvidenceViewModel evidenceViewModel = null;

    private PhaseTimerView timer = null;
    private PhaseTimerControlView timerPlayControl = null;

    private AppCompatTextView difficultyNameView = null;

    private String defaultDifficultyName = null;
    private String[] difficultyNames = null;
    private long[] difficultyTimes = null;

    /**
     * DifficultySelectControl parameterized constructor
     * @param timer -
     * @param evidence_prev -
     * @param evidence_next -
     * @param difficultyNameView -
     * @param defaultDifficultyName -
     * @param difficultyNames -
     * @param difficultyTimes -
     */
    public DifficultyCarouselView(PhaseTimerView timer,
                                  AppCompatImageButton evidence_prev, AppCompatImageButton evidence_next, AppCompatTextView difficultyNameView,
                                  String defaultDifficultyName, String[] difficultyNames, String[] difficultyTimes) {
        setTimer(timer);

        setPrev(evidence_prev);
        setNext(evidence_next);

        setDifficultyName(difficultyNameView);
        setDifficultyNames(defaultDifficultyName, difficultyNames);
        setDifficultyTimes(difficultyTimes);
    }

    /**
     * init method
     * @param evidenceViewModel -
     */
    public void init(EvidenceViewModel evidenceViewModel){
        this.evidenceViewModel = evidenceViewModel;
    }

    /**
     * setPrev method
     * @param prev -
     */
    private void setPrev(AppCompatImageButton prev){
        prev.setOnClickListener(v -> {

            if(evidenceViewModel != null) {
                int state = evidenceViewModel.getDifficulty() - 1;
                if (state < 0)
                    state = difficultyNames.length - 1;
                evidenceViewModel.setDifficulty(state);

                createTimer();

                if(evidenceViewModel.hasSanityData())
                    evidenceViewModel.getSanityData().setCanWarn(true);
            }
        });
    }

    /**
     * setNext method
     * @param next
     */
    private void setNext(AppCompatImageButton next){
        next.setOnClickListener(v -> {

            if(evidenceViewModel != null) {
                int state = evidenceViewModel.getDifficulty() + 1;
                if (state >= difficultyNames.length)
                    state = 0;
                evidenceViewModel.setDifficulty(state);

                createTimer();

                if(evidenceViewModel.hasSanityData())
                    evidenceViewModel.getSanityData().setCanWarn(true);
            }

        });
    }

    /**
     * setTimerControl method
     * @param stateControl
     */
    public void setTimerControl(PhaseTimerControlView stateControl) {
        this.timerPlayControl = stateControl;
    }

    /**
     * createTimer method
     */
    private void createTimer() {
        timer.createTimer(difficultyTimes[evidenceViewModel.getDifficulty()], 1000L);
        difficultyNameView.setText(difficultyNames[evidenceViewModel.getDifficulty()]);
        timerPlayControl.setPaused();
    }

    /**
     * setDifficultyName method
     * @param difficultyNameView
     */
    private void setDifficultyName(AppCompatTextView difficultyNameView){
        this.difficultyNameView = difficultyNameView;
    }

    /**
     * setDifficultyNames method
     * @param defaultDifficultyName
     * @param difficultyNames
     */
    private void setDifficultyNames(String defaultDifficultyName, String[] difficultyNames) {
        this.defaultDifficultyName = defaultDifficultyName;
        this.difficultyNames = difficultyNames;
    }

    /**
     * setDifficultyTimes
     * @param dt
     */
    private void setDifficultyTimes(String[] dt) {
        long[] temp = new long[dt.length];
        for(int i = 0; i < dt.length; i++)
            temp[i] = Long.parseLong(dt[i]);

        this.difficultyTimes = temp;
    }

    /**
     * setTimer method
     * @param timer
     */
    public void setTimer(PhaseTimerView timer) {
        this.timer = timer;
    }

    /**
     * getState method
     * @return
     */
    public int getState() {
        return evidenceViewModel.getDifficulty();
    }

    /**
     * setState method
     * @param state
     */
    public void setState(int state) {
        if(evidenceViewModel != null)
            evidenceViewModel.setDifficulty(state);

        if(state == -1)
            difficultyNameView.setText(defaultDifficultyName);
        else
            difficultyNameView.setText(difficultyNames[state]);
    }

    /**
     * reset method
     */
    public void reset(){
        setState(0);
    }

}
