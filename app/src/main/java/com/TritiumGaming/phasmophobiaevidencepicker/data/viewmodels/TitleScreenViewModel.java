package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.ReviewTrackingData;
import com.google.android.gms.ads.AdRequest;
import com.TritiumGaming.phasmophobiaevidencepicker.data.data.AnimationData;

import java.util.Locale;

/**
 * TitleScreenViewModel class
 *
 * @author TritiumGamingStudios
 */
public class TitleScreenViewModel extends ViewModel {

    //private int latestDatabaseVersion = 0;

    private final AnimationData animationData = new AnimationData();
    private ReviewTrackingData reviewRequestData;

    private AdRequest adRequest = null;

    private String languageName = Locale.getDefault().getLanguage();
    private int colorSpace = 0;

    private boolean isAlwaysOn = false, isHuntAudioAllowed = true;
    private int huntWarningFlashTimeout = -1;

    private boolean canRefresh = true;

    /**
     * init method
     * @param context
     */
    public void init(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences(context.getResources().getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE);

        //setLatestDatabaseVersion(sharedPref.getInt(context.getResources().getString(R.string.preference_latestDatabaseVersion), -1));
        setLanguageName(sharedPref.getString(context.getResources().getString(R.string.preference_language), getLanguageName()));
        setIsAlwaysOn(sharedPref.getBoolean(context.getResources().getString(R.string.preference_isAlwaysOn), getIsAlwaysOn()));
        setAllowHuntWarningAudio(sharedPref.getBoolean(context.getResources().getString(R.string.preference_isHuntAudioWarningAllowed), getIsHuntAudioAllowed()));
        setHuntWarningFlashTimeout(sharedPref.getInt(context.getResources().getString(R.string.preference_huntWarningFlashTimeout), getHuntWarningFlashTimeout()));
        setColorSpace(sharedPref.getInt(context.getResources().getString(R.string.preference_colorSpace), getColorSpace()));

        reviewRequestData = new ReviewTrackingData(
                sharedPref.getBoolean(context.getResources().getString(R.string.reviewtracking_canRequestReview), false),
                sharedPref.getLong(context.getResources().getString(R.string.reviewtracking_appTimeAlive), 0),
                sharedPref.getInt(context.getResources().getString(R.string.reviewtracking_appTimesOpened), 0)
        );

        saveToFile(context);

    }

    /*
    public void setLatestDatabaseVersion(int version){
        this.latestDatabaseVersion = version;
    }

    public int getLatestDatabaseVersion() {
        return latestDatabaseVersion;
    }
    */

    /**
     * getReviewRequestData method
     * @return reviewRequestData
     */
    public ReviewTrackingData getReviewRequestData(){
        return reviewRequestData;
    }

    /**
     * getAnimatedData method
     * @return animationData
     */
    public AnimationData getAnimationData(){
        return animationData;
    }

    /**
     * hasAdRequest method
     * @return is adRequest null
     */
    public boolean hasAdRequest(){
        return adRequest != null;
    }

    /**
     * setAdRequest method
     * @param adRequest
     */
    public void setAdRequest(AdRequest adRequest){
        this.adRequest = adRequest;
    }

    /**
     * getAdRequest method
     * @return AdRequest
     */
    public AdRequest getAdRequest(){
        return adRequest;
    }

    /**
     * setLanguage method
     * @param position
     * @param languageNames
     */
    public void setLanguage(int position, String[] languageNames) { setLanguageName(languageNames[position]); }

    /**
     * setLanguageName method
     * @param languageName
     */
    public void setLanguageName(String languageName){
        this.languageName = languageName;
    }

    /**
     * getLanguageName method
     * @return languageName
     */
    public String getLanguageName(){
        return languageName;
    }

    /**
     * setCanRefresh method
     * @param canRefresh
     */
    public void setCanRefresh(boolean canRefresh){
        this.canRefresh = canRefresh;
    }

    /**
     * canRefresh method
     * @return canRefresh
     */
    public boolean canRefresh(){
        return canRefresh;
    }

    /**
     * getIsAlwaysOn method
     * @return isAlwaysOn
     */
    public boolean getIsAlwaysOn() {
        return isAlwaysOn;
    }

    /**
     * setIsAlwaysOn method
     * @param isAlwaysOn
     */
    public void setIsAlwaysOn(boolean isAlwaysOn) {
        this.isAlwaysOn = isAlwaysOn;
    }

    /**
     * setAllowHuntWarningAudio method
     * @param isAllowed
     */
    public void setAllowHuntWarningAudio(boolean isAllowed) {
        isHuntAudioAllowed = isAllowed;
    }

    /**
     * getIsHuntAudioAllowed method
     * @return isHuntAudioAllowed
     */
    public boolean getIsHuntAudioAllowed(){
        return isHuntAudioAllowed;
    }

    /**
     * setHuntWarningFlashTimeout method
     * @param timeout
     */
    public void setHuntWarningFlashTimeout(int timeout){
        huntWarningFlashTimeout = timeout;
    }

    /**
     * getHuntWarningFlashTimeout method
     * @return huntWarningFlashTimeout
     */
    public int getHuntWarningFlashTimeout(){
        return huntWarningFlashTimeout;
    }

    /**
     * setColorSpace method
     * @param colorSpace
     */
    public void setColorSpace(int colorSpace){
        this.colorSpace = colorSpace;
    }

    /**
     * getColorSpace method
     * @return ColorSpace
     */
    public int getColorSpace(){
        return colorSpace;
    }

    /**
     * saveToFile method
     * @param c
     */
    public void saveToFile(Context c) {

        SharedPreferences sharedPref = c.getSharedPreferences(c.getResources().getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //editor.putInt(c.getResources().getString(R.string.preference_latestDatabaseVersion), getLatestDatabaseVersion());
        editor.putString(c.getResources().getString(R.string.preference_language), getLanguageName());
        editor.putBoolean(c.getResources().getString(R.string.preference_isAlwaysOn), getIsAlwaysOn());
        editor.putBoolean(c.getResources().getString(R.string.preference_isHuntAudioWarningAllowed), getIsHuntAudioAllowed());
        editor.putInt(c.getResources().getString(R.string.preference_huntWarningFlashTimeout), getHuntWarningFlashTimeout());
        editor.putInt(c.getResources().getString(R.string.preference_colorSpace), getColorSpace());

        editor.putBoolean(c.getResources().getString(R.string.reviewtracking_canRequestReview), getReviewRequestData().getWasRequested());
        editor.putInt(c.getResources().getString(R.string.reviewtracking_appTimesOpened), getReviewRequestData().getTimesOpened());
        editor.putLong(c.getResources().getString(R.string.reviewtracking_appTimeAlive), getReviewRequestData().getTimeActive());

        editor.apply();

    }
}