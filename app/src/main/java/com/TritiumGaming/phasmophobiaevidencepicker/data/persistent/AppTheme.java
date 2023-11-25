package com.TritiumGaming.phasmophobiaevidencepicker.data.persistent;

import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

public class AppTheme {

    private final @StringRes int name;
    private final @StringRes int id;
    private final @StyleRes int styleId;

    public AppTheme(int id, int name, int styleId) {
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
