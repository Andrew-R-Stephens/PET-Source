package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.ghost

import android.content.Context
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.InvestigationModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ResourceUtils.ResourceUtils.intArrayFromTypedArray

class GhostRepository {

    fun init(context: Context, investigationData: InvestigationModel) {
        ghostList = ArrayList()
        val namesTypedArray = context.resources.obtainTypedArray(R.array.ghost_names)
        val namesArray = intArrayFromTypedArray(context.resources, namesTypedArray)

        val typedArrayEvidence =
            context.resources.obtainTypedArray(R.array.ghost_evidence_arrays)
        val typedArrayRequiredEvidence =
            context.resources.obtainTypedArray(R.array.ghost_requiredevidence_arrays)

        for (i in namesArray.indices) {
            val ghost = GhostModel(investigationData, i, namesArray[i])
            // ghost.name =

            // Set Normal Evidence
            val evidenceNameTypedArray =
                context.resources.obtainTypedArray(typedArrayEvidence.getResourceId(i, 0))
            for (j in 0 until evidenceNameTypedArray.length()) {
                ghost.addEvidence(evidenceNameTypedArray.getString(j)!!, context)
            }
            evidenceNameTypedArray.recycle()

            // Set Required Evidence
            val requiredEvidenceNameTypedArray =
                context.resources.obtainTypedArray(typedArrayRequiredEvidence.getResourceId(i, 0))
            for (j in 0 until requiredEvidenceNameTypedArray.length()) {
                ghost.addNightmareEvidence(requiredEvidenceNameTypedArray.getString(j)!!, context)
            }
            requiredEvidenceNameTypedArray.recycle()

            ghostList.add(ghost)
        }
        typedArrayEvidence.recycle()
        typedArrayRequiredEvidence.recycle()
    }

    fun getAt(index: Int): GhostModel {
        return ghostList[index]
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
