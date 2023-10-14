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
/*
    @ColorInt int fontEmphasisColor = 0;*/

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

        /*

        // THEME
        if (getContext() != null) {
            Resources.Theme theme = getContext().getTheme();
            TypedValue typedValue = new TypedValue();
            theme.resolveAttribute(R.attr.bodyEmphasisFontColor, typedValue, true);
            fontEmphasisColor = typedValue.data;
        }
*/

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

    /*
    @SuppressLint("ResourceType")
    private void createEvidenceViews() {

        final EvidenceViewData[][] evidenceViewDatas = { null };
        evidenceViewDatas[0] = buildEvidenceViewData();
        if (evidenceViewDatas[0] == null) return;

        if(getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                buildEvidenceViews(evidenceViewDatas[0]);

                list_evidences.post(() -> haltProgressAnimation(evidenceProgressBar));
            });
        }

    }
    */

    private void haltProgressAnimation(ProgressBar progressBar) {
        progressBar.animate().alpha(0).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressBar.setVisibility(View.GONE);
                super.onAnimationEnd(animation);
            }
        }).start();
    }

    /*
    @SuppressLint("ResourceType")
    private EvidenceViewData[] buildEvidenceViewData() {

        if(getContext() == null) {return null; }

        TypedArray evidenceTypes =
                getContext().getResources().obtainTypedArray(R.array.equipment_tiers_arrays);

        EvidenceViewData[] evidenceViewDatas = new EvidenceViewData[evidenceTypes.length()];
        for(int i = 0; i < evidenceViewDatas.length; i++) {

            @IntegerRes int evidenceName;
            @IntegerRes int[] descriptions = new int[4];
            @IntegerRes int[] animations = new int[4];
            @IntegerRes int[] unlock_level = new int[3];
            @IntegerRes int evidenceCost;

            TypedArray evidenceType =
                    getContext().getResources().obtainTypedArray(evidenceTypes.getResourceId(i, 0));
            //Log.d("EvType", evidenceType.toString() + "\n" + evidenceType.getString(0));

            evidenceName = evidenceType.getResourceId(0, 0);
            evidenceCost = evidenceType.getResourceId(3, 0);

            @SuppressLint("ResourceType")
            TypedArray evidenceDescription =
                    getContext().getResources().obtainTypedArray(evidenceType.getResourceId(1, 0));
            for (int j = 0; j < evidenceDescription.length(); j++) {
                descriptions[j] = evidenceDescription.getResourceId(j, 0);
                //Log.d("EvDescription", getString(descriptions[j]) + "");
            }
            evidenceDescription.recycle(); //cleanup

            @SuppressLint("ResourceType")
            TypedArray evidenceAnimations =
                    getContext().getResources().obtainTypedArray(evidenceType.getResourceId(2, 0));
            for (int j = 0; j < evidenceAnimations.length(); j++) {
                animations[j] = evidenceAnimations.getResourceId(j, 0);
                //Log.d("EvDAnimation", animations[j] + "");
            }
            evidenceAnimations.recycle(); //cleanup

            @SuppressLint("ResourceType")
            TypedArray evidenceLevels =
                    getContext().getResources().obtainTypedArray(evidenceType.getResourceId(4, 0));
            for (int j = 0; j < evidenceLevels.length(); j++) {
                unlock_level[j] = evidenceLevels.getResourceId(j, 0);
                //Log.d("EvDALevels", unlock_level[j] + "");
            }
            evidenceLevels.recycle(); //cleanup
            evidenceType.recycle();

            EvidenceViewData evidenceViewData = new EvidenceViewData(
                    i,
                    evidenceName, evidenceCost, unlock_level,
                    descriptions, animations);
            evidenceViewDatas[i] = evidenceViewData;
        }
        evidenceTypes.recycle();

        return evidenceViewDatas;
    }
    */

    /*
    @SuppressLint("ClickableViewAccessibility")
    private void buildEvidenceViews(EvidencePopupData evidencePopupData) {
        if(getContext() == null) { return; }

        LayoutInflater inflater = LayoutInflater.from(getContext());
        for(int i = 0; i < evidencePopupData.getCount(); i++) {
            final int currGroup = i;

            evidencePopupData.getEvidencePopupRecordAt(i);

            EvidenceViewData evidenceViewData = evidencePopupData[i];

            View evidenceParent = inflater.inflate(
                    R.layout.item_investigation_evidence,
                    (ViewGroup) getView(),
                    false);
            ConstraintLayout mainLayout = evidenceParent.findViewById(R.id.layout_main);
            AppCompatTextView name = evidenceParent.findViewById(R.id.label_name);

            EvidenceRadioGroup radioGroup = evidenceParent.findViewById(R.id.radioGroup);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            mainLayout.setLayoutParams(params);

            name.setText(evidenceViewData.getName(getContext()));

            EvidenceNameGesture evidenceNameGesture =
                    new EvidenceNameGesture(getView(), evidenceViewData);
            GestureDetector nameDetector =
                    new GestureDetector(getContext(), evidenceNameGesture);
            name.setOnTouchListener((v, motionEvent) -> nameDetector.onTouchEvent(motionEvent));

            evidenceViewModel.getInvestigationData().getEvidences().get(currGroup)
                    .setRuling(InvestigationData.Evidence.Ruling.values()[
                            evidenceViewModel.getRadioButtonsChecked()[currGroup]]);

            for(int j = 0; j < radioGroup.getChildCount(); j++) {
                final int currRadio = j;

                EvidenceRadioButton evidenceRadioButton = radioGroup.getChildAt(j).findViewById(R.id.radioIcon);
                evidenceRadioButton.setImageLevel(currRadio + 1);
                int selectedRatio = evidenceViewModel.getRadioButtonsChecked()[currGroup];
                evidenceRadioButton.setState(currRadio == selectedRatio);

                evidenceViewModel.getInvestigationData().getEvidences().get(currGroup)
                        .setRuling(InvestigationData.Evidence.Ruling.values()[
                                evidenceViewModel.getRadioButtonsChecked()[currGroup]]);

                // ---
                EvidenceSelectGesture evidenceSelectGesture =
                        new EvidenceSelectGesture(list_ghosts, currGroup,
                                radioGroup, currRadio, evidenceRadioButton);
                GestureDetector selectDetector =
                        new GestureDetector(getContext(), evidenceSelectGesture);
                evidenceRadioButton.setOnTouchListener((v, motionEvent) ->
                        selectDetector.onTouchEvent(motionEvent));
            }

            evidenceParent.setVisibility(View.INVISIBLE);
            evidenceParent.setAlpha(0);
            list_evidences.addView(evidenceParent);

            evidenceParent.animate()
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            evidenceParent.setVisibility(View.VISIBLE);
                        }}
                    ).alpha(1).setStartDelay((long)(10f * currGroup)).setDuration(100);
        }
    }
    */

    /*
    private void selectEvidenceIcon(LinearLayout ghostContainer, int currGroup,
                                    ConstraintLayout radioGroup, int currRadio,
                                    AppCompatImageView icon) {

        for(int k = 0; k < radioGroup.getChildCount(); k++) {
            AppCompatImageView allIcon = radioGroup.getChildAt(k).findViewById(R.id.radioIcon);
            allIcon.setImageState(new int[] { -R.attr.state_selected }, true);
        }
        icon.setImageState(new int[] { (R.attr.state_selected) }, true);

        evidenceViewModel.setRadioButtonChecked(currGroup, currRadio);
        evidenceViewModel.getInvestigationData().getEvidences().get(currGroup)
                .setRuling(InvestigationData.Evidence.Ruling.values()[
                        evidenceViewModel.getRadioButtonsChecked()[currGroup]]);

        evidenceViewModel.getGhostOrderData().updateOrder();
        requestInvalidateGhostContainer(ghostContainer);

        ScrollView parentScroller = ((ScrollView) ghostContainer.getParent());
        if(parentScroller != null) {
            parentScroller.smoothScrollTo(0, 0);
        }
    }
    */

    /*
    private void showEvidencePopup(EvidencePopupData.EvidencePopupRecord popupRecord) {
        if(getContext() == null || getView() == null || getView().getContext() == null) {
            return;
        }

        if (popupWindow != null) {
            popupWindow.dismiss();
        }

        LayoutInflater inflaterPopup =
                (LayoutInflater) getView().getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);

        View customView = inflaterPopup.inflate(
                R.layout.popup_info_evidence,
                (ViewGroup) view,
                false);

        AppCompatImageButton closeButton = customView.findViewById(R.id.popup_close_button);
        AppCompatTextView label = customView.findViewById(R.id.label_name);
        AppCompatTextView info = customView.findViewById(R.id.label_info);

        AppCompatImageView select_overview = customView.findViewById(R.id.textView_overview_image);
        AppCompatImageView select_tiers = customView.findViewById(R.id.textView_tiers_image);

        AppCompatImageView select_tier_1 = customView.findViewById(R.id.textView_tiers_1_image);
        AppCompatImageView select_tier_2 = customView.findViewById(R.id.textView_tiers_2_image);
        AppCompatImageView select_tier_3 = customView.findViewById(R.id.textView_tiers_3_image);

        ScrollView scroller = customView.findViewById(R.id.scrollView);
        View indicator = customView.findViewById(R.id.scrollview_indicator);
        GifImageView animation = customView.findViewById(R.id.animation_evidence);
        GifImageView animation_fullscreen = customView.findViewById(R.id.animation_evidence_fullscreen);

        ConstraintLayout layout_overview = customView.findViewById(R.id.constraintLayout_evidence_overview);
        ConstraintLayout layout_tiers = customView.findViewById(R.id.constraintLayout_evidence_tiers);
        AppCompatTextView label_cost = customView.findViewById(R.id.label_cost);

        // Init
        label_cost.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                getString(R.string.evidence_requirement_cost_title) + " $" + evidenceViewData.getCost(getContext()),
                "#ff6161", fontEmphasisColor + "")));
        //MAIN STATES
        select_overview.setImageState(new int[]{R.attr.state_done}, true);
        select_overview.setOnClickListener(selectView -> {
            select_overview.setImageState(new int[]{R.attr.state_done}, true);
            select_tiers.setImageState(new int[]{-R.attr.state_done}, true);

            layout_overview.setVisibility(View.VISIBLE);
            layout_tiers.setVisibility(View.GONE);

            animation_fullscreen.setImageResource(evidenceViewData.getAnimation(getContext(), 0));
        });
        select_tiers.setOnClickListener(selectView -> {
            select_tiers.setImageState(new int[]{R.attr.state_done}, true);
            select_overview.setImageState(new int[]{-R.attr.state_done}, true);

            layout_tiers.setVisibility(View.VISIBLE);
            layout_overview.setVisibility(View.GONE);

            select_tier_1.setImageState(new int[]{R.attr.state_done}, true);
            select_tier_2.setImageState(new int[]{-R.attr.state_done}, true);
            select_tier_3.setImageState(new int[]{-R.attr.state_done}, true);
            generateEvidenceTierView(customView, 1, animation_fullscreen,
                    evidenceViewData.getDescription(getContext(), 1),
                    evidenceViewData.getAnimation(getContext(), 1),
                    evidenceViewData.getUnlockLevel(getContext(), 0));
        });

        //TIER STATES
        select_tier_1.setImageState(new int[]{R.attr.state_done}, true);
        select_tier_1.setOnClickListener(selectView -> {
            select_tier_1.setImageState(new int[]{R.attr.state_done}, true);
            select_tier_2.setImageState(new int[]{-R.attr.state_done}, true);
            select_tier_3.setImageState(new int[]{-R.attr.state_done}, true);

            generateEvidenceTierView(customView, 1, animation_fullscreen,
                    evidenceViewData.getDescription(getContext(), 1),
                    evidenceViewData.getAnimation(getContext(), 1),
                    evidenceViewData.getUnlockLevel(getContext(), 0));
        });
        select_tier_2.setOnClickListener(selectView -> {
            select_tier_2.setImageState(new int[]{R.attr.state_done}, true);
            select_tier_1.setImageState(new int[]{-R.attr.state_done}, true);
            select_tier_3.setImageState(new int[]{-R.attr.state_done}, true);

            generateEvidenceTierView(customView, 2, animation_fullscreen,
                    evidenceViewData.getDescription(getContext(), 2),
                    evidenceViewData.getAnimation(getContext(), 2),
                    evidenceViewData.getUnlockLevel(getContext(), 1));
        });
        select_tier_3.setOnClickListener(selectView -> {
            select_tier_3.setImageState(new int[]{R.attr.state_done}, true);
            select_tier_1.setImageState(new int[]{-R.attr.state_done}, true);
            select_tier_2.setImageState(new int[]{-R.attr.state_done}, true);

            generateEvidenceTierView(customView, 3, animation_fullscreen,
                    evidenceViewData.getDescription(getContext(), 3),
                    evidenceViewData.getAnimation(getContext(), 3),
                    evidenceViewData.getUnlockLevel(getContext(), 2));
        });


        animation.setOnClickListener(view12 -> {
            if(animation_fullscreen.getVisibility() != View.VISIBLE) {
                animation_fullscreen.setVisibility(View.VISIBLE);
            }
        });
        animation_fullscreen.setOnClickListener(view1 -> {
            if(view1.getVisibility() == View.VISIBLE) {
                view1.setVisibility(View.GONE);
            }
        });

        fadeOutIndicatorAnimation(
                null,
                null,
                scroller,
                indicator);

        closeButton.setOnClickListener(v1 -> popupWindow.dismiss());

        label.setText(evidenceViewData.getName(getContext()));
        info.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                evidenceViewData.getDescription(getContext(), 0),
                "#ff6161", fontEmphasisColor + "")));

        TypedArray typedArray;
        try {
            typedArray = view.getContext().getResources().
                    obtainTypedArray(R.array.equipment_animation_array);
            animation.setImageResource(evidenceViewData.getAnimation(getContext(), 0));
            animation_fullscreen.setImageResource(typedArray.getResourceId(evidenceViewData.index(), 0));
            typedArray.recycle();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        popupWindow = new PopupWindow(
                customView,
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        popupWindow.setAnimationStyle(androidx.navigation.ui.R.anim.nav_default_enter_anim);
        popupWindow.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, 0);

        if (getActivity() != null) {
            MobileAds.initialize(getActivity(), initializationStatus -> {
            });
            AdView mAdView = customView.findViewById(R.id.adView);
            adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }*/

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

    /*

    public void generateEvidenceTierView(View parentView, int tierIndex, GifImageView animation_fullscreen, String description, @DrawableRes int animation, String level) {

        ConstraintLayout scrollView = parentView.findViewById(R.id.scrollview_tiers);
        AppCompatTextView title = parentView.findViewById(R.id.label_tier);
        GifImageView animationView = parentView.findViewById(R.id.animation_tier);
        AppCompatTextView details = scrollView.findViewById(R.id.info_tier);
        AppCompatTextView levelView = parentView.findViewById(R.id.label_level);


        details.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                description,
                "#ff6161", fontEmphasisColor + "")));
        levelView.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                getString(R.string.evidence_requirement_level_title) + " " + level,
                "#ff6161", fontEmphasisColor + "")));

        animationView.setImageResource(animation);
        animation_fullscreen.setImageResource(animation);

        if(getContext() != null) {
            TypedArray typedArray =
                    getContext().getResources().obtainTypedArray(R.array.equipment_tiers);
            title.setText(typedArray.getString(tierIndex-1));
            typedArray.recycle();
        }


        animationView.setOnClickListener(v -> {
            if(animation_fullscreen.getVisibility() != View.VISIBLE) {
                animation_fullscreen.setVisibility(View.VISIBLE);
            }
        });
        animation_fullscreen.setOnClickListener(v -> {
            if(v.getVisibility() == View.VISIBLE) {
                v.setVisibility(View.GONE);
            }
        });

    }
*/

    @SuppressLint("ClickableViewAccessibility")
    private void buildGhostViews() {

        if(getContext() == null) { return; }

        int[] newGhostOrder = evidenceViewModel.getGhostOrderData().getCurrOrder();

        list_ghosts.setWeightSum(newGhostOrder.length);

        for (int j : newGhostOrder) {

            GhostView ghostView = new GhostView(getContext()) {

                @Override
                public void createGhostDetailPopup() {

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
                public void createEvidenceDetailPopup() {

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

            /*
            View evidenceParent = inflater.inflate(
                    R.layout.item_investigation_evidence,
                    (ViewGroup) getView(),
                    false);
            ConstraintLayout mainLayout = evidenceParent.findViewById(R.id.layout_main);
            AppCompatTextView name = evidenceParent.findViewById(R.id.label_name);

            EvidenceRadioGroup radioGroup = evidenceParent.findViewById(R.id.radioGroup);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            mainLayout.setLayoutParams(params);

            name.setText(popupRecord.getName(getContext()));

            EvidenceNameGesture evidenceNameGesture =
                    new EvidenceNameGesture(getView(), popupRecord);
            GestureDetector nameDetector =
                    new GestureDetector(getContext(), evidenceNameGesture);
            name.setOnTouchListener((v, motionEvent) -> nameDetector.onTouchEvent(motionEvent));

            evidenceViewModel.getInvestigationData().getEvidences().get(currGroup)
                    .setRuling(InvestigationData.Evidence.Ruling.values()[
                            evidenceViewModel.getRadioButtonsChecked()[currGroup]]);

            for(int j = 0; j < radioGroup.getChildCount(); j++) {
                final int currRadio = j;

                EvidenceRadioButton evidenceRadioButton = radioGroup.getChildAt(j).findViewById(R.id.radioIcon);
                evidenceRadioButton.setImageLevel(currRadio + 1);
                int selectedRatio = evidenceViewModel.getRadioButtonsChecked()[currGroup];
                evidenceRadioButton.setState(currRadio == selectedRatio);

                evidenceViewModel.getInvestigationData().getEvidences().get(currGroup)
                        .setRuling(InvestigationData.Evidence.Ruling.values()[
                                evidenceViewModel.getRadioButtonsChecked()[currGroup]]);

                // ---
                EvidenceSelectGesture evidenceSelectGesture =
                        new EvidenceSelectGesture(list_ghosts, currGroup,
                                radioGroup, currRadio, evidenceRadioButton);
                GestureDetector selectDetector =
                        new GestureDetector(getContext(), evidenceSelectGesture);
                evidenceRadioButton.setOnTouchListener((v, motionEvent) ->
                        selectDetector.onTouchEvent(motionEvent));
            }

            evidenceParent.setVisibility(View.INVISIBLE);
            evidenceParent.setAlpha(0);
            list_evidences.addView(evidenceParent);

            evidenceParent.animate()
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            evidenceParent.setVisibility(View.VISIBLE);
                        }}
                    ).alpha(1).setStartDelay((long)(10f * currGroup)).setDuration(100);*/
        }

    }

    /*
    public void fadeOutIndicatorAnimation(ConstraintLayout bodyCons, ConstraintLayout container, ScrollView scroller, View indicator) {
        scroller.post(() -> {
            if (!scroller.canScrollVertically(1)) {
                indicator.setVisibility(View.INVISIBLE);
                indicatorFadeAnimation(indicator, 0);
            } else {
                if(container != null) {
                    if(container.getLayoutParams() instanceof ConstraintLayout.LayoutParams lParams) {

                        Log.d("Scroller", "Should constrain");
                        lParams.constrainedHeight = true;
                        container.setLayoutParams(lParams);
                        container.invalidate();

                        if (!scroller.canScrollVertically(1)) {
                            indicator.setVisibility(View.INVISIBLE);

                            indicatorFadeAnimation(indicator, 0);
                        } else {
                            indicator.setVisibility(View.VISIBLE);
                            indicator.setAlpha(1f);
                        }
                    }
                }
            }

            if(bodyCons != null) {
                //initialize info content scroller
                bodyCons.setVisibility(View.VISIBLE);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scroller.setOnScrollChangeListener((v13, scrollX, scrollY, oldScrollX,
                                                 oldScrollY) -> {
                if (!scroller.canScrollVertically(1)) {
                    indicatorFadeAnimation(indicator, getResources().getInteger(
                            android.R.integer.config_longAnimTime));
                }
            });
        } else {
            scroller.setOnDragListener((v12, event) -> {
                if (!scroller.canScrollVertically(1)) {
                    indicatorFadeAnimation(indicator, getResources().getInteger(
                            android.R.integer.config_longAnimTime));
                }
                return true;
            });
        }
    }

    private void indicatorFadeAnimation(View indicator, int time) {

        indicator.animate()
                .alpha(0f)
                .setDuration(time)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        indicator.setVisibility(View.INVISIBLE);
                    }
                });
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


        if(list_evidences != null) {
            forceResetEvidenceContainer();
            list_evidences.invalidate();
        }

        if(list_ghosts != null) {
            forceResetGhostContainer();
            //ghostContainer.invalidate();
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
