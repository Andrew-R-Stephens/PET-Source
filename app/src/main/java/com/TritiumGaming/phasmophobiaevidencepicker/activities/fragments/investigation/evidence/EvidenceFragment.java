package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.ColorInt;
import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.DifficultyCarouselData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.MapCarouselData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.PhaseTimerData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views.DifficultyCarouselView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views.PhaseTimerView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views.SanitySeekBarView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views.WarnTextView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.InvestigationData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.SanityData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.SanityMeterView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FontUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MapMenuViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

/**
 * EvidenceFragment class
 *
 * @author TritiumGamingStudios
 */
public class EvidenceFragment extends Fragment {

    private FirebaseAnalytics analytics;

    protected EvidenceViewModel evidenceViewModel;
    protected MapMenuViewModel mapMenuViewModel;
    protected GlobalPreferencesViewModel globalPreferencesViewModel;

    protected PopupWindow popup;

    private AdRequest adRequest;

    protected SanityData sanityData;
    protected PhaseTimerData phaseTimerData;
    protected DifficultyCarouselData difficultyCarouselData;
    protected MapCarouselData mapCarouselData;

    protected LinearLayout ghostContainer, evidenceContainer;

    protected ConstraintLayout sanityTrackingConstraintLayout;

    protected AppCompatImageView collapseButton;
    protected AppCompatImageView expandButton;
    protected AppCompatTextView phaseTimerTextView;
    protected AppCompatTextView sanityPercentTextView;

    protected CompositeListener compositeListenerPrev, compositeListenerNext;

    protected DifficultyCarouselView difficultyCarouselView;
    protected PhaseTimerView phaseTimerCountdownView;
    protected SanitySeekBarView sanitySeekBarView;
    protected SanityMeterView sanityMeterView;
    protected WarnTextView sanityWarningTextView;

    protected Drawable icon_circle;
    protected Drawable[] icons_strikethrough;
    private String[] titles;

    //protected int[] font_sanitySize;
    @ColorInt
    int fontEmphasisColor = 0;

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

    //@SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        initFirebase();

        if (evidenceViewModel == null) {
            evidenceViewModel =
                    new ViewModelProvider(requireActivity()).get(EvidenceViewModel.class);
            evidenceViewModel.init(getContext());
        }

        if (mapMenuViewModel == null) {
            mapMenuViewModel =
                    new ViewModelProvider(requireActivity()).get(MapMenuViewModel.class);
            mapMenuViewModel.init(getContext());
        }

        if (globalPreferencesViewModel == null) {
            globalPreferencesViewModel =
                    new ViewModelProvider(requireActivity()).get(GlobalPreferencesViewModel.class);
            if (getContext() != null) {
                globalPreferencesViewModel.init(getContext());
            }
        }

        if(evidenceViewModel != null) {
            sanityData = evidenceViewModel.getSanityData();
            phaseTimerData = evidenceViewModel.getPhaseTimerData();
            difficultyCarouselData = evidenceViewModel.getDifficultyCarouselData();
        }

        if(sanityData != null) {
            sanityData.setFlashTimeoutMax(globalPreferencesViewModel.getHuntWarningFlashTimeout());
        }

        // THEME
        if (getContext() != null) {
            Resources.Theme theme = getContext().getTheme();
            TypedValue typedValue = new TypedValue();
            theme.resolveAttribute(R.attr.bodyEmphasisFontColor, typedValue, true);
            fontEmphasisColor = typedValue.data;
        }

        titles = new String[] {
            getResources().getString(R.string.evidence_popup_ghost_info),
                    getResources().getString(R.string.evidence_popup_ghost_strength),
                    getResources().getString(R.string.evidence_popup_ghost_weakness)
        };

        // GHOST / EVIDENCE CONTAINERS
        AppCompatTextView header_ghostLabel, header_evidenceLabel;
        if(!globalPreferencesViewModel.getIsLeftHandSupportEnabled()) {
            header_ghostLabel = view.findViewById(R.id.textLabel_headerLeft);
            header_evidenceLabel = view.findViewById(R.id.textLabel_headerRight);
            ghostContainer = view.findViewById(R.id.layout_leftList);
            evidenceContainer = view.findViewById(R.id.layout_rightList);
        } else {
            header_ghostLabel = view.findViewById(R.id.textLabel_headerRight);
            header_evidenceLabel = view.findViewById(R.id.textLabel_headerLeft);
            ghostContainer = view.findViewById(R.id.layout_rightList);
            evidenceContainer = view.findViewById(R.id.layout_leftList);
        }

        collapseButton = view.findViewById(R.id.button_collapsesanity);
        expandButton = view.findViewById(R.id.button_raisesanity);

        // SANITY CONSTRAINTS
        //ConstraintLayout constraint_sanityContainer = view.findViewById(R.id.constraint_sanityContainer);

        // TIMER VIEW
        phaseTimerTextView = view.findViewById(R.id.evidence_timer_text);

        // SANITY METER VIEWS
        sanityPercentTextView = view.findViewById(R.id.evidence_sanitymeter_percentage);
        sanityMeterView = view.findViewById(R.id.evidence_sanitymeter_progressbar);
        sanitySeekBarView = view.findViewById(R.id.evidence_sanitymeter_seekbar);
        sanityWarningTextView = view.findViewById(R.id.evidence_sanitymeter_huntwarning);

