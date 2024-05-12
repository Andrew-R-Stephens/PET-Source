package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.runnables

import android.media.MediaPlayer
import androidx.appcompat.widget.AppCompatTextView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.SanitySeekBarView
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.GlobalPreferencesViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.SanityWarningView

/**
 * Contains references to all Views for Sanity-specific UI and updates them via Runnable.
 * @author TritiumGamingStudios
 */
class SanityRunnable (
    private val evidenceViewModel: EvidenceViewModel?,
    private val globalPreferencesViewModel: GlobalPreferencesViewModel,
    private var sanityMeterTextView: AppCompatTextView?,
    private var sanitySeekBarView: SanitySeekBarView?,
    private var setupPhaseTextView: SanityWarningView?,
    private var actionPhaseTextView: SanityWarningView?,
    private var huntWarningTextView: SanityWarningView?,
    private var audio_huntWarn: MediaPlayer?
) : Runnable {

    private var wait: Long = 0
    private var flashTick = 0

    /**
     * Set the time that the parent Thread will wait between local run() calls.
     * Used to properly time the Flash Warning in the local run() method.
     * @param wait
     */
    fun setWait(wait: Long) {
        this.wait = wait
    }

    /**
     * Updates both the Sanity Image, the Sanity Percent,
     * the Hunting Phase Warnings, the Low Sanity Warning, and the Sanity Seek Bar.
     */
    override fun run() {

        val sanityData = evidenceViewModel?.sanityData ?: return

        if (sanityData.isNewCycle) {
            sanityData.initStartTime()
        }

        if (!sanityData.isPaused) {
            val phaseTimerData = evidenceViewModel.phaseTimerData
            if (phaseTimerData?.isPaused == false) {

                sanityData.tick()

                sanityMeterTextView?.text = sanityData.toPercentString()
                setupPhaseTextView?.setState(phaseTimerData.isSetupPhase, true)
                actionPhaseTextView?.setState(!phaseTimerData.isSetupPhase, true)

                if (globalPreferencesViewModel.isHuntWarningAudioAllowed
                    && !phaseTimerData.isSetupPhase && sanityData.isWarningAudioAllowed()) {
                    audio_huntWarn?.start()
                    sanityData.setWarningAudioAllowed(false)
                }

                if ((sanityData.insanityPercent.value < .7) &&
                    (!phaseTimerData.isSetupPhase && sanityData.canFlashWarning())) {
                    if ((wait * (++flashTick).toDouble()) > 1000L / 2.0) {
                        huntWarningTextView?.toggleFlash(true)
                        flashTick = 0
                    } else { huntWarningTextView?.setState(true) }
                } else { huntWarningTextView?.setState(false) }

                if (!sanityData.isPaused) { sanitySeekBarView?.updateProgress() }
            }
        }
    }

    fun dereferenceViews() {
        sanitySeekBarView = null
        sanityMeterTextView = null
        setupPhaseTextView = null
        actionPhaseTextView = null
        huntWarningTextView = null

        audio_huntWarn = null
    }

    /** Releases the audio stream of Hunt Warning audio. */
    fun haltMediaPlayer() {
        audio_huntWarn?.stop()
        audio_huntWarn?.release()
        audio_huntWarn = null
    }
}