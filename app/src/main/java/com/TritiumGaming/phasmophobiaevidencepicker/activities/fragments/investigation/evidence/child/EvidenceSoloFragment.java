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
import com.TritiumGaming.phasmophobiaevidencepicker.assets.viewobjects.MapSelectControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.data.InvestigationData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.data.SanityData;
import com.TritiumGaming.phasmophobiaevidencepicker.assets.viewobjects.TimerPlayControl;
import com.TritiumGaming.phasmophobiaevidencepicker.assets.viewobjects.DifficultySelectControl;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.activity.InvestigationActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.data.data.SanityRunnable;
import com.TritiumGaming.phasmophobiaevidencepicker.assets.viewobjects.WarnTextView;

/**
 * EvidenceSoloFragment class
 *
 * @author TritiumGamingStudios
 */
public class EvidenceSoloFragment extends EvidenceFragment {

    private Thread sanityThread; //Thread that updates the sanity levels

    private DifficultySelectControl difficultySelectControls;
    private WarnTextView sanityPhaseView_setup, sanityPhaseView_action;

    /**
     * EvidenceSoloFragment default constructor
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
        AppCompatTextView difficulty_name = view.findViewById(R.id.difficulty_name);
        AppCompatTextView map_name = view.findViewById(R.id.mapchoice_name);
        AppCompatImageButton timer_play_pause = view.findViewById(R.id.timer_play_pause);
        AppCompatImageButton timer_skip = view.findViewById(R.id.timer_skip);
        AppCompatImageButton difficulty_prev = view.findViewById(R.id.difficulty_prev);
        AppCompatImageButton difficulty_next = view.findViewById(R.id.difficulty_next);
        AppCompatImageButton map_prev = view.findViewById(R.id.mapchoice_prev);
        AppCompatImageButton map_next = view.findViewById(R.id.mapchoice_next);
        View navigation_fragListener_reset = view.findViewById(R.id.listener_resetAll);
        sanityPhaseView_setup = view.findViewById(R.id.evidence_sanitymeter_phase_setup);
        sanityPhaseView_action = view.findViewById(R.id.evidence_sanitymeter_phase_action);

        // TEXT SIZE
        map_name.setAutoSizeTextTypeUniformWithConfiguration(5,50,1, TypedValue.COMPLEX_UNIT_SP);

        // LISTENERS
        timer_skip.setOnClickListener(v -> {
            if(evidenceViewModel != null && evidenceViewModel.hasTimer()) {
                evidenceViewModel.getTimer().createTimer(0L, 1000L);
                if((!(evidenceViewModel.getTimer().getTimeRemaining() > 0L)) && evidenceViewModel.getSanityData().getSanityActual() < 50)
                    evidenceViewModel.getSanityData().setProgressManually(50);
            }
        });
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

        // TIMER CONTROL
        TimerPlayControl playPauseButton = new TimerPlayControl(
                evidenceViewModel.getTimer(),
                timer_play_pause,
                R.drawable.icon_play,
                R.drawable.icon_pause);
        if(evidenceViewModel.hasTimer()) {
            if (evidenceViewModel.getTimer().isPaused())
                playPauseButton.setPaused();
            else
                playPauseButton.setPlayed();
        }

        // MAP SELECTION
        MapSelectControl mapTrackControl = new MapSelectControl(map_prev, map_next, map_name);
        mapTrackControl.init(evidenceViewModel);
        map_name.setText(evidenceViewModel.getMapCurrentName().split(" ")[0]);
        difficultySelectControls = new DifficultySelectControl(
                evidenceViewModel.getTimer(),
                difficulty_prev,
                difficulty_next,
                difficulty_name,
                getResources().getString(R.string.evidence_timer_difficulty_default),
                getResources().getStringArray(R.array.evidence_timer_difficulty_names_array),
                getResources().getStringArray(R.array.evidence_timer_difficulty_times_array));
        if(evidenceViewModel != null && evidenceViewModel.hasTimer())
            evidenceViewModel.getTimer().setTimerControls(playPauseButton);
        difficultySelectControls.init(evidenceViewModel);
        difficultySelectControls.setTimerControl(playPauseButton);
        if(evidenceViewModel != null)
            difficultySelectControls.setState(evidenceViewModel.getDifficulty());

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
     * saveStates method
     */
    public void saveStates() {
        if(evidenceViewModel != null)
            evidenceViewModel.setDifficulty(difficultySelectControls.getState());
        super.saveStates();
    }

    /**
     * softReset method
     */
    public void softReset(){
        disableUIThread();
        if(difficultySelectControls != null)
            difficultySelectControls.reset();
        super.softReset();
    }

    /**
     * enableUIThread method
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
                        evidenceViewModel, sanityMeterView, sanityPercent, sanitySeekBar, sanityPhaseView_setup, sanityPhaseView_action, sanityWarning, huntwarn));
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
     * disableUIThread method
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
     * onPause method
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
     * onResume method
     */
    @Override
    public void onResume() {
        enableUIThread();
        super.onResume();
    }

}