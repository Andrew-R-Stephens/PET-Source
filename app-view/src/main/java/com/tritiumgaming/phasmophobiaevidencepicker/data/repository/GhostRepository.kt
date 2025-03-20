package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import androidx.annotation.IntegerRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.ghost.GhostEvidenceModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.ghost.GhostModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.popups.GhostPopupModel
import com.tritiumgaming.phasmophobiaevidencepicker.util.ResourceUtils.ResourceUtils.intArrayFromTypedArray

class GhostRepository(
    evidenceRepository: EvidenceRepository,
    context: Context
) {

    var ghosts: ArrayList<GhostModel> = ArrayList()

    val count: Int
        get() = ghosts.size

    init {

        val resources = context.resources

        // Populate Ghosts

        ghosts.clear()

        val namesTypedArray = resources.obtainTypedArray(R.array.ghost_names)
        val namesArray = intArrayFromTypedArray(resources, namesTypedArray)

        val typedArrayEvidence =
            resources.obtainTypedArray(R.array.ghost_evidence_arrays)
        val typedArrayRequiredEvidence =
            resources.obtainTypedArray(R.array.ghost_requiredevidence_arrays)

        val infoTypedArray = resources.obtainTypedArray(R.array.ghost_info_array)
        val strengthsTypedArray = resources.obtainTypedArray(R.array.ghost_strengths_array)
        val weaknessesTypedArray = resources.obtainTypedArray(R.array.ghost_weaknesses_array)
        val huntDataTypedArray = resources.obtainTypedArray(R.array.ghost_huntdata_array)

        for (i in namesArray.indices) {

            // Set Popup

            val ghostPopupModel = GhostPopupModel(
                info = infoTypedArray.getResourceId(i, 0),
                strength = strengthsTypedArray.getResourceId(i, 0),
                weakness = weaknessesTypedArray.getResourceId(i, 0),
                huntInfo = huntDataTypedArray.getResourceId(i, 0)
            )

            // Set Evidence

            val ghostEvidenceModel = GhostEvidenceModel()
            val normalEvidenceIdTypedArray =
                resources.obtainTypedArray(typedArrayEvidence.getResourceId(i, 0))
            for (j in 0 until normalEvidenceIdTypedArray.length()) {
                @IntegerRes val evidenceIdRes = normalEvidenceIdTypedArray.getResourceId(j, 0)
                val evidenceID = resources.getInteger(evidenceIdRes)
                ghostEvidenceModel.addEvidence(
                    evidenceRepository.evidenceList,
                    evidenceID
                )
            }
            normalEvidenceIdTypedArray.recycle()

            val requiredEvidenceIdTypedArray =
                resources.obtainTypedArray(typedArrayRequiredEvidence.getResourceId(i, 0))
            for (j in 0 until requiredEvidenceIdTypedArray.length()) {
                @IntegerRes val evidenceIdRes = requiredEvidenceIdTypedArray.getResourceId(j, 0)
                val evidenceID = resources.getInteger(evidenceIdRes)
                ghostEvidenceModel.addNightmareEvidence(
                    evidenceRepository.evidenceList,
                    evidenceID
                )
            }
            requiredEvidenceIdTypedArray.recycle()

            // Finalize Ghost

            val ghost = GhostModel(
                id= i,
                name = namesArray[i],
                popupModel = ghostPopupModel,
                evidence = ghostEvidenceModel
            )

            ghosts.add(ghost)
        }

        typedArrayEvidence.recycle()
        typedArrayRequiredEvidence.recycle()
        infoTypedArray.recycle()
        strengthsTypedArray.recycle()
        weaknessesTypedArray.recycle()
        huntDataTypedArray.recycle()
    }

    fun getAt(index: Int): GhostModel {
        return ghosts[index]
    }

}
