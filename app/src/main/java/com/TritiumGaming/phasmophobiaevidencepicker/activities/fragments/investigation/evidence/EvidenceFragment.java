package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
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

/**
 * EvidenceFragment class
 *
 * @author TritiumGamingStudios
 */
public class EvidenceFragment extends Fragment {

    protected EvidenceViewModel evidenceViewModel;
    protected MapMenuViewModel mapMenuViewModel;
    protected GlobalPreferencesViewModel globalPreferencesViewModel;

    protected PopupWindow popup;

    private AdRequest adRequest;

    protected SanityData sanityData;
    protected PhaseTimerData phaseTimerData;
    protected DifficultyCarouselData difficultyCarouselData;
    protected MapCarouselData mapCarouselData;

    protected ConstraintLayout mainConstraintLayout;
    protected ConstraintLayout sanityTrackingConstraintLayout;
    protected AppCompatTextView phaseTimerTextView;
    protected AppCompatTextView sanityPercentTextView;

    protected DifficultyCarouselView difficultyCarouselView;
    protected PhaseTimerView phaseTimerCountdownView;
    protected SanitySeekBarView sanitySeekBarView;
    protected SanityMeterView sanityMeterView;
    protected WarnTextView sanityWarningTextView;

    protected Drawable icon_circle;
    protected Drawable[] icons_strikethrough;

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

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

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

        // GHOST / EVIDENCE CONTAINERS
        LinearLayout ghostContainer = view.findViewById(R.id.layout_ghostList);
        LinearLayout evidenceContainer = view.findViewById(R.id.layout_evidenceList);

        // SANITY METER VIEWS
        sanityPercentTextView = view.findViewById(R.id.evidence_sanitymeter_percentage);
        // TIMER VIEW
        phaseTimerTextView = view.findViewById(R.id.evidence_timer_text);

        // SANITY METER VIEWS
        sanityMeterView = view.findViewById(R.id.evidence_sanitymeter_progressbar);
        sanitySeekBarView = view.findViewById(R.id.evidence_sanitymeter_seekbar);
        sanityWarningTextView = view.findViewById(R.id.evidence_sanitymeter_huntwarning);

        mainConstraintLayout = view.findViewById(R.id.layout_main);
        sanityTrackingConstraintLayout = view.findViewById(R.id.constraintLayout_sanityTracking);

        // DRAWABLES
        icons_strikethrough = new Drawable[]{
                getResources().getDrawable(R.drawable.icon_strikethrough_1),
                getResources().getDrawable(R.drawable.icon_strikethrough_2),
                getResources().getDrawable(R.drawable.icon_strikethrough_3)
        };
        icon_circle = getResources().getDrawable(R.drawable.icon_circle);

        // LISTENERS
        initNavListeners(
                view.findViewById(R.id.listener_goto_left),
                null,
                view.findViewById(R.id.listener_resetAll),
                null,
                view.findViewById(R.id.listener_goto_right),
                view.findViewById(R.id.icon_goto_left),
                null,
                view.findViewById(R.id.icon_resetAll),
                null,
                view.findViewById(R.id.icon_goto_right));

