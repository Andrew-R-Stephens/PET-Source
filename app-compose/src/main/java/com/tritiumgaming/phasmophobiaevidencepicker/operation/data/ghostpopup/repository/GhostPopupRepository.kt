package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostpopup.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostpopup.source.local.GhostPopupLocalDataSource

class GhostPopupRepository(
    context: Context,
    localSource: GhostPopupLocalDataSource
) {

    val popups = localSource.fetchPopups(context)

}