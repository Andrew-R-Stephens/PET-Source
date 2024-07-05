package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.sanity

import android.media.MediaPlayer
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.GlobalPreferencesViewModel

/**
 * Contains references to all Views for Sanity-specific UI and updates them via Runnable.
 * @author TritiumGamingStudios
 */
class SanityRunnable (
    private val investigationViewModel: InvestigationViewModel?,
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

        val timerModel = investigationViewModel?.timerModel ?: return

        /*
        if (timerModel.isNewCycle) {
            timerModel.initStartTime()
        }
        */

        if (!timerModel.paused.value) {

            investigationViewModel.sanityModel?.tick()

            /** TODO : recreate the following action flow

                if the timer is not paused {
                    tick the SanityModel
                    write sanity percent to display view
                    set state of the setup phase view to "active"
                    set state of the action phase view to "inactive"

                    if (huntAudio is both allowed) AND (huntAudio is not "triggered") AND (the phase is "action") {
                        play the huntWarning audio
                        set huntAudio as "triggered"
                    }

                    if (insanityPercent is below safe) AND (the phase is "action") AND (huntWarnView is allowed) {
                        set huntWarnView to "active"

                        if (huntWarnView blink duration is not exceeded) {
                            blink both huntWarnView and its text on and off in 500ms intervals
                        }
                        else {
                            set both huntWarnView and its text to blink steady "on"
                        }
                    }
                    else {
                        set huuntWarnView to "inactive"
                    }

                    update progressbar to proper value of insanityPercent
                 }
                 else {
                    if (phase is "setup") {
                        set huntWarnView to "active" and blink steady on
                    }
                 }
             */

            // If the timer is not paused
            if (!timerModel.paused.value) {
                // If the huntAudio is allowed
                if (globalPreferencesViewModel?.isHuntWarningAudioAllowed == true) {
                    // Play the huntWarning audio
                    huntWarningAudioListener?.play()
                }
            }
        }
    }

    var huntWarningAudioListener: HuntWarningAudioListener? = null
    abstract class HuntWarningAudioListener {
        var lang: String? = null
        var mediaPlayer: MediaPlayer? = null
        abstract fun init()
        abstract fun play()
        abstract fun stop()
    }
}