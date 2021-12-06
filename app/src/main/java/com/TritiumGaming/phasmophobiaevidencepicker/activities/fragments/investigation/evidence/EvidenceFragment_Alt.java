package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
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

/**
 * EvidenceFragment class
 *
 * @author TritiumGamingStudios
 */
public class EvidenceFragment_Alt extends Fragment {

    protected EvidenceViewModel evidenceViewModel;
    protected GlobalPreferencesViewModel globalPreferencesViewModel;

    protected PopupWindow popup;

    protected SanityData sanityData;
    protected PhaseTimerData phaseTimerData;
    protected DifficultyCarouselData difficultyCarouselData;
    protected MapCarouselData mapCarouselData;

    protected ConstraintLayout mainConstraintLayout;
    protected ConstraintLayout sanityTrackingConstraintLayout;
    protected DifficultyCarouselView difficultyCarouselView;
    protected PhaseTimerView phaseTimerCountdownView;
    protected AppCompatTextView phaseTimerTextView;
    protected SanitySeekBarView sanitySeekBarView;
    protected AppCompatTextView sanityPercentTextView;
    protected SanityMeterView sanityMeterView;
    protected WarnTextView sanityWarningTextView;

    protected Drawable icon_circle;
    protected Drawable[] icons_strikethrough;
    protected Typeface font_normal;

    @ColorInt
    int fontEmphasisColor = 0;

    /**
     * EvidenceFragment constructor
     * <p>
     * TODO
     *
     * @param layout -
     */
    public EvidenceFragment_Alt(int layout) {
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

        // FONT FAMILY
        font_normal = Typeface.MONOSPACE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            font_normal = getResources().getFont(R.font.norse_regular);
        }
        else if (getContext() != null) {
            font_normal = ResourcesCompat.getFont(getContext(), R.font.norse_regular);
        }

        // THEME
        if (getContext() != null) {
            Resources.Theme theme = getContext().getTheme();
            TypedValue typedValue = new TypedValue();
            theme.resolveAttribute(R.attr.light_inactive, typedValue, true);
            fontEmphasisColor = typedValue.data;
        }

        // GHOST / EVIDENCE CONTAINERS
        LinearLayout ghostContainer = view.findViewById(R.id.layout_ghostList);
        LinearLayout evidenceContainer = view.findViewById(R.id.layout_evidenceList);

        // SANITY METER VIEWS
        AppCompatTextView sanityMeterTitle = view.findViewById(R.id.evidence_sanitymeter_title);
        sanityPercentTextView = view.findViewById(R.id.evidence_sanitymeter_percentage);
        // TIMER VIEW
        phaseTimerTextView = view.findViewById(R.id.evidence_timer_text);
        // NAVIGATION VIEWS
        AppCompatTextView label_goto_left = view.findViewById(R.id.label_goto_left);
        AppCompatTextView label_goto_right = view.findViewById(R.id.label_goto_right);
        View listener_goto_left = view.findViewById(R.id.listener_goto_left);
        View listener_goto_right = view.findViewById(R.id.listener_goto_right);
        // RESET VIEWS
        AppCompatTextView label_resetAll = view.findViewById(R.id.label_resetAll);
        View listener_resetAll = view.findViewById(R.id.listener_resetAll);
        AppCompatImageView navigation_goto_medLeft = view.findViewById(R.id.icon_goto_medLeft);
        AppCompatImageView navigation_goto_medRight = view.findViewById(R.id.icon_goto_medRight);

        // SANITY METER VIEWS
        sanityMeterView = view.findViewById(R.id.evidence_sanitymeter_progressbar);
        sanitySeekBarView = view.findViewById(R.id.evidence_sanitymeter_seekbar);
        sanityWarningTextView = view.findViewById(R.id.evidence_sanitymeter_huntwarning);

        mainConstraintLayout = view.findViewById(R.id.layout_main);
        sanityTrackingConstraintLayout = view.findViewById(R.id.constraintLayout_sanityTracking);

