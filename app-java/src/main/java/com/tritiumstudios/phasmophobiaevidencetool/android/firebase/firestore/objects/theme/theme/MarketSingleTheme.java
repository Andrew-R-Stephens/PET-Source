package com.tritiumstudios.phasmophobiaevidencetool.android.firebase.firestore.objects.theme.theme;

import androidx.annotation.NonNull;

import com.tritiumstudios.phasmophobiaevidencetool.android.data.controllers.theming.CustomTheme;
import com.tritiumstudios.phasmophobiaevidencetool.android.firebase.firestore.objects.theme.MarketplaceItem;

import org.jetbrains.annotations.NotNull;

public class MarketSingleTheme extends MarketplaceItem {

    /*

    private String uuid;
    private String name, group;
    private int buyCredits;

    private CustomTheme theme;

    public MarketSingleTheme() {
    }

    public MarketSingleTheme(int buyCredits, String group, String name) {
        this.buyCredits = buyCredits;
        this.group = group;
        this.name = name;
    }

    public MarketSingleTheme(String uuid, @NotNull MarketSingleTheme marketTheme, CustomTheme theme) {
        this.uuid = uuid;
        this.theme = theme;
        this.buyCredits = marketTheme.getBuyCredits();
        this.group = marketTheme.getGroup();
        this.name = marketTheme.getName();
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public int getBuyCredits() {
        return buyCredits;
    }

    public boolean isUnlocked() {
        return theme.isUnlocked();
    }

    public int getStyle() {
        if(theme != null) {
            return theme.getStyle();
        }

        return CustomTheme.getDefaultTheme().getStyle();
    }
*/

    private String group;
    private CustomTheme theme;

    public MarketSingleTheme() {
    }

    public MarketSingleTheme(long buyCredits, String group, String name) {
        setBuyCredits(buyCredits);
        setGroup(group);
        setName(name);
    }

    public MarketSingleTheme(String uuid, @NotNull MarketSingleTheme marketTheme, CustomTheme theme) {
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

        return CustomTheme.getDefaultTheme().getStyle();
    }

    @NonNull
    public String toString() {
        return super.toString() + " " + getBuyCredits() + " " + getGroup() + " " + getTheme();
    }

}
