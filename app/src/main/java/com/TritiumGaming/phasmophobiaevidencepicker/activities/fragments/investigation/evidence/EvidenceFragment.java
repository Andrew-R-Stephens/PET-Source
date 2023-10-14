package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.InvestigationFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.DifficultyCarouselData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.MapCarouselData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.PhaseTimerData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views.DifficultyCarouselView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views.MapCarouselView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views.PhaseTimerControlView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views.PhaseTimerView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views.SanitySeekBarView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views.WarnTextView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.EvidenceViewData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.GhostOrderData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.GhostPopupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.InvestigationData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.SanityData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.EvidencePopupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.EvidencePopupWindow;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.EvidenceRadioButton;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.EvidenceRadioGroup;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.EvidenceView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.GhostPopupWindow;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.GhostView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.SanityMeterView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FontUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.listeners.CompositeListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import pl.droidsonroids.gif.GifImageView;

/**
 * EvidenceFragment class
 *
 * @author TritiumGamingStudios
 */
public class EvidenceFragment extends InvestigationFragment {

    protected GhostPopupData ghostPopupData;
    protected EvidencePopupData evidencePopupData;

    protected SanityData sanityData;
    protected PhaseTimerData phaseTimerData;
    protected DifficultyCarouselData difficultyCarouselData;
    protected MapCarouselData mapCarouselData;

    protected LinearLayout list_ghosts;
    protected LinearLayout list_evidences;
    protected ProgressBar ghostProgressBar,
            evidenceProgressBar;

    protected ConstraintLayout sanityTrackingConstraintLayout;

    protected AppCompatImageView toggleSanityButton;
    protected AppCompatTextView phaseTimerTextView;
    protected AppCompatTextView sanityPercentTextView;

    protected CompositeListener compositeListenerPrev, compositeListenerNext;

    protected DifficultyCarouselView difficultyCarouselView;
    protected PhaseTimerView phaseTimerCountdownView;
    protected PhaseTimerControlView playPauseButton;
    protected MapCarouselView mapTrackControl;
    protected SanitySeekBarView sanitySeekBarView;
    protected SanityMeterView sanityMeterView;
    protected WarnTextView sanityWarningTextView;


    /**
     * EvidenceFragment constructor
     * <p>
     * TODO
     *
     * @param layout -
     */
    public EvidenceFragment(int layout) {
        super(layout);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_evidence_solo, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        if(evidenceViewModel != null) {
            sanityData = evidenceViewModel.getSanityData();
            phaseTimerData = evidenceViewModel.getPhaseTimerData();
            difficultyCarouselData = evidenceViewModel.getDifficultyCarouselData();
        }

        if(sanityData != null) {
            sanityData.setFlashTimeoutMax(globalPreferencesViewModel.getHuntWarningFlashTimeout());
        }

        // GHOST / EVIDENCE CONTAINERS
        AppCompatTextView header_ghostLabel, header_evidenceLabel;
        FrameLayout
                section_left = view.findViewById(R.id.column_left),
                section_right = view.findViewById(R.id.column_right);
        section_right.findViewById(R.id.scrollview)
                .setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_RIGHT);
        if(!globalPreferencesViewModel.getIsLeftHandSupportEnabled()) {
            header_ghostLabel = section_left.findViewById(R.id.label_container);
            header_evidenceLabel = section_right.findViewById(R.id.label_container);
            list_ghosts = section_left.findViewById(R.id.list);
            list_evidences = section_right.findViewById(R.id.list);
            ghostProgressBar = section_left.findViewById(R.id.progressbar);
            evidenceProgressBar = section_right.findViewById(R.id.progressbar);
        } else {
            header_ghostLabel = section_right.findViewById(R.id.label_container);
            header_evidenceLabel = section_left.findViewById(R.id.label_container);
            list_ghosts = section_right.findViewById(R.id.list);
            list_evidences =  section_left.findViewById(R.id.list);
            ghostProgressBar = section_right.findViewById(R.id.progressbar);
            evidenceProgressBar = section_left.findViewById(R.id.progressbar);
        }
        header_evidenceLabel.setText(R.string.investigation_section_title_evidence);
        header_ghostLabel.setText(R.string.investigation_section_title_ghosts);
        //ghostContainer.requestDisallowInterceptTouchEvent(true);

