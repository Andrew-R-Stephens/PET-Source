package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.missions;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.missions.data.MissionsData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.missions.views.MissionsCompletedButton;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.missions.views.MissionsSpinner;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.ObjectivesViewModel;

/**
 * ObjectivesFragment class
 *
 * @author TritiumGamingStudios
 */
public class MissionsFragment extends Fragment {

    private ObjectivesViewModel objectivesViewModel = null;

    private MissionsData data = null;

    private MissionsSpinner[] objectiveSpinner = null;
    private EditText name_input = null;
    private AppCompatImageButton button_alone = null, button_everyone = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(objectivesViewModel == null)
            objectivesViewModel = new ViewModelProvider(requireActivity()).get(ObjectivesViewModel.class);

        return inflater.inflate(R.layout.fragment_objectives, container, false);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        data = new MissionsData(view.getContext());

        // TITLES
        AppCompatTextView title_optionals = view.findViewById(R.id.label_objectivesTitle);
        AppCompatTextView title_debrief = view.findViewById(R.id.label_debriefTitle);
        // OBJECTIVES
        AppCompatTextView label_objective1 = view.findViewById(R.id.label_objectivetitle1);
        AppCompatTextView label_objective2 = view.findViewById(R.id.label_objectivetitle2);
        AppCompatTextView label_objective3 = view.findViewById(R.id.label_objectivetitle3);
        // DEBRIEF
        AppCompatTextView label_response = view.findViewById(R.id.label_repondsTo);
        AppCompatTextView label_alone = view.findViewById(R.id.label_alone);
        AppCompatTextView label_everyone = view.findViewById(R.id.label_everyone);
        // GHOST NAME
        AppCompatTextView label_ghostName = view.findViewById(R.id.label_ghostName);
        name_input = view.findViewById(R.id.textInput_ghostName);
        // RESPONSE TYPE
        button_alone = view.findViewById(R.id.button_alone);
        button_everyone = view.findViewById(R.id.button_everyone);
        // FOOTERS
        AppCompatImageView icon_goto_left = view.findViewById(R.id.icon_goto_left);
        AppCompatImageView icon_goto_right = view.findViewById(R.id.icon_goto_right);
        AppCompatTextView label_goto_left = view.findViewById(R.id.label_goto_left);
        AppCompatTextView label_goto_right = view.findViewById(R.id.label_goto_right);
        AppCompatTextView label_reset = view.findViewById(R.id.label_resetAll);
        View listener_goto_left = view.findViewById(R.id.listener_goto_left);
        View listener_goto_right = view.findViewById(R.id.listener_goto_right);
        View listener_resetAll = view.findViewById(R.id.listener_resetAll);

        // COLORS
        @ColorInt int color_unselectedItem = Color.LTGRAY, color_selectedItem = Color.RED;
        TypedValue typedValue = new TypedValue();
        if(getContext() != null && getContext().getTheme() != null) {
            Resources.Theme theme = getContext().getTheme();
            theme.resolveAttribute(R.attr.unselectedColor, typedValue, true);
            color_unselectedItem = typedValue.data;
            theme.resolveAttribute(R.attr.selectedColor, typedValue, true);
            color_selectedItem = typedValue.data;
        }

        // TEXT SIZES
        title_optionals.setAutoSizeTextTypeUniformWithConfiguration(20, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        title_debrief.setAutoSizeTextTypeUniformWithConfiguration(20, 50, 1, TypedValue.COMPLEX_UNIT_SP);

        label_objective1.setAutoSizeTextTypeUniformWithConfiguration(12, 24, 1, TypedValue.COMPLEX_UNIT_SP);
        label_objective2.setAutoSizeTextTypeUniformWithConfiguration(12, 24, 1, TypedValue.COMPLEX_UNIT_SP);
        label_objective3.setAutoSizeTextTypeUniformWithConfiguration(12, 24, 1, TypedValue.COMPLEX_UNIT_SP);

        label_ghostName.setAutoSizeTextTypeUniformWithConfiguration(12, 24, 1, TypedValue.COMPLEX_UNIT_SP);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            name_input.setAutoSizeTextTypeUniformWithConfiguration(12, 24, 1, TypedValue.COMPLEX_UNIT_SP);
        else
            name_input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f);
        label_response.setAutoSizeTextTypeUniformWithConfiguration(12, 24, 1, TypedValue.COMPLEX_UNIT_SP);
        label_alone.setAutoSizeTextTypeUniformWithConfiguration(12, 24, 1, TypedValue.COMPLEX_UNIT_SP);
        label_everyone.setAutoSizeTextTypeUniformWithConfiguration(12, 24, 1, TypedValue.COMPLEX_UNIT_SP);
        label_reset.setAutoSizeTextTypeUniformWithConfiguration(5, 25, 1, TypedValue.COMPLEX_UNIT_SP);

