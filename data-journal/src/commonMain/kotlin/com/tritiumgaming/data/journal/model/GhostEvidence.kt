package com.tritiumgaming.data.journal.model

import com.tritiumgaming.data.evidence.model.EvidenceType
import com.tritiumgaming.data.ghost.model.Ghost

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