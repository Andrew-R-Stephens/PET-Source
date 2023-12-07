package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import androidx.annotation.StringRes;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class GhostPopupData {

    private String ghostName;
    private @StringRes int[] infos, strengths, weaknesses, huntDatas;

    public GhostPopupData(Context context) {

        if(context == null) { return; }
        Resources resources = context.getResources();
        if(resources == null) { return; }

        TypedArray infoTypedArray = resources.obtainTypedArray(R.array.ghost_info_array);
        TypedArray strengthTypedArray = resources.obtainTypedArray(R.array.ghost_strengths_array);
        TypedArray weaknesseTypedArray = resources.obtainTypedArray(R.array.ghost_weaknesses_array);
        TypedArray huntDataTypedArray =  resources.obtainTypedArray(R.array.ghost_huntdata_array);

        infos = new int[infoTypedArray.length()];
        for (int j = 0; j < infos.length; j++) {
            infos[j] = infoTypedArray.getResourceId(j, 0);
        }
        infoTypedArray.recycle(); //cleanup

        strengths = new int[strengthTypedArray.length()];
        for (int j = 0; j < strengths.length; j++) {
            strengths[j] = strengthTypedArray.getResourceId(j, 0);
        }
        strengthTypedArray.recycle(); //cleanup

        weaknesses = new int[weaknesseTypedArray.length()];
        for (int j = 0; j < weaknesses.length; j++) {
            weaknesses[j] = weaknesseTypedArray.getResourceId(j, 0);
        }
        weaknesseTypedArray.recycle(); //cleanup

        huntDatas = new int[huntDataTypedArray.length()];
        for (int j = 0; j < huntDatas.length; j++) {
            huntDatas[j] = huntDataTypedArray.getResourceId(j, 0);
        }
        huntDataTypedArray.recycle(); //cleanup

    }

    public String[] getCycleDetails(Context c, int i) {
        String[] cycleDetails = new String[3];
        cycleDetails[0] = getInfo(c, i);
        cycleDetails[1] = getStrength(c, i);
        cycleDetails[2] = getWeakness(c, i);

        return cycleDetails;
    }

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
