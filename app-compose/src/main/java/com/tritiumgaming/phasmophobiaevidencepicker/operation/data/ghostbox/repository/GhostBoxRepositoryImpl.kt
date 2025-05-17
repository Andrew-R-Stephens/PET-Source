package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.repository.GhostBoxRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.repository.GhostBoxType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.source.GhostBoxDataSource

class GhostBoxRepositoryImpl(
    context: Context,
    override val localSource: GhostBoxDataSource
): GhostBoxRepository {

    override val voiceRequests: MutableMap<String, Int> = mutableMapOf()

    override fun fetchVoiceRequests(context: Context) {
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