        // SANITY COLLAPSIBLE
        sanityTrackingConstraintLayout = view.findViewById(R.id.constraintLayout_sanityTracking);

        /*
        AppCompatTextView label_difficultyTitle = view.findViewById(R.id.difficulty_title);
        AppCompatTextView label_setupTitle = view.findViewById(R.id.setup_title);
        AppCompatTextView label_mapChoiceTitle = view.findViewById(R.id.mapchoice_title);
        */

        /*
        int[] font_sanitySize;
        float sanity_smallestFont = FontUtils.dpToSp(view, label_difficultyTitle.getTextSize());
        sanity_smallestFont = Math.min(sanity_smallestFont, FontUtils.dpToSp(view, label_setupTitle.getTextSize()));
        sanity_smallestFont = Math.min(sanity_smallestFont, FontUtils.dpToSp(view, label_mapChoiceTitle.getTextSize()));
        font_sanitySize = new int[]{(int)sanity_smallestFont};

        label_difficultyTitle.setAutoSizeTextTypeUniformWithPresetSizes(font_sanitySize, AUTO_SIZE_TEXT_TYPE_UNIFORM);
        label_setupTitle.setAutoSizeTextTypeUniformWithPresetSizes(font_sanitySize, AUTO_SIZE_TEXT_TYPE_UNIFORM);
        label_mapChoiceTitle.setAutoSizeTextTypeUniformWithPresetSizes(font_sanitySize, AUTO_SIZE_TEXT_TYPE_UNIFORM);
        */


        // DRAWABLES
        icons_strikethrough = new Drawable[]{
                ResourcesCompat.getDrawable(getResources(), R.drawable.icon_strikethrough_1, getContext().getTheme()),
                ResourcesCompat.getDrawable(getResources(), R.drawable.icon_strikethrough_2, getContext().getTheme()),
                ResourcesCompat.getDrawable(getResources(), R.drawable.icon_strikethrough_3, getContext().getTheme()),
                ResourcesCompat.getDrawable(getResources(), R.drawable.icon_strikethrough_forced, getContext().getTheme()),
        };
        icon_circle = ResourcesCompat.getDrawable(getResources(), R.drawable.icon_circle, getContext().getTheme());

        // LISTENERS
        initNavListeners(
                view.findViewById(R.id.listener_goto_left),
                null, //view.findViewById(R.id.icon_goto_medLeft),
                view.findViewById(R.id.listener_resetAll),
                null,
                view.findViewById(R.id.listener_goto_right),
                view.findViewById(R.id.icon_goto_left),
                null,
                view.findViewById(R.id.icon_resetAll),
                null,
                view.findViewById(R.id.icon_goto_right));

        if(collapseButton != null) {
            collapseButton.setOnClickListener(v -> {
                if(!evidenceViewModel.isCollapsed()) {
                    sanityTrackingConstraintLayout.setVisibility(View.GONE);
                    expandButton.setVisibility(View.VISIBLE);
                } else {
                    expandButton.setVisibility(View.GONE);
                    sanityTrackingConstraintLayout.setVisibility(View.VISIBLE);
                }
                evidenceViewModel.setCollapsed(true);
            });
            expandButton.setOnClickListener(v -> {
                if(evidenceViewModel.isCollapsed()) {
                    expandButton.setVisibility(View.GONE);
                    sanityTrackingConstraintLayout.setVisibility(View.VISIBLE);
                }
                evidenceViewModel.setCollapsed(false);
            });

            initCollapsible();
        }

        sanitySeekBarView.init(
                sanityData,
                sanityPercentTextView);

       /* View.OnClickListener difficultyListener = v -> {
            createEvidenceViews(v, evidenceContainer, ghostContainer);
            createGhostViews(v, ghostContainer);
        };
        compositeListenerPrev = new CompositeListener();
        compositeListenerNext = new CompositeListener();
        compositeListenerPrev.registerListener(difficultyListener);
        compositeListenerNext.registerListener(difficultyListener);
        difficultyCarouselView = new DifficultyCarouselView();
        difficultyCarouselView.registerListener(compositeListenerPrev, compositeListenerNext);*/

        // COLORS
        @ColorInt int color_strikethrough = Color.WHITE, color_circle = Color.WHITE;
        TypedValue typedValue = new TypedValue();
        if (getContext() != null && getContext().getTheme() != null) {
            Resources.Theme theme = getContext().getTheme();
            theme.resolveAttribute(R.attr.strikethroughColor, typedValue, true);
            color_strikethrough = typedValue.data;
            theme.resolveAttribute(R.attr.circleColor, typedValue, true);
            color_circle = typedValue.data;
            theme.resolveAttribute(R.attr.negativeSelColor, typedValue, true);
        }

        // DRAWABLE TINT
        for (Drawable d : icons_strikethrough) {
            d.setTint(color_strikethrough);
        }
        icon_circle.setTint(color_circle);

        // SANITY
        if (sanitySeekBarView != null) {
            sanitySeekBarView.resetProgress();
        }

        sanityMeterView.init(sanityData);
        if (sanityData != null) {
            sanityPercentTextView.setText(String.format("%1$-4s", sanityData.toPercentString()));
        }

        header_ghostLabel.setText(R.string.evidence_ghosts_title);
        header_evidenceLabel.setText(R.string.evidence_evidence_title);

