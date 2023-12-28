package com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming;

import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

public class CustomTheme {

    private final @StringRes int name;
    private final String hashID;
    private final @StyleRes int styleId;

    public CustomTheme(String id, int name, int styleId) {
        this.hashID = id;
        this.name = name;
        this.styleId = styleId;
    }

    public CustomTheme(int id, int name, int styleId) {
        this.hashID = "-1";
        this.name = name;
        this.styleId = styleId;
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
}
