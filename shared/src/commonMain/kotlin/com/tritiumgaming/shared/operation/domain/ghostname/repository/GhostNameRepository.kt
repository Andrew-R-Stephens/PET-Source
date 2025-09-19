package com.tritiumgaming.shared.operation.domain.ghostname.repository

import com.tritiumgaming.shared.operation.domain.ghostname.model.GhostName

interface GhostNameRepository {

    fun getNames(): Result<List<GhostName>>
    fun getNamesBy(
        namePriority: GhostName.NamePriority? = null,
        gender: GhostName.Gender? = null
    ): Result<List<GhostName>>

}