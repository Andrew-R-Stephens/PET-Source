package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationtype;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

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

    public Evidence() {
        setName("Null");
        setIcon(R.drawable.icon_ev_dots);
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setIcon(@DrawableRes int icon) {
        this.icon = icon;
    }

    public @DrawableRes int getIcon() {
        return icon;
    }

    public void setRuling(Ruling ruling) {
        this.ruling = ruling;
    }

    public Ruling getRuling() {
        return ruling;
    }

    public boolean isRuling(Ruling r) {
        return ruling == r;
    }

    @NonNull
    public String toString() {
        return ruling.name();
    }
}
