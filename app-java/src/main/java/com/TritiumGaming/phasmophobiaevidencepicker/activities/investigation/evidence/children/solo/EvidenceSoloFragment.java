package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.children.solo;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.EvidenceFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.children.solo.data.MapCarouselData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.children.solo.views.DifficultyCarouselView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.children.solo.views.MapCarouselView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.children.solo.views.PhaseTimerControlView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.children.solo.views.PhaseTimerView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.runnables.SanityRunnable;
import com.TritiumGaming.phasmophobiaevidencepicker.listeners.CompositeListener;
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.SanityCarouselView;
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.SanityWarningView;

/**
 * EvidenceSoloFragment class
 *
 * @author TritiumGamingStudios
 */
public class EvidenceSoloFragment extends EvidenceFragment {

    @Nullable
    private Thread sanityThread; //Thread that updates the sanity levels

    private SanityWarningView sanityPhaseView_setup, sanityPhaseView_action;

    /**
     * EvidenceSoloFragment default constructor
     */
    public EvidenceSoloFragment() {
        super(R.layout.fragment_evidence_solo);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        MapCarouselData mapCarouselData = evidenceViewModel.getMapCarouselData();
        if (mapCarouselData.hasMapSizeData()) {
            TypedArray typedArray = getResources().obtainTypedArray(R.array.maps_resources_array);
            String[] names = new String[typedArray.length()];
            int[] sizes = new int[typedArray.length()];
            for (int i = 0; i < typedArray.length(); i++) {
                TypedArray mapTypedArray =
                        getResources().obtainTypedArray(typedArray.getResourceId(i, 0));
                names[i] = mapTypedArray.getString(0);
                int sizeLayer = 6;
                sizes[i] = mapTypedArray.getInt(sizeLayer, 0);
                mapTypedArray.recycle();
            }
            typedArray.recycle();
            mapCarouselData.setMapSizeData(names, sizes);
        }

        /* INITIALIZE VIEWS */
        AppCompatImageButton timer_play_pause = view.findViewById(R.id.timer_play_pause);
        AppCompatImageButton timer_skip = view.findViewById(R.id.timer_skip);

        SanityCarouselView mapCarouselView = view.findViewById(R.id.mapCarouselView);
        AppCompatTextView map_name = mapCarouselView.findViewById(R.id.carousel_name);
        AppCompatImageButton map_prev = mapCarouselView.findViewById(R.id.carousel_prev);
        AppCompatImageButton map_next = mapCarouselView.findViewById(R.id.carousel_next);

        SanityCarouselView difficultyCarouselView = view.findViewById(R.id.difficultyCarouselView);
        AppCompatTextView difficulty_name = difficultyCarouselView.findViewById(R.id.carousel_name);
        AppCompatImageButton difficulty_prev = difficultyCarouselView.findViewById(R.id.carousel_prev);
        AppCompatImageButton difficulty_next = difficultyCarouselView.findViewById(R.id.carousel_next);

        AppCompatImageView button_reset = view.findViewById(R.id.button_reset);
        sanityPhaseView_setup = view.findViewById(R.id.evidence_sanitymeter_phase_setup);
        sanityPhaseView_action = view.findViewById(R.id.evidence_sanitymeter_phase_action);

        /* LISTENERS */
        timer_skip.setOnClickListener(v -> {
            phaseTimerCountdownView.createTimer(false, 0L, 1000L);
            evidenceViewModel.skipSanityToPercent(0, 50, 50);
        });

        button_reset.setOnClickListener(v -> {
            /*LayerDrawable drawable = (LayerDrawable) button_reset.getDrawable();
            drawable.getDrawable(R.id.arrow);*/
            reset();
        });

        /* TIMER CONTROL */
        phaseTimerCountdownView = new PhaseTimerView(
                evidenceViewModel.getSanityData(),
                evidenceViewModel.getPhaseTimerData(),
                phaseTimerTextView);

        playPauseButton = new PhaseTimerControlView(
                evidenceViewModel.getPhaseTimerData(),
                phaseTimerCountdownView,
                timer_play_pause,
                R.drawable.icon_control_play,
                R.drawable.icon_control_pause);

        /* MAP SELECTION */
        mapTrackControl = new MapCarouselView(
            mapCarouselData,
            map_prev, map_next, map_name);
        map_name.setText(mapCarouselData.getMapCurrentName().split(" ")[0]);

        View.OnClickListener difficultyListener = v -> {
            evidenceViewModel.getGhostOrderData().updateOrder();
            ghostList.requestInvalidateGhostContainer(
                    globalPreferencesViewModel.getReorderGhostViews());

            ScrollView parentScroller = ghostSection.findViewById(R.id.list);
            if(parentScroller != null) {
                parentScroller.smoothScrollTo(0, 0);
            }
        };
        compositeListenerPrev = new CompositeListener();
        compositeListenerNext = new CompositeListener();
        compositeListenerPrev.registerListener(difficultyListener);
        compositeListenerNext.registerListener(difficultyListener);
        this.difficultyCarouselView = new DifficultyCarouselView();
        this.difficultyCarouselView.registerListener(compositeListenerPrev, compositeListenerNext);

        /* DIFFICULTY SELECTION */
        this.difficultyCarouselView.init(
                evidenceViewModel.getDifficultyCarouselData(),
                phaseTimerCountdownView,
                playPauseButton,
                difficulty_prev,
                difficulty_next,
                difficulty_name,
                sanityWarningTextView, sanityPhaseView_action, sanityPhaseView_setup,
                sanitySeekBarView
        );

        phaseTimerCountdownView.setTimerControls(playPauseButton);

        this.difficultyCarouselView.setIndex(
                evidenceViewModel.getDifficultyCarouselData()
                        .getDifficultyIndex());

        /* SANITY METER */
        /*
        sanityMeterView.init(
                evidenceViewModel.getSanityData());
        */

        enableUIThread();
    }

