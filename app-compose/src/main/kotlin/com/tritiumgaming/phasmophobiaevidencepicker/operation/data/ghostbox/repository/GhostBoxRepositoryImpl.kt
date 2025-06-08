package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.model.GhostBoxType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.repository.GhostBoxRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.source.GhostBoxDataSource

class GhostBoxRepositoryImpl(
    override val localSource: GhostBoxDataSource
): GhostBoxRepository {

    override fun getVoiceRequests(): Result<MutableMap<String, Int>> {

        val voiceRequests = mutableMapOf<String, Int>()

        val generalResult = localSource.fetchGeneralRequests()
        generalResult.exceptionOrNull()?.printStackTrace()
        generalResult.getOrNull()?.forEach {
            voiceRequests[GhostBoxType.GENERAL.title] = it
        }

        val spiritBoxResult = localSource.fetchSpiritBoxRequests()
        spiritBoxResult.exceptionOrNull()?.printStackTrace()
        spiritBoxResult.getOrNull()?.forEach {
            voiceRequests[GhostBoxType.SPIRIT_BOX.title] = it
        }

        val ouijaBoardResult = localSource.fetchOuijaBoardRequests()
        ouijaBoardResult.exceptionOrNull()?.printStackTrace()
        ouijaBoardResult.getOrNull()?.forEach {
            voiceRequests[GhostBoxType.OUIJA_BOARD.title] = it
        }

        return Result.success(voiceRequests)
    }

}
