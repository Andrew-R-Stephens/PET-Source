package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.source

import android.content.Context
import androidx.annotation.StringRes

interface GhostBoxDataSource {

    @StringRes fun fetchGeneralRequests(context: Context): IntArray
    @StringRes fun fetchSpiritBoxRequests(context: Context): IntArray
    @StringRes fun fetchOuijaBoardRequests(context: Context): IntArray

}