package com.tritiumgaming.feature.investigation.ui.journal.lists.evidence

import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence

data class EvidenceListUiActions(
    val onChangeEvidenceRuling: (evidence: EvidenceType, ruling: RuledEvidence.Ruling) -> Unit,
    val onClickItem: (evidence: EvidenceType) -> Unit
)
