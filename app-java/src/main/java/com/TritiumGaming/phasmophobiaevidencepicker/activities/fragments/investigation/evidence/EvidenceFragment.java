package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.SanityData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.SanityMeterView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.evidence.EvidenceList;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.ghost.GhostList;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.investigation.InvestigationSection;
import com.TritiumGaming.phasmophobiaevidencepicker.listeners.CompositeListener;

/**
 * EvidenceFragment class
 *
 * @author TritiumGamingStudios
 */
public class EvidenceFragment extends InvestigationFragment {

    protected SanityData sanityData;
    protected PhaseTimerData phaseTimerData;
    protected DifficultyCarouselData difficultyCarouselData;
    protected MapCarouselData mapCarouselData;

    protected InvestigationSection ghostSection, evidenceSection;

    protected GhostList ghostList;
    protected EvidenceList evidenceList;

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

        if(getContext() == null) { return; }

        if(evidenceViewModel != null) {
            sanityData = evidenceViewModel.getSanityData();
            phaseTimerData = evidenceViewModel.getPhaseTimerData();
            difficultyCarouselData = evidenceViewModel.getDifficultyCarouselData();
        }

        if(sanityData != null) {
            sanityData.setFlashTimeoutMax(globalPreferencesViewModel.getHuntWarningFlashTimeout());
        }

        // GHOST / EVIDENCE CONTAINERS
        FrameLayout
                column_left = view.findViewById(R.id.column_left),
                column_right = view.findViewById(R.id.column_right);
        column_right.findViewById(R.id.scrollview)
                .setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_RIGHT);

        if(!globalPreferencesViewModel.getIsLeftHandSupportEnabled()) {
            ghostSection = (InvestigationSection) column_left.getChildAt(0);
            evidenceSection = (InvestigationSection) column_right.getChildAt(0);
        } else {
            evidenceSection = (InvestigationSection) column_left.getChildAt(0);
            ghostSection = (InvestigationSection) column_right.getChildAt(0);
        }

        ghostSection.setLabel(getString(R.string.investigation_section_title_ghosts));
        evidenceSection.setLabel(getString(R.string.investigation_section_title_evidence));

        ScrollView ghost_scrollview = ghostSection.findViewById(R.id.scrollview);
        ScrollView evidence_scrollview = evidenceSection.findViewById(R.id.scrollview);

        ghostList = new GhostList(getContext());
        evidenceList = new EvidenceList(getContext());

        ghostList.init(evidenceViewModel, popupWindow,
                ghostSection.findViewById(R.id.progressbar),
                adRequest);
        evidenceList.init(evidenceViewModel, popupWindow,
                evidenceSection.findViewById(R.id.progressbar),
                adRequest, ghostList);

        ViewStub list_ghosts = ghostSection.findViewById(R.id.list);
        ViewStub list_evidence = evidenceSection.findViewById(R.id.list);

        ghost_scrollview.removeView(list_ghosts);
        ghost_scrollview.addView(ghostList);

        evidence_scrollview.removeView(list_evidence);
        evidence_scrollview.addView(evidenceList);

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

        // SANITY
        if (sanitySeekBarView != null) {
            sanitySeekBarView.init(
                    sanityData,
                    sanityPercentTextView);
            sanitySeekBarView.resetProgress();
        }

        sanityMeterView.init(sanityData);

        new Thread(ghostList::createGhostViews).start();
        new Thread(evidenceList::createEvidenceViews).start();

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
/*

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
*/
/*
    @SuppressLint("ResourceType")
    private void createEvidenceViews() {

        evidencePopupData = new EvidencePopupData(getContext());

        if(getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                buildEvidenceViews();

                list_evidences.post(() -> haltProgressAnimation(evidenceProgressBar));
            });
        }

    }*/
/*

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
*/
/*

    protected void requestInvalidateGhostContainer() {
        if(evidenceViewModel.getGhostOrderData().hasChanges()) {
            reorderGhostViews();
        } else {
            updateGhostViews();
        }
    }
    protected void forceResetGhostContainer() {
        reorderGhostViews();
    }
*/
/*
    private void forceResetEvidenceContainer() {

        for(int i = 0; i < evidenceList.getChildCount(); i++) {
            ((EvidenceRadioGroup)evidenceList.getChildAt(i).findViewById(R.id.radioGroup))
                    .reset(evidenceViewModel, i);
        }

    }*/
/*

    protected void reorderGhostViews() {

        GhostOrderData ghostOrderData = evidenceViewModel.getGhostOrderData();
        int[] currOrder = ghostOrderData.getCurrOrder();

        for (int j : currOrder) {

            View childView = list_ghosts.findViewById(j);

            list_ghosts.removeView(childView);
            list_ghosts.addView(childView);

        }

    }

    private void updateGhostViews() {
        for(int i = 0; i < list_ghosts.getChildCount(); i++) {
            GhostView g = (GhostView) list_ghosts.getChildAt(i);
            g.forceUpdateComponents();
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

*/
/*

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
*/

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

        if(evidenceList != null) {
            evidenceList.forceResetEvidenceContainer();
        }

        if(ghostList != null) {
            ghostList.forceResetGhostContainer();
        }

        // SANITY
        if (sanitySeekBarView != null) {
            sanitySeekBarView.resetProgress();
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
