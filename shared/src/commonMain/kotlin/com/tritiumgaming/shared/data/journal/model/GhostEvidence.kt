package com.tritiumgaming.shared.data.journal.model

import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.ghost.model.Ghost

expect class GhostEvidence(
    ghost: Ghost,
    normalEvidenceList: List<EvidenceType>,
    strictEvidenceList: List<EvidenceType>
) {

    val ghost: Ghost
    val normalEvidenceList: List<EvidenceType>
    val strictEvidenceList: List<EvidenceType>

    override fun toString(): String

}