package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.annotation.DrawableRes;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.investigationtype.Evidence;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.investigationtype.Ghost;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;

import java.util.ArrayList;

/**
 * InvestigationData class
 *
 * @author TritiumGamingStudios
 */
public class InvestigationData {

    private final EvidenceViewModel evidenceViewModel;

    //public static Ghosts ghostData = new Ghosts();

    public static ArrayList<Ghost> ghosts = null;
    public static ArrayList<Evidence> evidence = null;

    public InvestigationData(EvidenceViewModel evidenceViewModel, Context c) {
        this.evidenceViewModel = evidenceViewModel;

        initEvidence(c);
        initGhosts(c);
    }

    public void initGhosts(Context c) {
        ghosts = new ArrayList<>();
        String[] ghostNames = c.getResources().getStringArray(R.array.ghost_names);

        TypedArray typedArrayEvidence =
                c.getResources().obtainTypedArray(R.array.ghost_evidence_arrays);
        TypedArray typedArrayRequiredEvidence =
                c.getResources().obtainTypedArray(R.array.ghost_requiredevidence_arrays);
        for (int i = 0; i < ghostNames.length; i++) {
            Ghost ghost = new Ghost(this, i);
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

            ghosts.add(ghost);
        }
        typedArrayEvidence.recycle();
        typedArrayRequiredEvidence.recycle();
    }

    public void initEvidence(Context c) {
        evidence = new ArrayList<>();
        String[] evidenceNames = c.getResources().getStringArray(R.array.evidence_type_names);
        TypedArray typedArray = c.getResources().obtainTypedArray(R.array.evidence_icon_array);
        for (int i = 0; i < evidenceNames.length; i++) {
            Evidence evidence = new Evidence();
            evidence.setName(evidenceNames[i]);
            evidence.setIcon(typedArray.getResourceId(i, 0));
            InvestigationData.evidence.add(evidence);
        }
        typedArray.recycle();
    }

    public EvidenceViewModel getEvidenceViewModel() {
        return evidenceViewModel;
    }

    public static int getEvidenceCount() {
        return evidence.size();
    }

    public ArrayList<Evidence> getEvidences() {
        return evidence;
    }

    public static int getGhostCount() {
        return ghosts.size();
    }

    public Ghost getGhost(int index) {
        return ghosts.get(index);
    }

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    /**
     * Resets the Ruling for each Evidence type
     */
    public void reset() {
        for (int i = 0; i < evidence.size(); i++) {
            evidence.get(i).setRuling(Evidence.Ruling.NEUTRAL);
        }
        for (Ghost g : getGhosts()) {
            g.setIsForcefullyRejected(false);
        }
    }

    public void print() {
        for(Ghost g: ghosts) {
            //Log.d("InvestigationData", g.name + ": [ " + g + "]");
        }
    }

}