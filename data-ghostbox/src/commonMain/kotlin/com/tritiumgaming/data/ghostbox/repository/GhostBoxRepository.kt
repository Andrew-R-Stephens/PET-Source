package com.tritiumgaming.data.ghostbox.repository

import com.tritiumgaming.data.ghostbox.mapper.GhostBoxResources.Response

interface GhostBoxRepository {

    fun getVoiceRequests(): Result<MutableMap<String, Response>>

}
