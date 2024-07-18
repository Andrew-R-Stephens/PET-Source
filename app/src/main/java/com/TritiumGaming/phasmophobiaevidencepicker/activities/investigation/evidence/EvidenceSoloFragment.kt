package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.sanity.SanityRunnable
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.ResetButton
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

        val buttonReset = view.findViewById<ComposeView>(R.id.button_reset)
        buttonReset.setContent {
            ResetButton(
                onClick = { reset() }
            )
        }

    }

    private fun createSanityRunnable() {
        investigationViewModel?.sanityRunnable =
            object : SanityRunnable(requireContext(), 10f, 10f) {

                override fun runCondition(): Boolean {
                    return sanityJob?.isActive == true
                }

                override fun onTick() {

                    /** TODO : recreate the following action flow

                    if (huntAudio is both allowed) AND (huntAudio is not "triggered") AND (the phase is "action") {
                        play the huntWarning audio
                    set huntAudio as "triggered"
                    }
                    */

                    investigationViewModel?.let { investigationViewModel ->
                        if (investigationViewModel.timerModel?.paused?.value == false) {
                            investigationViewModel.sanityModel?.tick()

                            investigationViewModel.phaseWarnModel?.let { phaseWarnModel ->
                                phaseWarnModel.updateTimeElapsed()

                                // If the huntAudio is allowed
                                if (phaseWarnModel.audioAllowed) {
                                    huntWarningAudioListener?.play()
                                }
                            }
                        }
                    }
                }
            }

        val huntWarningListener: SanityRunnable.HuntWarningAudioListener? = null
        globalPreferencesViewModel?.currentLanguageAbbr?.let { language ->
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
                    lang = language
                    mediaPlayer = createMediaPlayer(language)
                }

                override fun play() {
                    if (investigationViewModel?.phaseWarnModel?.audioAllowed == true) {
                        mediaPlayer?.start()
                        investigationViewModel?.phaseWarnModel?.audioWarnTriggered = true
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