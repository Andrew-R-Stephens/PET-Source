package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.source

import androidx.annotation.StringRes

interface GhostBoxDataSource {

    @StringRes fun fetchGeneralRequests(): IntArray
    @StringRes fun fetchSpiritBoxRequests(): IntArray
    @StringRes fun fetchOuijaBoardRequests(): IntArray

}