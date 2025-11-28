package com.tritiumgaming.shared.data.ghostname.repository

interface GhostNameRepository {

    fun getNames(): Result<List<com.tritiumgaming.shared.data.ghostname.model.GhostName>>
    fun getNamesBy(
        namePriority: com.tritiumgaming.shared.data.ghostname.model.GhostName.NamePriority? = null,
        gender: com.tritiumgaming.shared.data.ghostname.model.GhostName.Gender? = null
    ): Result<List<com.tritiumgaming.shared.data.ghostname.model.GhostName>>

}