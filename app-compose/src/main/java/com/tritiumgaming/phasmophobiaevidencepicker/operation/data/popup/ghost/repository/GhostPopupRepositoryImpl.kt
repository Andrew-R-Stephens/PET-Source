package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.popup.ghost.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.repository.GhostPopupRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source.GhostPopupDataSource

class GhostPopupRepositoryImpl(
    context: Context,
    override val localSource: GhostPopupDataSource
): GhostPopupRepository {

    override val popups = localSource.fetchPopups(context)

}