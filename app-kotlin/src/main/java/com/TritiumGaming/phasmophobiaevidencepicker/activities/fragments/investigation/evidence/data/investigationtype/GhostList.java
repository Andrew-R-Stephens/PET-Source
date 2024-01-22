package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.investigationtype;

import android.content.Context;
import android.content.res.TypedArray;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.InvestigationData;

import java.util.ArrayList;

public class GhostList {

    public static ArrayList<Ghost> ghostList = null;

    public void init(Context c, InvestigationData investigationData) {
        ghostList = new ArrayList<>();
        String[] ghostNames = c.getResources().getStringArray(R.array.ghost_names);

        TypedArray typedArrayEvidence =
                c.getResources().obtainTypedArray(R.array.ghost_evidence_arrays);
        TypedArray typedArrayRequiredEvidence =
                c.getResources().obtainTypedArray(R.array.ghost_requiredevidence_arrays);
        for (int i = 0; i < ghostNames.length; i++) {
            Ghost ghost = new Ghost(investigationData, i);
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
        return ghostList.size();
    }

    public Ghost getAt(int index) {
        return ghostList.get(index);
    }

    public ArrayList<Ghost> getList() {
        return ghostList;
    }

    /**
     * Resets the Ruling for each Evidence type
     */
    public void reset() {
        for (Ghost g : getList()) {
            g.setIsForcefullyRejected(false);
        }
    }

}
