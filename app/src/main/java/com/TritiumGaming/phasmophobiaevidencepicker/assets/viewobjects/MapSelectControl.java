package com.TritiumGaming.phasmophobiaevidencepicker.assets.viewobjects;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;

/**
 * MapSelectControl class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class MapSelectControl {

    private EvidenceViewModel evidenceViewModel = null;

    private CCountDownTimer timer = null;
    private TimerPlayControl stateControl = null;

    private AppCompatTextView difficultyDisplay = null;

    private String defaultDifficultyName = null;
    private String[] difficultyNames = null;
    private long[] difficultyTimes = null;

    /**
     * MapSelectControl constructor
     *
     * TODO
     *
     * @param timer
     * @param evidence_prev
     * @param evidence_next
     * @param evidence_timer_difficulty_title
     * @param defaultDifficultyName
     * @param difficultyNames
     * @param difficultyTimes
     */
    public MapSelectControl(CCountDownTimer timer,
                            AppCompatImageButton evidence_prev, AppCompatImageButton evidence_next, AppCompatTextView evidence_timer_difficulty_title,
                            String defaultDifficultyName, String[] difficultyNames, String[] difficultyTimes) {
        setTimer(timer);

        setPrev(evidence_prev);
        setNext(evidence_next);

        setDifficultyDisplay(evidence_timer_difficulty_title);
        setDifficultyNames(defaultDifficultyName, difficultyNames);
        setDifficultyTimes(difficultyTimes);
    }

    /**
     * init
     *
     * TODO
     *
     * @param evidenceViewModel
     */
    public void init(EvidenceViewModel evidenceViewModel){
        this.evidenceViewModel = evidenceViewModel;
    }

    /**
     * setPrev
     *
     * TODO
     *
     * @param prev
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
     * setNext
     *
     * TODO
     *
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
     * setTimerControl
     *
     * TODO
     *
     * @param stateControl
     */
    public void setTimerControl(TimerPlayControl stateControl) {
        this.stateControl = stateControl;
    }

    /**
     * createTimer
     *
     * TODO
     */
    private void createTimer() {
        timer.createTimer(difficultyTimes[evidenceViewModel.getDifficulty()], 1000L);
        difficultyDisplay.setText(difficultyNames[evidenceViewModel.getDifficulty()]);
        stateControl.setPaused();
    }

    /**
     * setDifficultyDisplay
     *
     * TODO
     *
     * @param difficultyDisplay
     */
    private void setDifficultyDisplay(AppCompatTextView difficultyDisplay){
        this.difficultyDisplay = difficultyDisplay;
    }

    /**
     * setDifficultyNames
     *
     * TODO
     *
     * @param defaultDifficultyName
     * @param difficultyNames
     */
    private void setDifficultyNames(String defaultDifficultyName, String[] difficultyNames) {
        this.defaultDifficultyName = defaultDifficultyName;
        this.difficultyNames = difficultyNames;
    }

    /**
     * setDifficultyTimes
     *
     * TODO
     *
     * @param dt
     */
    private void setDifficultyTimes(String[] dt) {
        long[] temp = new long[dt.length];
        for(int i = 0; i < dt.length; i++)
            temp[i] = Long.parseLong(dt[i]);

        this.difficultyTimes = temp;
    }

    /**
     * setTimer
     *
     * TODO
     *
     * @param timer
     */
    public void setTimer(CCountDownTimer timer) {
        this.timer = timer;
    }

    /**
     * getState
     *
     * TODO
     *
     * @return
     */
    public int getState() {
        return evidenceViewModel.getDifficulty();
    }

    /**
     * setState
     *
     * TODO
     *
     * @param state
     */
    public void setState(int state) {
        if(evidenceViewModel != null)
            evidenceViewModel.setDifficulty(state);

        if(state == -1)
            difficultyDisplay.setText(defaultDifficultyName);
        else
            difficultyDisplay.setText(difficultyNames[state]);
    }

    /**
     * reset
     *
     * TODO
     */
    public void reset(){
        setState(0);
    }

}
