package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.ghostboxutility.data;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;

/**
 * GhostSpeakData class
 *
 * @author TritiumGamingStudios
 */
public class GhostBoxUtilityData {

    /**
     * getEntries method
     * @param context
     * @param resourceArray
     * @return
     */
    public int[] getEntries(@NonNull Context context, @ArrayRes int resourceArray) {
        TypedArray typedArray = context.getResources().obtainTypedArray(resourceArray);
        int[] spiritBoxEntries = new int[typedArray.length()];
        for (int i = 0; i < spiritBoxEntries.length; i++)
            spiritBoxEntries[i] = typedArray.getResourceId(i, 0);
        typedArray.recycle();

        return spiritBoxEntries;
    }

}
