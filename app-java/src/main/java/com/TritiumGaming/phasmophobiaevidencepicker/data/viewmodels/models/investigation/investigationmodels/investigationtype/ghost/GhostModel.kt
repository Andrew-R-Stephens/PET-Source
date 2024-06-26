package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.ghost

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.InvestigationModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.evidence.EvidenceModel.Ruling
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.carousels.DifficultyCarouselModel

class GhostModel {
    private var investigationData: InvestigationModel? = null

    var id: Int = -1
    var name: String = "NA"

    var forcefullyRejected: Boolean = false

    private val thisGhostEvidence = ArrayList<EvidenceModel>()
    private val thisGhostRequiredEvidence = ArrayList<EvidenceModel>()

    constructor() { id = 0 }

    constructor(investigationData: InvestigationModel?, id: Int) {
        this.investigationData = investigationData
        this.id = id
    }

    fun addEvidence(e: EvidenceModel) {
        thisGhostEvidence.add(e)
    }

    fun addEvidence(evidence: String) {
        for (e in investigationData!!.evidenceListModel.evidenceList) {
            if (evidence == e.name) {
                addEvidence(e)
                break
            }
        }
    }

    fun addNightmareEvidence(e: EvidenceModel) {
        thisGhostRequiredEvidence.add(e)
    }

    fun addNightmareEvidence(evidence: String) {
        for (e in investigationData!!.evidenceListModel.evidenceList) {
            if (evidence == e.name) {
                addNightmareEvidence(e)
                break
            }
        }
    }

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
         *
         * @return numerical representation of the Ghost's Evidence score
         */
        get() {
            if (forcefullyRejected) { return -5 }

            val currentDifficulty =
                investigationData?.evidenceViewModel?.difficultyCarouselModel?.currentDifficulty

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

            investigationData?.let { investigationData ->
                for (e in investigationData.evidenceListModel.evidenceList) {
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

    override fun toString(): String {
        val s = StringBuilder(name).append(": ")
        for (e in thisGhostEvidence) { s.append(e.name).append(", ") }
        if (thisGhostRequiredEvidence.isNotEmpty()) { s.append(" / ") }
        for (e in thisGhostRequiredEvidence) { s.append(e.name).append(", ") }
        return s.toString()
    }
}
