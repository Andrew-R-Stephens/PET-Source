package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidencepopup.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidencepopup.source.local.EvidencePopupLocalDataSource

class EvidencePopupRepository(
    context: Context,
    localSource: EvidencePopupLocalDataSource
) {

    val popups = localSource.fetchPopups(context)

}