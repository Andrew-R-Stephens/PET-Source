package com.tritiumstudios.feature.investigation.ui.popups

import com.tritiumgaming.shared.data.popup.model.EvidencePopupRecord
import com.tritiumgaming.shared.data.popup.model.GhostPopupRecord

data class JournalPopupUiState (
    val isShown: Boolean = false,
    val evidencePopupRecord: EvidencePopupRecord? = null,
    val ghostPopupRecord: GhostPopupRecord? = null
)