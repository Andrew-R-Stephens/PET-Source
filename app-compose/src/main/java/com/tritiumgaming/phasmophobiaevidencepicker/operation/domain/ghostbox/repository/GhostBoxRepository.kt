package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.source.GhostBoxDataSource

interface GhostBoxRepository {
    val localSource: GhostBoxDataSource

    fun getVoiceRequests(): MutableMap<String, Int>

}
