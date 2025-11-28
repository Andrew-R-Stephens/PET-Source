package com.tritiumgaming.shared.data.ghostbox.repository

import com.tritiumgaming.shared.data.ghostbox.mapper.GhostBoxResources.Response

interface GhostBoxRepository {

    fun getVoiceRequests(): Result<MutableMap<String, Response>>

}
