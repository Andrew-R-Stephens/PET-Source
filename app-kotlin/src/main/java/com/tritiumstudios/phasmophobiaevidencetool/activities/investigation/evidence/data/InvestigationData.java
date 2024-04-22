package com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.data;

import android.content.Context;

import androidx.annotation.NonNull;

import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.data.investigationtype.EvidenceList;
import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.data.investigationtype.GhostList;
import com.tritiumstudios.phasmophobiaevidencetool.data.viewmodels.EvidenceViewModel;

/**
 * InvestigationData class
 *
 * @author TritiumGamingStudios
 */
public class InvestigationData {

    private final EvidenceViewModel evidenceViewModel;

    @NonNull
    public static GhostList ghostList = new GhostList();
    @NonNull
    public static EvidenceList evidenceList = new EvidenceList();

    public InvestigationData(@NonNull Context context, EvidenceViewModel evidenceViewModel) {
        this.evidenceViewModel = evidenceViewModel;

        evidenceList.init(context);
        ghostList.init(context, this);
    }

    public EvidenceViewModel getEvidenceViewModel() {
        return evidenceViewModel;
    }

    public GhostList getGhostList() {
        return ghostList;
    }

    public EvidenceList getEvidenceList() {
        return evidenceList;
    }

    /**
     * Resets the Ruling for each Evidence type
     */
    public void reset() {
        evidenceList.reset();
        ghostList.reset();
    }

}