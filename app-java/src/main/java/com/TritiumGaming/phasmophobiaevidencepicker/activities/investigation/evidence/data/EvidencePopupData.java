package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.StringRes;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class EvidencePopupData {

    private EvidencePopupRecord[] evidenceViewDatas;

    public EvidencePopupData(Context context) {

        if(context == null) { return; }
        Resources resources = context.getResources();
        if(resources == null) { return; }

        TypedArray evidenceTypes =
                resources.obtainTypedArray(R.array.equipment_tiers_arrays);

        evidenceViewDatas = new EvidencePopupRecord[evidenceTypes.length()];

        for(int i = 0; i < evidenceViewDatas.length; i++) {

            @StringRes int evidenceName;
            @StringRes int[] descriptions = new int[4];
            @DrawableRes int[] animations = new int[4];
            @IntegerRes int[] unlock_level = new int[3];
            @IntegerRes int evidenceCost;

            TypedArray evidenceType =
                    resources.obtainTypedArray(evidenceTypes.getResourceId(i, 0));
            //Log.d("EvType", evidenceType.toString() + "\n" + evidenceType.getString(0));

            evidenceName = evidenceType.getResourceId(0, 0);
            evidenceCost = evidenceType.getResourceId(3, 0);

            @SuppressLint("ResourceType")
            TypedArray evidenceDescription =
                    resources.obtainTypedArray(evidenceType.getResourceId(1, 0));
            for (int j = 0; j < evidenceDescription.length(); j++) {
                descriptions[j] = evidenceDescription.getResourceId(j, 0);
                //Log.d("EvDescription", getString(descriptions[j]) + "");
            }
            evidenceDescription.recycle(); //cleanup

            @SuppressLint("ResourceType")
            TypedArray evidenceAnimations =
                    resources.obtainTypedArray(evidenceType.getResourceId(2, 0));
            for (int j = 0; j < evidenceAnimations.length(); j++) {
                animations[j] = evidenceAnimations.getResourceId(j, 0);
                //Log.d("EvDAnimation", animations[j] + "");
            }
            evidenceAnimations.recycle(); //cleanup

            @SuppressLint("ResourceType")
            TypedArray evidenceLevels =
                    resources.obtainTypedArray(evidenceType.getResourceId(4, 0));
            for (int j = 0; j < evidenceLevels.length(); j++) {
                unlock_level[j] = evidenceLevels.getResourceId(j, 0);
                //Log.d("EvDALevels", unlock_level[j] + "");
            }
            evidenceLevels.recycle(); //cleanup
            evidenceType.recycle();

            EvidencePopupRecord evidenceViewData = new EvidencePopupRecord(
                    i, evidenceName,
                    evidenceCost, unlock_level,
                    descriptions, animations);

            evidenceViewDatas[i] = evidenceViewData;
        }
        evidenceTypes.recycle();

    }

    public int getCount() {
        return evidenceViewDatas == null ? 0 : evidenceViewDatas.length;
    }

    public EvidencePopupRecord getEvidencePopupRecordAt(int index) {
        if(evidenceViewDatas == null) { return null; }

        return evidenceViewDatas[index];
    }

    public record EvidencePopupRecord(
            int index, @StringRes int name,
            @IntegerRes int cost, @IntegerRes int[] unlock_level,
            @StringRes int[] descriptions, @DrawableRes int[] animations) {

        public String getName(Context c) {
            return c.getString(name);
        }

        public String getCost(Context c) {
            return String.valueOf(c.getResources().getInteger(cost));
        }

        public @DrawableRes int getAnimation(Context c, int i) {
            return animations[i];
        }

        public String getDescription(Context c, int i) {
            return c.getString(descriptions[i]);
        }

        public String getUnlockLevel(Context c, int i) {
            return String.valueOf(c.getResources().getInteger(unlock_level[i]));
        }
    }

}
