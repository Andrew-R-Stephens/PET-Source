package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.objects.theme.bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.CustomTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.objects.theme.MarketplaceItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MarketThemeBundle extends MarketplaceItem {

    /*

    private String uuid;
    private String name;
    private long buyCredits;

    private ArrayList<CustomTheme> themes = null;
    //private CustomTheme.Availability unlockedState = CustomTheme.Availability.UNLOCKED_PURCHASE;
    private CustomTheme.Availability unlockedState = CustomTheme.Availability.LOCKED;

    public MarketThemeBundle() {
    }

    public MarketThemeBundle(long buyCredits, String name) {
        this.buyCredits = buyCredits;
        this.name = name;
    }

    public MarketThemeBundle(String uuid, @NotNull MarketThemeBundle theme,
                             List<CustomTheme> themes) {
        this.uuid = uuid;
        this.buyCredits = theme.getBuyCredits();
        this.name = theme.getName();

        this.themes = new ArrayList<>();
        this.themes.addAll(themes);

        setUnlockedState();
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public long getBuyCredits() {
        return buyCredits;
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

        for(CustomTheme customTheme: themes) {
            if(customTheme.isUnlocked()) {
                lockedThemeCount--;
            }
        }
        return lockedThemeCount;
    }

    public ArrayList<CustomTheme> getThemes() {
        return themes;
    }

    public boolean isUnlocked() {
        return unlockedState != CustomTheme.Availability.LOCKED;
    }

    public void setUnlockedState() {
        if(themes == null) { return; }

        if(getLockedItemCount() <= 1) {
            unlockedState = CustomTheme.Availability.UNLOCKED_PURCHASE;
        }

        */
    /*
        for(CustomTheme customTheme: themes) {
            if(customTheme.isUnlocked()) {
                unlockedState = CustomTheme.Availability.UNLOCKED_PURCHASE;
                break;
            }
        }
        */
    /*

    }
*/

    @Nullable
    private ArrayList<CustomTheme> themes = null;
    @NonNull
    private CustomTheme.Availability unlockedState = CustomTheme.Availability.LOCKED;

    public MarketThemeBundle() {
    }

    public MarketThemeBundle(long buyCredits, String name) {
        super(buyCredits, name);
    }

    public MarketThemeBundle(String uuid,
                             @NotNull MarketThemeBundle theme,
                             @NonNull List<CustomTheme> themes) {
        super(theme.getBuyCredits(), theme.getName());

        setUUID(uuid);
        addThemes(themes);
        setUnlockedState();
    }

    public void addThemes(@NonNull List<CustomTheme> themes) {
        this.themes = new ArrayList<>();
        this.themes.addAll(themes);
    }

    public ArrayList<CustomTheme> getThemes() {
        return themes;
    }

    public CustomTheme.Availability getUnlockedState() {
        return unlockedState;
    }

    public boolean isUnlocked() {
        return unlockedState != CustomTheme.Availability.LOCKED;
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

        for(CustomTheme customTheme: themes) {
            if(customTheme.isUnlocked()) {
                lockedThemeCount--;
            }
        }
        return lockedThemeCount;
    }

    public void setUnlockedState() {
        if(themes == null) { return; }

        if(getLockedItemCount() <= 1) {
            unlockedState = CustomTheme.Availability.UNLOCKED_PURCHASE;
        }
    }

    @NonNull
    public String toString() {
        StringBuilder themeOut = new StringBuilder();
        for(CustomTheme t: themes) {
            themeOut.append(t);
        }

        return super.toString() + " " + getBuyCredits() + " " + themeOut + " " + getUnlockedState();
    }

}
