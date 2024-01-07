package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.missions;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.InvestigationFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.missions.data.MissionsData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.missions.views.MissionsCompletedButton;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.missions.views.MissionsSpinner;

/**
 * ObjectivesFragment class
 *
 * @author TritiumGamingStudios
 */
public class MissionsFragment extends InvestigationFragment {

    //private EvidenceViewModel evidenceViewModel;
    //private ObjectivesViewModel objectivesViewModel;

    private MissionsData data;

    private MissionsSpinner[] objectiveSpinner;
    private EditText name_input;
    private AppCompatImageButton button_alone, button_everyone;

    /*
    public MissionsFragment(int layout) {
        super(layout);
    }
    */

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_objectives, container, false);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        if(getContext() != null) {
            data = new MissionsData(getContext());
        }

        AppCompatTextView label_alone = view.findViewById(R.id.label_alone);
        AppCompatTextView label_everyone = view.findViewById(R.id.label_everyone);

        // GHOST NAME
        name_input = view.findViewById(R.id.textInput_ghostName);

        // RESPONSE TYPE
        ConstraintLayout blocking_responds = view.findViewById(R.id.blocking_group);
        button_alone = view.findViewById(R.id.button_alone);
        button_everyone = view.findViewById(R.id.button_everyone);

        // COLORS
        @ColorInt int color_unselectedItem = Color.LTGRAY, color_selectedItem = Color.RED;
        TypedValue typedValue = new TypedValue();
        if (getContext() != null && getContext().getTheme() != null) {
            Resources.Theme theme = getContext().getTheme();
            theme.resolveAttribute(R.attr.unselectedColor, typedValue, true);
            color_unselectedItem = typedValue.data;
            theme.resolveAttribute(R.attr.selectedColor, typedValue, true);
            color_selectedItem = typedValue.data;
        }

        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

        // OBJECTIVE BUTTONS
        MissionsCompletedButton[] button_check_evidence = new MissionsCompletedButton[]{
                view.findViewById(R.id.evidence1_button_completed),
                view.findViewById(R.id.evidence2_button_completed),
                view.findViewById(R.id.evidence3_button_completed)};
        for (MissionsCompletedButton ocb : button_check_evidence) {
            if (ocb != null) {
                ocb.setColorStates(color_unselectedItem, color_selectedItem);
            }
        }

        // OBJECTIVE SPINNERS
        objectiveSpinner = new MissionsSpinner[]{
                view.findViewById(R.id.objectives_item_1),
                view.findViewById(R.id.objectives_item_2),
                view.findViewById(R.id.objectives_item_3)
        };

        MissionsData.Objective[] tempObjectives =
                objectivesViewModel.getObjectivesSpinnerObjectives();
        boolean[] tempIsCompleted = objectivesViewModel.getObjectiveCompletion();
        if (objectiveSpinner != null) {
            for (int i = 0; i < objectiveSpinner.length; i++) {
                if (objectiveSpinner[i] != null) {
                    objectiveSpinner[i].setCheckButton(button_check_evidence[i]);
                    objectiveSpinner[i].setStrikeout();
                    button_check_evidence[i].setSpinner(objectiveSpinner[i]);
                    setSpinnerData(objectiveSpinner[i]);

                    if (tempObjectives != null && tempObjectives[i] != null) {
                        objectiveSpinner[i].updateAdapter();
                        objectiveSpinner[i].setCurrentObjective(tempObjectives[i]);
                    }
                }

                if ((tempIsCompleted != null) &&
                        (tempIsCompleted[i]) && objectiveSpinner[i] != null) {
                    objectiveSpinner[i].setObjectiveAsCompleted();
                }

            }
        }

        // GHOST NAME
        String ghostName = objectivesViewModel.getGhostName();
        if (name_input != null && ghostName != null) {
            name_input.setText(ghostName);
        }

        // RESPONDS TO
        final int selC = color_selectedItem, unselC = color_unselectedItem;
        if(evidenceViewModel.getDifficultyCarouselData().isResponseTypeKnown()) {
            if (objectivesViewModel.getResponseState()) {
                button_alone.setColorFilter(selC);
                button_everyone.setColorFilter(unselC);
            } else {
                button_alone.setColorFilter(unselC);
                button_everyone.setColorFilter(selC);
            }
            button_alone.setOnClickListener(v -> {
                        objectivesViewModel.setResponseState(true);
                        button_alone.setColorFilter(selC);
                        button_everyone.setColorFilter(unselC);
                    }
            );
            button_everyone.setOnClickListener(v -> {
                        objectivesViewModel.setResponseState(false);
                        button_everyone.setColorFilter(selC);
                        button_alone.setColorFilter(unselC);
                    }
            );
        } else {
            button_alone.setColorFilter(unselC);
            button_everyone.setColorFilter(unselC);
            label_alone.setTextColor(unselC);
            label_everyone.setTextColor(unselC);
            blocking_responds.setVisibility(View.VISIBLE);
        }

    }

    /*
    protected void initNavListeners(View lstnr_navLeft,
                                  View lstnr_navMedLeft,
                                  View lstnr_navCenter,
                                  View lstnr_navMedRight,
                                  View lstnr_navRight,
                                  AppCompatImageView icon_navLeft,
                                  AppCompatImageView icon_navMedLeft,
                                  AppCompatImageView icon_navCenter,
                                  AppCompatImageView icon_navMedRight,
                                  AppCompatImageView icon_navRight ) {
        if(lstnr_navLeft != null) { }

        if(lstnr_navMedLeft != null) { }

        if(lstnr_navCenter != null) {
            ((View)lstnr_navCenter.getParent()).setVisibility(View.VISIBLE);
            lstnr_navCenter.setOnClickListener(v -> {
                if (objectivesViewModel != null) {
                    objectivesViewModel.reset();
                    objectivesViewModel = null;
                }
                //isAlone = false;
                data = null;

                objectiveSpinner = null;
                if (name_input != null) {
                    name_input.setText("");
                }

                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                if (Build.VERSION.SDK_INT >= 26) {
                    ft.setReorderingAllowed(false);
                }
                ft.detach(MissionsFragment.this).commitNow();
                ft.attach(MissionsFragment.this).commitNow();
                    }
            );
        }

        if(lstnr_navMedRight != null) { }

        if(lstnr_navRight != null) {
            ((View)lstnr_navRight.getParent()).setVisibility(View.VISIBLE);
            icon_navRight.setImageResource(R.drawable.icon_evidence);
            lstnr_navRight.setOnClickListener(v -> Navigation.findNavController(v).popBackStack()
            );
        }

    }
    */

    @Override
    public void softReset() {

    }

    /**
     * setSpinnerData method
     *
     * @param spinner the spinner who's data will be set
     */
    private void setSpinnerData(@NonNull MissionsSpinner spinner) {
        spinner.setData(data);
    }

    /**
     * findObjectiveSpinnerObjective method
     *
     * @return array of Objectives contained within a spinner
     */
    private MissionsData.Objective[] findObjectiveSpinnerObjectives() {

        MissionsData.Objective[] temp = new MissionsData.Objective[objectiveSpinner.length];
        for (int i = 0; i < objectiveSpinner.length; i++) {
            temp[i] = objectiveSpinner[i].getSelectedObjective();
        }
        return temp;

    }

    /**
     * findObjectiveCompletion method
     *
     * @return an array of completed objectives
     */
    private boolean[] findObjectiveCompletion() {

        boolean[] temp = new boolean[objectiveSpinner.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = objectiveSpinner[i].isCompleted();
        }
        return temp;

    }

    /**
     * saveStates method
     */
    public void saveStates() {
        if (objectivesViewModel != null) {
            objectivesViewModel.setObjectiveCompletion(findObjectiveCompletion());
            objectivesViewModel.setObjectivesSpinnerObjectives(findObjectiveSpinnerObjectives());
            objectivesViewModel.setGhostName(name_input.getText().toString());
        }
    }

    @Override
    public void onPause() {
        saveStates();

        super.onPause();
    }

}
