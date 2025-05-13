package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.util.ResourceUtils
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.investigation.popups.GhostPopup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.ghost.GhostType

class GhostLocalDataSource(
    context: Context
) {

    var ghosts: ArrayList<GhostType> = ArrayList()

    init {
        val resources = context.resources

        // Populate Ghosts

        ghosts.clear()

        val namesTypedArray = resources.obtainTypedArray(R.array.ghost_names)
        val namesArray = ResourceUtils.intArrayFromTypedArray(resources, namesTypedArray)

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

            // Finalize Ghost
            val ghost = GhostType(
                id = i,
                name = namesArray[i],
                popupModel = ghostPopupModel
            )

            ghosts.add(ghost)
        }

        infoTypedArray.recycle()
        strengthsTypedArray.recycle()
        weaknessesTypedArray.recycle()
        huntDataTypedArray.recycle()
    }

}