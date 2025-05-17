package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model.PopupRecord
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source.PopupDataSource

interface PopupRepository {
    val localSource: PopupDataSource

    val popups: List<PopupRecord>
}