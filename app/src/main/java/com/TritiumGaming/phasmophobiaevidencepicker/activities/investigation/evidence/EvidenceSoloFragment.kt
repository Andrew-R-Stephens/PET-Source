package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationActivity
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.runnables.SanityRunnable
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.PETActivity
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.jobs.DeltaRunnable
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

    @Deprecated("Replaced with Coroutine")
    private var sanityThread: Thread? = null

    private var scope: CoroutineScope? = CoroutineScope(Dispatchers.Default)

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonReset = view.findViewById<AppCompatImageView>(R.id.button_reset)

        buttonReset.setOnClickListener {
            // TODO animate reset arrow
            reset()
        }

        startSanityThread()
    }

    private fun createSanityThread() {
        sanityThread = Thread(object : DeltaRunnable(context, 10f, 24f) {
            override fun runCondition(): Boolean {
                return investigationViewModel?.timerModel?.paused?.value == false
            }

            override fun onTick() {
                try {
                    requireActivity().runOnUiThread(investigationViewModel?.sanityRunnable)
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                }
            }
        })
    }

    private fun createSanityRunnable() {
        try {
            val appLang = (requireActivity() as InvestigationActivity).appLanguage
            investigationViewModel?.sanityRunnable =
                SanityRunnable(investigationViewModel, globalPreferencesViewModel)

            investigationViewModel?.sanityRunnable?.huntWarningAudioListener =
                object : SanityRunnable.HuntWarningAudioListener() {

                    private fun getHuntWarningAudio(appLang: String): MediaPlayer? {
                        var p: MediaPlayer? = null
                        try {
                            p = when (appLang) {
                                "es" -> MediaPlayer.create(requireContext(), R.raw.huntwarning_es)
                                "fr" -> MediaPlayer.create(requireContext(), R.raw.huntwarning_fr)
                                "de" -> MediaPlayer.create(requireContext(), R.raw.huntwarning_de)
                                else -> MediaPlayer.create(requireContext(), R.raw.huntwarning_en)
                            }
                        } catch (e: IllegalStateException) {
                            e.printStackTrace()
                        }
                        return p
                    }

                    override fun init() {
                        lang = appLang
                        mediaPlayer = getHuntWarningAudio(appLang)
                    }

                    override fun play() {
                        if (globalPreferencesViewModel?.isHuntWarningAudioAllowed == true) {
                            mediaPlayer?.start()
                            investigationViewModel?.sanityModel?.warnTriggered = true
                        }
                    }

                    override fun stop() { mediaPlayer?.release() }
                }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    private fun startSanityThread(){
        createSanityRunnable()

        if (sanityThread == null) {
            createSanityThread()
            sanityThread?.start()
        }
    }

    private fun stopSanityThread(){
        sanityThread?.interrupt()

        investigationViewModel?.let { investigationViewModel ->
            investigationViewModel.sanityRunnable?.huntWarningAudioListener?.stop()
            investigationViewModel.sanityRunnable = null
        }
        sanityThread = null
    }

    override fun reset() {
        stopSanityThread()

        super.reset()

        requestInvalidateComponents()

        startSanityThread()
    }

    override fun onPause() {
        stopSanityThread()

        investigationViewModel?.sanityRunnable?.huntWarningAudioListener?.stop()

        super.onPause()
    }

    override fun onResume() {
        startSanityThread()

        super.onResume()
    }
}