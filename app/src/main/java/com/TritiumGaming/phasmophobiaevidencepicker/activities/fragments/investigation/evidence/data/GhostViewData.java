package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data;

import android.content.Context;

import androidx.annotation.StringRes;

public record GhostViewData (@StringRes int[] infos,
                          @StringRes int[] strengths,
                          @StringRes int[] weaknesses,
                          @StringRes int[] huntDatas) {
    
    public String getWeakness(Context c, int i) {
        return c.getString(weaknesses[i]);
    }

    public String getHuntData(Context c, int i) {
        return c.getString(huntDatas[i]);
    }

    public String getStrength(Context c, int i) {
        return c.getString(strengths[i]);
    }

    public String getInfo(Context c, int i) {
        return c.getString(infos[i]);
    }

    public @StringRes int getWeakness(int i) {
        return weaknesses[i];
    }

    public @StringRes int getHuntData(int i) {
        return huntDatas[i];
    }

    public @StringRes int getStrength(int i) {
        return strengths[i];
    }

    public @StringRes int getInfo(int i) {
        return infos[i];
    }
}
