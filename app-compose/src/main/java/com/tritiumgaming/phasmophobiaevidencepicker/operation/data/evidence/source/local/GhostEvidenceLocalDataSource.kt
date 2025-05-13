package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.source.local

import android.content.Context
import androidx.annotation.IntegerRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceType

class GhostEvidenceLocalDataSource(
    context: Context,
    evidenceLocalSource: EvidenceLocalDataSource
) {

    val ghostEvidences = mutableListOf<GhostEvidenceDto>()
    
    init {
        val resources = context.resources

        val normalEvidenceIndex = 0
        val requiredEvidenceIndex = 1

        val typedArrayGhostEvidences =
            resources.obtainTypedArray(R.array.ghost_evidence_arrays)

        for (i in 0 until typedArrayGhostEvidences.length()) {

            val typedArrayGhostEvidence = resources.obtainTypedArray(
                typedArrayGhostEvidences.getResourceId(i, 0))

            val ghostEvidenceDto = GhostEvidenceDto()

            val normalEvidenceIdTypedArray = resources.obtainTypedArray(
                typedArrayGhostEvidence.getResourceId(normalEvidenceIndex, 0))

            for (j in 0 until normalEvidenceIdTypedArray.length()) {

                @IntegerRes val evidenceIdRes = normalEvidenceIdTypedArray.getResourceId(j, 0)

                evidenceLocalSource.evidenceList.firstOrNull {
                    resources.getString(it.id) == resources.getString(evidenceIdRes)
                }?.let { evidence ->
                    ghostEvidenceDto.normalEvidences.add(evidence)
                }

            }
            normalEvidenceIdTypedArray.recycle()

            val requiredEvidenceIdTypedArray = resources.obtainTypedArray(
                typedArrayGhostEvidence.getResourceId(requiredEvidenceIndex, 0))

            for (j in 0 until requiredEvidenceIdTypedArray.length()) {

                @IntegerRes val evidenceIdRes = requiredEvidenceIdTypedArray.getResourceId(j, 0)

                evidenceLocalSource.evidenceList.firstOrNull {
                    resources.getString(it.id) == resources.getString(evidenceIdRes)
                }?.let { evidence ->
                    ghostEvidenceDto.strictEvidences.add(evidence)
                }

            }
            requiredEvidenceIdTypedArray.recycle()

            ghostEvidences.add(ghostEvidenceDto)

            typedArrayGhostEvidence.recycle()
        }

        typedArrayGhostEvidences.recycle()
    }

    data class GhostEvidenceDto(
        val normalEvidences: ArrayList<EvidenceType> = ArrayList<EvidenceType>(),
        val strictEvidences: ArrayList<EvidenceType> = ArrayList<EvidenceType>()
    )

}