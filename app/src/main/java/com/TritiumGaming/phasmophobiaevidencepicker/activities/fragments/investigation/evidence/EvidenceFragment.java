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
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.DifficultyCarouselData;
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
@Deprecated
public class EvidenceFragment extends Fragment {

    protected EvidenceViewModel evidenceViewModel;
    protected GlobalPreferencesViewModel globalPreferencesViewModel;

    protected PopupWindow popup;

    protected SanityData sanityData;
    protected PhaseTimerData phaseTimerData;
    protected DifficultyCarouselData difficultyCarouselData;

    protected EvidenceView[] evidenceItems;
    protected EvidenceRadioGroup[] evidenceRadioGroups;

    protected GhostView[] ghostItems;
    protected GhostLabel[] ghostLabels;
    protected GhostIcon[][] ghostEvidenceIcons;

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
    protected int[] fontSize;

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
        phaseTimerTextView.setAutoSizeTextTypeUniformWithConfiguration(
                5, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);
        label_resetAll.setAutoSizeTextTypeUniformWithConfiguration(
                5, 25, 1,
                TypedValue.COMPLEX_UNIT_SP);
        label_goto_left.setAutoSizeTextTypeUniformWithConfiguration(
                10, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);
        label_goto_right.setAutoSizeTextTypeUniformWithConfiguration(
                10, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);
        sanityPercentTextView.setAutoSizeTextTypeUniformWithConfiguration(
                5, 20, 1,
                TypedValue.COMPLEX_UNIT_SP);
        sanityMeterTitle.setAutoSizeTextTypeUniformWithConfiguration(
                5, 20, 1,
                TypedValue.COMPLEX_UNIT_SP);
        sanityWarningTextView.setAutoSizeTextTypeUniformWithConfiguration(
                5, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);

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

        // INITIALIZE OBJECTS
        // GHOST GROUPS
        ghostItems = new GhostView[InvestigationData.getGhostCount()];
        // EVIDENCE GROUPS
        evidenceItems = new EvidenceView[InvestigationData.getEvidenceCount()];
        // EVIDENCE RADIO BUTTON GROUPS
        evidenceRadioGroups = new EvidenceRadioGroup[InvestigationData.getEvidenceCount()];
        EvidenceRadioButton[][] radioButtons_evidence =
                new EvidenceRadioButton
                        [InvestigationData.getEvidenceCount()]
                        [InvestigationData.Evidence.Ruling.values().length];
        // GHOST LABELS
        ghostLabels = new GhostLabel[InvestigationData.getGhostCount()];
        // EVIDENCE ICONS
        ghostEvidenceIcons = new GhostIcon
                [InvestigationData.getGhostCount()]
                [InvestigationData.Evidence.Ruling.values().length];

        // FONT SIZES
        fontSize = new int[]{12, 40}; //min - max

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

        //Initialize Ghost and Evidence Lists
        initGhosts(ghostContainer, view);
        initEvidence(evidenceContainer, radioButtons_evidence);

        // FINALIZE BY REORDERING GHOST LIST
        updateGhosts();

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
                        /*
                        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        if (Build.VERSION.SDK_INT >= 26) {
                            ft.setReorderingAllowed(false);
                        }
                        ft.detach(EvidenceFragment.this).commitNow();
                        ft.attach(EvidenceFragment.this).commitNow();
                        */
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
     * initGhostList
     * <p>
     * TODO
     *
     * @param ghostContainer
     * @param view
     */
    public void initGhosts(LinearLayout ghostContainer, View view) {
        Log.d("Evidence", "Initiating Ghost list");

        // LOOP THROUGH GHOST LIST BODY
        for (int i = 0; i < ghostLabels.length; i++) {
            // CREATE LABEL FOR GHOST ITEM
            ghostLabels[i] = new GhostLabel(view.getContext(), i);
            // CREATE GHOST EVIDENCE ICON LIST
            GhostIcons iconLayout = new GhostIcons(getContext());
            // CREATE GHOST ICONS AND ADD THEM TO ICON LIST
            InvestigationData.Evidence[] gE =
                    evidenceViewModel.getInvestigationData().getGhost(i).getEvidenceArray();

            for (int j = 0; j < gE.length; j++) {
                ghostEvidenceIcons[i][j] = new GhostIcon(getContext(), gE[j]);
                iconLayout.addView(ghostEvidenceIcons[i][j]);
            }

            // CREATE NEW CONTAINER FOR GHOST ITEM
            ghostItems[i] = new GhostView(getContext(), ghostLabels[i], iconLayout);
            // ADD GHOST LABEL TO GHOST ITEM
            ghostContainer.addView(ghostItems[i]);
        }

        Log.d("Evidence", "Finished Init Ghost list");
    }


