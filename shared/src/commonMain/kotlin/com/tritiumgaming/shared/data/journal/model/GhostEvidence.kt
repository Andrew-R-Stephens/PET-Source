package com.tritiumgaming.shared.data.journal.model

import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.ghost.model.GhostType

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