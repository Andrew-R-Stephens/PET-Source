package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.mapper.GhostBoxResources.Response
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.source.GhostBoxDataSource

interface GhostBoxRepository {

    fun getVoiceRequests(): Result<MutableMap<String, Response>>

}
