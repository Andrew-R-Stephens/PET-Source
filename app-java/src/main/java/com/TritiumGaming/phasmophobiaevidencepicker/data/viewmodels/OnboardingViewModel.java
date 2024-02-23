package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.ReviewTrackingData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.CustomTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.subsets.ColorThemeControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.subsets.FontThemeControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/** @noinspection SameParameterValue*/
public class OnboardingViewModel extends ViewModel {

    private static final @StringRes int fileName = R.string.preferences_onboardingFile_name;

    private boolean canShowIntroduction = true;

    /**
     * init method
     *
     * @param context
     */
    public void init(Context context) {

        SharedPreferences sharedPref = getSharedPreferences(context);

        setCanShowIntroduction(sharedPref.getBoolean(context.getResources().getString(R.string.onboarding_canShow_intro), getCanShowIntroduction()));

        saveToFile(context);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(
                context.getResources().getString(fileName),
                Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(fileName),
                Context.MODE_PRIVATE);
        return sharedPref.edit();
    }

    public void setCanShowIntroduction(boolean canShowIntroduction) {
        this.canShowIntroduction = canShowIntroduction;
    }

    public boolean getCanShowIntroduction() {
        return canShowIntroduction;
    }
    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    public void saveCanShowIntroduction(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putBoolean(c.getResources().getString(R.string.tutorialTracking_canShowIntroduction),
                getCanShowIntroduction());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     * saveToFile method
     *
     * @param context The Activity context.
     */
    public void saveToFile(Context context) {

        SharedPreferences.Editor editor = getEditor(context);

        saveCanShowIntroduction(context, editor, false);

        editor.apply();
    }

    @NonNull
    public HashMap<String, String> getDataAsList() {
        HashMap<String, String> settings = new HashMap<>();
        settings.put("can_show_intro", String.valueOf(getCanShowIntroduction()));

        return settings;
    }

    /**
     *
     * @param context
     */
    public void printFromFile(Context context) {
        SharedPreferences sharedPref = getSharedPreferences(context);

        Log.d("OnboardingPrefs",
                "Can Show Introduction: " + sharedPref.getBoolean(context.getResources().getString(R.string.tutorialTracking_canShowIntroduction), false));
    }

    /**
     *
     */
    public void printFromVariables() {
        Log.d("GlobalPreferencesVars",
                "; Can Show Introduction: " + getCanShowIntroduction());
    }

}
