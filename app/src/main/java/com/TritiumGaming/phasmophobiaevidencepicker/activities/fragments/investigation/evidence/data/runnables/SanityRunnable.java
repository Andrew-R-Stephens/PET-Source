package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.runnables;

import android.media.MediaPlayer;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.SanityMeterView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views.WarnTextView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;

/**
 * SanityRunnable class
 *
 * Contains references to all Views for Sanity-specific UI and updates them via Runnable.
 *
 * @author TritiumGamingStudios
 */
public class SanityRunnable implements Runnable {

    private final EvidenceViewModel evidenceViewModel;

    private final SanityMeterView sanityMeterSoloView;
    private final AppCompatSeekBar sanityMeterSeekBar;
    private final AppCompatTextView sanityMeterTextView;
    private final WarnTextView setupPhaseTextView, actionPhaseTextView, huntWarningTextView;

    private MediaPlayer audio_huntWarn;

    private long wait = 0;
    private int flashTick = 0;

    /**
     * SanityRunnable parameterized constructor -
     * Locally sets references to EvidenceViewModel
     * Locally sets references to Views and Resources found within the parent Fragment.
     *
     * @param evidenceViewModel
     * @param sanityMeterSoloView
     * @param sanityMeterTextView
     * @param sanityMeterSeekBar
     * @param setupPhaseTextView
     * @param actionPhaseTextView
     * @param huntWarningTextView
     * @param audio_huntWarn
     */
    public SanityRunnable(EvidenceViewModel evidenceViewModel, SanityMeterView sanityMeterSoloView, AppCompatTextView sanityMeterTextView, AppCompatSeekBar sanityMeterSeekBar,
                          WarnTextView setupPhaseTextView, WarnTextView actionPhaseTextView, WarnTextView huntWarningTextView, MediaPlayer audio_huntWarn){

        this.evidenceViewModel = evidenceViewModel;

        this.sanityMeterSoloView = sanityMeterSoloView;
        this.sanityMeterTextView = sanityMeterTextView;
        this.sanityMeterSeekBar = sanityMeterSeekBar;
        this.setupPhaseTextView = setupPhaseTextView;
        this.actionPhaseTextView = actionPhaseTextView;
        this.huntWarningTextView = huntWarningTextView;
        this.audio_huntWarn = audio_huntWarn;
    }

    /**
     * setWait
     *
     * Set the time that the parent Thread will wait between local run() calls.
     * Used to properly time the Flash Warning in the local run() method.
     *
     * @param wait
     */
    public void setWait(long wait){
        this.wait = wait;
    }

    /**
     * run
     *
     * Updates both the Sanity Image, the Sanity Percent,
     * the Hunting Phase Warnings, the Low Sanity Warning, and the Sanity Seek Bar.
     */
    @Override
    public void run() {

        if(evidenceViewModel != null && evidenceViewModel.hasSanityData() && evidenceViewModel.getSanityData().getStartTime() == -1)
            evidenceViewModel.getSanityData().setStartTime(System.currentTimeMillis());

        if(evidenceViewModel != null && evidenceViewModel.hasSanityData() && !evidenceViewModel.getSanityData().isPaused()) {

            if(evidenceViewModel.hasTimer() && !evidenceViewModel.getTimer().isPaused()) {

                evidenceViewModel.getSanityData().tick();
                sanityMeterTextView.setText(evidenceViewModel.getSanityData().toPercentString());

                if(evidenceViewModel.hasTimer()) {

                    if(setupPhaseTextView != null) {
                        setupPhaseTextView.setState(evidenceViewModel.isSetup(), true);
                    }

                    if(actionPhaseTextView != null) {
                        actionPhaseTextView.setState(!evidenceViewModel.isSetup(), true);
                    }

                    if (audio_huntWarn != null) {
                        if (evidenceViewModel.isHuntWarningAudioAllowed() && !evidenceViewModel.isSetup() && evidenceViewModel.getSanityData().canWarn()) {
                            audio_huntWarn.start();
                            if (evidenceViewModel.hasSanityData())
                                evidenceViewModel.getSanityData().setCanWarn(false);
                        }
                    }

                    if (evidenceViewModel.getSanityData().getInsanityPercent() < .7) {
                        if (!evidenceViewModel.isSetup()) {
                            if (evidenceViewModel.getSanityData().canFlashWarning()) {
                                if ((wait * (double) ++flashTick) > 1000L / 2.0) {
                                    if (huntWarningTextView != null)
                                        huntWarningTextView.toggleFlash(true);
                                    flashTick = 0;

                                } else
                                    huntWarningTextView.setState(true);
                            }
                        }
                    } else
                        huntWarningTextView.setState(false);

                    if (evidenceViewModel.hasSanityData() && !evidenceViewModel.getSanityData().isPaused())
                        sanityMeterSeekBar.setProgress((int) evidenceViewModel.getSanityData().getSanityActual());

                }
            }

            if(sanityMeterSoloView != null)
                sanityMeterSoloView.invalidate();

        }
    }

    /**
     * haltMediaPlayer
     *
     * Releases the audio stream of Hunt Warning audio.
     */
    public void haltMediaPlayer(){

        if(audio_huntWarn != null) {
            audio_huntWarn.stop();
            audio_huntWarn.release();
            audio_huntWarn = null;
        }

    }

}