package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.mapper.GhostBoxResources.Response
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.model.GhostBoxType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.repository.GhostBoxRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.source.GhostBoxDataSource

class GhostBoxRepositoryImpl(
    override val localSource: GhostBoxDataSource
): GhostBoxRepository {

    override fun getVoiceRequests(): Result<MutableMap<String, Response>> {

        val voiceRequests = mutableMapOf<String, Response>()

        val generalResult = localSource.fetchGeneralRequests()
        generalResult.exceptionOrNull()?.printStackTrace()
        generalResult.getOrNull()?.forEach {
            voiceRequests[GhostBoxType.GENERAL.title] = it.content
        }

        val spiritBoxResult = localSource.fetchSpiritBoxRequests()
        spiritBoxResult.exceptionOrNull()?.printStackTrace()
        spiritBoxResult.getOrNull()?.forEach {
            voiceRequests[GhostBoxType.SPIRIT_BOX.title] = it.content
        }

        val ouijaBoardResult = localSource.fetchOuijaBoardRequests()
        ouijaBoardResult.exceptionOrNull()?.printStackTrace()
        ouijaBoardResult.getOrNull()?.forEach {
            voiceRequests[GhostBoxType.OUIJA_BOARD.title] = it.content
        }

        return Result.success(voiceRequests)
    }

}
