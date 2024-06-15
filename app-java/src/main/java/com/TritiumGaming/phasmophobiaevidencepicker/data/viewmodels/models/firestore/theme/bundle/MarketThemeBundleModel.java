package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.firestore.theme.bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.firestore.theme.MarketplaceItemModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.settings.ThemeModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MarketThemeBundleModel extends MarketplaceItemModel {

    @Nullable
    private ArrayList<ThemeModel> themes = null;
    @NonNull
    private ThemeModel.Availability unlockedState = ThemeModel.Availability.LOCKED;

    public MarketThemeBundleModel(String uuid,
                                  @NotNull MarketThemeBundleModel theme,
                                  @NonNull List<ThemeModel> themes) {
        super(theme.getBuyCredits(), theme.getName());

        setUUID(uuid);
        addThemes(themes);
        setUnlockedState();
    }

    public void addThemes(@NonNull List<ThemeModel> themes) {
        this.themes = new ArrayList<>();
        this.themes.addAll(themes);
    }

    public ArrayList<ThemeModel> getThemes() {
        return themes;
    }

    public ThemeModel.Availability getUnlockedState() {
        return unlockedState;
    }

    public boolean isUnlocked() {
        return unlockedState != ThemeModel.Availability.LOCKED;
    }

    public long getDiscountedBuyCredits() {
        int lockedThemeCount = getLockedItemCount();

        if(lockedThemeCount == 0) {
            return 0;
        }

        double ratio = lockedThemeCount / (double)themes.size();

        return (long)(getBuyCredits() * ratio);
    }

    public int getLockedItemCount() {
        int lockedThemeCount = themes.size();

        for(ThemeModel customTheme: themes) {
            if(customTheme.isUnlocked()) {
                lockedThemeCount--;
            }
        }
        return lockedThemeCount;
    }

    public void setUnlockedState() {
        if(themes == null) { return; }

        if(getLockedItemCount() <= 1) {
            unlockedState = ThemeModel.Availability.UNLOCKED_PURCHASE;
        }
    }

    @NonNull
    public String toString() {
        StringBuilder themeOut = new StringBuilder();
        for(ThemeModel t: themes) {
            themeOut.append(t);
        }

        return super.toString() + " " + getBuyCredits() + " " + themeOut + " " + getUnlockedState();
    }

}
