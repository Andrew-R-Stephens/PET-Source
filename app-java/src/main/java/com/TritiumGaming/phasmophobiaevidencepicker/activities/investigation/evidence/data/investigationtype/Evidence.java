package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationtype;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Evidence enums
 */
public class Evidence {

    @Nullable
    private String name = null;
    private @DrawableRes int icon;

    private Ruling ruling = Ruling.NEUTRAL;

    public enum Ruling {
        NEGATIVE, NEUTRAL, POSITIVE
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIcon(@DrawableRes int icon) {
        this.icon = icon;
    }

    public @DrawableRes int getIcon() {
        return icon;
    }

    /**
     * @param ruling
     */
    public void setRuling(Ruling ruling) {
        this.ruling = ruling;
    }

    /**
     * @return
     */
    public Ruling getRuling() {
        return ruling;
    }

    public boolean isRuling(Ruling r) {
        return ruling == r;
    }

    /**
     * @return
     */
    @NonNull
    public String toString() {
        return ruling.name();
    }
}
