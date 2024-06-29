package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.ghost

import android.content.Context
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.InvestigationModel

class GhostListModel {

    fun init(c: Context, investigationData: InvestigationModel) {
        ghostList = ArrayList()
        val ghostNames = c.resources.getStringArray(R.array.ghost_names)

        val typedArrayEvidence =
            c.resources.obtainTypedArray(R.array.ghost_evidence_arrays)
        val typedArrayRequiredEvidence =
            c.resources.obtainTypedArray(R.array.ghost_requiredevidence_arrays)
        for (i in ghostNames.indices) {
            val ghost = GhostModel(investigationData, i)
            ghost.name = ghostNames[i]

            // Set Normal Evidence
            val evidenceNameTypedArray =
                c.resources.obtainTypedArray(typedArrayEvidence.getResourceId(i, 0))
            for (j in 0 until evidenceNameTypedArray.length()) {
                ghost.addEvidence(evidenceNameTypedArray.getString(j)!!)
            }
            evidenceNameTypedArray.recycle()

            // Set Required Evidence
            val requiredEvidenceNameTypedArray =
                c.resources.obtainTypedArray(typedArrayRequiredEvidence.getResourceId(i, 0))
            for (j in 0 until requiredEvidenceNameTypedArray.length()) {
                ghost.addNightmareEvidence(requiredEvidenceNameTypedArray.getString(j)!!)
            }
            requiredEvidenceNameTypedArray.recycle()

            ghostList.add(ghost)
        }
        typedArrayEvidence.recycle()
        typedArrayRequiredEvidence.recycle()
    }

    fun getAt(index: Int): GhostModel? {
        return ghostList.get(index)
    }

    /** Resets the Ruling for each Evidence type  */
    fun reset() {
        val ghostList: ArrayList<GhostModel> = ghostList

        for (g in ghostList) { g.forcefullyRejected = false }
    }

    companion object {
        var ghostList: ArrayList<GhostModel> = ArrayList()

        val count: Int
            get() {
                return ghostList.size
            }
    }
}
