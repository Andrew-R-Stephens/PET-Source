package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost

import android.content.Context
import androidx.annotation.IntegerRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.util.ResourceUtils
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.investigation.popups.GhostPopup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.ghost.GhostEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.ghost.GhostType

class GhostRepository(
    evidenceRepository: EvidenceRepository,
    context: Context
) {

    var ghosts: ArrayList<GhostType> = ArrayList()

    val count: Int
        get() = ghosts.size

    init {

        val resources = context.resources

        // Populate Ghosts

        ghosts.clear()

        val namesTypedArray = resources.obtainTypedArray(R.array.ghost_names)
        val namesArray = ResourceUtils.intArrayFromTypedArray(resources, namesTypedArray)

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

            val ghostPopupModel = GhostPopup(
                info = infoTypedArray.getResourceId(i, 0),
                strength = strengthsTypedArray.getResourceId(i, 0),
                weakness = weaknessesTypedArray.getResourceId(i, 0),
                huntInfo = huntDataTypedArray.getResourceId(i, 0)
            )

            // Set Evidence

            val ghostEvidenceModel = GhostEvidence()
            val normalEvidenceIdTypedArray =
                resources.obtainTypedArray(typedArrayEvidence.getResourceId(i, 0))
            for (j in 0 until normalEvidenceIdTypedArray.length()) {
                @IntegerRes val evidenceIdRes = normalEvidenceIdTypedArray.getResourceId(j, 0)
                val evidenceID = resources.getString(evidenceIdRes)
                for(e in evidenceRepository.evidenceList) {
                    val eID = resources.getString(e.id)
                    if (evidenceID == eID) {
                        ghostEvidenceModel.addNormalEvidence(e)
                        break
                    }
                }

            }
            normalEvidenceIdTypedArray.recycle()

            val requiredEvidenceIdTypedArray =
                resources.obtainTypedArray(typedArrayRequiredEvidence.getResourceId(i, 0))
            for (j in 0 until requiredEvidenceIdTypedArray.length()) {
                @IntegerRes val evidenceIdRes = requiredEvidenceIdTypedArray.getResourceId(j, 0)
                val evidenceID = resources.getString(evidenceIdRes)
                for(e in evidenceRepository.evidenceList) {
                    val eID = resources.getString(e.id)
                    if (evidenceID == eID) {
                        ghostEvidenceModel.addStrictEvidence(e)
                        break
                    }
                }
            }
            requiredEvidenceIdTypedArray.recycle()

            // Finalize Ghost

            val ghost = GhostType(
                id = i,
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

    fun getAt(index: Int): GhostType {
        return ghosts[index]
    }

    fun getById(id: Int): GhostType? {
        return ghosts.find { it.id == id }
    }

}