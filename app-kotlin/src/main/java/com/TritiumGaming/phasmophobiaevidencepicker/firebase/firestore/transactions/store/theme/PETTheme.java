package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.theme;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PETTheme {

    private String uuid;
    private String name, group;
    private int buyCredits;

    public PETTheme() {
    }

    public PETTheme(int buyCredits, String group, String name) {
        this.buyCredits = buyCredits;
        this.group = group;
        this.name = name;
    }

    public PETTheme(String uuid, @NotNull PETTheme theme) {
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
