package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.ghostboxutility;

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
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.ghostboxutility.data.GhostBoxUtilityData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.ghostboxutility.views.WaveformView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.PermissionsViewModel;

import java.util.Arrays;
import java.util.Locale;

/**
 * ToolGhostSpeakFragment class
 *
 * @author TritiumGamingStudios
 */
public class GhostBoxUtilityFragment extends Fragment implements Visualizer.OnDataCaptureListener {

    private PermissionsViewModel permissionsViewModel;

    private WaveformView waveFormView = null;
    private TextToSpeech textToSpeech = null;
    private Visualizer visualizer = null;

    /**
     *
     */
    public GhostBoxUtilityFragment() {
        super(R.layout.fragment_utility_ghostbox);
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

        //AppCompatTextView title = view.findViewById(R.id.toolspiritbox_title);
        //AppCompatTextView button_gotoEvidence_label = view.findViewById(R.id.label_goto_left);
        AppCompatTextView label_reset = view.findViewById(R.id.label_resetAll);
        AppCompatImageView image_reset = view.findViewById(R.id.icon_resetAll);
        View listener_reset = view.findViewById(R.id.listener_resetAll);
        View listener_goto_left = view.findViewById(R.id.listener_goto_left);
        View listener_goto_right = view.findViewById(R.id.listener_goto_right);

        LinearLayout scrollview_list1 = view.findViewById(R.id.linearlayout_scrollview_list1);
        LinearLayout scrollview_list2 = view.findViewById(R.id.linearlayout_scrollview_list2);
        LinearLayout scrollview_list3 = view.findViewById(R.id.linearlayout_scrollview_list3);

        waveFormView = view.findViewById(R.id.waveFormView);

        listener_goto_left.setOnClickListener(v -> Navigation.findNavController(v).popBackStack()
        );

        label_reset.setEnabled(false);
        image_reset.setEnabled(false);
        listener_reset.setEnabled(false);
        listener_goto_right.setEnabled(false);

        if (getContext() != null) {
            setScrollListEntries(
                    getContext(), R.array.ghostspeaktool_general_array, scrollview_list1);
            setScrollListEntries(
                    getContext(), R.array.ghostspeaktool_spiritbox_array, scrollview_list2);
            setScrollListEntries(
                    getContext(), R.array.ghostspeaktool_oijaboard_array, scrollview_list3);

            if (!permissionsViewModel.isRecordAudioAllowed()) {
                requestAudioPermissions(getContext(), getActivity());
            }
        }

        waveFormView.updateVisualizer(null);

        startTextToSpeech();
    }

    /**
     * @param context     The context
     * @param arrayRes    The Resource array of Strings holding phrases
     * @param destination The target LinearLayout
     */
    public void setScrollListEntries(@NonNull Context context,
                                     @ArrayRes int arrayRes,
                                     LinearLayout destination) {
        GhostBoxUtilityData toolGhostSpeakData = new GhostBoxUtilityData();
        int[] spiritBoxEntries = toolGhostSpeakData.getEntries(context, arrayRes);
        for (int e : spiritBoxEntries) {
            LinearLayout linearLayout = new LinearLayout(context);
            LinearLayout.LayoutParams linearParams =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1f);
            linearParams.setMargins(0, 8, 0, 8);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setLayoutParams(linearParams);

            AppCompatTextView entry = new AppCompatTextView(context);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f);
            entry.setLayoutParams(textParams);
            entry.setGravity(Gravity.CENTER);

            float dip = 24f;
            float px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dip,
                    getResources().getDisplayMetrics());
            entry.setMinimumHeight((int) px);
            entry.setText(getResources().getString(e));
            entry.setMaxLines(1);
            linearLayout.setOnClickListener(v -> new Thread(() -> {
                startTextToSpeech();
                if (textToSpeech != null && permissionsViewModel.isRecordAudioAllowed()) {
                    startVisualizer();
                    textToSpeech.speak(
                            entry.getText(), TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    Log.d("TTS",
                            "TTS is null, or RECORD_AUDIO is " +
                                    permissionsViewModel.isRecordAudioAllowed());
                }

            }).start());

            linearLayout.addView(entry);
            destination.addView(linearLayout);
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
            textToSpeech = new TextToSpeech(getContext(), status -> {
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
            if (getContext() != null) {
                getContext().startActivity(intent);
            }
            else {
                Log.e("TTS", "Context is null");
            }
        } catch (Exception e) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    Toast toast = Toast.makeText(getContext(),
                            "Could not locate Text to Speech Settings Page", Toast.LENGTH_LONG);
                    toast.show();
                });
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
                                      byte[] waveform,
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
    private void requestAudioPermissions(Context c, Activity a) {
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
            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(
                        a,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);
            }
        }

        // If permission is granted, then go ahead recording audio
        if (ContextCompat.checkSelfPermission(c,
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            permissionsViewModel.setRecordAudioAllowed(true);

        }
    }

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