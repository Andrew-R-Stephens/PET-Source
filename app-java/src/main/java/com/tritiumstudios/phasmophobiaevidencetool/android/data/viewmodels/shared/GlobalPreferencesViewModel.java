package com.tritiumstudios.phasmophobiaevidencetool.android.data.viewmodels.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tritiumstudios.phasmophobiaevidencetool.R;
import com.tritiumstudios.phasmophobiaevidencetool.android.data.controllers.ReviewTrackingData;
import com.tritiumstudios.phasmophobiaevidencetool.android.data.controllers.theming.CustomTheme;
import com.tritiumstudios.phasmophobiaevidencetool.android.data.controllers.theming.subsets.ColorThemeControl;
import com.tritiumstudios.phasmophobiaevidencetool.android.data.controllers.theming.subsets.FontThemeControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/** @noinspection SameParameterValue*/
public class GlobalPreferencesViewModel extends SharedViewModel {

    // Review Tracker
    private ReviewTrackingData reviewRequestData;

    // Language
    //private LanguageControl languageControl;
    private String languageName = Locale.getDefault().getLanguage();

    // Persistent Styles
    private FontThemeControl fontThemeControl;
    private ColorThemeControl colorThemeControl;

    // Generic settings
    private int huntWarningFlashTimeout = -1;
    private boolean
            isAlwaysOn = false,
            isHuntAudioAllowed = true,
            networkPreference = true,
            isLeftHandSupportEnabled = false;

    // Title screen increments
    private boolean canShowIntroduction = true;

    @Override
    public void setFileName() {
        fileName = R.string.preferences_globalFile_name;
    }

