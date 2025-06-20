package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model

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
    actual val normalEvidenceList: List<EvidenceType>
    actual val strictEvidenceList: List<EvidenceType>

    actual override fun toString(): String {
        val s = StringBuilder()
        for (e in normalEvidenceList) { s.append(e.name).append(", ") }
        if (strictEvidenceList.isNotEmpty()) { s.append(" / ") }
        for (e in strictEvidenceList) { s.append(e.name).append(", ") }
        return s.toString()
    }
}
