package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.source.local

import android.content.Context
import android.content.res.Resources
import androidx.annotation.ArrayRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghost.source.GhostDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostType

class GhostLocalDataSource: GhostDataSource {

    override fun fetchGhosts(context: Context): ArrayList<GhostType> {
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

    override fun readGhost(
        resources: Resources,
        @ArrayRes ghostsArrayID: Int
    ): GhostType {

        val ghostsTypedArray = resources.obtainTypedArray(ghostsArrayID)

        val keyId = 0
        val keyName = 1

        val id = ghostsTypedArray.getResourceId(keyId, 0)
        val name = ghostsTypedArray.getResourceId(keyName, 0)

        ghostsTypedArray.recycle()

        // Finalize Ghost
        val ghost = GhostType(
            id = resources.getString(id),
            name = name
        )

        return ghost
    }

}