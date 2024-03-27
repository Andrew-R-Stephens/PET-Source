package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

import java.util.HashMap;

/** @noinspection SameParameterValue*/
public class OnboardingViewModel extends SharedViewModel {

    private boolean canShowIntroduction = true;

    @Override
    public void setFileName() {
        fileName = R.string.preferences_onboardingFile_name;
    }

    public boolean init(@NonNull Context context) {

        setFileName();

        SharedPreferences sharedPref = getSharedPreferences(context);

        setCanShowIntroduction(sharedPref.getBoolean(context.getResources().getString(R.string.onboarding_canShow_intro), getCanShowIntroduction()));

        saveToFile(context);

        return true;
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
            @NonNull Context c, @Nullable SharedPreferences.Editor editor, boolean localApply) {
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
    public void saveToFile(@NonNull Context context) {

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
    public void printFromFile(@NonNull Context context) {
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
