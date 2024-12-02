package com.TritiumGaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import androidx.annotation.IntegerRes
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.investigation.investigationmodels.investigationtype.ghost.GhostModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ResourceUtils.ResourceUtils.intArrayFromTypedArray

class GhostRepository(
    evidenceRepository: EvidenceRepository,
    context: Context
) {

    var ghostList: ArrayList<GhostModel> = ArrayList()

    val count: Int
        get() = ghostList.size

    init {

        ghostList = ArrayList()

        val namesTypedArray = context.resources.obtainTypedArray(R.array.ghost_names)
        val namesArray = intArrayFromTypedArray(context.resources, namesTypedArray)

        val typedArrayEvidence =
            context.resources.obtainTypedArray(R.array.ghost_evidence_arrays)
        val typedArrayRequiredEvidence =
            context.resources.obtainTypedArray(R.array.ghost_requiredevidence_arrays)

        for (i in namesArray.indices) {
            val ghost = GhostModel(i, namesArray[i])

            // Set Normal Evidence
            val normalEvidenceIdTypedArray =
                context.resources.obtainTypedArray(typedArrayEvidence.getResourceId(i, 0))
            for (j in 0 until normalEvidenceIdTypedArray.length()) {
                @IntegerRes val evidenceIdRes = normalEvidenceIdTypedArray.getResourceId(j, 0)
                val evidenceID = context.resources.getInteger(evidenceIdRes)
                ghost.addEvidence(
                    evidenceRepository.evidenceList,
                    evidenceID
                )
            }
            normalEvidenceIdTypedArray.recycle()

            // Set Required Evidence
            val requiredEvidenceIdTypedArray =
                context.resources.obtainTypedArray(typedArrayRequiredEvidence.getResourceId(i, 0))
            for (j in 0 until requiredEvidenceIdTypedArray.length()) {
                @IntegerRes val evidenceIdRes = requiredEvidenceIdTypedArray.getResourceId(j, 0)
                val evidenceID = context.resources.getInteger(evidenceIdRes)
                ghost.addNightmareEvidence(
                    evidenceRepository.evidenceList,
                    evidenceID
                )
            }
            requiredEvidenceIdTypedArray.recycle()

            ghostList.add(ghost)
        }
        typedArrayEvidence.recycle()
        typedArrayRequiredEvidence.recycle()
    }

    fun getAt(index: Int): GhostModel {
        return ghostList[index]
    }

}
