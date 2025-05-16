package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.repository

import android.content.Context
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.source.local.GhostBoxLocalDataSource

class GhostBoxRepository(
    context: Context,
    private val localSource: GhostBoxLocalDataSource
) {

    val voiceRequests: MutableMap<String, Int> = mutableMapOf()

    fun fetchVoiceRequests(context: Context) {
        localSource.fetchGeneralRequests(context).forEach {
            voiceRequests[GhostBoxType.GENERAL.title] = it
        }
        localSource.fetchSpiritBoxRequests(context).forEach {
            voiceRequests[GhostBoxType.SPIRIT_BOX.title] = it
        }
        localSource.fetchOuijaBoardRequests(context).forEach {
            voiceRequests[GhostBoxType.OUIJA_BOARD.title] = it
        }
    }

    init {
        fetchVoiceRequests(context)
    }

}

enum class GhostBoxType(val title: String) {
    GENERAL("General"),
    SPIRIT_BOX("Spirit Box"),
    OUIJA_BOARD("Ouija Board")
}