        toggleSanityButton = view.findViewById(R.id.button_toggleSanity);

        // TIMER VIEW
        phaseTimerTextView = view.findViewById(R.id.evidence_timer_text);

        // SANITY METER VIEWS
        sanityPercentTextView = view.findViewById(R.id.evidence_sanitymeter_percentage);
        sanityMeterView = view.findViewById(R.id.evidence_sanitymeter_progressbar);
        sanitySeekBarView = view.findViewById(R.id.evidence_sanitymeter_seekbar);
        sanityWarningTextView = view.findViewById(R.id.evidence_sanitymeter_huntwarning);

        // SANITY COLLAPSIBLE
        sanityTrackingConstraintLayout = view.findViewById(R.id.constraintLayout_sanityTracking);

        if(toggleSanityButton != null) {
            toggleSanityButton.setOnClickListener(v -> {
                if(evidenceViewModel.isCollapsed()) {
                    sanityTrackingConstraintLayout.animate()
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);
                                    sanityTrackingConstraintLayout.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);

                                    sanityTrackingConstraintLayout.setVisibility(View.VISIBLE);
                                    toggleSanityButton.setImageLevel(2);
                                    evidenceViewModel.setCollapsed(false);
                                }
                            })
                            .start();
                } else {
                    sanityTrackingConstraintLayout.animate()
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);

                                    sanityTrackingConstraintLayout.setVisibility(View.VISIBLE);
                                }
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationStart(animation);

                                    sanityTrackingConstraintLayout.setVisibility(View.GONE);
                                    toggleSanityButton.setImageLevel(1);
                                    evidenceViewModel.setCollapsed(true);
                                }
                            })
                            .start();
                }
            });

            initCollapsible();
        }

        sanitySeekBarView.init(
                sanityData,
                sanityPercentTextView);

        // SANITY
        if (sanitySeekBarView != null) {
            sanitySeekBarView.resetProgress();
        }

        sanityMeterView.init(sanityData);
        if (sanityData != null) {
            sanityPercentTextView.setText(String.format("%1$-4s", sanityData.toPercentString()));
        }

        new Thread(this::createGhostViews).start();

        new Thread(this::createEvidenceViews).start();

    }

    private void initCollapsible() {
        if(evidenceViewModel.isCollapsed()) {
            sanityTrackingConstraintLayout.setVisibility(View.GONE);
            toggleSanityButton.setImageLevel(1);
        } else {
            sanityTrackingConstraintLayout.setVisibility(View.VISIBLE);
            toggleSanityButton.setImageLevel(2);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void createGhostViews() {

        ghostPopupData = new GhostPopupData(getContext());

        if(getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                buildGhostViews();

                list_ghosts.post(() -> haltProgressAnimation(ghostProgressBar));
            });
        }

    }

    @SuppressLint("ResourceType")
    private void createEvidenceViews() {

        evidencePopupData = new EvidencePopupData(getContext());

        if(getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                buildEvidenceViews();

                list_evidences.post(() -> haltProgressAnimation(evidenceProgressBar));
            });
        }

    }

    private void haltProgressAnimation(ProgressBar progressBar) {
        progressBar.animate().alpha(0).setDuration(250).setListener(
                new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressBar.setVisibility(View.GONE);
                super.onAnimationEnd(animation);
            }
        }).start();
    }

    protected void requestInvalidateGhostContainer() {
        if(evidenceViewModel.getGhostOrderData().hasChanges()) {
            reorderGhostViews();
        }
    }

    protected void forceResetGhostContainer() {
        reorderGhostViews();
    }

    private void forceResetEvidenceContainer() {

        for(int i = 0; i < list_evidences.getChildCount(); i++) {
            ((EvidenceRadioGroup)list_evidences.getChildAt(i).findViewById(R.id.radioGroup))
                    .reset(evidenceViewModel, i);
        }

    }

    protected void reorderGhostViews() {

        GhostOrderData ghostOrderData = evidenceViewModel.getGhostOrderData();
        int[] currOrder = ghostOrderData.getCurrOrder();

        for (int j : currOrder) {

            View childView = list_ghosts.findViewById(j);

            list_ghosts.removeView(childView);
            list_ghosts.addView(childView);

        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void buildGhostViews() {

        if(getContext() == null) { return; }

        int[] newGhostOrder = evidenceViewModel.getGhostOrderData().getCurrOrder();

        list_ghosts.setWeightSum(newGhostOrder.length);

        for (int j : newGhostOrder) {

            GhostView ghostView = new GhostView(getContext()) {

                @Override
                public void createPopup() {

                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }

                    GhostPopupWindow ghostPopupWindow = new GhostPopupWindow(getContext());
                    ghostPopupWindow.build(evidenceViewModel, ghostPopupData, j, adRequest);
                    popupWindow = ghostPopupWindow.getPopupWindow();

                }
            };

            ghostView.build(evidenceViewModel, j);

            list_ghosts.addView(ghostView);

        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private void buildEvidenceViews() {
        if(getContext() == null) { return; }

        for(int i = 0; i < evidencePopupData.getCount(); i++) {
            final int groupIndex = i;

            EvidencePopupData.EvidencePopupRecord popupRecord =
                    evidencePopupData.getEvidencePopupRecordAt(i);

            EvidenceView evidenceView = new EvidenceView(getContext()) {

                @Override
                public void createPopup() {

                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }

                    EvidencePopupWindow evidencePopupWindow =
                            new EvidencePopupWindow(getContext());
                    evidencePopupWindow.build(
                            evidenceViewModel, popupRecord, groupIndex, adRequest);

                    popupWindow = evidencePopupWindow.getPopupWindow();

                }

                @Override
                public void requestInvalidateGhostContainer() {
                    EvidenceFragment.this.requestInvalidateGhostContainer();
                }

            };

            evidenceView.build(evidenceViewModel, i, list_ghosts);

            list_evidences.addView(evidenceView);

        }

    }

    @Override
    public void softReset() {
        if (evidenceViewModel != null) {
            evidenceViewModel.reset();
        }

        if (sanitySeekBarView != null) {
            sanitySeekBarView.updateProgress();
        }

        if(phaseTimerCountdownView != null) {
            phaseTimerCountdownView.destroyTimer();
        }
    }


    public void requestInvalidateComponents() {

        if(evidenceViewModel != null) {
            evidenceViewModel.getGhostOrderData().updateOrder();
        }


        if(list_evidences != null) {
            forceResetEvidenceContainer();
            list_evidences.invalidate();
        }

        if(list_ghosts != null) {
            forceResetGhostContainer();
        }

        // SANITY
        if (sanitySeekBarView != null) {
            sanitySeekBarView.resetProgress();
        }

        if (sanityData != null) {
            sanityPercentTextView.setText(String.format("%1$-4s", sanityData.toPercentString()));
        }

        if(phaseTimerData != null) {
            playPauseButton.pause();
            phaseTimerCountdownView.reset();
        }

        if(sanityWarningTextView != null) {
            sanityWarningTextView.reset();
        }

    }

    /**
     * onPause
     */
    @Override
    public void onPause() {

        phaseTimerCountdownView.destroyTimer();

        super.onPause();
    }

    /**
     * onDestroyView
     */
    @Override
    public void onDestroyView() {

        if (sanityMeterView != null) {
            sanityMeterView.recycleBitmaps();
        }

        if(popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }

        super.onDestroyView();
    }

    /**
     * onResume
     */
    @Override
    public void onResume() {

        if (!sanityMeterView.hasBuiltImages()) {
            sanityMeterView.buildImages();
        }

        super.onResume();
    }


}
