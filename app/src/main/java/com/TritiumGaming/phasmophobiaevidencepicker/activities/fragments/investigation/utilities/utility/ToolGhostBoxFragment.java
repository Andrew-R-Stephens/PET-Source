package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.utility;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.audiofx.Visualizer;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.assets.viewobjects.WaveformView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.data.ToolGhostSpeakData;

import java.util.Arrays;
import java.util.Locale;

/**
 * ToolGhostSpeakFragment class
 *
 * @author TritiumGamingStudios
 */
public class ToolGhostBoxFragment extends Fragment implements Visualizer.OnDataCaptureListener {

    private WaveformView waveFormView = null;
    private TextToSpeech textToSpeech = null;
    private Visualizer visualizer = null;

    /**
     *
     */
    public ToolGhostBoxFragment(){
        super(R.layout.fragment_utility_ghostbox);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        AppCompatTextView title = view.findViewById(R.id.toolspiritbox_title);
        AppCompatTextView button_gotoEvidence_label = view.findViewById(R.id.goto_evidence_title);
        View button_gotoEvidence = view.findViewById(R.id.fragnavlistener_evidence);
        LinearLayout scrollview_list1 = view.findViewById(R.id.linearlayout_scrollview_list1);
        LinearLayout scrollview_list2 = view.findViewById(R.id.linearlayout_scrollview_list2);
        LinearLayout scrollview_list3 = view.findViewById(R.id.linearlayout_scrollview_list3);

        waveFormView = view.findViewById(R.id.waveFormView);

        title.setAutoSizeTextTypeUniformWithConfiguration(20, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        button_gotoEvidence_label.setAutoSizeTextTypeUniformWithConfiguration(10, 50, 1, TypedValue.COMPLEX_UNIT_SP);

        button_gotoEvidence.setOnClickListener(v -> Navigation.findNavController(v).popBackStack()
        );

        if(getContext() != null) {
            setScrollListEntries(getContext(), R.array.ghostspeaktool_general_array, scrollview_list1);
            setScrollListEntries(getContext(), R.array.ghostspeaktool_spiritbox_array, scrollview_list2);
            setScrollListEntries(getContext(), R.array.ghostspeaktool_oijaboard_array, scrollview_list3);

            requestAudioPermissions(getContext(), getActivity());
        }

        waveFormView.updateVisualizer(null);
        waveFormView.invalidate();

        //startTextToSpeech();

    }

    /**
     *
     * @param context The context
     * @param arrayRes The Resource array of Strings holding phrases
     * @param destination The target LinearLayout
     */
    public void setScrollListEntries(@NonNull Context context, @ArrayRes int arrayRes, LinearLayout destination){
        ToolGhostSpeakData toolGhostSpeakData = new ToolGhostSpeakData();
        int[] spiritBoxEntries = toolGhostSpeakData.getEntries(context, arrayRes);
        for(int e: spiritBoxEntries) {
            LinearLayout linearLayout = new LinearLayout(context);
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
            linearParams.setMargins(0,8,0,8);
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            linearLayout.setLayoutParams(linearParams);

            AppCompatTextView entry = new AppCompatTextView(context);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
            entry.setLayoutParams(textParams);
            entry.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                entry.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            float dip = 24f;
            float px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dip,
                    getResources().getDisplayMetrics());
            entry.setMinimumHeight((int)px);
            entry.setText(getResources().getString(e));
            entry.setMaxLines(1);
            linearLayout.setOnClickListener(v -> {
                startTextToSpeech();
                if(textToSpeech != null) {
                    startVisualizer();
                    textToSpeech.speak(entry.getText(), TextToSpeech.QUEUE_FLUSH, null, null);
                }
            });

            linearLayout.addView(entry);
            destination.addView(linearLayout);
        }
    }

    /**
     *
     */
    private void startVisualizer() {
        if(visualizer == null) {
            visualizer = new Visualizer(0);
            visualizer.setDataCaptureListener(this, Visualizer.getMaxCaptureRate(), true, false);
            int CAPTURE_SIZE = 44100;
            visualizer.setCaptureSize(CAPTURE_SIZE);
            visualizer.setEnabled(true);
        }
    }

    /**
     *
     */
    private void stopVisualizer() {
        if(visualizer != null) {
            visualizer.setEnabled(false);
            visualizer.release();
            visualizer = null;
        }
    }

    /**
     *
     */
    private void startTextToSpeech(){
        if(textToSpeech == null) {
            textToSpeech = new TextToSpeech(getContext(), status -> {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.getDefault());
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language " + Locale.getDefault().getDisplayCountry() + " not supported");
                        stopTextToSpeech();
                        visitTTSVoiceDownloads();
                    }
                } else {
                    //Log.d("TTS", "Initialization failed");
                    stopTextToSpeech();
                    visitTTSVoiceDownloads();
                }
            });
        }
    }

    /**
     *
     */
    private void stopTextToSpeech(){
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }

    /**
     * visitTTSVoiceDownloads method
     * The solution was to turn on Wi-Fi on the device and add German and Russian in "Settings -> Language & Input -> Google voice typing -> Voices". After that the languages were downloaded and the app worked as desired.
     */
    public void visitTTSVoiceDownloads(){
        Intent intent = new Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(getContext() != null)
            getContext().startActivity(intent);
        else
            Log.e("TTS", "Content is null");
    }

    /**
     *
     * @param thisVisualiser The visualizer
     * @param waveform The waveform
     * @param samplingRate The sample rate
     */
    @Override
    public void onWaveFormDataCapture(Visualizer thisVisualiser, byte[] waveform, int samplingRate) {
        if(waveFormView != null)
            if (textToSpeech != null && textToSpeech.isSpeaking()) {
                waveFormView.updateVisualizer(Arrays.copyOf(waveform, waveform.length));
            } else
                waveFormView.updateVisualizer(null);
    }

    /**
     *
     * @param thisVisualiser The visualizer
     * @param fft The fft
     * @param samplingRate The sampling rate
     */
    @Override
    public void onFftDataCapture(Visualizer thisVisualiser, byte[] fft, int samplingRate) {
        // NO-OP
    }

    /**
     *
     * @param c The context
     * @param a The current activity
     */
    private void requestAudioPermissions(Context c, Activity a) {
        if (ContextCompat.checkSelfPermission(c, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            //When permission is not granted by user, show them message why this permission is needed.
            int MY_PERMISSIONS_RECORD_AUDIO = 1;
            if (ActivityCompat.shouldShowRequestPermissionRationale(a, Manifest.permission.RECORD_AUDIO)) {
                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(a, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(a, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(c, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            //Go ahead with recording audio now
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