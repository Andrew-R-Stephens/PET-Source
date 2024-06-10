package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.carousels.MapCarouselModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.runnables.SanityRunnable;

/**
 * EvidenceSoloFragment class
 *
 * @author TritiumGamingStudios
 */
public class EvidenceSoloFragment extends EvidenceFragment {

    @Nullable
    private Thread sanityThread; //Thread that updates the sanity levels

    /**
     * EvidenceSoloFragment default constructor
     */
    public EvidenceSoloFragment() {
        super(R.layout.fragment_evidence);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        MapCarouselModel mapCarouselData = evidenceViewModel.getMapCarouselData();
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

        AppCompatImageView button_reset = view.findViewById(R.id.button_reset);

        button_reset.setOnClickListener(v -> {
            // TODO animate reset arrow
            reset();
        });

        /*
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
    }

    /**
     * enableUIThread method
     */
    public void enableUIThread() {

        if (evidenceViewModel != null && evidenceViewModel.getSanityRunnable() == null) {

            try {
                String appLang = ((InvestigationActivity) requireActivity()).getAppLanguage();
                evidenceViewModel.setSanityRunnable(
                        new SanityRunnable(
                            evidenceViewModel,
                            globalPreferencesViewModel,
                            getHuntWarningAudio(appLang)
                        )
                );
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }

            if (sanityThread == null) {

                if (evidenceViewModel.hasSanityData()) {
                    evidenceViewModel.getSanityData().updatePaused(false);
                }

                if (evidenceViewModel.hasSanityRunnable()) {

                    sanityThread = new Thread() {
                        public void run() {
                            while (evidenceViewModel != null && evidenceViewModel.hasSanityData()
                                    && !evidenceViewModel.getSanityData().getPaused().getValue()) {
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
                            evidenceViewModel.getSanityRunnable().setWait(wait);
                            Thread.sleep(wait);
                        }

                        private void update() {
                            try {
                                requireActivity().runOnUiThread(
                                        evidenceViewModel.getSanityRunnable());
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
                evidenceViewModel.getSanityData().updatePaused(true);
            }

            SanityRunnable sanityRunnable = evidenceViewModel.getSanityRunnable();

            if (sanityThread != null) {
                sanityThread.interrupt();

                if (sanityRunnable != null) {
                    sanityRunnable.haltMediaPlayer();
                    sanityRunnable.dereferenceViews();
                    evidenceViewModel.setSanityRunnable(null);
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
                evidenceViewModel.getSanityRunnable().haltMediaPlayer();
                evidenceViewModel.getSanityRunnable().dereferenceViews();
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