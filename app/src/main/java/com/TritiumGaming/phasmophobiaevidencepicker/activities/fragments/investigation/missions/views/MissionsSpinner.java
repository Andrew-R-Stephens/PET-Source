package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.missions.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.missions.data.MissionsData;

import java.util.ArrayList;

/**
 * ObjectivesSpinner class
 *
 * @author TritiumGamingStudios
 */
public class MissionsSpinner extends androidx.appcompat.widget.AppCompatSpinner{

    private MissionsData data = null;
    private MissionsData.Objective currentObjective = null;

    private MissionsCompletedButton checkButton = null;
    private final Drawable[] strikeout = new Drawable[2];

    /**
     *
     * @param context
     */
    public MissionsSpinner(Context context) {
        super(context);
        init();
    }

    /**
     *
     * @param context
     * @param mode
     */
    public MissionsSpinner(@NonNull Context context, int mode) {
        super(context, mode);
        init();
    }

    /**
     *
     * @param context
     * @param attrs
     */
    public MissionsSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     *
     * @param context
     * @param attrs
     * @param data
     */
    public MissionsSpinner(@NonNull Context context, @Nullable AttributeSet attrs, MissionsData data) {
        super(context, attrs);
        init();
    }

    /**
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MissionsSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param mode
     */
    public MissionsSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
        init();
    }

    /**
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param mode
     * @param popupTheme
     */
    public MissionsSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
        init();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean performClick() {
        updateAdapter();

        return super.performClick();
    }

    /**
     *
     */
    public void updateAdapter() {
        ArrayList<MissionsData.Objective> obtainedObjectives = data.getObjectivesOfSelectedState(false);
        ArrayAdapter<MissionsData.Objective> adapter = new ArrayAdapter<>(super.getContext(), R.layout.popup_spinner, obtainedObjectives);
        setAdapter(adapter);
    }

    /**
     *
     */
    public void init(){
        setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(currentObjective != null)
                    currentObjective.setSelected(false);
                currentObjective = (MissionsData.Objective)parent.getItemAtPosition(position);
                currentObjective.setSelected(true);
                currentObjective.setPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     *
     * @param data
     */
    public void setData(MissionsData data){
        this.data = data;
    }

    /**
     *
     * @param isCompleted
     */
    public void setCompleted(boolean isCompleted){
        if(isCompleted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setForeground(strikeout[0]);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setForeground(null);
            }
        }
    }

    /**
     *
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public void setStrikeout(){
        strikeout[0] = super.getContext().getDrawable(R.drawable.icon_strikethrough_1);
        //strikeout[0].setTint(Color.RED);
        strikeout[0].setTint(checkButton.getColorStates()[1]);
    }

    /**
     *
     * @param checkButton
     */
    public void setCheckButton(MissionsCompletedButton checkButton) {
        this.checkButton = checkButton;
    }

    /**
     *
     * @return
     */
    public MissionsData.Objective getSelectedObjective() {
        return currentObjective;
    }

    /**
     *
     * @param currentObjective
     */
    public void setCurrentObjective(MissionsData.Objective currentObjective){
        this.currentObjective = data.getCopyOfObjective(currentObjective);

        if(currentObjective != null)
            setSelection(this.currentObjective.getPosition());
    }

    /**
     *
     * @return
     */
    public boolean isCompleted(){
        return checkButton.isEnabled();
    }

    /**
     *
     */
    public void setObjectiveAsCompleted() {
        checkButton.overrideStateAndIcon(true);
    }

}
