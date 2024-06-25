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

/**
 * EvidenceSoloFragment class
 *
 * @author TritiumGamingStudios
 */
class EvidenceSoloFragment : EvidenceFragment(R.layout.fragment_evidence) {

    private var sanityThread: Thread? = null //Thread that updates the sanity levels

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonReset = view.findViewById<AppCompatImageView>(R.id.button_reset)

        buttonReset.setOnClickListener {
            // TODO animate reset arrow
            reset()
        }

        enableUIThread()
    }

    /**
     * reset method
     */
    override fun reset() {
        disableUIThread()

        super.reset()

        requestInvalidateComponents()

        enableUIThread()
    }

    /**
     * enableUIThread method
     */
    private fun enableUIThread() {
        if (evidenceViewModel?.sanityRunnable == null) {
            try {
                val appLang = (requireActivity() as InvestigationActivity).appLanguage
                val runnable = SanityRunnable(evidenceViewModel, globalPreferencesViewModel)
                runnable.huntWarningAudioListener =
                    object : SanityRunnable.HuntWarningAudioListener() {
                        override fun init() { mediaPlayer = getHuntWarningAudio(appLang) }
                        override fun play() {
                            if(globalPreferencesViewModel?.isHuntWarningAudioAllowed == true) {
                                mediaPlayer?.start()
                                evidenceViewModel?.sanityModel?.warnTriggered = true
                            }
                        }
                        override fun stop() { mediaPlayer?.release() }
                    }
                evidenceViewModel?.sanityRunnable = runnable
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }

            Log.d("SanityThread", if(sanityThread == null) "DNE" else "Exists")
            if (sanityThread == null) {
                Log.d("SanityRunnable", if(evidenceViewModel?.sanityRunnable == null) "DNE" else "Exists")
                if (evidenceViewModel?.hasSanityRunnable() == true) {
                    sanityThread = object : Thread() {
                        override fun run() {
                            while (evidenceViewModel?.timerModel?.paused?.value == false) {
                                try {
                                    update()
                                    tick()
                                } catch (e: InterruptedException) {
                                    Log.e("EvidenceFragment",
                                        "(SanityThread) InterruptedException error handled.")
                                }
                            }
                        }

                        @Throws(InterruptedException::class)
                        private fun tick() {
                            val now = System.nanoTime()
                            val updateTime = System.nanoTime() - now
                            val fpsTarget = 30
                            val timeOptimal = (1000000000 / fpsTarget).toLong()

                            var wait = (timeOptimal - updateTime) / 1000000
                            if (wait < 0) { wait = 1 }

                            evidenceViewModel?.sanityRunnable?.wait = wait

                            sleep(wait)
                        }

                        private fun update() {
                            try {
                                requireActivity().runOnUiThread(evidenceViewModel?.sanityRunnable)
                            } catch (e: IllegalStateException) {
                                e.printStackTrace()
                            }
                        }
                    }
                    sanityThread?.start()
                }
            }
        }
    }

    /**
     * disableUIThread method
     */
    private fun disableUIThread() {
        sanityThread?.interrupt()

        evidenceViewModel?.let { evidenceViewModel ->
            evidenceViewModel.sanityRunnable?.huntWarningAudioListener?.stop()
            evidenceViewModel.sanityRunnable = null
        }
        sanityThread = null
    }

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

    /**
     * onPause method
     */
    override fun onPause() {
        disableUIThread()

        evidenceViewModel?.sanityRunnable?.huntWarningAudioListener?.stop()

        super.onPause()
    }

    /**
     * onResume method
     */
    override fun onResume() {
        enableUIThread()

        super.onResume()
    }
}