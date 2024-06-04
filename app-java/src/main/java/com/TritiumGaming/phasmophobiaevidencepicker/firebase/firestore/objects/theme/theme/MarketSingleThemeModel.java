package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.objects.theme.theme;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.CustomTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.objects.theme.MarketplaceItemModel;

import org.jetbrains.annotations.NotNull;

public class MarketSingleThemeModel extends MarketplaceItemModel {

    private String group;
    private CustomTheme theme;

    public MarketSingleThemeModel() {
    }

    public MarketSingleThemeModel(long buyCredits, String group, String name) {
        setBuyCredits(buyCredits);
        setGroup(group);
        setName(name);
    }

    public MarketSingleThemeModel(String uuid, @NotNull MarketSingleThemeModel marketTheme, CustomTheme theme) {
        setUUID(uuid);
        setTheme(theme);
        setBuyCredits(marketTheme.getBuyCredits());
        setGroup(marketTheme.getGroup());
        setName(marketTheme.getName());
    }

    protected void setGroup(String group) {
        this.group = group;
    }

    protected void setTheme(CustomTheme theme) {
        this.theme = theme;
    }

    public String getGroup() {
        return group;
    }

    private CustomTheme getTheme() {
        return theme;
    }

    public boolean isUnlocked() {
        return theme.isUnlocked();
    }

    public int getStyle() {
        if(theme != null) {
            return theme.getStyle();
        }

        return CustomTheme.Companion.getDefaultTheme().getStyle();
    }

    @NonNull
    public String toString() {
        return super.toString() + " " + getBuyCredits() + " " + getGroup() + " " + getTheme();
    }

}