        createEvidenceViews(view, evidenceContainer, ghostContainer);
        createGhostViews(view, ghostContainer);

    }

    private void initCollapsible() {
        if(!evidenceViewModel.isCollapsed()) {
            sanityTrackingConstraintLayout.setVisibility(View.VISIBLE);
            expandButton.setVisibility(View.GONE);
        } else {
            sanityTrackingConstraintLayout.setVisibility(View.GONE);
            expandButton.setVisibility(View.VISIBLE);
        }
    }

    protected void recreateGhostView() {
        evidenceViewModel.updateGhostOrder();
        createGhostViews(getView(), ghostContainer);
    }

    private void initNavListeners(View lstnr_navLeft,
                                  View lstnr_navMedLeft,
                                  View lstnr_navCenter,
                                  View lstnr_navMedRight,
                                  View lstnr_navRight,
                                  AppCompatImageView icon_navLeft,
                                  AppCompatImageView icon_navMedLeft,
                                  AppCompatImageView icon_navCenter,
                                  AppCompatImageView icon_navMedRight,
                                  AppCompatImageView icon_navRight) {

        if(lstnr_navLeft != null) {
            ((View)lstnr_navLeft.getParent()).setVisibility(View.VISIBLE);
            icon_navLeft.setImageResource(R.drawable.icon_tasks);
            lstnr_navLeft.setOnClickListener(v -> {
                        if (evidenceViewModel != null && evidenceViewModel.hasSanityData()) {
                            evidenceViewModel.getSanityData().setFlashTimeoutStart(-1);
                        }
                        Navigation.findNavController(v)
                                .navigate(R.id.action_evidence_to_objectives);
                    }
            );
        }

        if(lstnr_navMedLeft != null) {
            ((View)lstnr_navMedLeft.getParent()).setVisibility(View.VISIBLE);
            ((View)icon_navMedLeft.getParent()).setVisibility(View.VISIBLE);
            icon_navMedLeft.setImageResource(R.drawable.icon_tools);
            lstnr_navMedLeft.setOnClickListener(v -> {
                        if(evidenceViewModel != null && evidenceViewModel.hasSanityData()) {
                            evidenceViewModel.getSanityData().setFlashTimeoutStart(-1);
                        }
                        Navigation.findNavController(v)
                                .navigate(R.id.action_evidenceFragment_to_utilitiesFragment);
                    }
            );
        }

        if(lstnr_navCenter != null) {
            ((View)lstnr_navCenter.getParent()).setVisibility(View.VISIBLE);
            icon_navCenter.setImageResource(R.drawable.icon_reset);
            lstnr_navCenter.setOnClickListener(v -> softReset()
            );
        }

        if(lstnr_navMedRight != null) {
            lstnr_navMedRight.setVisibility(View.VISIBLE);
            lstnr_navMedRight.setOnClickListener(v -> {
                        if(sanityTrackingConstraintLayout.getVisibility() == View.VISIBLE) {
                            sanityTrackingConstraintLayout.setVisibility(View.GONE);
                        } else if(sanityTrackingConstraintLayout.getVisibility() == View.GONE) {
                            sanityTrackingConstraintLayout.setVisibility(View.VISIBLE);
                        }
                    }
            );
        }

        if(lstnr_navRight != null) {
            ((View)lstnr_navRight.getParent()).setVisibility(View.VISIBLE);
            icon_navRight.setImageResource(R.drawable.icon_map);
            lstnr_navRight.setOnClickListener(v -> {
                        if (evidenceViewModel != null && evidenceViewModel.hasSanityData()) {
                            evidenceViewModel.getSanityData().setFlashTimeoutStart(-1);
                        }
                        Navigation.findNavController(v)
                                .navigate(R.id.action_evidence_to_mapmenu);
                    }
            );
        }

    }

    private void createEvidenceViews(View view, LinearLayout evidenceContainer,
                                     LinearLayout ghostContainer) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        String[] evidenceNames = getResources().getStringArray(R.array.evidence_tool_names);

        //Avoid pass null in the root it ignores spaces in the child layout
        TypedArray evidenceTypes =
                getContext().getResources().obtainTypedArray(R.array.evidence_tiers_arrays);
        for(int i = 0; i < InvestigationData.getEvidenceCount(); i++) {

            final int evidenceIndex = i;
            String evidenceName = evidenceNames[i];
            @IntegerRes int[] descriptions = new int[4];
            @IntegerRes int[] animations = new int[4];

            TypedArray evidenceType =
                    getContext().getResources().obtainTypedArray(evidenceTypes.getResourceId(i, 0));
            Log.d("EvType", evidenceType.toString() + "\n" + evidenceType.getString(0));
/*

            TypedArray evidenceTier =
                    getContext().getResources().obtainTypedArray(evidenceType.getResourceId(0, 0));
            Log.d("EvTier", evidenceTier.toString() + "");
*/


            @SuppressLint("ResourceType")
            TypedArray evidenceDescription =
                    getContext().getResources().obtainTypedArray(evidenceType.getResourceId(1, 0));
            for (int j = 0; j < evidenceDescription.length(); j++) {
                descriptions[j] = evidenceDescription.getResourceId(j, 0);
                Log.d("EvDescription", getString(descriptions[j]) + "");
            }

            @SuppressLint("ResourceType")
            TypedArray evidenceAnimations =
                    getContext().getResources().obtainTypedArray(evidenceType.getResourceId(2, 0));
            for (int j = 0; j < evidenceAnimations.length(); j++) {
                animations[j] = evidenceAnimations.getResourceId(j, 0);
                Log.d("EvDAnimation", animations[j] + "");
            }

            evidenceDescription.recycle(); //cleanup
            evidenceAnimations.recycle(); //cleanup

            //evidenceTier.recycle();
            evidenceType.recycle();

            //String evidenceInfo = ""; //evidenceTypedArray2.

            View evidenceParent = inflater.inflate(
                    R.layout.item_investigation_evidence,
                    (ViewGroup) view,
                    false);
            ConstraintLayout mainLayout = evidenceParent.findViewById(R.id.layout_main);
            AppCompatTextView name = evidenceParent.findViewById(R.id.label_name);

            View radioGroup = evidenceParent.findViewById(R.id.radioGroup);

            View radioButton1 = radioGroup.findViewById(R.id.radio1);
            View radioButton2 = radioGroup.findViewById(R.id.radio2);
            View radioButton3 = radioGroup.findViewById(R.id.radio3);

            AppCompatImageView icon1 = radioButton1.findViewById(R.id.radioIcon);
            AppCompatImageView icon2 = radioButton2.findViewById(R.id.radioIcon);
            AppCompatImageView icon3 = radioButton3.findViewById(R.id.radioIcon);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            mainLayout.setLayoutParams(params);

            name.setText(evidenceName);
            name.setOnClickListener(v -> {

                if(getView() == null || getView().getContext() == null) {
                    return;
                }

                if (popup != null) {
                    popup.dismiss();
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

                // Init

                //MAIN STATES
                select_overview.setImageState(new int[]{R.attr.state_done}, true);
                select_overview.setOnClickListener(selectView -> {
                    select_overview.setImageState(new int[]{R.attr.state_done}, true);
                    select_tiers.setImageState(new int[]{-R.attr.state_done}, true);

                    layout_overview.setVisibility(View.VISIBLE);
                    layout_tiers.setVisibility(View.GONE);

                    animation_fullscreen.setImageResource(animations[0]);
                });
                select_tiers.setOnClickListener(selectView -> {
                    select_tiers.setImageState(new int[]{R.attr.state_done}, true);
                    select_overview.setImageState(new int[]{-R.attr.state_done}, true);

                    layout_tiers.setVisibility(View.VISIBLE);
                    layout_overview.setVisibility(View.GONE);

                    select_tier_1.setImageState(new int[]{R.attr.state_done}, true);
                    select_tier_2.setImageState(new int[]{-R.attr.state_done}, true);
                    select_tier_3.setImageState(new int[]{-R.attr.state_done}, true);
                    generateEvidenceTierView(customView, 1, animation_fullscreen, getString(descriptions[1]), animations[1]);
                });


                //TIER STATES
                select_tier_1.setImageState(new int[]{R.attr.state_done}, true);
                select_tier_1.setOnClickListener(selectView -> {
                    select_tier_1.setImageState(new int[]{R.attr.state_done}, true);
                    select_tier_2.setImageState(new int[]{-R.attr.state_done}, true);
                    select_tier_3.setImageState(new int[]{-R.attr.state_done}, true);

                    generateEvidenceTierView(customView, 1, animation_fullscreen, getString(descriptions[1]), animations[1]);
                });
                select_tier_2.setOnClickListener(selectView -> {
                    select_tier_2.setImageState(new int[]{R.attr.state_done}, true);
                    select_tier_1.setImageState(new int[]{-R.attr.state_done}, true);
                    select_tier_3.setImageState(new int[]{-R.attr.state_done}, true);

                    generateEvidenceTierView(customView, 2, animation_fullscreen, getString(descriptions[2]), animations[2]);
                });
                select_tier_3.setOnClickListener(selectView -> {
                    select_tier_3.setImageState(new int[]{R.attr.state_done}, true);
                    select_tier_1.setImageState(new int[]{-R.attr.state_done}, true);
                    select_tier_2.setImageState(new int[]{-R.attr.state_done}, true);

                    generateEvidenceTierView(customView, 3, animation_fullscreen, getString(descriptions[3]), animations[3]);
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

                closeButton.setOnClickListener(v1 -> popup.dismiss());

                label.setText(evidenceName);
                info.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                        getString(descriptions[0]),
                        "#ff6161", fontEmphasisColor + "")));

                TypedArray typedArray;
                try {
                    typedArray = view.getContext().getResources().obtainTypedArray(R.array.evidence_animation_array);
                    animation.setImageResource(animations[0]);
                    animation_fullscreen.setImageResource(typedArray.getResourceId(evidenceIndex, 0));
                    typedArray.recycle();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }

                popup = new PopupWindow(
                        customView,
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );
                popup.setAnimationStyle(R.anim.nav_default_enter_anim);
                popup.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, 0);

                if (getActivity() != null) {
                    MobileAds.initialize(getActivity(), initializationStatus -> {
                    });
                    AdView mAdView = customView.findViewById(R.id.adView);
                    adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                }
            });

            TypedValue typedValue = new TypedValue();
            if(getContext() == null) {
                return;
            }
            Resources.Theme theme = getContext().getTheme();

            theme.resolveAttribute(R.attr.positiveSelColor, typedValue, true);
            int positiveSelColor = typedValue.data;
            theme.resolveAttribute(R.attr.neutralSelColor, typedValue, true);
            int neutralSelColor = typedValue.data;
            theme.resolveAttribute(R.attr.negativeSelColor, typedValue, true);
            int negativeSelColor = typedValue.data;

            if(evidenceViewModel.getRadioButtonsChecked()[i] == 0) {

                icon1.setImageResource(R.drawable.icon_negative_selected);
                icon2.setImageResource(R.drawable.icon_inconclusive_unselected);
                icon3.setImageResource(R.drawable.icon_positive_unselected);
                icon1.setColorFilter(negativeSelColor);
                icon2.setColorFilter(neutralSelColor);
                icon3.setColorFilter(neutralSelColor);

                evidenceViewModel.getInvestigationData().getEvidences().get(i)
                        .setRuling(InvestigationData.Evidence.Ruling.NEGATIVE);

            } else if(evidenceViewModel.getRadioButtonsChecked()[i] == 1) {

                icon1.setImageResource(R.drawable.icon_negative_unselected);
                icon2.setImageResource(R.drawable.icon_inconclusive_selected);
                icon3.setImageResource(R.drawable.icon_positive_unselected);
                icon1.setColorFilter(neutralSelColor);
                icon2.setColorFilter(neutralSelColor);
                icon3.setColorFilter(neutralSelColor);

                evidenceViewModel.getInvestigationData().getEvidences().get(i)
                        .setRuling(InvestigationData.Evidence.Ruling.NEUTRAL);

            } else if(evidenceViewModel.getRadioButtonsChecked()[i] == 2) {

                icon1.setImageResource(R.drawable.icon_negative_unselected);
                icon2.setImageResource(R.drawable.icon_inconclusive_unselected);
                icon3.setImageResource(R.drawable.icon_positive_selected);
                icon1.setColorFilter(neutralSelColor);
                icon2.setColorFilter(neutralSelColor);
                icon3.setColorFilter(positiveSelColor);

                evidenceViewModel.getInvestigationData().getEvidences().get(i)
                        .setRuling(InvestigationData.Evidence.Ruling.POSITIVE);

            }

            int index = i;

            icon1.setOnClickListener(v -> {

                if(getContext() == null) {
                    return;
                }

                icon1.setImageResource(R.drawable.icon_negative_selected);
                icon2.setImageResource(R.drawable.icon_inconclusive_unselected);
                icon3.setImageResource(R.drawable.icon_positive_unselected);

                icon1.setColorFilter(negativeSelColor);
                icon2.setColorFilter(neutralSelColor);
                icon3.setColorFilter(neutralSelColor);

                evidenceViewModel.getInvestigationData().getEvidences().get(index)
                        .setRuling(InvestigationData.Evidence.Ruling.NEGATIVE);

                evidenceViewModel.setRadioButtonChecked(index, 0);
                evidenceViewModel.updateGhostOrder();

                createGhostViews(view, ghostContainer);
            });

            icon2.setOnClickListener(v -> {

                if(getContext() == null) {
                    return;
                }

                icon1.setImageResource(R.drawable.icon_negative_unselected);
                icon2.setImageResource(R.drawable.icon_inconclusive_selected);
                icon3.setImageResource(R.drawable.icon_positive_unselected);

                icon1.setColorFilter(neutralSelColor);
                icon2.setColorFilter(neutralSelColor);
                icon3.setColorFilter(neutralSelColor);

                evidenceViewModel.getInvestigationData().getEvidences().get(index)
                        .setRuling(InvestigationData.Evidence.Ruling.NEUTRAL);

                evidenceViewModel.setRadioButtonChecked(index, 1);
                evidenceViewModel.updateGhostOrder();

                createGhostViews(view, ghostContainer);
            });

            icon3.setOnClickListener(v -> {

                if(getContext() == null) {
                    return;
                }

                icon1.setImageResource(R.drawable.icon_negative_unselected);
                icon2.setImageResource(R.drawable.icon_inconclusive_unselected);
                icon3.setImageResource(R.drawable.icon_positive_selected);

                icon1.setColorFilter(neutralSelColor);
                icon2.setColorFilter(neutralSelColor);
                icon3.setColorFilter(positiveSelColor);

                evidenceViewModel.getInvestigationData().getEvidences().get(index)
                        .setRuling(InvestigationData.Evidence.Ruling.POSITIVE);

                evidenceViewModel.setRadioButtonChecked(index, 2);
                evidenceViewModel.updateGhostOrder();

                createGhostViews(view, ghostContainer);
            });

            evidenceContainer.addView(evidenceParent);
        }

        evidenceTypes.recycle();
    }

    public void generateEvidenceTierView(View parentView, int tierIndex, GifImageView animation_fullscreen, String description, int animation) {
        ConstraintLayout scrollView = parentView.findViewById(R.id.scrollview_tiers);
        AppCompatTextView title = parentView.findViewById(R.id.label_tier);
        GifImageView animationView = parentView.findViewById(R.id.animation_tier);
        AppCompatTextView details = scrollView.findViewById(R.id.info_tier);

        details.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                description,
                "#ff6161", fontEmphasisColor + "")));
        animationView.setImageResource(animation);
        animation_fullscreen.setImageResource(animation);


        TypedArray typedArray =
                getContext().getResources().obtainTypedArray(R.array.evidence_tiers);
        title.setText(typedArray.getString(tierIndex-1));
        typedArray.recycle();

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

    @SuppressLint("ClickableViewAccessibility")
    public void createGhostViews(View view, LinearLayout ghostContainer) {

        ghostContainer.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(getContext());

        String[] infos = getResources().getStringArray(R.array.ghost_info_array);
        String[] strengths = getResources().getStringArray(R.array.ghost_strengths_array);
        String[] weaknesses = getResources().getStringArray(R.array.ghost_weaknesses_array);
        String[] huntDatas = getResources().getStringArray(R.array.ghost_huntdata_array);

        int[] newGhostOrder = evidenceViewModel.getGhostOrder();

        Log.d("Scores", "===== Reordering =====");
        //Avoid pass null in the root it ignores spaces in the child layout
        for (int j : newGhostOrder) {

            if(getContext() == null) {
                return;
            }

            String ghostName = evidenceViewModel.getInvestigationData().getGhost(j).getName();
            String ghostInfo = infos[j];
            String ghostStrength = strengths[j];
            String ghostWeakness = weaknesses[j];
            String ghostHuntData = huntDatas[j];

            View inflatedLayout = inflater.inflate(
                    R.layout.item_investigation_ghost,
                    (ViewGroup) view,
                    false);

            LinearLayoutCompat linearLayout_iconRow = inflatedLayout.findViewById(R.id.icon_container);

            AppCompatTextView name = inflatedLayout.findViewById(R.id.label_name);
            AppCompatImageView statusIcon = inflatedLayout.findViewById(R.id.icon_status);

            ConstraintLayout mainLayout = inflatedLayout.findViewById(R.id.layout_main);

            LinearLayoutCompat.LayoutParams params =
                    new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT, 1f);
            mainLayout.setLayoutParams(params);

            name.setText(ghostName);

            GestureDetectorCompat swipeListener = new GestureDetectorCompat(getContext(),
                    new GhostSwipeListener(
                            ghostContainer,
                            name,
                            j,
                            ghostName,
                            ghostInfo,
                            ghostStrength,
                            ghostWeakness,
                            ghostHuntData));

            name.setOnTouchListener((view1, motionEvent) -> {
                swipeListener.onTouchEvent(motionEvent);
                return true;
            });

            InvestigationData.Ghost ghost = evidenceViewModel.getInvestigationData().getGhost(j);
            int score = ghost.getEvidenceScore();
            Log.d("Scores", ghost + " -> score " + score);
            boolean rejectionStatus = evidenceViewModel.getRejectionPile()[j];

            if (rejectionStatus) {
                statusIcon.setImageDrawable(icons_strikethrough[3]);
                statusIcon.setVisibility(View.VISIBLE);
            } else if (score <= -5) {
                statusIcon.setImageDrawable(icons_strikethrough[(int) (Math.random() * 3)]);
                statusIcon.setVisibility(View.VISIBLE);
            } else if (score == 3) {
                statusIcon.setImageDrawable(icon_circle);
                statusIcon.setVisibility(View.VISIBLE);
            } else {
                statusIcon.setVisibility(View.INVISIBLE);
            }

            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getContext().getTheme();
            theme.resolveAttribute(R.attr.neutralSelColor, typedValue, true);
            int neutralSelColor = typedValue.data;
            theme.resolveAttribute(R.attr.negativeSelColor, typedValue, true);
            int negativeSelColor = typedValue.data;
            theme.resolveAttribute(R.attr.positiveSelColor, typedValue, true);
            int positiveSelColor = typedValue.data;

            for (int i = 0; i < ghost.getEvidence().length; i++) {
                ConstraintLayout evidenceIconContainer =
                        (ConstraintLayout) inflater.inflate(R.layout.item_investigation_ghost_icon,
                                null);
                AppCompatImageView evidenceIcon = evidenceIconContainer.findViewById(R.id.evidence_icon);
                evidenceIcon.setImageResource(ghost.getEvidence()[i].getIcon());

                switch(ghost.getEvidence()[i].getRuling()) {
                    case POSITIVE: {
                        evidenceIcon.setColorFilter(positiveSelColor);
                        break;
                    }
                    case NEGATIVE: {
                        evidenceIcon.setColorFilter(negativeSelColor);
                        break;
                    }
                    case NEUTRAL: {
                        evidenceIcon.setColorFilter(neutralSelColor);
                        break;
                    }
                }
                linearLayout_iconRow.addView(evidenceIconContainer);
            }

            ghostContainer.addView(inflatedLayout);

        }
    }

    public void fadeOutIndicatorAnimation(ConstraintLayout bodyCons, ConstraintLayout container, ScrollView scroller, View indicator) {
        scroller.post(() -> {
            if (!scroller.canScrollVertically(1)) {
                indicator.setVisibility(View.VISIBLE);
                indicator.animate()
                        .alpha(0f)
                        .setDuration(getResources().getInteger(
                                android.R.integer.config_longAnimTime))
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                indicator.setVisibility(View.INVISIBLE);
                            }
                        });
            } else {
                if(container != null) {
                    Log.d("Scroller", "Should constrain");
                    ConstraintLayout.LayoutParams lParams =
                            (ConstraintLayout.LayoutParams) container.getLayoutParams();
                    lParams.constrainedHeight = true;
                    container.setLayoutParams(lParams);
                    container.invalidate();
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
                    indicator.animate()
                            .alpha(0f)
                            .setDuration(getResources().getInteger(
                                    android.R.integer.config_longAnimTime))
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    indicator.setVisibility(View.INVISIBLE);
                                }
                            });
                }
            });
        } else {
            scroller.setOnDragListener((v12, event) -> {
                if (!scroller.canScrollVertically(1)) {
                    indicator.animate()
                            .alpha(0f)
                            .setDuration(getResources().getInteger(
                                    android.R.integer.config_longAnimTime))
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    indicator.setVisibility(View.INVISIBLE);
                                }
                            });
                }
                return true;
            });
        }
    }

    private void initFirebase() {
        if(getContext() != null){
            analytics = FirebaseAnalytics.getInstance(getContext());
            Log.d("Firebase", "Obtained instance.");
        }
    }


    /**
     * softReset
     *
     * TODO
     */
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

        if(popup != null) {
            popup.dismiss();
            popup = null;
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

    private class GhostSwipeListener extends GestureDetector.SimpleOnGestureListener {
        private final LinearLayout ghostContainer;
        private final View view;
        private final int index;
        private final String ghostName, ghostInfo, ghostStrength, ghostWeakness, ghostHuntData;
        private String[] cycleDetails;
        int detailIndex = 0;

        public GhostSwipeListener(
                LinearLayout ghostContainer,
                View view,
                int index,
                String ghostName,
                String ghostInfo,
                String ghostStrength,
                String ghostWeakness,
                String ghostHuntData) {

            super();

            this.ghostContainer = ghostContainer;
            this.view = view;
            this.index = index;
            this.ghostName = ghostName;
            this.ghostInfo = ghostInfo;
            this.ghostStrength = ghostStrength;
            this.ghostWeakness = ghostWeakness;
            this.ghostHuntData = ghostHuntData;

            cycleDetails = new String[]{ghostInfo, ghostStrength, ghostWeakness};

        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            boolean status = !evidenceViewModel.swapStatusInRejectedPile(index);

            evidenceViewModel.updateGhostOrder();
            createGhostViews(getView(), ghostContainer);

            Bundle params = new Bundle();
            params.putString("event_type", "ghost_swiped");
            params.putString("event_details", status ? "ghost_impartial" : "ghost_rejected");
            analytics.logEvent("event_investigation", params);

            return true;
        }

        @Override
        public boolean onSingleTapUp(@NonNull MotionEvent e) {
            if(getView() == null || getView().getContext() == null) {
                return false;
            }

            if (popup != null) {
                popup.dismiss();
            }

            LayoutInflater inflaterPopup =
                    (LayoutInflater) getView().getContext().getSystemService(
                            Context.LAYOUT_INFLATER_SERVICE);
            View customView = inflaterPopup.inflate(R.layout.popup_info_ghost, null);

            popup = new PopupWindow(
                    customView,
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );

            ImageButton closeButton = customView.findViewById(R.id.popup_close_button);

            LinearLayoutCompat evidenceIconLayout =
                    customView.findViewById(R.id.layout_evidenceicons);

            View linearLayout_icons_container = inflaterPopup.inflate(R.layout.item_investigation_ghost_icons, null);
            LinearLayoutCompat linearLayout_iconRow = linearLayout_icons_container.findViewById(R.id.icon_container);
            for (int i = 0; i < evidenceViewModel.getInvestigationData()
                    .getGhost(index)
                    .getEvidenceArray().length; i++) {

                ConstraintLayout evidenceIconContainer =
                        (ConstraintLayout) inflaterPopup.inflate(R.layout.item_investigation_ghost_icon,
                                null);
                AppCompatImageView evidenceIcon = evidenceIconContainer.findViewById(R.id.evidence_icon);
                evidenceIcon.setImageResource(evidenceViewModel.getInvestigationData()
                        .getGhost(index).getEvidence()[i].getIcon());

                linearLayout_iconRow.addView(evidenceIconContainer);
            }
            evidenceIconLayout.addView(linearLayout_iconRow);

            AppCompatTextView label_name =
                    customView.findViewById(R.id.label_name);

            label_name.setText(ghostName);

            ConstraintLayout bodyCons = customView.findViewById(R.id.layout_contentbody);

            ConstraintLayout scrollCons_swapping = customView.findViewById(R.id.scrollView_swapping);
            ScrollView scroller_swapping = scrollCons_swapping.findViewById(R.id.scrollView);
            View indicator_swapping = scrollCons_swapping.findViewById(R.id.scrollview_indicator);
            AppCompatTextView data_swapping = scroller_swapping.findViewById(R.id.label_info);

            ConstraintLayout scrollCons_huntdata = customView.findViewById(R.id.scrollView_huntdata);
            ScrollView scroller_huntdata = scrollCons_huntdata.findViewById(R.id.scrollView);
            AppCompatTextView data_huntdata = scroller_huntdata.findViewById(R.id.label_info);

            AppCompatImageButton left = customView.findViewById(R.id.title_left);
            AppCompatImageButton right = customView.findViewById(R.id.title_right);
            AppCompatTextView title = customView.findViewById(R.id.label_infoTitle);

            //initialize info content scroller
            bodyCons.setVisibility(View.INVISIBLE);

            //title.setPaintFlags(data.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            title.setText(titles[detailIndex]);
            data_swapping.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                    cycleDetails[detailIndex],
                    "#ff6161", fontEmphasisColor + "")));
            data_huntdata.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                    ghostHuntData,
                    "#ff6161", fontEmphasisColor + "")));


            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                scrollCons_huntdata.post(() ->
                        scrollCons_huntdata.setMaxHeight((int)(((ConstraintLayout)scrollCons_huntdata.getParent()).getHeight() * .4f)));
            }


            left.setOnClickListener(view -> {
                detailIndex = Math.min(((detailIndex -1) % cycleDetails.length) & (cycleDetails.length), cycleDetails.length-1);

                title.setText(titles[detailIndex]);
                data_swapping.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                        cycleDetails[detailIndex],
                        "#ff6161", fontEmphasisColor + "")));

                //initialize info content scroller
                /*
                bodyCons.setVisibility(View.INVISIBLE);
                scroller_swapping.post(() -> {
                    if (scroller_swapping.canScrollVertically(1)) {
                        Log.d("Scroller", "Should constrain");
                        ConstraintLayout.LayoutParams lParams =
                                (ConstraintLayout.LayoutParams) scrollCons_swapping.getLayoutParams();
                        lParams.constrainedHeight = true;
                        scrollCons_swapping.setLayoutParams(lParams);
                        scrollCons_swapping.invalidate();
                    }
                    //initialize info content scroller
                    bodyCons.setVisibility(View.VISIBLE);
                });
                */
            });

            right.setOnClickListener(view -> {
                detailIndex = (detailIndex +1)% cycleDetails.length;

                title.setText(titles[detailIndex]);
                data_swapping.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                        cycleDetails[detailIndex],
                        "#ff6161", fontEmphasisColor + "")));


                //initialize info content scroller
                /*
                bodyCons.setVisibility(View.INVISIBLE);
                scroller_swapping.post(() -> {
                    if (scroller_swapping.canScrollVertically(1)) {
                        Log.d("Scroller", "Should constrain");
                        ConstraintLayout.LayoutParams lParams =
                                (ConstraintLayout.LayoutParams) scrollCons_swapping.getLayoutParams();
                        lParams.constrainedHeight = true;
                        scrollCons_swapping.setLayoutParams(lParams);
                        scrollCons_swapping.invalidate();
                    }
                    //initialize info content scroller
                    bodyCons.setVisibility(View.VISIBLE);
                });
                */
            });

            fadeOutIndicatorAnimation(
                    bodyCons,
                    scrollCons_swapping,
                    scroller_swapping,
                    indicator_swapping);

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            } else {
                /*

                ConstraintLayout scrollCons1 = customView.findViewById(R.id.scrollview1);
                ConstraintLayout scrollCons2 = customView.findViewById(R.id.scrollview2);
                ConstraintLayout scrollCons3 = customView.findViewById(R.id.scrollview3);
                ScrollView scroller1 = scrollCons1.findViewById(R.id.scrollView);
                ScrollView scroller2 = scrollCons2.findViewById(R.id.scrollView);
                ScrollView scroller3 = scrollCons3.findViewById(R.id.scrollView);
                View indicator1 = scrollCons1.findViewById(R.id.scrollview_indicator);
                View indicator2 = scrollCons2.findViewById(R.id.scrollview_indicator);
                View indicator3 = scrollCons3.findViewById(R.id.scrollview_indicator);

                AppCompatTextView info = scroller1.findViewById(R.id.label_info);
                AppCompatTextView strength = scroller2.findViewById(R.id.label_info);
                AppCompatTextView weakness = scroller3.findViewById(R.id.label_info);

                AppCompatTextView label_info =
                        customView.findViewById(R.id.label_infoTitle);
                AppCompatTextView label_strength =
                        customView.findViewById(R.id.label_strengthsTitle);
                AppCompatTextView label_weakness =
                        customView.findViewById(R.id.label_weaknessesTitle);

                label_info.setPaintFlags(info.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                label_strength.setPaintFlags(info.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                label_weakness.setPaintFlags(info.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                info.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                    ghostInfo,
                    "#ff6161", fontEmphasisColor + "")));
                strength.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                        ghostStrength,
                        "#ff6161", fontEmphasisColor + "")));
                weakness.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                        ghostWeakness,
                        "#ff6161", fontEmphasisColor + "")));

                fadeOutIndicatorAnimation(
                        scroller1,
                        indicator1);
                fadeOutIndicatorAnimation(
                        scroller2,
                        indicator2);
                fadeOutIndicatorAnimation(
                        scroller3,
                        indicator3);
                */
            }

            closeButton.setOnClickListener(v1 -> popup.dismiss());

            popup.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, 0);

            if (getActivity() != null) {
                MobileAds.initialize(getActivity(), initializationStatus -> {
                });
                AdView mAdView = customView.findViewById(R.id.adView);
                adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }

            return super.onSingleTapConfirmed(e);
        }
    }


    private class EvidenceContainerListener extends GestureDetector.SimpleOnGestureListener {

        private final LinearLayout evidenceContainer;

        public EvidenceContainerListener(
                LinearLayout evidenceContainer ) {

            super();

            this.evidenceContainer = evidenceContainer;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {



            return true;
        }

    }


    public static class CompositeListener implements View.OnClickListener {

        private ArrayList<View.OnClickListener> registeredListeners = new ArrayList<>();

        public void registerListener (View.OnClickListener listener) {
            registeredListeners.add(listener);
        }

        @Override
        public void onClick(View view) {
            for(View.OnClickListener listener:registeredListeners) {
                listener.onClick(view);
            }
        }
    }
}