        label_goto_right.setAutoSizeTextTypeUniformWithConfiguration(10, 50, 1, TypedValue.COMPLEX_UNIT_SP);

        // LISTENERS
        listener_resetAll.setOnClickListener(v -> {

                    if (objectivesViewModel != null) {
                        objectivesViewModel.reset();
                        objectivesViewModel = null;
                    }
                    //isAlone = false;
                    data = null;

                    objectiveSpinner = null;
                    if (name_input != null)
                        name_input.setText("");

                    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                    if (Build.VERSION.SDK_INT >= 26) {
                        ft.setReorderingAllowed(false);
                    }
                    ft.detach(MissionsFragment.this).commitNow();
                    ft.attach(MissionsFragment.this).commitNow();
                }
        );
        listener_goto_right.setOnClickListener(v -> Navigation.findNavController(v).popBackStack()
        );

        // SET VIEWS DISABLED
        listener_goto_left.setEnabled(false);
        label_goto_left.setEnabled(false);
        icon_goto_left.setEnabled(false);
        listener_goto_left.setVisibility(View.INVISIBLE);
        label_goto_left.setVisibility(View.INVISIBLE);
        icon_goto_left.setVisibility(View.INVISIBLE);

        // SET NAVIGATION ITEMS
        label_goto_right.setText(R.string.general_evidence_button);
        icon_goto_right.setImageResource(R.drawable.icon_evidence);

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

        MissionsData.Objective[] tempObjectives = objectivesViewModel.getObjectivesSpinnerObjectives();
        boolean[] tempIsCompleted = objectivesViewModel.getObjectiveCompletion();
        if(objectiveSpinner != null)
            for (int i = 0; i < objectiveSpinner.length; i++) {
                if(objectiveSpinner[i] != null) {
                    objectiveSpinner[i].setCheckButton(button_check_evidence[i]);
                    objectiveSpinner[i].setStrikeout();
                    button_check_evidence[i].setSpinner(objectiveSpinner[i]);
                    setSpinnerData(objectiveSpinner[i]);

                    if(tempObjectives != null && tempObjectives[i] != null) {
                        objectiveSpinner[i].updateAdapter();
                        objectiveSpinner[i].setCurrentObjective(tempObjectives[i]);
                    }
                }

                if((tempIsCompleted != null) && (tempIsCompleted[i]) && objectiveSpinner[i] != null)
                    objectiveSpinner[i].setObjectiveAsCompleted();

            }

        // GHOST NAME
        String ghostName = objectivesViewModel.getGhostName();
        if(name_input != null && ghostName != null)
            name_input.setText(ghostName);

        // RESPONDS TO
        final int selC = color_selectedItem, unselC = color_unselectedItem;
        if(objectivesViewModel.getResponseState()) {
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

    }

    /**
     * setSpinnerData method
     * @param spinner the spinner who's data will be set
     */
    private void setSpinnerData(@NonNull MissionsSpinner spinner) {
        spinner.setData(data);
    }

    /**
     * findObjectiveSpinnerObjective method
     * @return array of Objectives contained within a spinner
     */
    private MissionsData.Objective[] findObjectiveSpinnerObjectives(){

        MissionsData.Objective[] temp = new MissionsData.Objective[objectiveSpinner.length];
        for(int i = 0; i < objectiveSpinner.length; i++){
            temp[i] = objectiveSpinner[i].getSelectedObjective();
        }
        return temp;

    }

    /**
     * findObjectiveCompletion method
     * @return an array of completed objectives
     */
    private boolean[] findObjectiveCompletion(){

        boolean[] temp = new boolean[objectiveSpinner.length];
        for(int i = 0; i < temp.length; i++)
            temp[i] = objectiveSpinner[i].isCompleted();
        return temp;

    }

    /**
     * saveStates method
     */
    public void saveStates(){
        if(objectivesViewModel != null) {
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
