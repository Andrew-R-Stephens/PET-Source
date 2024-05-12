package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.DifficultyCarouselView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.MapCarouselView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.PhaseTimerControlView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.PhaseTimerView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.SanitySeekBarView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.SanityMeterView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.evidence.EvidenceList;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.ghost.GhostList;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.InvestigationSection;
import com.TritiumGaming.phasmophobiaevidencepicker.listeners.CompositeListener;
import com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.SanityWarningView;

/**
 * EvidenceFragment class
 *
 * @author TritiumGamingStudios
 */
public class EvidenceFragment extends InvestigationFragment {

    protected InvestigationSection ghostSection, evidenceSection;

    protected GhostList ghostList;
    protected EvidenceList evidenceList;

    protected ConstraintLayout sanityTrackingConstraintLayout;

    protected AppCompatImageView toggleCollapseButton;
    protected AppCompatTextView phaseTimerTextView;
    protected AppCompatTextView sanityPercentTextView;

    protected CompositeListener compositeListenerPrev, compositeListenerNext;

    protected DifficultyCarouselView difficultyCarouselView;
    protected PhaseTimerView phaseTimerCountdownView;
    protected PhaseTimerControlView playPauseButton;
    protected MapCarouselView mapTrackControl;
    protected SanitySeekBarView sanitySeekBarView;
    protected SanityMeterView sanityMeterView;
    protected SanityWarningView sanityWarningTextView;


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

        if(evidenceViewModel.getSanityData() != null) {
            evidenceViewModel.getSanityData()
                    .setFlashTimeoutMax(globalPreferencesViewModel.getHuntWarningFlashTimeout());
        }

        // GHOST / EVIDENCE CONTAINERS
        FrameLayout
                column_left = view.findViewById(R.id.column_left),
                column_right = view.findViewById(R.id.column_right);
        column_right.findViewById(R.id.scrollview)
                .setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_RIGHT);

        if(!globalPreferencesViewModel.isLeftHandSupportEnabled()) {
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

        ghostList = new GhostList(requireContext());
        evidenceList = new EvidenceList(requireContext());

        ghostList.init(
                globalPreferencesViewModel, evidenceViewModel,
                popupWindow, ghostSection.findViewById(R.id.progressbar),
                adRequest);
        evidenceList.init(
                globalPreferencesViewModel, evidenceViewModel,
                popupWindow, evidenceSection.findViewById(R.id.progressbar),
                adRequest, ghostList);

        ViewStub list_ghosts = ghostSection.findViewById(R.id.list);
        ViewStub list_evidence = evidenceSection.findViewById(R.id.list);

        ghost_scrollview.removeView(list_ghosts);
        ghost_scrollview.addView(ghostList);

        evidence_scrollview.removeView(list_evidence);
        evidence_scrollview.addView(evidenceList);

        //ghostContainer.requestDisallowInterceptTouchEvent(true);

        toggleCollapseButton = view.findViewById(R.id.button_toggleSanity);

        // TIMER VIEW
        phaseTimerTextView = view.findViewById(R.id.evidence_timer_text);

        // SANITY METER VIEWS
        sanityPercentTextView = view.findViewById(R.id.evidence_sanitymeter_percentage);
        sanityMeterView = view.findViewById(R.id.evidence_sanitymeter_progressbar);
        sanitySeekBarView = view.findViewById(R.id.evidence_sanitymeter_seekbar);
        sanityWarningTextView = view.findViewById(R.id.evidence_sanitymeter_huntwarning);

        // SANITY COLLAPSIBLE
        sanityTrackingConstraintLayout = view.findViewById(R.id.constraintLayout_sanityTracking);

        if(toggleCollapseButton != null) {
            toggleCollapseButton.setOnClickListener(v -> {
                if(evidenceViewModel.isDrawerCollapsed) {
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
                                    toggleCollapseButton.setImageLevel(2);
                                    evidenceViewModel.isDrawerCollapsed = false;
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
                                    toggleCollapseButton.setImageLevel(1);
                                    evidenceViewModel.isDrawerCollapsed = true;
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
                    evidenceViewModel.getSanityData(),
                    sanityPercentTextView);
            sanitySeekBarView.resetProgress();
        }

        sanityMeterView.init(evidenceViewModel.getSanityData());

        popupWindow = new PopupWindow(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        ghostList.createPopupWindow(popupWindow);
        evidenceList.createPopupWindow(popupWindow);
        new Thread(() -> ghostList.createViews()).start();
        new Thread(() -> evidenceList.createViews()).start();

    }

    private void initCollapsible() {
        if(evidenceViewModel.isDrawerCollapsed) {
            sanityTrackingConstraintLayout.setVisibility(View.GONE);
            toggleCollapseButton.setImageLevel(1);
        } else {
            sanityTrackingConstraintLayout.setVisibility(View.VISIBLE);
            toggleCollapseButton.setImageLevel(2);
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

        if(evidenceList != null) {
            //evidenceList.forceResetEvidenceContainer();
        }

        if(ghostList != null) {
            ghostList.forceResetGhostContainer();
        }

        // SANITY
        if (sanitySeekBarView != null) {
            sanitySeekBarView.resetProgress();
        }

        if(evidenceViewModel != null &&
                evidenceViewModel.getPhaseTimerData() != null) {
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

    @Override
    protected void saveStates() {

    }

}
