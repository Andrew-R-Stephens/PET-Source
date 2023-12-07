package com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming;

import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

public class CustomTheme {

    private final @StringRes int name;
    private final @StringRes int id;
    private final @StyleRes int styleId;

    public CustomTheme(int id, int name, int styleId) {
        this.id = id;
        this.name = name;
        this.styleId = styleId;
    }

    public @StringRes int getName() {
        return name;
    }

    public @StringRes int getID() {
        return id;
    }

    public @StyleRes int getStyle() {
        return styleId;
    }
}
