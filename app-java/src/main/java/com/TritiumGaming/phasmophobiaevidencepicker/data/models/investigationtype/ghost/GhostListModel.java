package com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationtype.ghost;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationmodels.InvestigationModel;

import java.util.ArrayList;

public class GhostListModel {

    @Nullable
    public static ArrayList<GhostModel> ghostList = null;

    public void init(
            @NonNull Context c, @NonNull InvestigationModel investigationData) {
        ghostList = new ArrayList<>();
        String[] ghostNames = c.getResources().getStringArray(R.array.ghost_names);

        TypedArray typedArrayEvidence =
                c.getResources().obtainTypedArray(R.array.ghost_evidence_arrays);
        TypedArray typedArrayRequiredEvidence =
                c.getResources().obtainTypedArray(R.array.ghost_requiredevidence_arrays);
        for (int i = 0; i < ghostNames.length; i++) {
            GhostModel ghost = new GhostModel(investigationData, i);
            ghost.setName(ghostNames[i]);

            // Set Normal Evidence
            TypedArray evidenceNameTypedArray =
                    c.getResources().obtainTypedArray(typedArrayEvidence.getResourceId(i, 0));
            for (int j = 0; j < evidenceNameTypedArray.length(); j++) {
                ghost.addEvidence(evidenceNameTypedArray.getString(j));
            }
            evidenceNameTypedArray.recycle();

            // Set Required Evidence
            TypedArray requiredEvidenceNameTypedArray =
                    c.getResources().obtainTypedArray(typedArrayRequiredEvidence.getResourceId(i, 0));
            for (int j = 0; j < requiredEvidenceNameTypedArray.length(); j++) {
                ghost.addNightmareEvidence(requiredEvidenceNameTypedArray.getString(j));
            }
            requiredEvidenceNameTypedArray.recycle();

            ghostList.add(ghost);
        }
        typedArrayEvidence.recycle();
        typedArrayRequiredEvidence.recycle();
    }

    public static int getCount() {
        if(ghostList == null) { return 0; }
        return ghostList.size();
    }

    public GhostModel getAt(int index) {
        if(ghostList == null) { return null; }
        return ghostList.get(index);
    }

    public ArrayList<GhostModel> getList() {
        return ghostList;
    }

    /**
     * Resets the Ruling for each Evidence type
     */
    public void reset() {

        ArrayList<GhostModel> ghostList = getList();
        if(ghostList == null) { return; }

        for (GhostModel g : ghostList) {
            g.setIsForcefullyRejected(false);
        }
    }

}