    /**
     * init method
     *
     * @param context
     */
    public boolean init(@NonNull Context context) {

        setFileName();

        SharedPreferences sharedPref = getSharedPreferences(context);

        setNetworkPreference(sharedPref.getBoolean(context.getResources().getString(R.string.preference_network), getNetworkPreference()));
        setLanguageName(sharedPref.getString(context.getResources().getString(R.string.preference_language), getLanguageName()));
        setIsAlwaysOn(sharedPref.getBoolean(context.getResources().getString(R.string.preference_isAlwaysOn), getIsAlwaysOn()));
        setHuntWarningAudioAllowed(sharedPref.getBoolean(context.getResources().getString(R.string.preference_isHuntAudioWarningAllowed), getIsHuntAudioAllowed()));
        setHuntWarningFlashTimeout(sharedPref.getInt(context.getResources().getString(R.string.preference_huntWarningFlashTimeout), getHuntWarningFlashTimeout()));

        setLeftHandSupportEnabled(sharedPref.getBoolean(context.getResources().getString(R.string.preference_isLeftHandSupportEnabled), getIsLeftHandSupportEnabled()));
        setCanShowIntroduction(sharedPref.getBoolean(context.getResources().getString(R.string.tutorialTracking_canShowIntroduction), getCanShowIntroduction()));

        reviewRequestData = new ReviewTrackingData(
                sharedPref.getBoolean(context.getResources().getString(R.string.reviewtracking_canRequestReview), false),
                sharedPref.getLong(context.getResources().getString(R.string.reviewtracking_appTimeAlive), 0),
                sharedPref.getInt(context.getResources().getString(R.string.reviewtracking_appTimesOpened), 0)
        );

        fontThemeControl = new FontThemeControl(context);
        fontThemeControl.init(
                sharedPref.getString(
                        context.getResources().getString(R.string.preference_savedFont),
                        getFontThemeID()
                )
        );

        colorThemeControl = new ColorThemeControl(context);
        colorThemeControl.init(
                sharedPref.getString(
                        context.getResources().getString(R.string.preference_savedTheme),
                        getColorThemeID()
            )
        );

        saveToFile(context);

        return true;
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

    public void incrementAppOpenCount(@NonNull Context context) {

        getReviewRequestData().incrementTimesOpened();

        try {
            saveTimesOpened(context, getEditor(context), true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

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
    @NonNull
    public String getLanguage(@NonNull Context context) {
        String lang = context.getSharedPreferences(
                context.getResources().getString(fileName), Context.MODE_PRIVATE).getString(
                "chosenLanguage", "en");

        Log.d("Current Chosen Language", lang);

        return lang;
    }

    public int getLanguageIndex(@NonNull ArrayList<String> languageNames) {
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
    public void setLanguage(int position, @NonNull String[] languageNames) {
        if(position < 0 || position >= languageNames.length) { return; }

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
     * getColorSpace method
     *
     * @return ColorSpace
     */
    /*
    public int getColorTheme() {
        return colorThemeControl.getSavedIndex();
    }
    */
    public String getColorThemeID() {
        return colorThemeControl.getID();
    }
    public CustomTheme getColorTheme() {
        return colorThemeControl.getCurrentTheme();
    }

    /**
     * getFontType method
     *
     * @return fontType
     */
    /*
    public int getFontTheme() {
        return fontThemeControl.getSavedIndex();
    }
    */
    public String getFontThemeID() {
        return fontThemeControl.getID();
    }
    public CustomTheme getFontTheme() {
        return fontThemeControl.getCurrentTheme();
    }

    public FontThemeControl getFontThemeControl() {
        return fontThemeControl;
    }

    public ColorThemeControl getColorThemeControl() {
        return colorThemeControl;
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
            @NonNull Context c, @Nullable SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null && (editor = getEditor(c)) == null) { return; }

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
            @NonNull Context c, @Nullable SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null && (editor = getEditor(c)) == null) { return; }

        editor.putString(
                c.getResources().getString(R.string.preference_language),
                getLanguageName());

        if (localApply) {
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
            @NonNull Context c, @Nullable SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null && (editor = getEditor(c)) == null) { return; }

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
            @NonNull Context c, @Nullable SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null && (editor = getEditor(c)) == null) { return; }

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
            @NonNull Context c, @Nullable SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null && (editor = getEditor(c)) == null) { return; }

        editor.putInt(
                c.getResources().getString(R.string.preference_huntWarningFlashTimeout),
                getHuntWarningFlashTimeout());

        if(localApply) {
            editor.apply();
        }
    }

    public void saveColorSpace(@NonNull Context c) {
        saveColorSpace(c, getEditor(c), true);
    }

    /**
     *
     * @param c
     * @param editor
     * @param localApply
     */
    private void saveColorSpace(
            @NonNull Context c, @Nullable SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null && (editor = getEditor(c)) == null) { return; }

        editor.putString(c.getResources().getString(R.string.preference_savedTheme), getColorThemeID());

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
    private void saveFontType(
            @NonNull Context c, @Nullable SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null && (editor = getEditor(c)) == null) { return; }

        editor.putString(c.getResources().getString(R.string.preference_savedFont), getFontThemeID());

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
            @NonNull Context c, @Nullable SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null && (editor = getEditor(c)) == null) { return; }

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
            @NonNull Context c, @Nullable SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null && (editor = getEditor(c)) == null) { return; }

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
            @NonNull Context c, @Nullable SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null && (editor = getEditor(c)) == null) { return; }

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
            @NonNull Context c, @Nullable SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null && (editor = getEditor(c)) == null) { return; }

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
            @NonNull Context c, @Nullable SharedPreferences.Editor editor, boolean localApply) {
        if(editor == null && (editor = getEditor(c)) == null) { return; }

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

        saveNetworkPreference(context, editor, false);
        saveChosenLanguage(context, editor, false);
        saveAlwaysOnState(context, editor, false);
        saveHuntWarningAudioAllowed(context, editor, false);
        saveHuntWarningFlashTimeout(context, editor, false);
        saveColorSpace(context, editor, false);
        saveFontType(context, editor, false);
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
        settings.put("network_pref", String.valueOf(getNetworkPreference()));
        settings.put("language", getLanguageName());
        settings.put("always_on", String.valueOf(getIsAlwaysOn()));
        settings.put("warning_enabled", String.valueOf(getIsHuntAudioAllowed()));
        settings.put("warning_timeout", String.valueOf(getHuntWarningFlashTimeout()));
        settings.put("color_theme", getColorThemeID());
        settings.put("font_type", getFontThemeID());
        settings.put("left_support", String.valueOf(getIsLeftHandSupportEnabled()));
        settings.put("can_show_intro", String.valueOf(getCanShowIntroduction()));
        if(getReviewRequestData() != null) {
            settings.put("review_request", String.valueOf(getReviewRequestData().canRequestReview()));
            settings.put("times_opened", String.valueOf(getReviewRequestData().getTimesOpened()));
            settings.put("active_time", String.valueOf(getReviewRequestData().getTimeActive()));
        }

        return settings;
    }

    /**
     *
     * @param context
     */
    public void printFromFile(@NonNull Context context) {
        SharedPreferences sharedPref = getSharedPreferences(context);

        Log.d(context.getString(fileName),
                "NetworkPreference: " + sharedPref.getBoolean(context.getResources().getString(R.string.preference_network), getNetworkPreference()) +
                "; Language: " + sharedPref.getString(context.getResources().getString(R.string.preference_language), getLanguageName()) +
                "; Always On: " + sharedPref.getBoolean(context.getResources().getString(R.string.preference_isAlwaysOn), getIsAlwaysOn()) +
                "; Is Hunt Audio Allowed: " + sharedPref.getBoolean(context.getResources().getString(R.string.preference_isHuntAudioWarningAllowed), getIsHuntAudioAllowed()) +
                "; Hunt Warning Flash Timeout: " + sharedPref.getInt(context.getResources().getString(R.string.preference_huntWarningFlashTimeout), getHuntWarningFlashTimeout()) +
                "; Color Space: " + sharedPref.getString(context.getResources().getString(R.string.preference_savedTheme), getColorThemeID()) +
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
                "; Color Space: " + getColorThemeID() +
                "; Can Show Introduction: " + getCanShowIntroduction() +
                "; ReviewRequestData: [" + getReviewRequestData().toString() + "]");
    }

}
