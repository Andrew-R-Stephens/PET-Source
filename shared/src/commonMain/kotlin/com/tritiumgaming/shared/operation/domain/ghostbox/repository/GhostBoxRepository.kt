package com.tritiumgaming.shared.operation.domain.ghostbox.repository

import com.tritiumgaming.shared.operation.domain.ghostbox.mapper.GhostBoxResources.Response

interface GhostBoxRepository {

    fun getVoiceRequests(): Result<MutableMap<String, Response>>

}
