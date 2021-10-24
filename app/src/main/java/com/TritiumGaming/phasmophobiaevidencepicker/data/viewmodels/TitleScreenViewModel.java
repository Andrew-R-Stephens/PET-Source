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
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class TitleScreenViewModel extends ViewModel {

    private final AnimationData animationData = new AnimationData();
    private ReviewTrackingData reviewRequestData;

    private AdRequest adRequest = null;

    private String languageName = Locale.getDefault().getLanguage();
    private int colorSpace = 0;

    private boolean isAlwaysOn = false, isHuntAudioAllowed = true;
    private int huntWarningFlashTimeout = -1;

    private boolean canRefresh = true;

    /**
     * init
     *
     * TODO
     *
     * @param context
     */
    public void init(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences(context.getResources().getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE);

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

    /**
     * getReviewRequestData
     *
     * TODO
     *
     * @return reviewRequestData
     */
    public ReviewTrackingData getReviewRequestData(){
        return reviewRequestData;
    }

    /**
     * getAnimatedData
     *
     * TODO
     *
     * @return animationData
     */
    public AnimationData getAnimationData(){
        return animationData;
    }

    /**
     * hasAdRequest
     *
     * TODO
     *
     * @return is adRequest null
     */
    public boolean hasAdRequest(){
        return adRequest != null;
    }

    /**
     * setAdRequest
     *
     * TODO
     *
     * @param adRequest
     */
    public void setAdRequest(AdRequest adRequest){
        this.adRequest = adRequest;
    }

    /**
     * getAdRequest
     *
     * TODO
     *
     * @return AdRequest
     */
    public AdRequest getAdRequest(){
        return adRequest;
    }

    /**
     * setLanguage
     *
     * TODO
     *
     * @param position
     * @param languageNames
     */
    public void setLanguage(int position, String[] languageNames) { setLanguageName(languageNames[position]); }

    /**
     * setLanguageName
     *
     * TODO
     *
     * @param languageName
     */
    public void setLanguageName(String languageName){
        this.languageName = languageName;
    }

    /**
     * getLanguageName
     *
     * TODO
     *
     * @return languageName
     */
    public String getLanguageName(){
        return languageName;
    }

    /**
     * setCanRefresh
     *
     * TODO
     *
     * @param canRefresh
     */
    public void setCanRefresh(boolean canRefresh){
        this.canRefresh = canRefresh;
    }

    /**
     * canRefresh
     *
     * TODO
     *
     * @return canRefresh
     */
    public boolean canRefresh(){
        return canRefresh;
    }

    /**
     * getIsAlwaysOn
     *
     * TODO
     *
     * @return isAlwaysOn
     */
    public boolean getIsAlwaysOn() {
        return isAlwaysOn;
    }

    /**
     * setIsAlwaysOn
     *
     * TODO
     *
     * @param isAlwaysOn
     */
    public void setIsAlwaysOn(boolean isAlwaysOn) {
        this.isAlwaysOn = isAlwaysOn;
    }

    /**
     * setAllowHuntWarningAudio
     *
     * TODO
     *
     * @param isAllowed
     */
    public void setAllowHuntWarningAudio(boolean isAllowed) {
        isHuntAudioAllowed = isAllowed;
    }

    /**
     * getIsHuntAudioAllowed
     *
     * TODO
     *
     * @return isHuntAudioAllowed
     */
    public boolean getIsHuntAudioAllowed(){
        return isHuntAudioAllowed;
    }

    /**
     * setHuntWarningFlashTimeout
     *
     * TODO
     *
     * @param timeout
     */
    public void setHuntWarningFlashTimeout(int timeout){
        huntWarningFlashTimeout = timeout;
    }

    /**
     * getHuntWarningFlashTimeout
     *
     * TODO
     *
     * @return huntWarningFlashTimeout
     */
    public int getHuntWarningFlashTimeout(){
        return huntWarningFlashTimeout;
    }

    /**
     * setColorSpace
     *
     * TODO
     *
     * @param colorSpace
     */
    public void setColorSpace(int colorSpace){
        this.colorSpace = colorSpace;
    }

    /**
     * getColorSpace
     *
     * TODO
     *
     * @return ColorSpace
     */
    public int getColorSpace(){
        return colorSpace;
    }

    /**
     * saveToFile
     *
     * TODO
     *
     * @param c
     */
    public void saveToFile(Context c) {

        SharedPreferences sharedPref = c.getSharedPreferences(c.getResources().getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

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