    /**
     * reset method
     */
    public void reset() {
        disableUIThread();

        super.softReset();

        requestInvalidateComponents();

        enableUIThread();
    }

    @Override
    public void requestInvalidateComponents() {
        super.requestInvalidateComponents();

        sanityPhaseView_setup.reset();
        sanityPhaseView_action.reset();
    }

    /**
     * enableUIThread method
     */
    public void enableUIThread() {

        if (evidenceViewModel != null && evidenceViewModel.sanityRunnable == null) {

            try {
                String appLang = ((InvestigationActivity) requireActivity()).getAppLanguage();
                evidenceViewModel.sanityRunnable = new SanityRunnable(
                        evidenceViewModel,
                        globalPreferencesViewModel,
                        /*sanityMeterView,*/
                        sanityPercentTextView,
                        sanitySeekBarView,
                        sanityPhaseView_setup,
                        sanityPhaseView_action,
                        sanityWarningTextView,
                        getHuntWarningAudio(appLang));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }

            if (sanityThread == null) {

                if (evidenceViewModel.hasSanityData()) {
                    evidenceViewModel.getSanityData().isPaused = false;
                }

                if (evidenceViewModel.hasSanityRunnable()) {

                    sanityThread = new Thread() {
                        public void run() {
                            while (evidenceViewModel != null && evidenceViewModel.hasSanityData()
                                    && !evidenceViewModel.getSanityData().isPaused) {
                                try {
                                    update();
                                    tick();
                                } catch (InterruptedException e) {
                                    Log.e("EvidenceFragment",
                                            "(SanityThread) InterruptedException error " +
                                                    "handled.");
                                }
                            }
                        }

                        private void tick() throws InterruptedException {
                            long now = System.nanoTime();
                            long updateTime = System.nanoTime() - now;
                            int TARGET_FPS = 30;
                            long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
                            long wait = (OPTIMAL_TIME - updateTime) / 1000000;

                            if (wait < 0) {
                                wait = 1;
                            }
                            evidenceViewModel.sanityRunnable.setWait(wait);
                            Thread.sleep(wait);
                        }

                        private void update() {
                            try {
                                requireActivity().runOnUiThread(
                                        evidenceViewModel.sanityRunnable);
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
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
    public void disableUIThread() {
        if (evidenceViewModel != null) {

            if (evidenceViewModel.hasSanityData()) {
                evidenceViewModel.getSanityData().isPaused = true;
            }

            SanityRunnable sanityRunnable = evidenceViewModel.sanityRunnable;

            if (sanityThread != null) {
                sanityThread.interrupt();

                if (sanityRunnable != null) {
                    sanityRunnable.haltMediaPlayer();
                    sanityRunnable.dereferenceViews();
                    evidenceViewModel.sanityRunnable = null;
                }

                sanityThread = null;
            }

        }

    }

    public MediaPlayer getHuntWarningAudio(@NonNull String appLang) {
        MediaPlayer p = null;
        try {
            p = switch (appLang) {
                case "es" -> MediaPlayer.create(requireContext(), R.raw.huntwarning_es);
                case "fr" -> MediaPlayer.create(requireContext(), R.raw.huntwarning_fr);
                case "de" -> MediaPlayer.create(requireContext(), R.raw.huntwarning_de);
                default -> MediaPlayer.create(requireContext(), R.raw.huntwarning_en);
            };
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return p;
    }

    /**
     * onPause method
     */
    @Override
    public void onPause() {
        disableUIThread();

        if (evidenceViewModel != null && evidenceViewModel.getSanityData() != null) {

            if (evidenceViewModel.hasSanityRunnable()) {
                evidenceViewModel.sanityRunnable.haltMediaPlayer();
                evidenceViewModel.sanityRunnable.dereferenceViews();
            }
        }

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