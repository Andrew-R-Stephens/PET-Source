package com.tritiumgaming.feature.operation.ui.investigation.journal.popups

import com.tritiumgaming.shared.operation.domain.popup.model.EvidencePopupRecord
import com.tritiumgaming.shared.operation.domain.popup.model.GhostPopupRecord

data class InvestigationPopupUiState (
    val isShown: Boolean = false,
    val evidencePopupRecord: EvidencePopupRecord? = null,
    val ghostPopupRecord: GhostPopupRecord? = null
)