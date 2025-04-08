package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.ghost

import androidx.compose.runtime.Stable
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel

class GhostEvidenceModel(
    @Stable
    val normalEvidenceList: ArrayList<EvidenceModel> = ArrayList<EvidenceModel>(),
    @Stable
    val strictEvidenceList: ArrayList<EvidenceModel> = ArrayList<EvidenceModel>()
) {
    fun addNormalEvidence(e: EvidenceModel) {
        normalEvidenceList.add(e)
    }

    fun addStrictEvidence(e: EvidenceModel) {
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