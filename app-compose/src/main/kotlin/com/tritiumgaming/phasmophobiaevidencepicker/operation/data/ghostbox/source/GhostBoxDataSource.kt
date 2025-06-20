package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.dto.GhostBoxResponseDto

interface GhostBoxDataSource {

    fun fetchGeneralRequests(): Result<List<GhostBoxResponseDto>>
    fun fetchSpiritBoxRequests(): Result<List<GhostBoxResponseDto>>
    fun fetchOuijaBoardRequests(): Result<List<GhostBoxResponseDto>>

}