package com.tritiumgaming.data.ghostname.repository

import com.tritiumgaming.data.ghostname.model.GhostName
import com.tritiumgaming.data.ghostname.model.GhostName.NamePriority

interface GhostNameRepository {

    fun getNames(): Result<List<GhostName>>
    fun getNamesBy(
        namePriority: NamePriority? = null,
        gender: GhostName.Gender? = null
    ): Result<List<GhostName>>

}