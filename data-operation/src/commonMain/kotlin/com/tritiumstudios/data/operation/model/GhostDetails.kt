package com.tritiumstudios.data.operation.model

import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.GhostResponseType
import com.tritiumgaming.data.ghostname.model.GhostName

data class GhostDetails(
    val firstName: GhostName? = null,
    val surname: GhostName? = null,
    val responseState: GhostResponseType = GhostResponseType.ALONE
)
