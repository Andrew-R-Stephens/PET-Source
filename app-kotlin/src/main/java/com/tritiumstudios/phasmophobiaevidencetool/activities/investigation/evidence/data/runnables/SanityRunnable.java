package com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.data.runnables;

import android.media.MediaPlayer;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.views.SanityMeterView;
import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.children.solo.data.PhaseTimerData;
import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.children.solo.views.SanitySeekBarView;
import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.children.solo.views.WarnTextView;
import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.data.SanityData;
import com.tritiumstudios.phasmophobiaevidencetool.data.viewmodels.EvidenceViewModel;
import com.tritiumstudios.phasmophobiaevidencetool.data.viewmodels.shared.GlobalPreferencesViewModel;

/**
 * SanityRunnable class
 * <p>
 * Contains references to all Views for Sanity-specific UI and updates them via Runnable.
 *
 * @author TritiumGamingStudios
 */
public class SanityRunnable implements Runnable {

    private final EvidenceViewModel evidenceViewModel;
    private final GlobalPreferencesViewModel globalPreferencesViewModel;

    @Nullable
    private SanityMeterView sanityMeterSoloView;
    @Nullable
    private SanitySeekBarView sanityMeterSeekBar;
    @Nullable
    private AppCompatTextView sanityMeterTextView;
    @Nullable
    private WarnTextView setupPhaseTextView, actionPhaseTextView, huntWarningTextView;

    @Nullable
    private MediaPlayer audio_huntWarn;

    private long wait = 0;
    private int flashTick = 0;

    /**
     * SanityRunnable parameterized constructor -
     * Locally sets references to EvidenceViewModel
     * Locally sets references to Views and Resources found within the parent Fragment.
     *
     * @param evidenceViewModel -
     * @param sanityMeterSoloView -
     * @param sanityMeterTextView -
     * @param sanityMeterSeekBar -
     * @param setupPhaseTextView -
     * @param actionPhaseTextView -
     * @param huntWarningTextView -
     * @param audio_huntWarn -
     */
    public SanityRunnable(
            EvidenceViewModel evidenceViewModel,
            GlobalPreferencesViewModel globalPreferencesViewModel,
            SanityMeterView sanityMeterSoloView,
            AppCompatTextView sanityMeterTextView,
            SanitySeekBarView sanityMeterSeekBar,
            WarnTextView setupPhaseTextView,
            WarnTextView actionPhaseTextView,
            WarnTextView huntWarningTextView,
            MediaPlayer audio_huntWarn) {

        this.evidenceViewModel = evidenceViewModel;
        this.globalPreferencesViewModel = globalPreferencesViewModel;

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
     * <p>
     * Set the time that the parent Thread will wait between local run() calls.
     * Used to properly time the Flash Warning in the local run() method.
     *
     * @param wait
     */
    public void setWait(long wait) {
        this.wait = wait;
    }

    /**
     * run
     * <p>
     * Updates both the Sanity Image, the Sanity Percent,
     * the Hunting Phase Warnings, the Low Sanity Warning, and the Sanity Seek Bar.
     */
    @Override
    public void run() {

        SanityData sanityData = evidenceViewModel.getSanityData();
        PhaseTimerData phaseTimerData = evidenceViewModel.getPhaseTimerData();

        if (sanityData != null) {
            if (sanityData.getStartTime() == -1) {
                sanityData.setStartTime(System.currentTimeMillis());
            }

            if (!sanityData.isPaused()) {
                if (phaseTimerData != null && !phaseTimerData.isPaused()) {

                    sanityData.tick();
                    if(sanityMeterTextView != null) {
                        sanityMeterTextView.setText(sanityData.toPercentString());
                    }

                    if (setupPhaseTextView != null) {
                        setupPhaseTextView.setState(phaseTimerData.isSetupPhase(), true);
                    }

                    if (actionPhaseTextView != null) {
                        actionPhaseTextView.setState(!phaseTimerData.isSetupPhase(), true);
                    }

                    if (audio_huntWarn != null) {
                        if (globalPreferencesViewModel.isHuntWarningAudioAllowed() &&
                                !phaseTimerData.isSetupPhase() && sanityData.canWarn()) {
                            audio_huntWarn.start();
                            sanityData.setCanWarn(false);
                        }
                    }

                    if (sanityData.getInsanityPercent() < .7) {
                        if (!phaseTimerData.isSetupPhase()) {
                            if (sanityData.canFlashWarning()) {
                                if ((wait * (double) ++flashTick) > 1000L / 2.0) {
                                    if (huntWarningTextView != null) {
                                        huntWarningTextView.toggleFlash(true);
                                    }
                                    flashTick = 0;

                                } else if (huntWarningTextView != null) {
                                    huntWarningTextView.setState(true);
                                }
                            }
                        } else if (huntWarningTextView != null) {
                            huntWarningTextView.setState(false);
                        }
                    } else if (huntWarningTextView != null) {
                        huntWarningTextView.setState(false);
                    }
                    if (!sanityData.isPaused()) {
                        if(sanityMeterSeekBar != null) {
                            sanityMeterSeekBar.updateProgress();
                        }
                    }
                }

                if (sanityMeterSoloView != null) {
                    sanityMeterSoloView.invalidate();
                }
            }
        }
    }

    public void dereferenceViews() {
        sanityMeterSoloView = null;
        sanityMeterSeekBar = null;
        sanityMeterTextView = null;
        setupPhaseTextView = null;
        actionPhaseTextView = null;
        huntWarningTextView = null;

        audio_huntWarn = null;
    }

    /**
     * haltMediaPlayer
     * <p>
     * Releases the audio stream of Hunt Warning audio.
     */
    public void haltMediaPlayer() {

        if (audio_huntWarn != null) {
            audio_huntWarn.stop();
            audio_huntWarn.release();
            audio_huntWarn = null;
        }

    }

}