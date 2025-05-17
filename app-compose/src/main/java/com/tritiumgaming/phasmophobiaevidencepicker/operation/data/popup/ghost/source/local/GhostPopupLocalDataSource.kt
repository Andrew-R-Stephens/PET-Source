package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.popup.ghost.source.local

import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model.GhostPopupRecord
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source.GhostPopupDataSource

class GhostPopupLocalDataSource: GhostPopupDataSource {


    override fun fetchPopups(context: Context): ArrayList<GhostPopupRecord> {
        var records: ArrayList<GhostPopupRecord> = ArrayList()

        val resources = context.resources

        val ghostsTypedArray = resources.obtainTypedArray(R.array.ghosts_array)
        for(i in 0 until ghostsTypedArray.length()) {
            val ghostTypedArray = ghostsTypedArray.getResourceId(i, 0)
            records.add(
                readGhost(resources, i, ghostTypedArray)
            )
        }
        ghostsTypedArray.recycle()

        return records
    }

    override fun readGhost(resources: Resources, index: Int, ghostsArrayID: Int): GhostPopupRecord {

        val ghostsTypedArray = resources.obtainTypedArray(ghostsArrayID)

        val keyId = 0
        val keyInfo = 3
        val keyStrengths = 4
        val keyWeaknesses = 5
        val keyHuntData = 6

        @StringRes val id = ghostsTypedArray.getResourceId(keyId, 0)
        @StringRes val info = ghostsTypedArray.getResourceId(keyInfo, 0)
        @StringRes val strength = ghostsTypedArray.getResourceId(keyStrengths, 0)
        @StringRes val weakness = ghostsTypedArray.getResourceId(keyWeaknesses, 0)
        @StringRes val huntData = ghostsTypedArray.getResourceId(keyHuntData, 0)

        ghostsTypedArray.recycle()

        // Set Popup
        val record = GhostPopupRecord(
            id = resources.getString(id),
            infoArray = info,
            strengthArray = strength,
            weaknessArray = weakness,
            huntDataArray = huntData
        )

        return record
    }

}