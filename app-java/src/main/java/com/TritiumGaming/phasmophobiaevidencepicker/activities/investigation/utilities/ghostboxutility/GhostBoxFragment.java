package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.ghostboxutility;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.ghostboxutility.data.GhostBoxUtilityModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.ghostboxutility.views.WaveformView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.PermissionsViewModel;

import java.util.Arrays;
import java.util.Locale;

/**
 * ToolGhostSpeakFragment class
 *
 * @author TritiumGamingStudios
 */
public class GhostBoxFragment extends InvestigationFragment
        implements Visualizer.OnDataCaptureListener {

    private PermissionsViewModel permissionsViewModel;

    @Nullable
    private WaveformView waveFormView = null;
    @Nullable
    private TextToSpeech textToSpeech = null;
    @Nullable
    private Visualizer visualizer = null;

    public GhostBoxFragment() {
        super(R.layout.fragment_utilities_ghostbox);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (permissionsViewModel == null) {
            permissionsViewModel = new ViewModelProvider(
                    requireActivity()).get(PermissionsViewModel.class);
        }

        AppCompatTextView title = view.findViewById(R.id.textView_title);
        AppCompatImageView prev = view.findViewById(R.id.button_prev);

        LinearLayout scrollview_list1 = view.findViewById(R.id.linearlayout_scrollview_list1);
        LinearLayout scrollview_list2 = view.findViewById(R.id.linearlayout_scrollview_list2);
        LinearLayout scrollview_list3 = view.findViewById(R.id.linearlayout_scrollview_list3);

        waveFormView = view.findViewById(R.id.waveFormView);


        title.setText("Ghost Box");


        prev.setOnClickListener(v ->
                Navigation.findNavController(v).popBackStack());

        if (getContext() != null) {
            setScrollListEntries(
                    getContext(), view, R.array.ghostspeaktool_general_array, scrollview_list1);
            setScrollListEntries(
                    getContext(), view, R.array.ghostspeaktool_spiritbox_array, scrollview_list2);
            setScrollListEntries(
                    getContext(), view, R.array.ghostspeaktool_oijaboard_array, scrollview_list3);

            if (!permissionsViewModel.isRecordAudioAllowed()) {
                try {
                    requestAudioPermissions(requireContext(), requireActivity());
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }

        waveFormView.updateVisualizer(null);

        startTextToSpeech();

        try {
            requireActivity()
                    .getOnBackPressedDispatcher()
                    .addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context     The context
     * @param arrayRes    The Resource array of Strings holding phrases
     * @param destination The target LinearLayout
     */
    public void setScrollListEntries(@NonNull Context context,
                                     View view,
                                     @ArrayRes int arrayRes,
                                     @NonNull LinearLayout destination) {
        LayoutInflater inflater;
        try {
            inflater = LayoutInflater.from(requireContext());
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return;
        }
        GhostBoxUtilityModel toolGhostSpeakData = new GhostBoxUtilityModel();

        int[] spiritBoxEntries = toolGhostSpeakData.getEntries(context, arrayRes);
        for (int e : spiritBoxEntries) {
            View evidenceParent = inflater.inflate(
                    R.layout.item_ghostbox_entry,
                    (ViewGroup) view,
                    false);

            AppCompatTextView text = evidenceParent.findViewById(R.id.textView_entry);

            text.setText(getResources().getString(e));

            text.setOnClickListener(v -> new Thread(() -> {
                startTextToSpeech();
                if (textToSpeech != null && permissionsViewModel.isRecordAudioAllowed()) {
                    startVisualizer();
                    textToSpeech.speak(
                            text.getText(), TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    Log.d("TTS",
                            "TTS is null, or RECORD_AUDIO is " +
                                    permissionsViewModel.isRecordAudioAllowed());
                }

            }).start());

            destination.addView(evidenceParent);
        }
    }

    /**
     *
     */
    private void startVisualizer() {
        if (visualizer == null) {
            visualizer = new Visualizer(0);
            visualizer.setDataCaptureListener(
                    this, Visualizer.getMaxCaptureRate(), true, false);
            int CAPTURE_SIZE = 44100;
            visualizer.setCaptureSize(CAPTURE_SIZE);
            visualizer.setEnabled(true);
        }
    }

    /**
     *
     */
    private void stopVisualizer() {
        if (visualizer != null) {
            visualizer.setEnabled(false);
            visualizer.release();
            visualizer = null;
        }
    }

    /**
     *
     */
    private void startTextToSpeech() {
        if (textToSpeech == null) {
            try {
                textToSpeech = new TextToSpeech(requireContext(), status -> {
                    if (status == TextToSpeech.SUCCESS) {
                        int result = textToSpeech.setLanguage(Locale.getDefault());
                        if (result == TextToSpeech.LANG_MISSING_DATA ||
                                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS",
                                    "Language " +
                                            Locale.getDefault().getDisplayCountry() + " not supported");
                            stopTextToSpeech();
                            visitTTSVoiceDownloads();
                        }
                    } else {
                        Log.d("TTS", "Initialization failed");
                        stopTextToSpeech();
                        visitTTSVoiceDownloads();
                    }
                });
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */
    private void stopTextToSpeech() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }

    /**
     * visitTTSVoiceDownloads method
     * <p>
     * The solution was to turn on Wi-Fi on the device and add German and Russian in
     * "Settings -> Language & Input -> Google voice typing -> Voices".
     * After that the languages were downloaded and the app worked as desired.
     */
    public void visitTTSVoiceDownloads() {
        try {
            Intent intent = new Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                requireContext().startActivity(intent);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            try {
                requireActivity().runOnUiThread(() -> {
                    Toast toast = Toast.makeText(getContext(),
                            "Could not locate Text to Speech Settings Page", Toast.LENGTH_LONG);
                    toast.show();
                });
            } catch (IllegalStateException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * @param thisVisualiser The visualizer
     * @param waveform       The waveform
     * @param samplingRate   The sample rate
     */
    @Override
    public void onWaveFormDataCapture(Visualizer thisVisualiser,
                                      @NonNull byte[] waveform,
                                      int samplingRate) {
        if (waveFormView != null) {
            if (textToSpeech != null && textToSpeech.isSpeaking()) {
                waveFormView.updateVisualizer(Arrays.copyOf(waveform, waveform.length));
            } else {
                waveFormView.updateVisualizer(null);
            }
        }
    }

    /**
     * @param thisVisualiser The visualizer
     * @param fft            The fft
     * @param samplingRate   The sampling rate
     */
    @Override
    public void onFftDataCapture(Visualizer thisVisualiser, byte[] fft, int samplingRate) {
        // NO-OP
    }

    /**
     * requestAudioPermissions
     * <p>
     * Allows the user to allow or deny RECORD_AUDIO system function. This will restrict
     * the Sprit Box utility at its most fundamental level.
     *
     * @param c The context
     * @param a The current activity
     */
    private void requestAudioPermissions(@NonNull Context c, @NonNull Activity a) {

        if (ContextCompat.checkSelfPermission(
                c, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            // When permission is not granted by user,
            // show them message why this permission is needed.
            int MY_PERMISSIONS_RECORD_AUDIO = 1;
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    a,
                    Manifest.permission.RECORD_AUDIO)) {
                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(
                        a,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);

                Log.d("RecordAudio", "Accepted1");
            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(
                        a,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);

                Log.d("RecordAudio", "Accepted2");
            }
        }

        // If permission is granted, then go ahead recording audio
        if (ContextCompat.checkSelfPermission(c,
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {

            permissionsViewModel.setRecordAudioAllowed(true);

            Log.d("RecordAudio", "Accepted3");
        }
    }

    @Override
    protected void initViewModels() {
        super.initViewModels();
    }

    @Override
    public void reset() { }

    /**
     *
     */
    @Override
    public void onDestroy() {

        stopTextToSpeech();
        stopVisualizer();

        super.onDestroy();
    }

}