package com.tritiumgaming.feature.investigation.ui.journal.lists.evidence

import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.evidence.model.EvidenceValidationType

data class EvidenceListUiActions(
    val onChangeEvidenceRuling: (evidence: EvidenceType, evidenceValidationType: EvidenceValidationType) -> Unit = { _, _ -> },
    val onClickItem: (evidence: EvidenceType) -> Unit = {}
)
