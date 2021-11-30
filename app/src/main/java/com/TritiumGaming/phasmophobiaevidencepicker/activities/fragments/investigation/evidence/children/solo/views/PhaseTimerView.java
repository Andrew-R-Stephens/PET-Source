package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.PhaseTimerData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.SanityData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FormatterUtils;

import java.text.DecimalFormat;

/**
 * SetupPhaseTimer class
 *
 * @author TritiumGamingStudios
 */
public class PhaseTimerView {

    private final SanityData sanityData;
    private final PhaseTimerData phaseTimerData;

    private CountDownTimer timer;
    private PhaseTimerControlView stateControl;
    private AppCompatTextView timerTextView;

    /**
     * SetupPhaseTimer parameterized constructor
     *
     * @param recipientView -
     */
    public PhaseTimerView(SanityData sanityData,
                          PhaseTimerData phaseTimerData,
                          AppCompatTextView recipientView) {

        this.sanityData = sanityData;
        this.phaseTimerData = phaseTimerData;

        setTimerTextView(recipientView);

        createTimer(
                phaseTimerData.getTimeRemaining(),
                1000L);

        if(!phaseTimerData.isPaused()) {
            timer.start();
        }
    }

    /**
     * createTimer method
     *
     * @param millisInFuture -
     * @param countDownInterval -
     */
    public void createTimer(long millisInFuture, long countDownInterval) {

        destroyTimer();

        phaseTimerData.setTimeRemaining(millisInFuture);

        timer = new CountDownTimer(millisInFuture, countDownInterval) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (!phaseTimerData.isPaused() && millisUntilFinished > 0L) {
                    phaseTimerData.setTimeRemaining(millisUntilFinished);
                    setText();
                }
            }

            @Override
            public void onFinish() {
                if (stateControl != null) {
                    stateControl.checkPaused();
                }
                phaseTimerData.setTimeRemaining(0L);
                setText();
            }
        };

        setText();

    }

    /**
     * pause method
     */
    public void pause() {
        if (!phaseTimerData.isPaused()) {
            phaseTimerData.setPaused(true);
            destroyTimer();
        }
    }

    /**
     * unPause method
     */
    public void play() {

        if (phaseTimerData.isPaused()) {
            if (sanityData != null) {
                sanityData.setProgressManually();
            }
            phaseTimerData.setPaused(false);
            createTimer(phaseTimerData.getTimeRemaining(), 1000L);
            if (timer != null) {
                Log.d("Timer", "Playing!");
                timer.start();
            }
        }

    }

    /**
     * setTimerControls method
     *
     * @param stateControl -
     */
    public void setTimerControls(PhaseTimerControlView stateControl) {
        this.stateControl = stateControl;
    }

    /**
     * setTimerTextView method
     *
     * @param timerTextView -
     */
    public void setTimerTextView(AppCompatTextView timerTextView) {
        this.timerTextView = timerTextView;
    }

    /**
     * setText method
     */
    public void setText() {
        String text = String.format(
                "%s:%s",
                new DecimalFormat("0").format(0),
                new DecimalFormat("00").format(0));

        if (phaseTimerData.getTimeRemaining() > 0L) {
            long breakdown = phaseTimerData.getTimeRemaining() / 1000L;
            text = FormatterUtils.millisToTime("%s:%s", breakdown);
        }

        if (timerTextView != null) {
            timerTextView.setText(text);
        }
    }

    public void destroyTimer() {
        if(timer != null) {
            timer.cancel();
        }
        timer = null;
    }

}
