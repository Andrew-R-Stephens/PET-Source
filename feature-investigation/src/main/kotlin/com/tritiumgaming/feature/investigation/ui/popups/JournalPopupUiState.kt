package com.tritiumgaming.feature.investigation.ui.popups

import com.tritiumstudios.data.operation.model.popup.EvidencePopupRecord
import com.tritiumstudios.data.operation.model.popup.GhostPopupRecord

data class JournalPopupUiState (
    val isShown: Boolean = false,
    val evidencePopupRecord: EvidencePopupRecord? = null,
    val ghostPopupRecord: GhostPopupRecord? = null
)