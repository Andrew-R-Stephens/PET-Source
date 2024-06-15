package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.evidence;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

import java.util.ArrayList;

public class EvidenceListModel {

    @Nullable
    public static ArrayList<EvidenceModel> evidenceList = null;

    public void init(@NonNull Context c) {
        evidenceList = new ArrayList<>();
        String[] evidenceNames = c.getResources().getStringArray(R.array.evidence_type_names);
        TypedArray typedArray = c.getResources().obtainTypedArray(R.array.evidence_icon_array);
        for (int i = 0; i < evidenceNames.length; i++) {
            EvidenceModel evidence = new EvidenceModel();
            evidence.setName(evidenceNames[i]);
            evidence.setIcon(typedArray.getResourceId(i, 0));
            evidenceList.add(evidence);
        }
        typedArray.recycle();
    }

    public static int getCount() {
        if(evidenceList == null) return 0;
        return evidenceList.size();
    }

    public ArrayList<EvidenceModel> getList() {
        return evidenceList;
    }

    /**
     * Resets the Ruling for each Evidence type
     */
    public void reset() {
        for (int i = 0; i < evidenceList.size(); i++) {
            evidenceList.get(i).setRuling(EvidenceModel.Ruling.NEUTRAL);
        }
    }
}