    /**
     * updateGhostsLists
     * <p>
     * <p>
     * <p>
     * Rebuilds the Ghost List
     */
    public void updateGhosts() {

        Log.d("Evidence", "Updating Ghost list");

        // FIND GHOST LIST AND REMOVE CONTAINED VIEWS
        LinearLayout ghostContainer_inner = null;
        if (getView() != null) {
            ghostContainer_inner = getView().findViewById(R.id.layout_ghostList);
            ghostContainer_inner.removeAllViews();
        }

        // CREATE NEW GHOST ARRAY FOR REORDERING ITEMS
        GhostView[] newReorderedList = new GhostView[ghostItems.length];

        // SET VALUES FOR GHOST ITEMS
        for (int i = 0; i < ghostItems.length; i++) {

            int ghostID = ghostItems[i].getID();
            int rating =
                    evidenceViewModel.getInvestigationData().getGhost(ghostID).getEvidenceScore();

            // OLD CODE
            if (ghostLabels[ghostID] != null) {
                //Clear FG and BG Images
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ghostLabels[ghostID].setForeground(null);
                } else {
                    ghostLabels[ghostID].setTextColor(Color.WHITE);
                }
                ghostLabels[ghostID].setBackground(null);

                //Set FG and BG Images based around Evidence Rating
                if (rating == 3) {
                    ghostLabels[ghostID].setBackground(icon_circle);
                } else if (rating <= -3) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ghostLabels[ghostID].setForeground(
                                icons_strikethrough[
                                        (int) (Math.random() * icons_strikethrough.length)]);
                    } else {
                        ghostLabels[ghostID].setTextColor(Color.DKGRAY);
                    }
                }
            }

            //Set Colors of Ghost icons
            for (int j = 0; j < ghostEvidenceIcons[ghostID].length; j++) {
                if (ghostEvidenceIcons[ghostID][j] != null) {
                    ghostEvidenceIcons[ghostID][j].updateColor();
                }
            }

            newReorderedList[i] = ghostItems[i];
        }

        // BUBBLE SORT THE GHOST LIST
        for (int i = 0; i < newReorderedList.length - 1; ) {

            int ratingA = evidenceViewModel.getInvestigationData().getGhost(
                    newReorderedList[i].getID()).getEvidenceScore();
            int ratingB = evidenceViewModel.getInvestigationData().getGhost(
                    newReorderedList[i + 1].getID()).getEvidenceScore();

            if (ratingA < ratingB) {
                GhostView t = newReorderedList[i + 1];
                newReorderedList[i + 1] = newReorderedList[i];
                newReorderedList[i] = t;

                if (i > 0) {
                    i--;
                }
            } else {
                i++;
            }
        }

        // Set Ghost list with reorder
        if (ghostContainer_inner != null) {
            for (GhostView ghostMasterItem : newReorderedList) {
                ghostContainer_inner.addView(ghostMasterItem);
            }
        }

        Log.d("Evidence", "Finished Update Ghost list");

    }


    /**
     * initEvidenceList
     * <p>
     * TODO
     *
     * @param evidenceContainer
     * @param radioButtons_evidence
     */
    public void initEvidence(
            LinearLayout evidenceContainer,
            EvidenceRadioButton[][] radioButtons_evidence) {

        Log.d("Evidence", "Initiating Evidence list");

        // EVIDENCE LIST
        // LOAD CHECKED RADIO BUTTONS
        int[] checkedStorage = null;
        if (evidenceViewModel != null) {
            checkedStorage = evidenceViewModel.getRadioButtonsChecked();
        }

        // LOOP THROUGH EVIDENCE LIST BODY
        for (int i = 0; i < InvestigationData.getEvidenceCount(); i++) {

            // CREATE NEW CONTAINER FOR EVIDENCE ITEM
            evidenceItems[i] = new EvidenceView(getContext());

            // CREATE LABEL FOR EVIDENCE ITEM AND ADD IT
            evidenceItems[i].addView(new EvidenceLabel(getContext(), i));

            // CREATE EVIDENCERADIOGROUP
            for (int j = 0; j < radioButtons_evidence[i].length; j++) {
                radioButtons_evidence[i][j] = new EvidenceRadioButton(
                        getContext(),
                        InvestigationData.getEvidence(i),
                        InvestigationData.Evidence.Ruling.values()[j]);
            }
            evidenceRadioGroups[i] = new EvidenceRadioGroup(
                    getContext(),
                    radioButtons_evidence[i]);

            if (checkedStorage != null) {
                evidenceRadioGroups[i].setCheckedStorage(checkedStorage[i]);
            }
            else {
                evidenceRadioGroups[i].setCheckedStorage(1);
            }
            evidenceItems[i].addView(evidenceRadioGroups[i]);

        }

        // FINALIZE EVIDENCE LIST
        for (EvidenceView evidence_masterItem : evidenceItems) {
            evidenceContainer.addView(evidence_masterItem);
        }

        Log.d("Evidence", "Finished Initiating Ghost list");
    }

    /**
     * updateEvidenceLists
     * <p>
     * <p>
     * <p>
     * Rebuilds the Evidence List
     */
    public void updateEvidence() {

        Log.d("Evidence", "Updating Evidence list");

        // FIND EVIDENCE LIST AND REMOVE CONTAINED VIEWS
        LinearLayout evidenceContainer_inner = null;
        if (getView() != null) {
            evidenceContainer_inner = getView().findViewById(R.id.layout_evidenceList);
            evidenceContainer_inner.removeAllViews();
        }

        AppCompatTextView[] labels_evidence =
                new AppCompatTextView[InvestigationData.getEvidenceCount()];
        EvidenceRadioButton[][] radioButtons_evidence = new EvidenceRadioButton
                [InvestigationData.getEvidenceCount()]
                [InvestigationData.Evidence.Ruling.values().length];

        int[] checkedStorage = getSelectedRadioButtons();
        // Evidence Body
        // Create Evidence group containers
        for (int i = 0; i < InvestigationData.getEvidenceCount(); i++) {
            //Create Evidence group containers
            LinearLayout evidence_labelAndRadios = new LinearLayout(getContext());
            evidence_labelAndRadios.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams evidenceLabelParams =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            evidence_labelAndRadios.setLayoutParams(evidenceLabelParams);
            evidence_labelAndRadios.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

            //create and add evidence labels
            if (getContext() != null) {
                labels_evidence[i] = new AppCompatTextView(getContext());
                labels_evidence[i].setLayoutParams(
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT, 1f));
                labels_evidence[i].setTypeface(font_normal, Typeface.NORMAL);
                labels_evidence[i].setAutoSizeTextTypeUniformWithConfiguration(
                        fontSize[0], fontSize[1], 1,
                        TypedValue.COMPLEX_UNIT_SP);
                labels_evidence[i].setTextColor(Color.WHITE);
                String evidenceName = getResources().getStringArray(R.array.evidence_tool_names)[i];
                labels_evidence[i].setText(evidenceName);
                labels_evidence[i].setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

                //set evidence label listener
                int num = i;
                labels_evidence[i].setOnClickListener(v -> {

                    if (popup != null) {
                        popup.dismiss();
                    }

                    LayoutInflater inflater =
                            (LayoutInflater) getView().getContext().getSystemService(
                                    Context.LAYOUT_INFLATER_SERVICE);
                    @SuppressLint("InflateParams")
                    View customView = inflater.inflate(R.layout.popup_info, null);

                    popup = new PopupWindow(
                            customView,
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.MATCH_PARENT
                    );
                    ImageButton closeButton = customView.findViewById(R.id.popup_close_button);
                    closeButton.setOnClickListener(v1 -> popup.dismiss());

                    AppCompatTextView name = customView.findViewById(R.id.label_queryName);
                    name.setAutoSizeTextTypeUniformWithConfiguration(
                            fontSize[0],
                            50,
                            1,
                            TypedValue.COMPLEX_UNIT_SP);
                    name.setText(evidenceName);
                    AppCompatTextView info = customView.findViewById(R.id.label_queryInfo);
                    info.setAutoSizeTextTypeUniformWithConfiguration(
                            fontSize[0],
                            fontSize[1],
                            1,
                            TypedValue.COMPLEX_UNIT_SP);
                    info.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                            getResources().getStringArray(R.array.evidence_info_array)[num],
                            "ff6161", fontEmphasisColor + "")));

                    popup.setAnimationStyle(R.anim.nav_default_enter_anim);
                    popup.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, 0);

                });

                //add label to evidence container
                evidence_labelAndRadios.addView(labels_evidence[i]);
            }

            //create radio group
            for (int j = 0; j < radioButtons_evidence[i].length; j++) {
                radioButtons_evidence[i][j] = new EvidenceRadioButton(getContext(),
                        InvestigationData.getEvidence(i),
                        InvestigationData.Evidence.Ruling.values()[j]);
                LinearLayout.LayoutParams radioButtonLayoutParams =
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                1f);
                radioButtonLayoutParams.setMargins(0, 0, 0, 0);
                radioButtons_evidence[i][j].setLayoutParams(radioButtonLayoutParams);
                radioButtons_evidence[i][j].setPadding(0, 0, 0, 0);
                radioButtons_evidence[i][j].setBackgroundResource(0);
                radioButtons_evidence[i][j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                radioButtons_evidence[i][j].setAdjustViewBounds(true);
            }

            evidenceRadioGroups[i] = new EvidenceRadioGroup(getContext(), radioButtons_evidence[i]);
            evidenceRadioGroups[i].setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1f));
            evidenceRadioGroups[i].setOrientation(RadioGroup.HORIZONTAL);

            evidenceRadioGroups[i].setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);

            if (checkedStorage != null) {
                evidenceRadioGroups[i].check(checkedStorage[i]);
            } else {
                evidenceRadioGroups[i].check(1);
            }

            //add radio group to evidence container
            evidence_labelAndRadios.addView(evidenceRadioGroups[i]);

            if (evidenceContainer_inner != null) {
                evidenceContainer_inner.addView(evidence_labelAndRadios);
            }
        }

        Log.d("Evidence", "Finished Updating Ghost list");
    }

    /**
     * getSelectedRatioButtons
     * <p>
     * TODO
     *
     * @return int[] of checkedButton ID's
     */
    public int[] getSelectedRadioButtons() {
        int[] selected = new int[evidenceRadioGroups.length];
        for (int i = 0; i < evidenceRadioGroups.length; i++) {
            selected[i] = evidenceRadioGroups[i].getCheckedButtonID();
        }

        return selected;
    }

    /**
     * saveStates
     * <p>
     * TODO
     */
    public void saveStates() {
        if (evidenceViewModel != null) {
            evidenceViewModel.setRadioButtonsChecked(getSelectedRadioButtons());
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

        for (EvidenceRadioGroup g : evidenceRadioGroups) {
            if (g != null) {
                g.reset();
            }
        }

        updateGhosts();
        updateEvidence();

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
     * GhostItem class
     */
    public class GhostView extends LinearLayout {
        private GhostLabel ghostLabel = null;

        /**
         * GhostItem constructor
         *
         * @param context Context
         * @param label   GhostLabel
         * @param icons   GhostIcons
         */
        public GhostView(Context context, GhostLabel label, GhostIcons icons) {
            super(context);


            setOrientation(LinearLayout.HORIZONTAL);
            setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT, 1f));
            setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            setPadding(10, 10, 10, 10);
            //add ghost label and icons to ghost group
            addGhostLabel(label);
            addGhostIcons(icons);
        }

        /**
         * addGhostLabel
         * <p>
         * TODO
         *
         * @param label GhostLabel
         */
        public void addGhostLabel(GhostLabel label) {
            this.ghostLabel = label;
            addView(label);
        }

        /**
         * addGhostIcons
         * <p>
         * TODO
         *
         * @param icons GhostIcons
         */
        public void addGhostIcons(GhostIcons icons) {
            addView(icons);
        }

        /**
         * getID
         * <p>
         * TODO
         *
         * @return id
         */
        public int getID() {
            return ghostLabel.getID();
        }

    }

    /**
     * GhostLabel class
     * <p>
     * TODO
     */
    public class GhostLabel extends androidx.appcompat.widget.AppCompatTextView {
        private int id = -1;

        /**
         * GhostLabel constructor
         * <p>
         * TODO
         *
         * @param context The Context of the current Activity
         * @param id      The ghost label id
         */
        @SuppressLint("RestrictedApi")
        public GhostLabel(@NonNull Context context, int id) {
            super(context);

            setID(id);

            setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
            setTypeface(font_normal, Typeface.NORMAL);

            setAutoSizeTextTypeUniformWithConfiguration(
                    fontSize[0],
                    fontSize[1],
                    1,
                    TypedValue.COMPLEX_UNIT_SP);

            String ghostName =
                    evidenceViewModel.getInvestigationData().getGhosts().get(id).getName();

            setText(ghostName);
            setTextColor(Color.WHITE);
            setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            setMaxHeight(getHeight() / 2);

            setOnClickListener(v -> {
                if (popup != null) {
                    popup.dismiss();
                }

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams")
                View customView = inflater.inflate(R.layout.popup_info, null);
                popup = new PopupWindow(
                        customView,
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );

                AppCompatTextView name = customView.findViewById(R.id.label_queryName);
                name.setAutoSizeTextTypeUniformWithConfiguration(
                        fontSize[0], 50, 1,
                        TypedValue.COMPLEX_UNIT_SP);
                name.setText(ghostName);
                AppCompatTextView info = customView.findViewById(R.id.label_queryInfo);
                info.setAutoSizeTextTypeUniformWithConfiguration(
                        fontSize[0], fontSize[1], 1,
                        TypedValue.COMPLEX_UNIT_SP);
                String[] ghostInfoArray = getResources().getStringArray(R.array.ghost_info_array);
                if (ghostInfoArray.length > id) {
                    info.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                            getResources().getStringArray(R.array.ghost_info_array)[id],
                            "#ff6161", fontEmphasisColor + "")));
                }

                ImageButton closeButton = customView.findViewById(R.id.popup_close_button);
                closeButton.setOnClickListener(v1 -> popup.dismiss());
                popup.setAnimationStyle(R.anim.nav_default_enter_anim);
                popup.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, 0);
            });
        }

        /**
         * setID
         *
         * @param id The ghost label id
         */
        private void setID(int id) {
            this.id = id;
        }

        /**
         * getID
         *
         * @return id
         */
        public int getID() {
            return id;
        }

    }

    /**
     * GhostIcons class
     */
    public static class GhostIcons extends LinearLayout {

        /**
         * GhostIcons constructor
         *
         * @param context The Context of the current Activity
         */
        public GhostIcons(Context context) {
            super(context);

            setOrientation(LinearLayout.HORIZONTAL);
            LayoutParams iconLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT, 1f);
            iconLayoutParams.setMargins(5, 0, 5, 0);
            setLayoutParams(iconLayoutParams);

            setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        }
    }

    /**
     * GhostIcon class
     */
    @SuppressLint("ViewConstructor")
    public static class GhostIcon extends androidx.appcompat.widget.AppCompatImageView {
        private InvestigationData.Evidence evidence;

        /**
         * GhostIcon constructor
         *
         * @param context  The Context of the current Activity
         * @param evidence The Evidence name
         */
        public GhostIcon(Context context, InvestigationData.Evidence evidence) {
            super(context);
            setEvidence(evidence);

            setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            setScaleType(ImageView.ScaleType.FIT_CENTER);
            setAdjustViewBounds(true);
            setPadding(10, 2, 2, 2);
            setImageResource(evidence.getIcon());
            updateColor();
        }

        /**
         * updateColor
         */
        public void updateColor() {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getContext().getTheme();
            if (evidence.getRuling() == InvestigationData.Evidence.Ruling.NEUTRAL) {
                theme.resolveAttribute(R.attr.neutralSelColor, typedValue, true);
                setColorFilter(typedValue.data);
            } else if (evidence.getRuling() == InvestigationData.Evidence.Ruling.NEGATIVE) {
                theme.resolveAttribute(R.attr.negativeSelColor, typedValue, true);
                setColorFilter(typedValue.data);
            } else if (evidence.getRuling() == InvestigationData.Evidence.Ruling.POSITIVE) {
                theme.resolveAttribute(R.attr.positiveSelColor, typedValue, true);
                setColorFilter(typedValue.data);
            }
        }

        /**
         * setEvidence
         *
         * @param evidence The Evidence name
         */
        private void setEvidence(InvestigationData.Evidence evidence) {
            this.evidence = evidence;
        }
    }

    /**
     * GhostIcon class
     */
    public static class EvidenceView extends LinearLayout {

        /**
         * EvidenceItem constructor
         *
         * @param context The Context of the current Activity
         */
        public EvidenceView(Context context) {
            super(context);

            setOrientation(LinearLayout.VERTICAL);
            setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        }
    }

    /**
     * EvidenceLabel class
     */
    public class EvidenceLabel extends AppCompatTextView {

        public EvidenceLabel(Context context, int index) {
            super(context);

            setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1f));
            setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
            setTypeface(font_normal, Typeface.NORMAL);
            setAutoSizeTextTypeUniformWithConfiguration(
                    fontSize[0], fontSize[1], 1,
                    TypedValue.COMPLEX_UNIT_SP);
            setTextColor(Color.WHITE);
            String evidenceName = getResources().getStringArray(R.array.evidence_tool_names)[index];
            setText(evidenceName);

            setOnClickListener(v -> {
                if (popup != null) {
                    popup.dismiss();
                }

                LayoutInflater inflater =
                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams")
                View customView = inflater.inflate(R.layout.popup_info, null);
                popup = new PopupWindow(
                        customView,
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );
                AppCompatImageButton closeButton = customView.findViewById(R.id.popup_close_button);
                closeButton.setOnClickListener(v1 -> popup.dismiss());
                AppCompatTextView name = customView.findViewById(R.id.label_queryName);
                name.setAutoSizeTextTypeUniformWithConfiguration(
                        fontSize[0], 50, 1,
                        TypedValue.COMPLEX_UNIT_SP);
                name.setText(evidenceName);
                AppCompatTextView info = customView.findViewById(R.id.label_queryInfo);
                info.setAutoSizeTextTypeUniformWithConfiguration(
                        fontSize[0], fontSize[1], 1,
                        TypedValue.COMPLEX_UNIT_SP);

                info.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                        getResources().getStringArray(R.array.evidence_info_array)[index],
                        "#ff6161", fontEmphasisColor + "")
                ));
                popup.setAnimationStyle(R.anim.nav_default_enter_anim);
                popup.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, 0);
            });
        }
    }

    /**
     * CRadioGroup class
     */
    public class EvidenceRadioGroup extends LinearLayout {
        private final EvidenceRadioButton[] group;

        /**
         * EvidenceRadioGroup constructor
         *
         * @param context Context
         * @param buttons EvidenceRadioButton array
         */
        public EvidenceRadioGroup(Context context, EvidenceRadioButton[] buttons) {
            super(context);

            setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1f));
            setOrientation(RadioGroup.HORIZONTAL);
            setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);

            group = buttons;
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setRadioGroup(this, i);
                addView(buttons[i]);
            }
        }

        /**
         * setCheckedStorage
         *
         * @param checkedStorage The checked Ruling that's stored before onDestroy
         */
        public void setCheckedStorage(int checkedStorage) {
            check(checkedStorage);
        }

        /**
         * updateGroupImages
         */
        public void updateGroupImages() {

            for (EvidenceRadioButton b : group) {
                b.updateImage();
            }
        }

        /**
         * check
         *
         * @param groupID The group ID of Evidence Rulings that's paired with each Ghost ID
         */
        public void check(int groupID) {
            uncheckAll();
            group[groupID].check();

            updateGroupImages();
        }

        /**
         * uncheck
         *
         * @param index The
         */
        public void uncheck(int index) {
            group[index].uncheck();
        }

        /**
         * uncheckAll
         */
        public void uncheckAll() {
            for (int i = 0; i < group.length; i++) {
                uncheck(i);
            }
        }

        /**
         * getCheckedButtonID
         *
         * @return the id of the CheckedButton
         */
        public int getCheckedButtonID() {
            for (int i = 0; i < group.length; i++) {
                if (group[i].isChecked()) {
                    return i;
                }
            }
            return 1;
        }

        /**
         * reset
         */
        public void reset() {
            uncheckAll();
            check(1);
        }
    }

    /**
     * CRadioButton class
     */
    public class EvidenceRadioButton extends androidx.appcompat.widget.AppCompatImageButton {

        private final InvestigationData.Evidence evidenceType;
        private final InvestigationData.Evidence.Ruling ruling;

        private boolean isChecked = false;
        private final int[] checkedState_images = new int[2];
        private int checkedColor = 0;

        private EvidenceRadioGroup group = null;
        private int groupID = 0;

        /**
         * EvidenceRadioButton constructor
         *
         * @param context      The Context of the current Activity
         * @param evidenceType The Evidence name
         * @param ruling       The Ruling of the Evidence
         */
        public EvidenceRadioButton(
                Context context,
                InvestigationData.Evidence evidenceType,
                InvestigationData.Evidence.Ruling ruling) {
            super(context);

            this.evidenceType = evidenceType;
            this.ruling = ruling;

            LinearLayout.LayoutParams radioButtonLayoutParams =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1f);
            radioButtonLayoutParams.setMargins(0, 0, 0, 0);
            setLayoutParams(radioButtonLayoutParams);
            setPadding(0, 0, 0, 0);
            setBackgroundResource(0);
            setScaleType(ImageView.ScaleType.CENTER_CROP);
            setAdjustViewBounds(true);

            setOnClickListener(v -> {
                if (!isChecked()) {
                    group.check(groupID);
                    doEvidenceAction();

                    updateEvidence();
                    updateGhosts();
                }
            });



            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getContext().getTheme();

            if (this.ruling.name().equals(InvestigationData.Evidence.Ruling.NEGATIVE.name())) {
                theme.resolveAttribute(R.attr.negativeSelColor, typedValue, true);
                checkedColor = typedValue.data;
                setStateImages(
                        R.drawable.icon_negative_unselected,
                        R.drawable.icon_negative_selected);
            } else if (this.ruling.name().equals(InvestigationData.Evidence.Ruling.NEUTRAL.name())) {
                theme.resolveAttribute(R.attr.neutralSelColor, typedValue, true);
                checkedColor = typedValue.data;
                setStateImages(
                        R.drawable.icon_inconclusive_unselected,
                        R.drawable.icon_inconclusive_selected);
            } else if (this.ruling.name().equals(InvestigationData.Evidence.Ruling.POSITIVE.name())) {
                theme.resolveAttribute(R.attr.positiveSelColor, typedValue, true);
                checkedColor = typedValue.data;
                setStateImages(
                        R.drawable.icon_positive_unselected,
                        R.drawable.icon_positive_selected);
            }

            updateImage();

        }

        /**
         * setRadioGroup
         *
         * @param group   The target Radio Button Group
         * @param groupID The ID of the Radio Button Group
         */
        public void setRadioGroup(EvidenceRadioGroup group, int groupID) {
            this.group = group;
            this.groupID = groupID;
        }

        /**
         * doEvidenceAction
         */
        public void doEvidenceAction() {
            if (evidenceViewModel.hasInvestigationData()) {
                evidenceViewModel.getInvestigationData().setEvidenceRuling(evidenceType, ruling);
            }
        }

        /**
         * updateImage
         */
        public void updateImage() {

            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getContext().getTheme();
            theme.resolveAttribute(R.attr.neutralSelColor, typedValue, true);

            if (!isChecked()) {
                setImageResource(checkedState_images[0]);
                setColorFilter(typedValue.data);
            } else {
                setImageResource(checkedState_images[1]);
                setColorFilter(checkedColor);
            }

        }

        /**
         * check
         */
        public void check() {
            isChecked = true;
        }

        /**
         * uncheck
         */
        public void uncheck() {
            isChecked = false;
        }

        /**
         * isChecked
         *
         * @return if the RadioButton is checked
         */
        public boolean isChecked() {
            return isChecked;
        }

        /**
         * setStateImages
         *
         * @param checkedImage   The Resource ID of the checked image
         * @param uncheckedImage The Resource ID of the unchecked image
         */
        public void setStateImages(@DrawableRes int checkedImage, @DrawableRes int uncheckedImage) {

            checkedState_images[0] = checkedImage;
            checkedState_images[1] = uncheckedImage;

        }
    }


    /**
     * onPause
     */
    @Override
    public void onPause() {
        saveStates();

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
