package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.DifficultyCarouselData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.PhaseTimerData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.SanityData;

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
    private AppCompatTextView recipientView;

    /**
     * SetupPhaseTimer parameterized constructor
     *
     * @param recipientView -
     */
    public PhaseTimerView(SanityData sanityData,
                          PhaseTimerData phaseTimerData,
                          DifficultyCarouselData difficultyCarouselData,
                          AppCompatTextView recipientView) {

        Log.d("Timer", "Creating Phase Timer");

        this.sanityData = sanityData;
        this.phaseTimerData = phaseTimerData;

        setRecipientView(recipientView);

        if(phaseTimerData.getTimeRemaining() == -1) {
            createTimer(
                    difficultyCarouselData.getCurrentDifficultyTime(),
                    1000L);
        }

        setText();
    }

    /**
     * createTimer method
     *
     * @param millisInFuture -
     * @param countDownInterval -
     */
    public void createTimer(long millisInFuture, long countDownInterval) {

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        phaseTimerData.setTimeRemaining(millisInFuture);
        setText();

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
                if (stateControl != null)
                    stateControl.checkPaused();
                phaseTimerData.setTimeRemaining(0L);
                setText();
            }
        };
    }

    /**
     * pause method
     */
    public void pause() {
        if (!phaseTimerData.isPaused()) {
            phaseTimerData.setPaused(true);
            if (timer != null)
                timer.cancel();
            timer = null;
        }
    }

    /**
     * unPause method
     */
    public void unPause() {

        if (phaseTimerData.isPaused()) {
            if (sanityData != null)
                sanityData.setProgressManually();
            phaseTimerData.setPaused(false);
            createTimer(phaseTimerData.getTimeRemaining(), 1000L);
            if (timer != null)
                timer.start();
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
     * setRecipientView method
     *
     * @param recipientView -
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

        if (phaseTimerData.getTimeRemaining() > 0L) {
            long breakdown = phaseTimerData.getTimeRemaining() / 1000L;
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
