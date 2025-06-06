package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.ghost

import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.InvestigationModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel.Ruling
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.carousels.DifficultyCarouselModel

class GhostModel(
    val investigationModel: InvestigationModel,
    var id: Int = 0,
    var name: Int = 0
) {

    var forcefullyRejected: Boolean = false

    private val thisGhostEvidence = ArrayList<EvidenceModel>()
    private val thisGhostRequiredEvidence = ArrayList<EvidenceModel>()

    val evidence: Array<EvidenceModel?>
        get() {
            val t = arrayOfNulls<EvidenceModel>(thisGhostEvidence.size)
            return thisGhostEvidence.toArray(t)
        }

    val evidenceArray: Array<EvidenceModel?>
        get() {
            val newEvidence = arrayOfNulls<EvidenceModel>(thisGhostEvidence.size)
            for (i in newEvidence.indices) {
                newEvidence[i] = thisGhostEvidence[i]
            }
            return newEvidence
        }

    val requiredEvidenceArray: Array<EvidenceModel?>
        get() {
            val newEvidence = arrayOfNulls<EvidenceModel>(thisGhostRequiredEvidence.size)
            for (i in newEvidence.indices) {
                newEvidence[i] = thisGhostRequiredEvidence[i]
            }
            return newEvidence
        }

    val evidenceScore: Int
        /**
         * getEvidenceScore method
         * Determines the possibility of the ghost based on user-determined Evidence.
         * Score starts at '0'.
         * Score adds +1 if Ghost's Evidence list contains a positive Evidence.
         * Score can surpass a positive value of '3' if the ghost is a Mimic.
         * Score sets to '-5' if the Ghost has been forcefully rejected by user.
         * Score sets to '-5' if an Evidence type is positive but not found in Ghost's Evidence
         * list.
         * Score sets to '-5' if an Evidence type is negative and found in Ghost's Evidence list.
         * @return numerical representation of the Ghost's Evidence score
         */
        get() {
            if (forcefullyRejected) { return -5 }

            val currentDifficulty =
                investigationModel?.investigationViewModel?.difficultyCarouselModel?.currentDifficulty

            val isNightmare = currentDifficulty == DifficultyCarouselModel.Difficulty.NIGHTMARE
            val isInsanity = currentDifficulty == DifficultyCarouselModel.Difficulty.INSANITY

            val maxPosScore = when {
                isInsanity -> 1
                isNightmare -> 2
                else -> 3
            }
            /*
            val maxPosScore =
                if (isInsanity) 1
                else
                    if (isNightmare) 2
                    else 3
            */

            var posScore = 0
            var negScore = 0

            investigationModel?.evidenceListModel?.evidenceList?.let { list ->
                for (e in list) {
                    var isContained = false
                    for (eThis in thisGhostEvidence) {
                        if (e == eThis) {
                            isContained = true
                            break
                        }
                    }
                    if (!isContained) { if (e.ruling == Ruling.POSITIVE) { return -5 } }
                }
            }

            for (i in thisGhostEvidence.indices) {
                val e = thisGhostEvidence[i]
                when (e.ruling) {
                    Ruling.POSITIVE -> { if (i < 3) { posScore++ } }
                    Ruling.NEGATIVE -> {
                        if (!(isNightmare || isInsanity)) return -6
                        negScore++
                        if (i >= 3) { return -7 }
                    }
                    Ruling.NEUTRAL -> {}
                }
            }

            for (e in thisGhostRequiredEvidence) { if (e.ruling == Ruling.NEGATIVE) { return -8 } }

            if (posScore > maxPosScore) return -8
            if (negScore > 3 - maxPosScore) return -9

            if (!(isNightmare || isInsanity)) {
                return posScore - negScore
            }

            if (posScore == maxPosScore - (3 - thisGhostEvidence.size)) for (e in thisGhostRequiredEvidence) {
                if (e.ruling != Ruling.POSITIVE) {
                    return -10
                }
            }

            return posScore
        }

    private fun addEvidence(e: EvidenceModel) {
        thisGhostEvidence.add(e)
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
        thisGhostRequiredEvidence.add(e)
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
        for (e in thisGhostEvidence) { s.append(e.name).append(", ") }
        if (thisGhostRequiredEvidence.isNotEmpty()) { s.append(" / ") }
        for (e in thisGhostRequiredEvidence) { s.append(e.name).append(", ") }
        return s.toString()
    }
}
