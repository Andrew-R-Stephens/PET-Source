package com.tritiumgaming.shared.operation.domain.journal.model

import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType
import com.tritiumgaming.shared.operation.domain.ghost.model.GhostType

expect class GhostEvidence(
    ghost: GhostType,
    normalEvidenceList: List<EvidenceType>,
    strictEvidenceList: List<EvidenceType>
) {

    val ghost: GhostType
    val normalEvidenceList: List<EvidenceType>
    val strictEvidenceList: List<EvidenceType>

    override fun toString(): String

}