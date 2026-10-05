package com.tritiumgaming.feature.investigation.ui.components.journal.evidence

import com.tritiumgaming.data.evidence.model.EvidenceType
import com.tritiumgaming.data.journal.model.EvidenceValidationType

internal data class EvidenceListUiActions(
    val onChangeEvidenceRuling: (
        evidence: EvidenceType,
        evidenceValidationType: EvidenceValidationType
    ) -> Unit = { _, _ -> },
    val onClickItem: (evidence: EvidenceType) -> Unit = {}
)