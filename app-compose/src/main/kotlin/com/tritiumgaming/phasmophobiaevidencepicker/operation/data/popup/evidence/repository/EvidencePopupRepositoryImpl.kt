package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.popup.evidence.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model.EvidencePopupRecord
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.repository.EvidencePopupRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source.EvidencePopupDataSource

class EvidencePopupRepositoryImpl(
    override val localSource: EvidencePopupDataSource
): EvidencePopupRepository<EvidencePopupDataSource, EvidencePopupRecord> {

    override fun getPopups(): Result<List<EvidencePopupRecord>> = localSource.fetchPopups()

}