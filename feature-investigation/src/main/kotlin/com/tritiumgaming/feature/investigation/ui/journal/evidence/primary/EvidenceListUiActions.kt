package com.tritiumgaming.feature.investigation.ui.journal.evidence.primary

import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.investigation.model.EvidenceValidationType

data class EvidenceListUiActions(
    val onChangeEvidenceRuling: (evidence: EvidenceType, evidenceValidationType: EvidenceValidationType) -> Unit = { _, _ -> },
    val onClickItem: (evidence: EvidenceType) -> Unit = {}
)