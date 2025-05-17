package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.popup.evidence.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.repository.EvidencePopupRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source.EvidencePopupDataSource

class EvidencePopupRepositoryImpl(
    context: Context,
    override val localSource: EvidencePopupDataSource
): EvidencePopupRepository {

    override val popups = localSource.fetchPopups(context)

}