package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghost.source

import android.content.Context
import android.content.res.Resources
import androidx.annotation.ArrayRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostType

interface GhostDataSource {

    fun fetchGhosts(context: Context): ArrayList<GhostType>

    fun readGhost(resources: Resources, @ArrayRes ghostsArrayID: Int): GhostType

}