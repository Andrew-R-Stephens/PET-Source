package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedGraphicData;
import com.google.android.gms.ads.AdRequest;

/**
 * TitleScreenViewModel class
 *
 * @author TritiumGamingStudios
 */
public class MainMenuViewModel extends ViewModel {

    private final AnimatedGraphicData animationData = new AnimatedGraphicData();

    @Nullable
    private AdRequest adRequest = null;

    private boolean canRefreshFragment = true;
    private int languageSelectedOriginal = -1;

    /**
     * getAnimatedData method
     *
     * @return animationData
     */
    public AnimatedGraphicData getAnimationData() {
        return animationData;
    }

    /**
     * hasAdRequest method
     *
     * @return is adRequest null
     */
    public boolean hasAdRequest() {
        return adRequest != null;
    }

    /**
     * setAdRequest method
     *
     * @param adRequest
     */
    public void setAdRequest(AdRequest adRequest) {
        this.adRequest = adRequest;
    }

    /**
     * getAdRequest method
     *
     * @return AdRequest
     */
    public AdRequest getAdRequest() {
        return adRequest;
    }

    /**
     * setCanRefreshFragment method
     *
     * @param canRefreshFragment
     */
    public void setCanRefreshFragment(boolean canRefreshFragment) {
        this.canRefreshFragment = canRefreshFragment;
    }

    /**
     * canRefreshFragment method
     *
     * @return canRefresh
     */
    public boolean canRefreshFragment() {
        return canRefreshFragment;
    }

    public int getLanguageSelectedOriginal() {
        return languageSelectedOriginal;
    }

    public void setLanguageSelectedOriginal(int languageSelectedOriginal) {
        this.languageSelectedOriginal = languageSelectedOriginal;
    }
}