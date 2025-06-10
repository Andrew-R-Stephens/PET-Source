package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.popup.ghost.source.local

import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model.GhostPopupRecord
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source.GhostPopupDataSource

class GhostPopupLocalDataSource(
    private val applicationContext: Context
): GhostPopupDataSource {

    override fun fetchPopups(): Result<ArrayList<GhostPopupRecord>> {

        val resources = applicationContext.resources

        var records: ArrayList<GhostPopupRecord> = ArrayList()

        val ghostsTypedArray = resources.obtainTypedArray(R.array.ghosts_array)
        for(i in 0 until ghostsTypedArray.length()) {
            val evidenceTypedArrayId = ghostsTypedArray.getResourceId(i, 0)
            records.add(
                readGhost(resources, evidenceTypedArrayId)
            )
        }
        ghostsTypedArray.recycle()

        return Result.success(records)
    }

    private fun readGhost(
        resources: Resources,
        ghostsArrayID: Int
    ): GhostPopupRecord {

        val ghostTypedArray = resources.obtainTypedArray(ghostsArrayID)

        val keyId = 0
        val keyInfo = 3
        val keyStrengths = 4
        val keyWeaknesses = 5
        val keyHuntData = 6

        @StringRes val id = ghostTypedArray.getResourceId(keyId, 0)
        @StringRes val info = ghostTypedArray.getResourceId(keyInfo, 0)
        @StringRes val strength = ghostTypedArray.getResourceId(keyStrengths, 0)
        @StringRes val weakness = ghostTypedArray.getResourceId(keyWeaknesses, 0)
        @StringRes val huntData = ghostTypedArray.getResourceId(keyHuntData, 0)

        ghostTypedArray.recycle()

        // Set Popup
        val record = GhostPopupRecord(
            id = resources.getString(id),
            generalInfo = info,
            strengthData = strength,
            weaknessData = weakness,
            huntData = huntData
        )

        return record
    }

}