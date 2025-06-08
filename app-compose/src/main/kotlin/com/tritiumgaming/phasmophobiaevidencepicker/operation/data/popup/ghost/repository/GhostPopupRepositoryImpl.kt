package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.popup.ghost.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model.GhostPopupRecord
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.repository.GhostPopupRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source.GhostPopupDataSource

class GhostPopupRepositoryImpl(
    override val localSource: GhostPopupDataSource
): GhostPopupRepository<GhostPopupDataSource, GhostPopupRecord> {

    override fun getPopups(): Result<List<GhostPopupRecord>> = localSource.fetchPopups()

}