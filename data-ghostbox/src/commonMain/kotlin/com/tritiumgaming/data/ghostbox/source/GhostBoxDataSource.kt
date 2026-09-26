package com.tritiumgaming.data.ghostbox.source

import com.tritiumgaming.data.ghostbox.dto.GhostBoxResponseDto

interface GhostBoxDataSource {

    fun fetchGeneralRequests(): Result<List<GhostBoxResponseDto>>
    fun fetchSpiritBoxRequests(): Result<List<GhostBoxResponseDto>>
    fun fetchOuijaBoardRequests(): Result<List<GhostBoxResponseDto>>

}