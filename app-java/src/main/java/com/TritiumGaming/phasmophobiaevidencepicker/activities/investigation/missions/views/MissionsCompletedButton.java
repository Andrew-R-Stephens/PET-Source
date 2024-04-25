package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.missions.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * ObjectiveCompletedButton class
 *
 * @author Tritium Gaming Studios
 */
public class MissionsCompletedButton extends androidx.appcompat.widget.AppCompatImageButton {

    @Nullable
    private MissionsSpinner objectivesSpinner = null;

    @ColorInt
    private final int[] colorState = {Color.LTGRAY, Color.RED}; // unselected, selected
    private boolean isEnabled = false;

    /**
     * ObjectiveCompletedButton parameterized constructor
     *
     * @param context
     */
    public MissionsCompletedButton(@NonNull Context context) {
        super(context);

        setColorFilter(colorState[0]);

        setOnClickListener(v -> toggleStateAndIcon());
    }

    /**
     * ObjectiveCompletedButton parameterized constructor
     *
     * @param context
     * @param attrs
     */
    public MissionsCompletedButton(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);

        setColorFilter(colorState[0]);

        setOnClickListener(v -> toggleStateAndIcon());
    }

    /**
     * ObjectiveCompletedButton parameterized constructor
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public MissionsCompletedButton(@NonNull Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setColorFilter(colorState[0]);

        setOnClickListener(v -> toggleStateAndIcon());
    }

    /**
     * setColorStates method
     *
     * @param unselected
     * @param selected
     */
    public void setColorStates(@ColorInt int unselected, @ColorInt int selected) {
        colorState[0] = unselected;
        colorState[1] = selected;

        if (isEnabled()) {
            setColorFilter(colorState[1]);
        }
        else {
            setColorFilter(colorState[0]);
        }
    }

    /**
     * getColorStates method
     *
     * @return int array of set colors
     */
    public int[] getColorStates() {
        return colorState;
    }

    /**
     * toggleStateAndIcon method
     */
    public void toggleStateAndIcon() {
        if (isEnabled()) {
            setEnabledState(false);
            setColorFilter(colorState[0]);
        } else {
            setEnabledState(true);
            setColorFilter(colorState[1]);
        }
        objectivesSpinner.setCompleted(isEnabled());
    }

    /**
     * setEnabledState method
     *
     * @param isEnabled
     */
    public void setEnabledState(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * overrideStateAndIcon method
     *
     * @param state
     */
    public void overrideStateAndIcon(boolean state) {
        if (!state) {
            setEnabledState(false);
            setColorFilter(colorState[0]);
        } else {
            setEnabledState(true);
            setColorFilter(colorState[1]);
        }
        objectivesSpinner.setCompleted(isEnabled());
    }

    /**
     * isEnabled method
     *
     * @return if the Button is enabled
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * setSpinner method
     *
     * @param objectivesSpinner
     */
    public void setSpinner(MissionsSpinner objectivesSpinner) {
        this.objectivesSpinner = objectivesSpinner;
    }

}
