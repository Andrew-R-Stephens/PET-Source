package com.tritiumgaming.feature.investigation.ui.journal.lists.evidence

import com.tritiumgaming.shared.data.evidence.model.EvidenceValidationType
import com.tritiumgaming.shared.data.evidence.model.EvidenceType

data class EvidenceListUiActions(
    val onChangeEvidenceRuling: (evidence: EvidenceType, evidenceValidationType: EvidenceValidationType) -> Unit,
    val onClickItem: (evidence: EvidenceType) -> Unit
)
