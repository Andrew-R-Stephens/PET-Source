package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.model.GhostBoxType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.repository.GhostBoxRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.source.GhostBoxDataSource

class GhostBoxRepositoryImpl(
    override val localSource: GhostBoxDataSource
): GhostBoxRepository {

    override fun getVoiceRequests(): MutableMap<String, Int> {

        val voiceRequests = mutableMapOf<String, Int>()

        localSource.fetchGeneralRequests().forEach {
            voiceRequests[GhostBoxType.GENERAL.title] = it
        }
        localSource.fetchSpiritBoxRequests().forEach {
            voiceRequests[GhostBoxType.SPIRIT_BOX.title] = it
        }
        localSource.fetchOuijaBoardRequests().forEach {
            voiceRequests[GhostBoxType.OUIJA_BOARD.title] = it
        }

        return voiceRequests
    }

}
