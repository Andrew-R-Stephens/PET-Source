package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.source.GhostBoxDataSource

interface GhostBoxRepository {
    val localSource: GhostBoxDataSource

    val voiceRequests: MutableMap<String, Int>

    fun fetchVoiceRequests(context: Context)

}

enum class GhostBoxType(val title: String) {
    GENERAL("General"),
    SPIRIT_BOX("Spirit Box"),
    OUIJA_BOARD("Ouija Board")
}