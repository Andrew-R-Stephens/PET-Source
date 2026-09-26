package com.tritiumgaming.data.ghostbox.repository

import com.tritiumgaming.data.ghostbox.source.GhostBoxDataSource
import com.tritiumgaming.shared.data.ghostbox.mapper.GhostBoxResources.Response
import com.tritiumgaming.shared.data.ghostbox.model.GhostBoxType
import com.tritiumgaming.shared.data.ghostbox.repository.GhostBoxRepository

class GhostBoxRepositoryImpl(
    val localSource: GhostBoxDataSource
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
