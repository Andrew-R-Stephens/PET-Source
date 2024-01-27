package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.bundle;

import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.CustomTheme;

import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MarketThemeBundle {

    private String uuid;
    private String name;
    private long buyCredits;

    private ArrayList<CustomTheme> themes;

    public MarketThemeBundle() {
    }

    public MarketThemeBundle(long buyCredits, String name) {
        this.buyCredits = buyCredits;
        this.name = name;
    }

    public MarketThemeBundle(String uuid, @NotNull MarketThemeBundle theme, List<CustomTheme> themes) {
        this.uuid = uuid;
        this.buyCredits = theme.getBuyCredits();
        this.name = theme.getName();

        this.themes = new ArrayList<>();
        this.themes.addAll(themes);
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

    public ArrayList<CustomTheme> getThemes() {
        return themes;
    }

    public String toString() {
        return getUuid() + " " + getBuyCredits() + " " + getName();
    }
}
