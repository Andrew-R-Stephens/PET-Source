package com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming;

import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class CustomTheme {

    public enum Availability {
        LOCKED, UNLOCKED_DEFAULT, UNLOCKED_PURCHASE
    }

    private final @StringRes int name;
    private final String hashID;
    private final @StyleRes int styleId;
    private Availability unlockedState; // start LOCKED

    public CustomTheme(String id, int name, int styleId) {
        this(id, name, styleId, false);
    }

    public CustomTheme(String id, int name, int styleId, boolean isUnlocked) {
        this.hashID = id;
        this.name = name;
        this.styleId = styleId;
        this.unlockedState = isUnlocked ? Availability.UNLOCKED_DEFAULT : Availability.LOCKED;
    }

    public @StringRes int getName() {
        return name;
    }

    public String getID() {
        return hashID;
    }

    public @StyleRes int getStyle() {
        return styleId;
    }

    public boolean isUnlocked() {
        return unlockedState != Availability.LOCKED;
    }

    public void setUnlocked(Availability state) {
        if(unlockedState == Availability.UNLOCKED_DEFAULT) { return; }

        this.unlockedState = state;
    }

    public void revertUnlockStatus() {
        if(unlockedState == Availability.UNLOCKED_PURCHASE) {
            unlockedState = Availability.LOCKED;
        }
    }

    public Availability getUnlockedState() {
        return unlockedState;
    }

    public static CustomTheme getDefaultTheme() {
        return new CustomTheme(
                "CzjtxSbXRwIpX8SYR0ttngAND",
                R.string.settings_colorblindnessmode_defaultName,
                R.style.Theme_PhasmophobiaEvidenceTool);
    }

    @Override
    public String toString() {
        return "CustomTheme{" +
                "name=" + name +
                ", hashID='" + hashID + '\'' +
                ", styleId=" + styleId +
                ", unlockedState=" + unlockedState +
                '}';
    }

}
