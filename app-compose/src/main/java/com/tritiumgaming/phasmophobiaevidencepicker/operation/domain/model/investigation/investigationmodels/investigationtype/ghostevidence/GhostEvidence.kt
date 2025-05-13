package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.ghostevidence

import androidx.compose.runtime.Stable
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceType

class GhostEvidence(
    @Stable
    val normalEvidenceList: ArrayList<EvidenceType> = ArrayList<EvidenceType>(),
    @Stable
    val strictEvidenceList: ArrayList<EvidenceType> = ArrayList<EvidenceType>()
) {
    fun addNormalEvidence(e: EvidenceType) {
        normalEvidenceList.add(e)
    }

    fun addStrictEvidence(e: EvidenceType) {
        strictEvidenceList.add(e)
    }

    override fun toString(): String {
        val s = StringBuilder()
        for (e in normalEvidenceList) { s.append(e.name).append(", ") }
        if (strictEvidenceList.isNotEmpty()) { s.append(" / ") }
        for (e in strictEvidenceList) { s.append(e.name).append(", ") }
        return s.toString()
    }
}