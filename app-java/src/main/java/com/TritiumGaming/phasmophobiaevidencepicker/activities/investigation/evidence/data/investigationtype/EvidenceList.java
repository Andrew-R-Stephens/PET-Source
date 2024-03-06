package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationtype;

import android.content.Context;
import android.content.res.TypedArray;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

import java.util.ArrayList;

public class EvidenceList {

    public static ArrayList<Evidence> evidenceList = null;

    public void init(Context c) {
        evidenceList = new ArrayList<>();
        String[] evidenceNames = c.getResources().getStringArray(R.array.evidence_type_names);
        TypedArray typedArray = c.getResources().obtainTypedArray(R.array.evidence_icon_array);
        for (int i = 0; i < evidenceNames.length; i++) {
            Evidence evidence = new Evidence();
            evidence.setName(evidenceNames[i]);
            evidence.setIcon(typedArray.getResourceId(i, 0));
            evidenceList.add(evidence);
        }
        typedArray.recycle();
    }

    public static int getCount() {
        return evidenceList.size();
    }

    public ArrayList<Evidence> getList() {
        return evidenceList;
    }

    /**
     * Resets the Ruling for each Evidence type
     */
    public void reset() {
        for (int i = 0; i < evidenceList.size(); i++) {
            evidenceList.get(i).setRuling(Evidence.Ruling.NEUTRAL);
        }
    }
}
