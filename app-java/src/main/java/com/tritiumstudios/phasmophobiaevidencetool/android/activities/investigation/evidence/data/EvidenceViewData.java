package com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.evidence.data;

import android.content.Context;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

public record EvidenceViewData(int index, @StringRes int name, @IntegerRes int cost,
                               @IntegerRes int[] unlock_level, @StringRes int[] descriptions,
                               @DrawableRes int[] animations) {

    public String getName(@NonNull Context c) {
        return c.getString(name);
    }

    @NonNull
    public String getCost(@NonNull Context c) {
        return String.valueOf(c.getResources().getInteger(cost));
    }

    public @DrawableRes int getAnimation(Context c, int i) {
        return animations[i];
    }

    public String getDescription(@NonNull Context c, int i) {
        return c.getString(descriptions[i]);
    }

    @NonNull
    public String getUnlockLevel(@NonNull Context c, int i) {
        return String.valueOf(c.getResources().getInteger(unlock_level[i]));
    }
}
