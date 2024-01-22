package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data;

import android.content.Context;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.investigationtype.EvidenceList;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.investigationtype.GhostList;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;

/**
 * InvestigationData class
 *
 * @author TritiumGamingStudios
 */
public class InvestigationData {

    private final EvidenceViewModel evidenceViewModel;

    public static GhostList ghostList = new GhostList();
    public static EvidenceList evidenceList = new EvidenceList();

    public InvestigationData(EvidenceViewModel evidenceViewModel, Context c) {
        this.evidenceViewModel = evidenceViewModel;

        evidenceList.init(c);
        ghostList.init(c, this);
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