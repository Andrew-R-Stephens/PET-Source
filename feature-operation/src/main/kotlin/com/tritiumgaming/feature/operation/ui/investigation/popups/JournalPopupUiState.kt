package com.tritiumgaming.feature.operation.ui.investigation.popups

import com.tritiumgaming.shared.operation.domain.popup.model.EvidencePopupRecord
import com.tritiumgaming.shared.operation.domain.popup.model.GhostPopupRecord

data class JournalPopupUiState (
    val isShown: Boolean = false,
    val evidencePopupRecord: EvidencePopupRecord? = null,
    val ghostPopupRecord: GhostPopupRecord? = null
)