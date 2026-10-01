package com.tritiumstudios.data.operation.model

import com.tritiumgaming.data.ghostname.model.GhostName

typealias Response = Int

data class GhostDetails(
    val firstName: GhostName? = null,
    val surname: GhostName? = null,
    val responseState: Response = RESPONSE_ALONE
) {
    companion object {
        const val RESPONSE_UNKNOWN: Response = 0
        const val RESPONSE_ALONE: Response = 1
        const val RESPONSE_GROUP: Response = 2
    }
}
