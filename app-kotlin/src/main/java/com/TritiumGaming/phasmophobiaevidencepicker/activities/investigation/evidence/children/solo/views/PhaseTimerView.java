package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.children.solo.views;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.SanityData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.children.solo.data.PhaseTimerData;
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

    @Nullable
    private CountDownTimer timer;
    private PhaseTimerControlView stateControl;
    private AppCompatTextView timerTextView;

    /**
     * SetupPhaseTimer parameterized constructor
     *
     * @param recipientView -
     */
    public PhaseTimerView(SanityData sanityData,
                          @NonNull PhaseTimerData phaseTimerData,
                          AppCompatTextView recipientView) {

        this.sanityData = sanityData;
        this.phaseTimerData = phaseTimerData;

        setTimerTextView(recipientView);

        createTimer(
                true,
                phaseTimerData.getTimeRemaining(),
                1000L);

        if(!phaseTimerData.isPaused()) {
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
     * setTimerTextView method
     *
     * @param timerTextView -
     */
    public void setTimerTextView(AppCompatTextView timerTextView) {
        this.timerTextView = timerTextView;
    }

    /*
    public void recreateTimer(long millisInFuture, long countDownInterval) {
        phaseTimerData.setTimeRemaining(millisInFuture);

        createTimer(millisInFuture, countDownInterval);
    }
    */

    /**
     * createTimer method
     *
     * @param millisInFuture -
     * @param countDownInterval -
     */
    public void createTimer(boolean isFresh, long millisInFuture, long countDownInterval) {

        destroyTimer();

        if(isFresh) {
            if(sanityData.getStartTime() != -1 && !sanityData.isPaused()) {
                Log.d("SettingTimeRemaining",
                        phaseTimerData.getDifficultyCarouselData().getCurrentDifficultyTime() +
                                " " + (sanityData.getStartTime() - System.currentTimeMillis()));
                phaseTimerData.setTimeRemaining(
                        phaseTimerData.getDifficultyCarouselData().getCurrentDifficultyTime() +
                                (sanityData.getStartTime() - System.currentTimeMillis())
                );
            }
        } else {
            phaseTimerData.setTimeRemaining(millisInFuture);
        }

        timer = new CountDownTimer(millisInFuture, countDownInterval) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (!phaseTimerData.isPaused() && millisUntilFinished > -1L) {
                    phaseTimerData.setTimeRemaining(millisUntilFinished);
                    updateText();
                }
            }

            @Override
            public void onFinish() {
                if (stateControl != null) {
                    stateControl.checkPaused();
                }
                phaseTimerData.setTimeRemaining(0L);
                updateText();
            }
        };

        updateText();

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
            createTimer(false, phaseTimerData.getTimeRemaining(), 1000L);
            if (timer != null) {
                Log.d("Timer", "Playing!");
                timer.start();
            }
        }

    }

    /**
     * setText method
     */
    public void updateText() {
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
        Log.d("Timer",
                String.valueOf(phaseTimerData.getTimeRemaining() -
                        (sanityData.getStartTime() - System.currentTimeMillis())));
    }

    public void reset() {
        createTimer(
                true,
                phaseTimerData.getTimeRemaining(),
                1000L);

        updateText();
    }
}