        // TEXT SIZES

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            phaseTimerTextView.setAutoSizeTextTypeUniformWithConfiguration(
                    5, 50, 1,
                    AppCompatTextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            label_resetAll.setAutoSizeTextTypeUniformWithConfiguration(
                    5, 25, 1,
                    AppCompatTextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            label_goto_left.setAutoSizeTextTypeUniformWithConfiguration(
                    10, 50, 1,
                    AppCompatTextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            label_goto_right.setAutoSizeTextTypeUniformWithConfiguration(
                    10, 50, 1,
                    AppCompatTextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            sanityPercentTextView.setAutoSizeTextTypeUniformWithConfiguration(
                    5, 20, 1,
                    AppCompatTextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            sanityMeterTitle.setAutoSizeTextTypeUniformWithConfiguration(
                    5, 20, 1,
                    AppCompatTextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            sanityWarningTextView.setAutoSizeTextTypeUniformWithConfiguration(
                    5, 50, 1,
                    AppCompatTextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        } else {
            phaseTimerTextView.setAutoSizeTextTypeUniformWithConfiguration(
                    5, 50, 1,
                    1);
            label_resetAll.setAutoSizeTextTypeUniformWithConfiguration(
                    5, 25, 1,
                    1);
            label_goto_left.setAutoSizeTextTypeUniformWithConfiguration(
                    10, 50, 1,
                    1);
            label_goto_right.setAutoSizeTextTypeUniformWithConfiguration(
                    10, 50, 1,
                    1);
            sanityPercentTextView.setAutoSizeTextTypeUniformWithConfiguration(
                    5, 20, 1,
                    1);
            sanityMeterTitle.setAutoSizeTextTypeUniformWithConfiguration(
                    5, 20, 1,
                    1);
            sanityWarningTextView.setAutoSizeTextTypeUniformWithConfiguration(
                    5, 50, 1,
                    1);
        }

        // LISTENERS
        initNavListeners(
                listener_goto_left,
                navigation_goto_medLeft,
                listener_goto_right,
                navigation_goto_medRight,
                listener_resetAll);

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

        // DRAWABLES
        icons_strikethrough = new Drawable[]{
                getResources().getDrawable(R.drawable.icon_strikethrough_1),
                getResources().getDrawable(R.drawable.icon_strikethrough_2),
                getResources().getDrawable(R.drawable.icon_strikethrough_3)
        };
        icon_circle = getResources().getDrawable(R.drawable.icon_circle);

        // DRAWABLE TINTS
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                name.setAutoSizeTextTypeUniformWithConfiguration(
                        12,
                        30,
                        1,
                        AppCompatTextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            } else {
                name.setAutoSizeTextTypeUniformWithConfiguration(
                        12,
                        30,
                        1,
                        1);
            }

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
                @SuppressLint("InflateParams")
                View customView = inflaterPopup.inflate(R.layout.popup_info, null);

                popup = new PopupWindow(
                        customView,
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );

                ImageButton closeButton = customView.findViewById(R.id.popup_close_button);
                AppCompatTextView label = customView.findViewById(R.id.label_queryName);
                AppCompatTextView info = customView.findViewById(R.id.label_queryInfo);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    label.setAutoSizeTextTypeUniformWithConfiguration(
                            12,
                            50,
                            1,
                            AppCompatTextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    info.setAutoSizeTextTypeUniformWithConfiguration(
                            12,
                            35,
                            1,
                            AppCompatTextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                } else {
                    label.setAutoSizeTextTypeUniformWithConfiguration(
                            12,
                            50,
                            1,
                            1);
                    info.setAutoSizeTextTypeUniformWithConfiguration(
                            12,
                            35,
                            1,
                            1);
                }

                closeButton.setOnClickListener(v1 -> popup.dismiss());

                label.setText(evidenceName);
                info.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                        evidenceInfo,
                        "ff6161", fontEmphasisColor + "")));

                popup.setAnimationStyle(R.anim.nav_default_enter_anim);
                popup.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, 0);

            });

            if(evidenceViewModel.getRadioButtonsChecked()[i] == 0) {
                icon1.setImageResource(R.drawable.icon_positive_selected);
                icon2.setImageResource(R.drawable.icon_inconclusive_unselected);
                icon3.setImageResource(R.drawable.icon_negative_unselected);
            } else if(evidenceViewModel.getRadioButtonsChecked()[i] == 1) {
                icon1.setImageResource(R.drawable.icon_positive_unselected);
                icon2.setImageResource(R.drawable.icon_inconclusive_selected);
                icon3.setImageResource(R.drawable.icon_negative_unselected);
            } else if(evidenceViewModel.getRadioButtonsChecked()[i] == 2) {
                icon1.setImageResource(R.drawable.icon_positive_unselected);
                icon2.setImageResource(R.drawable.icon_inconclusive_unselected);
                icon3.setImageResource(R.drawable.icon_negative_selected);
            }

            int index = i;
            icon1.setOnClickListener(v -> {
                icon1.setImageResource(R.drawable.icon_positive_selected);
                icon2.setImageResource(R.drawable.icon_inconclusive_unselected);
                icon3.setImageResource(R.drawable.icon_negative_unselected);

                evidenceViewModel.getInvestigationData().getEvidences().get(index)
                        .setRuling(InvestigationData.Evidence.Ruling.POSITIVE);

                evidenceViewModel.setRadioButtonChecked(index, 0);
                evidenceViewModel.updateGhostOrder();

                createGhostViews(view, ghostContainer);
            });
            icon2.setOnClickListener(v -> {
                icon1.setImageResource(R.drawable.icon_positive_unselected);
                icon2.setImageResource(R.drawable.icon_inconclusive_selected);
                icon3.setImageResource(R.drawable.icon_negative_unselected);

                evidenceViewModel.getInvestigationData().getEvidences().get(index)
                        .setRuling(InvestigationData.Evidence.Ruling.NEUTRAL);

                evidenceViewModel.setRadioButtonChecked(index, 1);
                evidenceViewModel.updateGhostOrder();

                createGhostViews(view, ghostContainer);
            });
            icon3.setOnClickListener(v -> {
                icon1.setImageResource(R.drawable.icon_positive_unselected);
                icon2.setImageResource(R.drawable.icon_inconclusive_unselected);
                icon3.setImageResource(R.drawable.icon_negative_selected);

                evidenceViewModel.getInvestigationData().getEvidences().get(index)
                        .setRuling(InvestigationData.Evidence.Ruling.NEGATIVE);

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

        int[] newGhostOrder = evidenceViewModel.getGhostOrder();

        //Avoid pass null in the root it ignores spaces in the child layout
        for (int j : newGhostOrder) {

            if(getContext() == null) {
                return;
            }

            String ghostName = evidenceViewModel.getInvestigationData().getGhost(j).getName();
            String ghostInfo = getResources().getStringArray(R.array.ghost_info_array)[j];

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                name.setAutoSizeTextTypeUniformWithConfiguration(
                        12,
                        30,
                        1,
                        AppCompatTextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            } else {
                name.setAutoSizeTextTypeUniformWithConfiguration(
                        12,
                        30,
                        1,
                        1);
            }

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
                View customView = inflaterPopup.inflate(R.layout.popup_info, null);

                popup = new PopupWindow(
                        customView,
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );

                ImageButton closeButton = customView.findViewById(R.id.popup_close_button);
                AppCompatTextView label = customView.findViewById(R.id.label_queryName);
                AppCompatTextView info = customView.findViewById(R.id.label_queryInfo);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    label.setAutoSizeTextTypeUniformWithConfiguration(
                            12,
                            50,
                            1,
                            AppCompatTextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);

                    info.setAutoSizeTextTypeUniformWithConfiguration(
                            12,
                            35,
                            1,
                            AppCompatTextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                } else {
                    label.setAutoSizeTextTypeUniformWithConfiguration(
                            12,
                            50,
                            1,
                            1);

                    info.setAutoSizeTextTypeUniformWithConfiguration(
                            12,
                            35,
                            1,
                            1);
                }

                closeButton.setOnClickListener(v1 -> popup.dismiss());

                label.setText(ghostName);
                info.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                        ghostInfo,
                        "ff6161", fontEmphasisColor + "")));

                popup.setAnimationStyle(R.anim.nav_default_enter_anim);
                popup.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, 0);

            });

            int score = evidenceViewModel.getInvestigationData().getGhost(j)
                    .getEvidenceScore();
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

            InvestigationData.Evidence.Ruling ruling =
                    evidenceViewModel.getInvestigationData().getGhost(j).getEvidenceArray()[0]
                            .getRuling();

            if (ruling == InvestigationData.Evidence.Ruling.POSITIVE) {
                icon1.setColorFilter(positiveSelColor);
            } else if (ruling == InvestigationData.Evidence.Ruling.NEGATIVE) {
                icon1.setColorFilter(negativeSelColor);
            } else {
                icon1.setColorFilter(neutralSelColor);
            }

            ruling = evidenceViewModel.getInvestigationData().getGhost(j).getEvidenceArray()[1]
                            .getRuling();

            if (ruling == InvestigationData.Evidence.Ruling.POSITIVE) {
                icon2.setColorFilter(positiveSelColor);
            } else if (ruling == InvestigationData.Evidence.Ruling.NEGATIVE) {
                icon2.setColorFilter(negativeSelColor);
            } else {
                icon2.setColorFilter(neutralSelColor);
            }

            ruling = evidenceViewModel.getInvestigationData().getGhost(j).getEvidenceArray()[2]
                            .getRuling();

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

    private void initNavListeners(View navLeft, View navMedLeft, View navRight,
                                  View navMedRight, View navCenter) {
        if(navLeft != null) {
            navLeft.setOnClickListener(v -> {
                        if (evidenceViewModel != null && evidenceViewModel.hasSanityData()) {
                            evidenceViewModel.getSanityData().setFlashTimeoutStart(-1);
                        }
                        Navigation.findNavController(v)
                                .navigate(R.id.action_evidence_to_objectives);
                    }
            );
        }
        if(navMedLeft != null) {
            navMedLeft.setOnClickListener(v -> {
                        if(evidenceViewModel != null && evidenceViewModel.hasSanityData()) {
                            evidenceViewModel.getSanityData().setFlashTimeoutStart(-1);
                        }
                        Navigation.findNavController(v)
                                .navigate(R.id.action_evidenceFragment_to_utilitiesFragment);
                    }
            );
        }

        if(navCenter != null) {
            navCenter.setOnClickListener(v -> {
                        softReset();
                    }
            );
        }

        if(navMedRight != null) {
            //Log.d("Navigation", "Med Right Navigation not defined.");
            navMedRight.setOnClickListener(v -> {
                        if(sanityTrackingConstraintLayout.getVisibility() == View.VISIBLE) {
                            sanityTrackingConstraintLayout.setVisibility(View.GONE);
                        } else if(sanityTrackingConstraintLayout.getVisibility() == View.GONE) {
                            sanityTrackingConstraintLayout.setVisibility(View.VISIBLE);
                        }
                    }
            );
        }

        if(navRight != null) {
            navRight.setOnClickListener(v -> {
                        if (evidenceViewModel != null && evidenceViewModel.hasSanityData()) {
                            evidenceViewModel.getSanityData().setFlashTimeoutStart(-1);
                        }
                        Navigation.findNavController(v)
                                .navigate(R.id.action_evidence_to_mapmenu);
                    }
            );
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
