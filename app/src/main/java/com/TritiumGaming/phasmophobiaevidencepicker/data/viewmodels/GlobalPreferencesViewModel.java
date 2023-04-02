package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.ReviewTrackingData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class GlobalPreferencesViewModel extends ViewModel {

    private ReviewTrackingData reviewRequestData;

    private String languageName = Locale.getDefault().getLanguage();

    private int colorSpace = 0;
    private int huntWarningFlashTimeout = -1;

    private boolean isAlwaysOn = false;
    private boolean isHuntAudioAllowed = true;
    private boolean networkPreference = true;
    private boolean isLeftHandSupportEnabled = false;

    private boolean canShowIntroduction = true;

    /**
     * init method
     *
     * @param context
     */
    public void init(Context context) {

        SharedPreferences sharedPref = getSharedPreferences(context);

        setNetworkPreference(sharedPref.getBoolean(context.getResources().getString(R.string.preference_network), getNetworkPreference()));
        setLanguageName(sharedPref.getString(context.getResources().getString(R.string.preference_language), getLanguageName()));
        setIsAlwaysOn(sharedPref.getBoolean(context.getResources().getString(R.string.preference_isAlwaysOn), getIsAlwaysOn()));
        setHuntWarningAudioAllowed(sharedPref.getBoolean(context.getResources().getString(R.string.preference_isHuntAudioWarningAllowed), getIsHuntAudioAllowed()));
        setHuntWarningFlashTimeout(sharedPref.getInt(context.getResources().getString(R.string.preference_huntWarningFlashTimeout), getHuntWarningFlashTimeout()));
        setColorSpace(sharedPref.getInt(context.getResources().getString(R.string.preference_colorSpace), getColorSpace()));
        setLeftHandSupportEnabled(sharedPref.getBoolean(context.getResources().getString(R.string.preference_isLeftHandSupportEnabled), getIsLeftHandSupportEnabled()));
        setLanguageName(sharedPref.getString(context.getResources().getString(R.string.preference_language), getLanguageName()));
        setLanguageName(sharedPref.getString(context.getResources().getString(R.string.preference_language), getLanguageName()));
        setCanShowIntroduction(sharedPref.getBoolean(context.getResources().getString(R.string.tutorialTracking_canShowIntroduction), getCanShowIntroduction()));

        reviewRequestData = new ReviewTrackingData(
                sharedPref.getBoolean(context.getResources().getString(R.string.reviewtracking_canRequestReview), false),
                sharedPref.getLong(context.getResources().getString(R.string.reviewtracking_appTimeAlive), 0),
                sharedPref.getInt(context.getResources().getString(R.string.reviewtracking_appTimesOpened), 0)
        );

        saveToFile(context);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(
                context.getResources().getString(R.string.preferences_globalFile_name),
                Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(
                        R.string.preferences_globalFile_name),
                Context.MODE_PRIVATE);
        return sharedPref.edit();
    }

    public void setLeftHandSupportEnabled(boolean isLeftHandSupportEnabled) {
        this.isLeftHandSupportEnabled = isLeftHandSupportEnabled;
    }

    public boolean getIsLeftHandSupportEnabled() {
        return isLeftHandSupportEnabled;
    }

    public void setNetworkPreference(boolean preference) {
        this.networkPreference = preference;
    }

    public boolean getNetworkPreference() {
        return networkPreference;
    }

    public void incrementAppOpenCount(Context context) {

        getReviewRequestData().incrementTimesOpened();

        saveTimesOpened(context, getEditor(context), true);

    }

    /**
     * getReviewRequestData method
     *
     * @return reviewRequestData
     */
    public ReviewTrackingData getReviewRequestData() {
        return reviewRequestData;
    }


    /**
     * setLanguageName method
     *
     * @param languageName
     */
    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    /**
     * getLanguageName method
     *
     * @return languageName
     */
    public String getLanguageName() {
        return languageName;
    }

    /**
     * getLanguage
     * <p>
     * Gets the language saved to GlobalPreferences.
     * Defaults to return 'en' if there is no previously saved preference.
     *
     * @return The language specified in the Preferences data, or otherwise English
     */
    public String getLanguage(Context context) {
        String lang = context.getSharedPreferences(
                context.getResources().getString(
                        R.string.preferences_globalFile_name), Context.MODE_PRIVATE).getString(
                "chosenLanguage", "en");

        Log.d("Current Chosen Language", lang);

        return lang;
    }

    public int getLanguageIndex(ArrayList<String> languageNames) {
        for(int i = 0; i < languageNames.size(); i++) {
            if(getLanguageName().equalsIgnoreCase(languageNames.get(i))) {
                return i;
            }
        }
        return 0;
    }

    /**
     * setLanguage method
     *
     * @param position
     * @param languageNames
     */
    public void setLanguage(int position, String[] languageNames) {
        setLanguageName(languageNames[position]);
    }


    /**
     * getIsAlwaysOn method
     *
     * @return isAlwaysOn
     */
    public boolean getIsAlwaysOn() {
        return isAlwaysOn;
    }

    /**
     * setIsAlwaysOn method
     *
     * @param isAlwaysOn
     */
    public void setIsAlwaysOn(boolean isAlwaysOn) {
        this.isAlwaysOn = isAlwaysOn;
    }

    /**
     * setHuntWarningAudioAllowed method
     *
     * @param isAllowed
     */
    public void setHuntWarningAudioAllowed(boolean isAllowed) {
        isHuntAudioAllowed = isAllowed;
    }

    /**
     * getIsHuntAudioAllowed method
     *
     * @return isHuntAudioAllowed
     */
    public boolean getIsHuntAudioAllowed() {
        return isHuntAudioAllowed;
    }

    /**
     * @return
     */
    public boolean isHuntWarningAudioAllowed() {
        return isHuntAudioAllowed;
    }

    /**
     * setHuntWarningFlashTimeout method
     *
     * @param timeout
     */
    public void setHuntWarningFlashTimeout(int timeout) {
        huntWarningFlashTimeout = timeout;
    }

    /**
     * getHuntWarningFlashTimeout method
     *
     * @return huntWarningFlashTimeout
     */
    public int getHuntWarningFlashTimeout() {
        return huntWarningFlashTimeout;
    }

    /**
     * setColorSpace method
     *
     * @param colorSpace
     */
    public void setColorSpace(int colorSpace) {
        this.colorSpace = colorSpace;
    }

    /**
     * getColorSpace method
     *
     * @return ColorSpace
     */
    public int getColorSpace() {
        return colorSpace;
    }

    public void setCanShowIntroduction(boolean canShowIntroduction) {
        this.canShowIntroduction = canShowIntroduction;
    }

    public boolean getCanShowIntroduction() {
        return canShowIntroduction;
    }

    public boolean canShowIntroduction() {
        return canShowIntroduction && reviewRequestData.getTimesOpened() <= 1;
    }


    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveNetworkPreference(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putBoolean(
                c.getResources().getString(R.string.preference_network),
                getNetworkPreference());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveChosenLanguage(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putString(
                c.getResources().getString(R.string.preference_language),
                getLanguageName());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveAlwaysOnState(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putBoolean(
                c.getResources().getString(R.string.preference_isAlwaysOn),
                getIsAlwaysOn());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveHuntWarningAudioAllowed(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putBoolean(
                c.getResources().getString(R.string.preference_isHuntAudioWarningAllowed),
                getIsHuntAudioAllowed());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveHuntWarningFlashTimeout(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putInt(
                c.getResources().getString(R.string.preference_huntWarningFlashTimeout),
                getHuntWarningFlashTimeout());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveColorSpace(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putInt(c.getResources().getString(R.string.preference_colorSpace), getColorSpace());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveAppTimeAlive(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putLong(c.getResources().getString(R.string.reviewtracking_appTimeAlive),
                getReviewRequestData().getTimeActive());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveTimesOpened(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putInt(c.getResources().getString(R.string.reviewtracking_appTimesOpened),
                getReviewRequestData().getTimesOpened());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveCanRequestReview(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putBoolean(c.getResources().getString(R.string.reviewtracking_canRequestReview),
                getReviewRequestData().getWasRequested());

        if(localApply) {
            editor.apply();
        }
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveIsLeftHandSupportEnabled(
            Context c, SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null) {
            editor = getEditor(c);
        }

        editor.putBoolean(c.getResources().getString(R.string.preference_isLeftHandSupportEnabled),
                getIsLeftHandSupportEnabled());

        if(localApply) {
            editor.apply();
        }
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

        saveNetworkPreference(context, editor, false);
        saveChosenLanguage(context, editor, false);
        saveAlwaysOnState(context, editor, false);
        saveHuntWarningAudioAllowed(context, editor, false);
        saveHuntWarningFlashTimeout(context, editor, false);
        saveColorSpace(context, editor, false);
        saveIsLeftHandSupportEnabled(context, editor, false);
        saveCanRequestReview(context, editor, false);
        saveTimesOpened(context, editor, false);
        saveAppTimeAlive(context, editor, false);
        saveCanShowIntroduction(context, editor, false);

        editor.apply();
    }

    @NonNull
    public HashMap<String, String> getDataAsList() {
        HashMap<String, String> settings = new HashMap<>();
        settings.put("network_pref", getNetworkPreference()+"");
        settings.put("language", getLanguageName());
        settings.put("always_on", getIsAlwaysOn()+"");
        settings.put("warning_enabled", getIsHuntAudioAllowed()+"");
        settings.put("warning_timeout", getHuntWarningFlashTimeout()+"");
        settings.put("color_theme", getColorSpace()+"");
        settings.put("left_support", getIsLeftHandSupportEnabled()+"");
        settings.put("can_show_intro", getCanShowIntroduction()+"");
        if(getReviewRequestData() != null) {
            settings.put("review_request", getReviewRequestData().canRequestReview()+"");
            settings.put("times_opened", getReviewRequestData().getTimesOpened()+"");
            settings.put("active_time", getReviewRequestData().getTimeActive()+"");
        }

        return settings;
    }

    /**
     *
     * @param context
     */
    public void printFromFile(Context context) {
        SharedPreferences sharedPref = getSharedPreferences(context);

        Log.d("GlobalPreferencesFile",
                "NetworkPreference: " + sharedPref.getBoolean(context.getResources().getString(R.string.preference_network), getNetworkPreference()) +
                "; Language: " + sharedPref.getString(context.getResources().getString(R.string.preference_language), getLanguageName()) +
                "; Always On: " + sharedPref.getBoolean(context.getResources().getString(R.string.preference_isAlwaysOn), getIsAlwaysOn()) +
                "; Is Hunt Audio Allowed: " + sharedPref.getBoolean(context.getResources().getString(R.string.preference_isHuntAudioWarningAllowed), getIsHuntAudioAllowed()) +
                "; Hunt Warning Flash Timeout: " + sharedPref.getInt(context.getResources().getString(R.string.preference_huntWarningFlashTimeout), getHuntWarningFlashTimeout()) +
                "; Color Space: " + sharedPref.getInt(context.getResources().getString(R.string.preference_colorSpace), getColorSpace()) +
                "; ReviewRequestData: [" +
                "Time Alive: " + sharedPref.getLong(context.getResources().getString(R.string.reviewtracking_appTimeAlive), 0) +
                "; Times Opened: " + sharedPref.getInt(context.getResources().getString(R.string.reviewtracking_appTimesOpened), 0) +
                "; Can Request Review: " + sharedPref.getBoolean(context.getResources().getString(R.string.reviewtracking_canRequestReview), false) + "]" +
                "; Can Show Introduction: " + sharedPref.getBoolean(context.getResources().getString(R.string.tutorialTracking_canShowIntroduction), false));
    }

    /**
     *
     */
    public void printFromVariables() {
        Log.d("GlobalPreferencesVars",
                "NetworkPreference: " + getNetworkPreference() +
                "; Language: " + getLanguageName() +
                "; Always On: " + getIsAlwaysOn() +
                "; Is Hunt Audio Allowed: " + getIsHuntAudioAllowed() +
                "; Hunt Warning Flash Timeout: " + getHuntWarningFlashTimeout() +
                "; Color Space: " +getColorSpace() +
                "; Can Show Introduction: " + getCanShowIntroduction() +
                "; ReviewRequestData: [" + getReviewRequestData().toString() + "]");
    }
}
