package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.child;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentTransaction;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.parent.EvidenceFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.data.data.InvestigationData;
import com.TritiumGaming.phasmophobiaevidencepicker.assets.viewobjects.MapTrackControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.data.SanityData;
import com.TritiumGaming.phasmophobiaevidencepicker.assets.viewobjects.TimerPlayControl;
import com.TritiumGaming.phasmophobiaevidencepicker.assets.viewobjects.MapSelectControl;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.activity.InvestigationActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.data.data.SanityRunnable;
import com.TritiumGaming.phasmophobiaevidencepicker.assets.viewobjects.WarnTextView;

/**
 * EvidenceSoloFragment class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class EvidenceSoloFragment extends EvidenceFragment {

    private Thread sanityThread; //Thread that updates the sanity levels

    private MapTrackControl mapTrackControl;
    private MapSelectControl prevNextButton;
    private WarnTextView sanityPhase_setup, sanityPhase_action;

    /**
     * EvidenceSoloFragment constructor
     *
     * TODO
     */
    public EvidenceSoloFragment(){
        super(R.layout.fragment_evidence_solo);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        if(!evidenceViewModel.hasInvestigationData() && getContext() != null)
            evidenceViewModel.setInvestigationData(new InvestigationData(getContext()));

        if(!evidenceViewModel.hasSanityData())
            evidenceViewModel.setSanityData(new SanityData(evidenceViewModel));

        // STORES MAP NAME AND SIZE
        if(evidenceViewModel.hasMapSizeData()) {
            TypedArray typedArray = getResources().obtainTypedArray(R.array.maps_resources_array);
            String[] names = new String[typedArray.length()];
            int[] sizes = new int[typedArray.length()];
            for(int i = 0; i < typedArray.length(); i++) {
                TypedArray mapTypedArray = getResources().obtainTypedArray(typedArray.getResourceId(i, 0));
                names[i] = mapTypedArray.getString(0);
                int sizeLayer = 6;
                sizes[i] = mapTypedArray.getInt(sizeLayer, 0);
                mapTypedArray.recycle();
            }
            typedArray.recycle();
            evidenceViewModel.setMapSizeData(names, sizes);
        }

        // INITIALIZE VIEWS
        AppCompatTextView evidence_timer_difficulty_title = view.findViewById(R.id.evidence_timer_difficulty_actual);
        AppCompatTextView evidence_map_title = view.findViewById(R.id.mapchoice_actual);
        AppCompatImageButton evidence_play_pause = view.findViewById(R.id.timer_play_pause);
        AppCompatImageButton evidence_skip = view.findViewById(R.id.timer_skip);
        AppCompatImageButton evidence_prev = view.findViewById(R.id.timer_prev);
        AppCompatImageButton evidence_next = view.findViewById(R.id.timer_next);
        AppCompatImageButton map_prev = view.findViewById(R.id.mapchoice_prev);
        AppCompatImageButton map_next = view.findViewById(R.id.mapchoice_next);
        //View navigation_fragListener_reset = view.findViewById(R.id.listener_resetAll);
        sanityPhase_setup = view.findViewById(R.id.evidence_sanitymeter_phase_setup);
        sanityPhase_action = view.findViewById(R.id.evidence_sanitymeter_phase_action);

        // TEXT SIZE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            evidence_map_title.setAutoSizeTextTypeUniformWithConfiguration(5,50,1, TypedValue.COMPLEX_UNIT_SP);
        }

        // LISTENERS
        evidence_skip.setOnClickListener(v -> {
            if(evidenceViewModel != null && evidenceViewModel.hasTimer()) {
                evidenceViewModel.getTimer().createTimer(0L, 1000L);
                if((!(evidenceViewModel.getTimer().getTimeRemaining() > 0L)) && evidenceViewModel.getSanityData().getSanityActual() < 50)
                    evidenceViewModel.getSanityData().setProgressManually(50);
            }
        });
        /*
        navigation_fragListener_reset.setOnClickListener(v -> {
                softReset();
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                if (Build.VERSION.SDK_INT >= 26) {
                    ft.setReorderingAllowed(false);
                }
                ft.detach(EvidenceSoloFragment.this).commitNow();
                ft.attach(EvidenceSoloFragment.this).commitNow();
                }
        );
        */
        // TIMER CONTROL
        TimerPlayControl playPauseButton = new TimerPlayControl(
                evidenceViewModel.getTimer(),
                evidence_play_pause,
                R.drawable.icon_play,
                R.drawable.icon_pause);
        if(evidenceViewModel.hasTimer()) {
            if (evidenceViewModel.getTimer().isPaused())
                playPauseButton.setPaused();
            else
                playPauseButton.setPlayed();
        }

        // MAP SELECTION
        mapTrackControl = new MapTrackControl(map_prev, map_next, evidence_map_title);
        mapTrackControl.init(evidenceViewModel);
        evidence_map_title.setText(evidenceViewModel.getMapCurrentName().split(" ")[0]);
        prevNextButton = new MapSelectControl(
                evidenceViewModel.getTimer(),
                evidence_prev,
                evidence_next,
                evidence_timer_difficulty_title,
                getResources().getString(R.string.evidence_timer_difficulty_default),
                getResources().getStringArray(R.array.evidence_timer_difficulty_names_array),
                getResources().getStringArray(R.array.evidence_timer_difficulty_times_array));
        if(evidenceViewModel != null && evidenceViewModel.hasTimer())
            evidenceViewModel.getTimer().setTimerControls(playPauseButton);
        prevNextButton.init(evidenceViewModel);
        prevNextButton.setTimerControl(playPauseButton);
        if(evidenceViewModel != null)
            prevNextButton.setState(evidenceViewModel.getDifficulty());

        // SANITY METER
        if(sanitySeekBar != null)
            sanitySeekBar.setProgress(0);
        if(evidenceViewModel != null) {
            sanityMeterView.init(evidenceViewModel.getSanityData());
            if (evidenceViewModel.hasSanityData())
                sanityPercent.setText(evidenceViewModel.getSanityData().toPercentString());
        }

        enableUIThread();
    }

    /**
     * saveStates
     *
     * TODO
     */
    public void saveStates() {
        if(evidenceViewModel != null)
            evidenceViewModel.setDifficulty(prevNextButton.getState());
        super.saveStates();
    }

    /**
     * softReset
     *
     * TODO
     */
    public void softReset() {
        disableUIThread();

        if (prevNextButton != null) {
            prevNextButton.reset();
        }

        if(mapTrackControl != null)
            mapTrackControl.reset();

        if (sanityPhase_setup != null) {
            sanityPhase_setup.reset();
            sanityPhase_setup.invalidate();
        }
        if(sanityPhase_action != null) {
            sanityPhase_action.reset();
            sanityPhase_action.invalidate();
        }

        super.softReset();
    }

    /**
     * enableUIThread
     *
     * TODO
     */
    public void enableUIThread(){
        if(evidenceViewModel != null && evidenceViewModel.getSanityRunnable() == null) {
            if (getActivity() != null) {
                String appLang = ((InvestigationActivity) getActivity()).getAppLanguage();
                MediaPlayer huntwarn = MediaPlayer.create(getContext(), R.raw.huntwarning_en);
                switch (appLang) {
                    case "es":
                        huntwarn = MediaPlayer.create(getContext(), R.raw.huntwarning_es);
                        break;
                    case "fr":
                        huntwarn = MediaPlayer.create(getContext(), R.raw.huntwarning_fr);
                        break;
                    case "de":
                        huntwarn = MediaPlayer.create(getContext(), R.raw.huntwarning_de);
                        break;
                }
                evidenceViewModel.setSanityRunnable(new SanityRunnable(
                        evidenceViewModel, sanityMeterView, sanityPercent, sanitySeekBar, sanityPhase_setup, sanityPhase_action, sanityWarning, huntwarn));
            }

            if (sanityThread == null) {
                if (evidenceViewModel.hasSanityData())
                    evidenceViewModel.getSanityData().setIsPaused(false);
                if (evidenceViewModel.hasSanityRunnable()) {
                    sanityThread = new Thread() {
                        public void run() {
                        while (evidenceViewModel != null && evidenceViewModel.hasSanityData() && !evidenceViewModel.getSanityData().isPaused()) {
                            try {
                                if (getActivity() != null)
                                    getActivity().runOnUiThread(evidenceViewModel.getSanityRunnable());
                                long now = System.nanoTime();
                                long updateTime = System.nanoTime() - now;
                                int TARGET_FPS = 30;
                                long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
                                long wait = (OPTIMAL_TIME - updateTime) / 1000000;
                                if(wait < 0)
                                    wait = 1;
                                evidenceViewModel.getSanityRunnable().setWait(wait);
                                Thread.sleep(wait);
                            } catch (InterruptedException e) {
                                Log.e("EvidenceFragment", "InterruptedException error handled.");
                            }
                        }
                        }
                    };
                    sanityThread.start();
                }
            }
        }
    }

    /**
     * disableUIThread
     *
     * TODO
     */
    public void disableUIThread(){
        if(evidenceViewModel != null && evidenceViewModel.hasSanityData())
            evidenceViewModel.getSanityData().setIsPaused(true);
        if(sanityThread != null) {
            sanityThread.interrupt();
            if(evidenceViewModel.hasSanityRunnable()) {
                evidenceViewModel.getSanityRunnable().haltMediaPlayer();
                evidenceViewModel.setSanityRunnable(null);
            }
            sanityThread = null;
        }
    }

    /**
     * onPause
     *
     * TODO
     */
    @Override
    public void onPause() {
        disableUIThread();
        if(evidenceViewModel != null)
            if(evidenceViewModel.hasSanityData()) {
                evidenceViewModel.getSanityData().setCanWarn(false);
            if(evidenceViewModel.hasSanityRunnable())
                evidenceViewModel.getSanityRunnable().haltMediaPlayer();
        }
        saveStates();
        super.onPause();
    }

    /**
     * onResume
     *
     * TODO
     */
    @Override
    public void onResume() {
        enableUIThread();
        super.onResume();
    }

}