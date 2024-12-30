package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.investigation.utilities.ghostboxutility

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.audiofx.Visualizer
import android.media.audiofx.Visualizer.OnDataCaptureListener
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.ArrayRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation.findNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.codex.ghostbox.GhostBoxUtilityModel
import com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.investigation.InvestigationFragment
import com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.investigation.utilities.ghostboxutility.views.WaveformView
import java.util.Locale

class GhostBoxFragment :
    InvestigationFragment(R.layout.fragment_utilities_ghostbox), OnDataCaptureListener {
    private var waveFormView: WaveformView? = null
    private var textToSpeech: TextToSpeech? = null
    private var visualizer: Visualizer? = null

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val title = view.findViewById<AppCompatTextView>(R.id.textView_title)
        val prev = view.findViewById<AppCompatImageView>(R.id.button_left)
        val promptList1 = view.findViewById<LinearLayout>(R.id.linearlayout_scrollview_list1)
        val promptList2 = view.findViewById<LinearLayout>(R.id.linearlayout_scrollview_list2)
        val promptList3 = view.findViewById<LinearLayout>(R.id.linearlayout_scrollview_list3)
        waveFormView = view.findViewById(R.id.waveFormView)

        title?.text = getString(R.string.ghostbox_title)

        prev.setOnClickListener { v: View? -> v?.let { findNavController(v).popBackStack() } }

        setScrollListEntries(
            requireContext(), view, R.array.ghostspeaktool_general_array, promptList1)
        setScrollListEntries(
            requireContext(), view, R.array.ghostspeaktool_spiritbox_array, promptList2)
        setScrollListEntries(
            requireContext(), view, R.array.ghostspeaktool_oijaboard_array, promptList3)

        if (!permissionsViewModel.isRecordAudioAllowed) {
            try { requestAudioPermissions(requireContext(), requireActivity()) }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }

        waveFormView?.updateVisualizer(null)

        startTextToSpeech()

        try {
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        findNavController(view).popBackStack()
                    }
                })
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    private fun setScrollListEntries(
        context: Context, view: View?, @ArrayRes arrayRes: Int, destination: LinearLayout) {
        val inflater: LayoutInflater
        try { inflater = LayoutInflater.from(requireContext())
        } catch (e: IllegalStateException) { e.printStackTrace()
            return }
        val toolGhostSpeakData = GhostBoxUtilityModel()

        val spiritBoxEntries = toolGhostSpeakData.getEntries(context, arrayRes)
        for (e in spiritBoxEntries) {
            val evidenceParent = inflater.inflate(
                R.layout.item_ghostbox_entry, view as ViewGroup?, false)

            val text = evidenceParent.findViewById<AppCompatTextView>(R.id.textView_entry)

            text.text = resources.getString(e)

            text.setOnClickListener {
                Thread {
                    permissionsViewModel.let { permissionsViewModel ->
                        startTextToSpeech()
                        if (permissionsViewModel.isRecordAudioAllowed) {
                            startVisualizer()
                            textToSpeech?.speak(
                                text.text, TextToSpeech.QUEUE_FLUSH, null, null) }
                        else {
                            Log.d("TTS",
                                "TTS is null, or RECORD_AUDIO is " +
                                        "${permissionsViewModel.isRecordAudioAllowed}") }
                    }
                }.start()
            }

            destination.addView(evidenceParent)
        }
    }

    private fun startVisualizer() {
        visualizer = visualizer ?: Visualizer(0)
        visualizer?.let { visualizer ->
            visualizer.setDataCaptureListener(
                this, Visualizer.getMaxCaptureRate(), true, false)
            val CAPTURE_SIZE = 44100
            visualizer.setCaptureSize(CAPTURE_SIZE)
            visualizer.setEnabled(true)
        }
    }

    private fun stopVisualizer() {
        visualizer?.let { visualizer ->
            visualizer.setEnabled(false)
            visualizer.release()
        }
        visualizer = null
    }

    private fun startTextToSpeech() {
        if (textToSpeech == null) {
            try {
                textToSpeech = TextToSpeech(requireContext()) { status: Int ->
                    if (status == TextToSpeech.SUCCESS) {
                        val result = textToSpeech!!.setLanguage(Locale.getDefault())
                        if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED
                        ) {
                            Log.e(
                                "TTS",
                                "Language " +
                                        Locale.getDefault().displayCountry + " not supported"
                            )
                            stopTextToSpeech()
                            visitTTSVoiceDownloads()
                        }
                    } else {
                        Log.d("TTS", "Initialization failed")
                        stopTextToSpeech()
                        visitTTSVoiceDownloads()
                    }
                }
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    private fun stopTextToSpeech() {
       textToSpeech?.let { textToSpeech ->
           textToSpeech.stop()
           textToSpeech.shutdown()
       }
        textToSpeech = null
    }

    /**
     * The solution was to turn on Wi-Fi on the device and add German and Russian in
     * "Settings -> Language & Input -> Google voice typing -> Voices".
     * After that the languages were downloaded and the app worked as desired.
     */
    private fun visitTTSVoiceDownloads() {
        try {
            val intent = Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try { requireContext().startActivity(intent) }
            catch (e: IllegalStateException) { e.printStackTrace() }
        } catch (e: Exception) {
            try {
                requireActivity().runOnUiThread {
                    val toast = Toast.makeText(
                        context,
                        "Could not locate Text to Speech Settings Page", Toast.LENGTH_LONG
                    )
                    toast.show()
                }
            } catch (e2: IllegalStateException) { e2.printStackTrace() }
            e.printStackTrace()
        }
    }

    /**
     * @param thisVisualiser The visualizer
     * @param waveform       The waveform
     * @param samplingRate   The sample rate
     */
    override fun onWaveFormDataCapture(
        thisVisualiser: Visualizer,
        waveform: ByteArray,
        samplingRate: Int
    ) {
        waveFormView?.let { waveFormView ->
            if (textToSpeech?.isSpeaking == true) {
                waveFormView.updateVisualizer(waveform.copyOf(waveform.size)) }
            else { waveFormView.updateVisualizer(null) }
        }
    }

    /**
     * @param thisVisualiser The visualizer
     * @param fft            The fft
     * @param samplingRate   The sampling rate
     */
    override fun onFftDataCapture(thisVisualiser: Visualizer, fft: ByteArray, samplingRate: Int) {
        // NO-OP
    }

    /**
     * Allows the user to allow or deny RECORD_AUDIO system function. This will restrict
     * the Sprit Box utility at its most fundamental level.
     *
     * @param c The context
     * @param a The current activity
     */
    private fun requestAudioPermissions(c: Context, a: Activity) {
        if (ContextCompat.checkSelfPermission(c, Manifest.permission.RECORD_AUDIO) !=
            PackageManager.PERMISSION_GRANTED) {

            // When permission is not granted by user,
            // show them message why this permission is needed.
            val recordAudioPermission = 1
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    a, Manifest.permission.RECORD_AUDIO)) {
                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(
                    a, arrayOf(Manifest.permission.RECORD_AUDIO), recordAudioPermission)

                Log.d("RecordAudio", "Accepted1")
            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(
                    a, arrayOf(Manifest.permission.RECORD_AUDIO), recordAudioPermission)

                Log.d("RecordAudio", "Accepted2")
            }
        }

        // If permission is granted, then go ahead recording audio
        if (ContextCompat.checkSelfPermission(
                c, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            permissionsViewModel.isRecordAudioAllowed = true

            Log.d("RecordAudio", "Accepted3")
        }
    }

    override fun reset() {}

    override fun onDestroy() {
        stopTextToSpeech()
        stopVisualizer()

        super.onDestroy()
    }
}