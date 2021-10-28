package com.TritiumGaming.phasmophobiaevidencepicker.assets.viewobjects;

import android.os.CountDownTimer;

import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;

import java.text.DecimalFormat;

/**
 * SetupPhaseTimer class
 *
 * @author TritiumGamingStudios
 */
public class SetupPhaseTimer {

    private EvidenceViewModel evidenceViewModel;

    private CountDownTimer timer = null;
    private TimerPlayControl stateControl = null;

    private AppCompatTextView recipientView = null;

    private boolean isPaused = true;
    private long timeRemaining = 0L;

    /**
     * SetupPhaseTimer parameterized constructor
     * @param recipientView
     */
    public SetupPhaseTimer(AppCompatTextView recipientView) {
        setRecipientView(recipientView);
        setText();
        setPaused(true);
    }

    /**
     * init method
     * @param evidenceViewModel
     */
    public void init(EvidenceViewModel evidenceViewModel) {
        this.evidenceViewModel = evidenceViewModel;
    }

    /**
     * createTimer method
     * @param millisInFuture
     * @param countDownInterval
     */
    public void createTimer(long millisInFuture, long countDownInterval) {

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        setTimeRemaining(millisInFuture);
        setText();

        timer = new CountDownTimer(millisInFuture, countDownInterval) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (!isPaused() && millisUntilFinished > 0L) {
                    setTimeRemaining(millisUntilFinished);
                    setText();
                }
            }

            @Override
            public void onFinish() {
                if (stateControl != null)
                    stateControl.checkPaused();
                setTimeRemaining(0L);
                setText();
            }
        };
    }

    /**
     * pause method
     */
    public void pause() {
        if (!isPaused()) {
            setPaused(true);
            if (timer != null)
                timer.cancel();
            timer = null;
        }
    }

    /**
     * unPause method
     */
    public void unPause() {
        if (isPaused()) {
            if (evidenceViewModel != null && evidenceViewModel.hasSanityData())
                evidenceViewModel.getSanityData().setProgressManually();
            setPaused(false);
            createTimer(timeRemaining, 1000L);
            if (timer != null)
                timer.start();
        }
    }

    /**
     * setTimerControls method
     * @param stateControl
     */
    public void setTimerControls(TimerPlayControl stateControl) {
        this.stateControl = stateControl;
    }

    /**
     * setPaused method
     * @param isPaused
     */
    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    /**
     * isPaused method
     * @return
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * setTimeRemaining method
     * @param timeRemaining
     */
    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    /**
     * getTimeRemaining method
     * @return
     */
    public long getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * setRecipientView method
     * @param recipientView
     */
    public void setRecipientView(AppCompatTextView recipientView) {
        this.recipientView = recipientView;
    }

    /**
     * setText method
     */
    public void setText() {
        String text = String.format(
                "%s:%s",
                new DecimalFormat("0").format(0),
                new DecimalFormat("00").format(0));

        if (timeRemaining > 0L) {
            long breakdown = timeRemaining / 1000L;
            long minutes = breakdown / 60L;
            long seconds = breakdown % 60L;
            text = String.format(
                    "%s:%s",
                    new DecimalFormat("0").format(minutes),
                    new DecimalFormat("00").format(seconds));
        }

        if (recipientView != null)
            recipientView.setText(text);
    }

}
