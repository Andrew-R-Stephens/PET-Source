package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model

import androidx.compose.runtime.Stable

class GhostEvidence(
    val ghost: GhostType,
    @Stable
    val normalEvidenceList: List<EvidenceType>,
    @Stable
    val strictEvidenceList: List<EvidenceType>
) {

    override fun toString(): String {
        val s = StringBuilder()
        for (e in normalEvidenceList) { s.append(e.name).append(", ") }
        if (strictEvidenceList.isNotEmpty()) { s.append(" / ") }
        for (e in strictEvidenceList) { s.append(e.name).append(", ") }
        return s.toString()
    }

}
