package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.ghost

import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel


class GhostModel(
    var id: Int = 0,
    var name: Int = 0
) {

    val normalEvidenceList = ArrayList<EvidenceModel>()
    val strictEvidenceList = ArrayList<EvidenceModel>()

    private fun addEvidence(e: EvidenceModel) {
        normalEvidenceList.add(e)
    }

    fun addEvidence(evidenceList: ArrayList<EvidenceModel>, evidenceID: Int) {
        for (e in evidenceList) {
            if (evidenceID == e.id) {
                addEvidence(e)
                break
            }
        }
    }

    private fun addNightmareEvidence(e: EvidenceModel) {
        strictEvidenceList.add(e)
    }

    fun addNightmareEvidence(evidenceList: ArrayList<EvidenceModel>, evidenceID: Int) {
        for (e in evidenceList) {
            if (evidenceID == e.id) {
                addNightmareEvidence(e)
                break
            }
        }
    }

    override fun toString(): String {
        val s = StringBuilder(name).append(": ")
        for (e in normalEvidenceList) { s.append(e.name).append(", ") }
        if (strictEvidenceList.isNotEmpty()) { s.append(" / ") }
        for (e in strictEvidenceList) { s.append(e.name).append(", ") }
        return s.toString()
    }
}
