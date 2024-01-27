package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.theme;

import org.jetbrains.annotations.NotNull;

public class MarketSingleTheme {

    private String uuid;
    private String name, group;
    private int buyCredits;

    public MarketSingleTheme() {
    }

    public MarketSingleTheme(int buyCredits, String group, String name) {
        this.buyCredits = buyCredits;
        this.group = group;
        this.name = name;
    }

    public MarketSingleTheme(String uuid, @NotNull MarketSingleTheme theme) {
        this.uuid = uuid;
        this.buyCredits = theme.getBuyCredits();
        this.group = theme.getGroup();
        this.name = theme.getName();
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

    public String toString() {
        return getUuid() + " " + getBuyCredits() + " " + getGroup() + " " + getName();
    }

}
