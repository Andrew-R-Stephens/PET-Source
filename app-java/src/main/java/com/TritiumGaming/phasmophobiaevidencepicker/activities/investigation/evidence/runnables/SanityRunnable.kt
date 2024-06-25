package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.runnables

import android.media.MediaPlayer
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.GlobalPreferencesViewModel

/**
 * Contains references to all Views for Sanity-specific UI and updates them via Runnable.
 * @author TritiumGamingStudios
 */
class SanityRunnable (
    private val evidenceViewModel: EvidenceViewModel?,
    private val globalPreferencesViewModel: GlobalPreferencesViewModel?
) : Runnable {

    /**
     * The time that the parent Thread will wait between local run() calls.
     * Used to properly time the Flash Warning in the local run() method.
     */
    var wait: Long = 0
    // private var flashTick: Double = 0.0
    // private var flashDuration: Double = 1000L / 2.0

    /**
     * Updates both the Sanity Image, the Sanity Percent,
     * the Hunting Phase Warnings, the Low Sanity Warning, and the Sanity Seek Bar.
     */
    override fun run() {

        // val sanityModel = evidenceViewModel?.sanityModel ?: return
        val timerModel = evidenceViewModel?.timerModel ?: return

        /*
        if (timerModel.isNewCycle) {
            timerModel.initStartTime()
        }
        */

        if (!timerModel.paused.value) {

            evidenceViewModel.sanityModel?.tick()

            /*
            if(evidenceViewModel.timerModel == null) return

            if (phaseTimerData?.isPaused == false) {

                sanityData.tick()

                // Populate the sanity percent text view with a value
                sanityMeterTextView?.text = sanityData.toPercentString()
                // Setup Warn is steady ON if setup phase activity is true
                setupPhaseTextView?.setState((phaseTimerData?.isSetupPhase == true), true)
                // Action Warn is steady ON if setup phase activity is false
                actionPhaseTextView?.setState((phaseTimerData?.isSetupPhase == false), true)

                // Hunt Audio is ACTIVE if setup phase activity is false
                if (globalPreferencesViewModel.isHuntWarningAudioAllowed &&
                    (phaseTimerData?.isSetupPhase == false) && sanityData.warningAudioAllowed) {
                    audio_huntWarn?.start()
                    sanityData.warningAudioAllowed = false
                }

                if ((sanityData.insanityPercent.value < SanityModel.SAFE_MIN_BOUNDS) &&
                    ((phaseTimerData?.isSetupPhase == false) && sanityData.canFlashWarning())) {
                    if ((wait * (++flashTick)) > flashDuration) {
                        println("Toggleable $wait $flashTick $flashDuration")
                        huntWarningTextView?.toggleTextState(true)
                        flashTick = 0.0
                    } else {
                        println("Not Toggleable $wait $flashTick $flashDuration")
                        huntWarningTextView?.setState(true) }
                } else {
                    huntWarningTextView?.setState(false)
                }

                //if (!sanityData.paused.value) { sanitySeekBarView?.updateProgress() }

            } else {
                if(phaseTimerData?.isSetupPhase == true) {
                    huntWarningTextView?.setState(true)
                }
            }
            */

            if (!timerModel.paused.value) {
                if (globalPreferencesViewModel?.isHuntWarningAudioAllowed == true) {
                    huntWarningAudioListener?.play()
                }
            }
        }
    }

    var huntWarningAudioListener: HuntWarningAudioListener? = null
    abstract class HuntWarningAudioListener {
        var mediaPlayer: MediaPlayer? = null
        abstract fun init()
        abstract fun play()
        abstract fun stop()
    }
}