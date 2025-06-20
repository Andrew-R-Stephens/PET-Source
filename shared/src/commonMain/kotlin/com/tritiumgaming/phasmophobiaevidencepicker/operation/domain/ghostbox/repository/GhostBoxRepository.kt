package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.mapper.GhostBoxResources.Response

interface GhostBoxRepository {

    fun getVoiceRequests(): Result<MutableMap<String, Response>>

}
