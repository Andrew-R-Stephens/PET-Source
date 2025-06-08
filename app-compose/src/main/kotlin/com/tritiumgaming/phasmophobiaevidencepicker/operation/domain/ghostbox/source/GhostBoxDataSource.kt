package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.source

interface GhostBoxDataSource {

    fun fetchGeneralRequests(): Result<IntArray>
    fun fetchSpiritBoxRequests(): Result<IntArray>
    fun fetchOuijaBoardRequests(): Result<IntArray>

}