        sanitySeekBarView.init(
                sanityData,
                sanityPercentTextView);

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
            sanityPercentTextView.setText(sanityData.toPercentString());
        }

        createGhostViews(view, ghostContainer);
        createEvidenceViews(view, evidenceContainer, ghostContainer);

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
            icon_navLeft.setBackgroundResource(R.drawable.icon_tasks);
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
            lstnr_navMedLeft.setVisibility(View.VISIBLE);
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
            icon_navCenter.setBackgroundResource(R.drawable.icon_reset);
            lstnr_navCenter.setOnClickListener(v -> {
                        softReset();
                    }
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
            icon_navRight.setBackgroundResource(R.drawable.icon_map);
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
        for(int i = 0; i < InvestigationData.getEvidenceCount(); i++) {

            String evidenceName = evidenceNames[i];
            String evidenceInfo = getResources().getStringArray(R.array.evidence_info_array)[i];

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
                ScrollView scroller = customView.findViewById(R.id.scrollView);
                View indicator = customView.findViewById(R.id.scrollview_indicator);

                fadeOutIndicatorAnimation(
                        scroller,
                        indicator);

                closeButton.setOnClickListener(v1 -> popup.dismiss());

                label.setText(evidenceName);
                info.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                        evidenceInfo,
                        "#ff6161", fontEmphasisColor + "")));

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
            } else if(evidenceViewModel.getRadioButtonsChecked()[i] == 1) {
                icon1.setImageResource(R.drawable.icon_negative_unselected);
                icon2.setImageResource(R.drawable.icon_inconclusive_selected);
                icon3.setImageResource(R.drawable.icon_positive_unselected);
                icon1.setColorFilter(neutralSelColor);
                icon2.setColorFilter(neutralSelColor);
                icon3.setColorFilter(neutralSelColor);
            } else if(evidenceViewModel.getRadioButtonsChecked()[i] == 2) {
                icon1.setImageResource(R.drawable.icon_negative_unselected);
                icon2.setImageResource(R.drawable.icon_inconclusive_unselected);
                icon3.setImageResource(R.drawable.icon_positive_selected);
                icon1.setColorFilter(neutralSelColor);
                icon2.setColorFilter(neutralSelColor);
                icon3.setColorFilter(positiveSelColor);
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

    }

    public void createGhostViews(View view, LinearLayout ghostContainer) {

        ghostContainer.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(getContext());

        String[] infos = getResources().getStringArray(R.array.ghost_info_array);
        String[] strengths = getResources().getStringArray(R.array.ghost_strengths_array);
        String[] weaknesses = getResources().getStringArray(R.array.ghost_weaknesses_array);

        int[] newGhostOrder = evidenceViewModel.getGhostOrder();

        //Avoid pass null in the root it ignores spaces in the child layout
        for (int j : newGhostOrder) {

            if(getContext() == null) {
                return;
            }

            String ghostName = evidenceViewModel.getInvestigationData().getGhost(j).getName();
            String ghostInfo = infos[j];
            String ghostStrength = strengths[j];
            String ghostWeakness = weaknesses[j];

            View inflatedLayout = inflater.inflate(
                    R.layout.item_investigation_ghost,
                    (ViewGroup) view,
                    false);

            AppCompatTextView name = inflatedLayout.findViewById(R.id.label_name);
            AppCompatImageView statusIcon = inflatedLayout.findViewById(R.id.icon_status);
            AppCompatImageView icon1 = inflatedLayout.findViewById(R.id.icon1);
            AppCompatImageView icon2 = inflatedLayout.findViewById(R.id.icon2);
            AppCompatImageView icon3 = inflatedLayout.findViewById(R.id.icon3);
            ConstraintLayout mainLayout = inflatedLayout.findViewById(R.id.layout_main);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            mainLayout.setLayoutParams(params);

            name.setText(ghostName);
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
                @SuppressLint("InflateParams")
                View customView = inflaterPopup.inflate(R.layout.popup_info_ghost, null);

                popup = new PopupWindow(
                        customView,
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );

                ImageButton closeButton = customView.findViewById(R.id.popup_close_button);
                ConstraintLayout scrollCons1 = customView.findViewById(R.id.scrollview1);
                ConstraintLayout scrollCons2 = customView.findViewById(R.id.scrollview2);
                ConstraintLayout scrollCons3 = customView.findViewById(R.id.scrollview3);
                ScrollView scroller1 = scrollCons1.findViewById(R.id.scrollView);
                ScrollView scroller2 = scrollCons2.findViewById(R.id.scrollView);
                ScrollView scroller3 = scrollCons3.findViewById(R.id.scrollView);
                View indicator1 = scrollCons1.findViewById(R.id.scrollview_indicator);
                View indicator2 = scrollCons2.findViewById(R.id.scrollview_indicator);
                View indicator3 = scrollCons3.findViewById(R.id.scrollview_indicator);

                AppCompatTextView label_name =
                        customView.findViewById(R.id.label_name);

                AppCompatTextView label_info =
                        customView.findViewById(R.id.label_infoTitle);
                AppCompatTextView label_strength =
                        customView.findViewById(R.id.label_strengthsTitle);
                AppCompatTextView label_weakness =
                        customView.findViewById(R.id.label_weaknessesTitle);

                ConstraintLayout parentLayout_info =
                        customView.findViewById(R.id.constraintLayout_info);
                ConstraintLayout parentLayout_strength =
                        customView.findViewById(R.id.constraintLayout_strengths);
                ConstraintLayout parentLayout_weakness =
                        customView.findViewById(R.id.constraintLayout_weaknesses);

                LinearLayout childLayout_info =
                        scroller1.findViewById(R.id.layout_textContainer);
                LinearLayout childLayout_strength =
                        scroller2.findViewById(R.id.layout_textContainer);
                LinearLayout childLayout_weakness =
                        scroller3.findViewById(R.id.layout_textContainer);

                AppCompatTextView info = scroller1.findViewById(R.id.label_info);
                AppCompatTextView strength = scroller2.findViewById(R.id.label_info);
                AppCompatTextView weakness = scroller3.findViewById(R.id.label_info);

                label_info.setPaintFlags(info.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                label_strength.setPaintFlags(info.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                label_weakness.setPaintFlags(info.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                label_name.setText(ghostName);
                info.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                        ghostInfo,
                        "#ff6161", fontEmphasisColor + "")));
                strength.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                        ghostStrength,
                        "#ff6161", fontEmphasisColor + "")));
                weakness.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                        ghostWeakness,
                        "#ff6161", fontEmphasisColor + "")));

                closeButton.setOnClickListener(v1 -> popup.dismiss());

                fadeOutIndicatorAnimation(
                        scroller1,
                        indicator1);
                fadeOutIndicatorAnimation(
                        scroller2,
                        indicator2);
                fadeOutIndicatorAnimation(
                        scroller3,
                        indicator3);

                popup.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, 0);

                if (getActivity() != null) {
                    MobileAds.initialize(getActivity(), initializationStatus -> {
                    });
                    AdView mAdView = customView.findViewById(R.id.adView);
                    adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                }

            });

            int score = evidenceViewModel.getInvestigationData().getGhost(j).getEvidenceScore();
            if (score == -5) {
                statusIcon.setImageDrawable(icons_strikethrough[(int) (Math.random() * 3)]);
                statusIcon.setVisibility(View.VISIBLE);
            } else if (score == 3) {
                statusIcon.setImageDrawable(icon_circle);
                statusIcon.setVisibility(View.VISIBLE);
            } else {
                statusIcon.setVisibility(View.INVISIBLE);
            }

            icon1.setImageResource(
                    evidenceViewModel.getInvestigationData()
                            .getGhost(j)
                            .getEvidenceArray()[0]
                            .getIcon());
            icon2.setImageResource(
                    evidenceViewModel.getInvestigationData()
                            .getGhost(j)
                            .getEvidenceArray()[1]
                            .getIcon());
            icon3.setImageResource(
                    evidenceViewModel.getInvestigationData()
                            .getGhost(j)
                            .getEvidenceArray()[2]
                            .getIcon());

            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getContext().getTheme();
            theme.resolveAttribute(R.attr.neutralSelColor, typedValue, true);
            int neutralSelColor = typedValue.data;
            theme.resolveAttribute(R.attr.negativeSelColor, typedValue, true);
            int negativeSelColor = typedValue.data;
            theme.resolveAttribute(R.attr.positiveSelColor, typedValue, true);
            int positiveSelColor = typedValue.data;

            InvestigationData.Evidence.Ruling ruling = evidenceViewModel.getInvestigationData()
                    .getGhost(j).getEvidenceArray()[0].getRuling();
            if (ruling == InvestigationData.Evidence.Ruling.POSITIVE) {
                icon1.setColorFilter(positiveSelColor);
            } else if (ruling == InvestigationData.Evidence.Ruling.NEGATIVE) {
                icon1.setColorFilter(negativeSelColor);
            } else {
                icon1.setColorFilter(neutralSelColor);
            }

            ruling = evidenceViewModel.getInvestigationData()
                    .getGhost(j).getEvidenceArray()[1].getRuling();
            if (ruling == InvestigationData.Evidence.Ruling.POSITIVE) {
                icon2.setColorFilter(positiveSelColor);
            } else if (ruling == InvestigationData.Evidence.Ruling.NEGATIVE) {
                icon2.setColorFilter(negativeSelColor);
            } else {
                icon2.setColorFilter(neutralSelColor);
            }

            ruling = evidenceViewModel.getInvestigationData()
                    .getGhost(j).getEvidenceArray()[2].getRuling();
            if (ruling == InvestigationData.Evidence.Ruling.POSITIVE) {
                icon3.setColorFilter(positiveSelColor);
            } else if (ruling == InvestigationData.Evidence.Ruling.NEGATIVE) {
                icon3.setColorFilter(negativeSelColor);
            } else {
                icon3.setColorFilter(neutralSelColor);
            }

            ghostContainer.addView(inflatedLayout);

        }
    }

    public void fadeOutIndicatorAnimation(ScrollView scroller, View indicator) {
        scroller.post(() -> {
            if (!scroller.canScrollVertically(1)) {
                indicator.animate()
                        .alpha(0f)
                        .setDuration(getResources().getInteger(
                                android.R.integer.config_longAnimTime))
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                indicator.setVisibility(View.GONE);
                            }
                        });
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
                                    indicator.setVisibility(View.GONE);
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
                                    indicator.setVisibility(View.GONE);
                                }
                            });
                }
                return true;
            });
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

        if (difficultyCarouselView != null) {
            difficultyCarouselView.reset();
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
