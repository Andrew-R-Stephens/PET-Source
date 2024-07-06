package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationActivity
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.sanity.SanityRunnable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * EvidenceSoloFragment class
 *
 * @author TritiumGamingStudios
 */
class EvidenceSoloFragment : EvidenceFragment(R.layout.fragment_evidence) {

    private var sanityScope: CoroutineScope? = CoroutineScope(Dispatchers.Default)
    private var sanityJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonReset = view.findViewById<AppCompatImageView>(R.id.button_reset)

        buttonReset.setOnClickListener {
            // TODO animate reset arrow
            reset()
        }

    }

    private fun createSanityRunnable() {
        investigationViewModel?.sanityRunnable =
            object : SanityRunnable(requireContext(), 10f, 10f) {

                override fun runCondition(): Boolean {
                    return sanityJob?.isActive == true
                }

                override fun onTick() {

                    if (investigationViewModel?.timerModel?.paused?.value == false) {
                        investigationViewModel?.sanityModel?.tick()
                    }

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

                    // If the huntAudio is allowed
                    if (globalPreferencesViewModel?.isHuntWarningAudioAllowed == true) {
                        // Play the huntWarning audio
                        huntWarningAudioListener?.play()
                    }
                }
            }

        var appLang: String? = null
        try { appLang = (requireActivity() as InvestigationActivity).appLanguage }
        catch (e: IllegalStateException) { e.printStackTrace() }

        val huntWarningListener: SanityRunnable.HuntWarningAudioListener? = null
        appLang?.let { language ->
            object : SanityRunnable.HuntWarningAudioListener() {
                private fun createMediaPlayer(language: String): MediaPlayer? {
                    var p: MediaPlayer? = null
                    try {
                        p = when (language) {
                            "es" -> MediaPlayer.create(requireContext(), R.raw.huntwarning_es)
                            "fr" -> MediaPlayer.create(requireContext(), R.raw.huntwarning_fr)
                            "de" -> MediaPlayer.create(requireContext(), R.raw.huntwarning_de)
                            "en" -> MediaPlayer.create(requireContext(), R.raw.huntwarning_en)
                            else -> MediaPlayer.create(requireContext(), R.raw.huntwarning_en)
                        }
                    } catch (e: IllegalStateException) { e.printStackTrace() }
                    return p
                }

                override fun init() {
                    lang = appLang
                    mediaPlayer = createMediaPlayer(language)
                }

                override fun play() {
                    if (globalPreferencesViewModel?.isHuntWarningAudioAllowed == true) {
                        mediaPlayer?.start()
                        investigationViewModel?.sanityModel?.warnTriggered = true
                    }
                }

                override fun stop() { mediaPlayer?.release() }
            }
        }

        huntWarningListener?.let { listener ->
            investigationViewModel?.sanityRunnable?.huntWarningAudioListener = listener }

    }

    private fun startSanityJob() {
        sanityJob = sanityScope?.launch {
            investigationViewModel?.sanityRunnable ?: createSanityRunnable()
            investigationViewModel?.sanityRunnable?.run()
        }
        sanityJob?.start()
    }

    private fun stopSanityJob() {
        sanityJob?.cancel("Cancelling Sanity Job")
        investigationViewModel?.sanityRunnable?.huntWarningAudioListener?.stop()
    }

    override fun reset() {
        stopSanityJob()
        super.reset()
        requestInvalidateComponents()
        startSanityJob()
    }

    override fun onPause() {
        stopSanityJob()
        super.onPause()
    }

    override fun onResume() {
        startSanityJob()
        super.onResume()
    }
}