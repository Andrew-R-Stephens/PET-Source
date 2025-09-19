package com.tritiumgaming.shared.operation.domain.journal.model

import androidx.compose.runtime.Stable

actual class GhostEvidence {

    actual constructor(
        ghost: GhostType,
        normalEvidenceList: List<EvidenceType>,
        strictEvidenceList: List<EvidenceType>
    ) {
        this.ghost = ghost
        this.normalEvidenceList = normalEvidenceList
        this.strictEvidenceList = strictEvidenceList
    }

    actual val ghost: GhostType
    @Stable
    actual val normalEvidenceList: List<EvidenceType>
    @Stable
    actual val strictEvidenceList: List<EvidenceType>

    actual override fun toString(): String {
        val s = StringBuilder()
        for (e in normalEvidenceList) { s.append(e.name).append(", ") }
        if (strictEvidenceList.isNotEmpty()) { s.append(" / ") }
        for (e in strictEvidenceList) { s.append(e.name).append(", ") }
        return s.toString()
    }

}
