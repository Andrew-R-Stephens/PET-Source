package com.tritiumgaming.shared.data.journal.model

import androidx.compose.runtime.Stable
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.ghost.model.GhostType

actual class GhostEvidence {

    actual val ghost: GhostType

    @Stable
    actual val normalEvidenceList: List<EvidenceType>
    @Stable
    actual val strictEvidenceList: List<EvidenceType>

    actual constructor(
        ghost: GhostType,
        normalEvidenceList: List<EvidenceType>,
        strictEvidenceList: List<EvidenceType>
    ) {
        this.ghost = ghost
        this.normalEvidenceList = normalEvidenceList
        this.strictEvidenceList = strictEvidenceList
    }

    actual override fun toString(): String {
        val s = StringBuilder()
        for (e in normalEvidenceList) { s.append(e.name).append(", ") }
        if (strictEvidenceList.isNotEmpty()) { s.append(" / ") }
        for (e in strictEvidenceList) { s.append(e.name).append(", ") }
        return s.toString()
    }

}
