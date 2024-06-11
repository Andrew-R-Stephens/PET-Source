package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationActivity;
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

        AppCompatImageView button_reset = view.findViewById(R.id.button_reset);

        button_reset.setOnClickListener(v -> {
            // TODO animate reset arrow
            reset();
        });

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

                if (evidenceViewModel.hasTimerModel()) {
                    evidenceViewModel.getTimerModel().playTimer();
                }

                if (evidenceViewModel.hasSanityRunnable()) {

                    sanityThread = new Thread() {
                        public void run() {
                            while (evidenceViewModel != null && evidenceViewModel.hasSanityModel()
                                    && !evidenceViewModel.getTimerModel().getPaused().getValue()) {
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

            if (evidenceViewModel.hasTimerModel()) {
                evidenceViewModel.getTimerModel().pauseTimer();
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

        if (evidenceViewModel != null && evidenceViewModel.hasSanityModel() &&
            evidenceViewModel.hasSanityRunnable()) {
            evidenceViewModel.getSanityRunnable().haltMediaPlayer();
            evidenceViewModel.getSanityRunnable().dereferenceViews();
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