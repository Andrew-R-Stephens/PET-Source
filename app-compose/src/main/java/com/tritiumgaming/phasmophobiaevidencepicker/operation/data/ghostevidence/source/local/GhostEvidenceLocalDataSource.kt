package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostevidence.source.local

import android.content.Context
import android.content.res.Resources
import androidx.annotation.ArrayRes
import androidx.annotation.IntegerRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostevidence.source.GhostEvidenceDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostEvidence.GhostEvidenceDto

class GhostEvidenceLocalDataSource(
    private val applicationContext: Context
): GhostEvidenceDataSource {

    override fun fetchGhostEvidences(): ArrayList<GhostEvidenceDto> {

        val resources = applicationContext.resources

        var ghostEvidences: ArrayList<GhostEvidenceDto> = ArrayList()

        val ghostsTypedArray = resources.obtainTypedArray(R.array.ghosts_array)
        for(i in 0 until ghostsTypedArray.length()) {
            val ghostTypedArray = ghostsTypedArray.getResourceId(i, 0)
            ghostEvidences.add(readGhost(resources, ghostTypedArray))
        }
        ghostsTypedArray.recycle()

        return ghostEvidences
    }

    private fun readGhost(
        resources: Resources,
        @ArrayRes ghostsArrayID: Int
    ): GhostEvidenceDto {

        val ghostsTypedArray = resources.obtainTypedArray(ghostsArrayID)

        val keyId = 0
        val keyEvidences = 2

        val id = ghostsTypedArray.getResourceId(keyId, 0)
        val keyEvidencesArr = ghostsTypedArray.getResourceId(keyEvidences, 0)

        val evidencesTypedArray = resources.obtainTypedArray(keyEvidencesArr)

        val keyNormalEvidences = 0
        val keyRequiredEvidences = 1

        val evidenceDto = GhostEvidenceDto(
            ghostId = resources.getString(id)
        )

        val normalEvidenceIdTypedArray = resources.obtainTypedArray(
            evidencesTypedArray.getResourceId(keyNormalEvidences, 0))

        for (j in 0 until normalEvidenceIdTypedArray.length()) {

            @IntegerRes val evidenceIdRes = normalEvidenceIdTypedArray.getResourceId(j, 0)
            val evidenceId = resources.getString(evidenceIdRes)

            evidenceDto.normalEvidences.add(evidenceId)
        }
        normalEvidenceIdTypedArray.recycle()

        val strictEvidenceIdTypedArray = resources.obtainTypedArray(
            evidencesTypedArray.getResourceId(keyRequiredEvidences, 0))

        for (j in 0 until strictEvidenceIdTypedArray.length()) {

            @IntegerRes val evidenceIdRes = strictEvidenceIdTypedArray.getResourceId(j, 0)
            val evidenceId = resources.getString(evidenceIdRes)

            evidenceDto.strictEvidences.add(evidenceId)
        }

        strictEvidenceIdTypedArray.recycle()

        evidencesTypedArray.recycle()

        ghostsTypedArray.recycle()

        return evidenceDto
    }

}