package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.PhaseTimerData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.SanityData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FormatterUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;

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
    @Nullable
    private PhaseTimerControlView stateControl;
    @Nullable
    private AppCompatTextView timerTextView;

    public PhaseTimerView(EvidenceViewModel evidenceViewModel,
                          AppCompatTextView recipientView) {

        this.sanityData = evidenceViewModel.getSanityData();
        this.phaseTimerData = evidenceViewModel.getPhaseTimerData();

        setTimerTextView(recipientView);

        createTimer(true,
                phaseTimerData.timeRemaining,
                1000L);

        if(!phaseTimerData.isPaused) {
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
    public void setTimerTextView(@Nullable AppCompatTextView timerTextView) {
        this.timerTextView = timerTextView;
    }

    /**
     * createTimer method
     *
     * @param millisInFuture -
     * @param countDownInterval -
     */
    public void createTimer(boolean isFresh, long millisInFuture, long countDownInterval) {

        destroyTimer();

        if(isFresh) {
            if(!sanityData.isNewCycle() && !sanityData.isPaused) {
                Log.d("SettingTimeRemaining",
                        phaseTimerData.difficultyCarouselData.getCurrentDifficultyTime() +
                                " " + (sanityData.startTime - System.currentTimeMillis()));
                phaseTimerData.timeRemaining = phaseTimerData.difficultyCarouselData.getCurrentDifficultyTime() +
                        (sanityData.startTime - System.currentTimeMillis());
            } else {
                Log.d("SettingTimeRemaining", "Not new Cycle, Not Paused " +
                        phaseTimerData.difficultyCarouselData.getCurrentDifficultyTime() +
                        " " + (sanityData.startTime - System.currentTimeMillis()));
            }
        } else {
            Log.d("SettingTimeRemaining", "Not Fresh " +
                    phaseTimerData.difficultyCarouselData.getCurrentDifficultyTime() +
                            " " + (sanityData.startTime - System.currentTimeMillis()));
            phaseTimerData.timeRemaining = millisInFuture;
        }

        timer = new CountDownTimer(millisInFuture, countDownInterval) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (!phaseTimerData.isPaused && millisUntilFinished > -1L) {
                    phaseTimerData.timeRemaining = millisUntilFinished;
                    updateText();
                }
            }

            @Override
            public void onFinish() {
                if (stateControl != null) {
                    stateControl.checkPaused();
                }
                phaseTimerData.timeRemaining = 0L;
                updateText();
            }
        };

        updateText();

    }

    /**
     * pause method
     */
    public void pause() {
        if (!phaseTimerData.isPaused) {
            phaseTimerData.isPaused = true;
            destroyTimer();
        }
    }

    /**
     * unPause method
     */
    public void play() {

        if (phaseTimerData.isPaused) {
            if (sanityData != null) {
                sanityData.setProgressManually();
            }
            phaseTimerData.isPaused = false;
            createTimer(false, phaseTimerData.timeRemaining, 1000L);
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

        if (phaseTimerData.timeRemaining > 0L) {
            long breakdown = phaseTimerData.timeRemaining / 1000L;
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
                String.valueOf(phaseTimerData.timeRemaining -
                        (sanityData.startTime - System.currentTimeMillis())));
    }

    public void reset() {
        createTimer(true,
                phaseTimerData.timeRemaining,
                1000L);

        updateText();
    }
}
