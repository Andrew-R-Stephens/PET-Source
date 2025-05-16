package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.source.local

import android.content.Context
import android.content.res.Resources
import androidx.annotation.ArrayRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.journal.type.ghost.GhostType

class GhostLocalDataSource {

    fun fetchGhosts(context: Context): ArrayList<GhostType> {
        var ghosts: ArrayList<GhostType> = ArrayList()

        val resources = context.resources

        val ghostsTypedArray = resources.obtainTypedArray(R.array.ghosts_array)
        for(i in 0 until ghostsTypedArray.length()) {
            val ghostTypedArray = ghostsTypedArray.getResourceId(i, 0)
            ghosts.add(readGhost(resources, ghostTypedArray))
        }
        ghostsTypedArray.recycle()

        return ghosts
    }

    private fun readGhost(
        resources: Resources,
        @ArrayRes ghostsArrayID: Int
    ): GhostType {

        val ghostsTypedArray = resources.obtainTypedArray(ghostsArrayID)

        val keyId = 0
        val keyName = 1
        val keyEvidences = 2
        val keyEvidencesArr = ghostsTypedArray.getResourceId(keyEvidences, 0)

        val id = ghostsTypedArray.getResourceId(keyId, 0)
        val name = ghostsTypedArray.getResourceId(keyName, 0)


        /*val evidencesTypedArray = resources.obtainTypedArray(keyEvidencesArr)

        val keyNormalEvidences = 0
        val keyRequiredEvidences = 1

        val evidencesArr = mutableListOf<GhostEvidenceDto>()

        for (i in 0 until evidencesTypedArray.length()) {

            val ghostEvidenceDto = GhostEvidenceDto()

            val normalEvidenceIdTypedArray = resources.obtainTypedArray(
                evidencesTypedArray.getResourceId(keyNormalEvidences, 0))

            for (j in 0 until normalEvidenceIdTypedArray.length()) {

                @IntegerRes val evidenceIdRes = normalEvidenceIdTypedArray.getResourceId(j, 0)
                val evidenceId = resources.getString(evidenceIdRes)

                ghostEvidenceDto.normalEvidences.add(evidenceId)
            }
            normalEvidenceIdTypedArray.recycle()

            val strictEvidenceIdTypedArray = resources.obtainTypedArray(
                evidencesTypedArray.getResourceId(keyRequiredEvidences, 0))

            for (j in 0 until strictEvidenceIdTypedArray.length()) {

                @IntegerRes val evidenceIdRes = strictEvidenceIdTypedArray.getResourceId(j, 0)
                val evidenceId = resources.getString(evidenceIdRes)

                ghostEvidenceDto.strictEvidences.add(evidenceId)
            }

            strictEvidenceIdTypedArray.recycle()

            evidencesArr.add(ghostEvidenceDto)

        }

        evidencesTypedArray.recycle()*/

        ghostsTypedArray.recycle()

        // Finalize Ghost
        val ghost = GhostType(
            id = resources.getString(id),
            name = name
        )

        return ghost
    }

}