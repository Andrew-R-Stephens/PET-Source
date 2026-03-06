package com.tritiumgaming.shared.data.ghostname.repository

import com.tritiumgaming.shared.data.ghostname.model.GhostName
import com.tritiumgaming.shared.data.ghostname.model.GhostName.*

interface GhostNameRepository {

    fun getNames(): Result<List<GhostName>>
    fun getNamesBy(
        namePriority: NamePriority? = null,
        gender: Gender? = null
    ): Result<List<GhostName>>

}