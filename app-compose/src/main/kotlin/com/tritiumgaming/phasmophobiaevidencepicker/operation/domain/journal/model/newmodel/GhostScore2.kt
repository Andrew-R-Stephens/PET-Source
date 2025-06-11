package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.newmodel

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.helpers.newhelpers.EvidenceRulingHandler2
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GhostScore2(
    val ghostModel: GhostType,
) {
    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()
    fun setScore(newScore: Int) {
        _score.update { newScore }
    }

    private val _forcefullyRejected = MutableStateFlow(false)
    val forcefullyRejected = _forcefullyRejected.asStateFlow()
    fun setForcefullyRejected(reject: Boolean) {
        _forcefullyRejected.update { reject }
    }
    fun toggleForcefullyRejected() {
        _forcefullyRejected.update { it -> !it }
    }

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
    fun getEvidenceScore(
        evidenceHandler: EvidenceRulingHandler2? = null,
        currentDifficulty: DifficultyType? =
            DifficultyType.AMATEUR
    ): Int {

        //if (forcefullyRejected.value) { return -5 }

        val isNightmare = currentDifficulty == DifficultyType.NIGHTMARE
        val isInsanity = currentDifficulty == DifficultyType.INSANITY

        val maxPosScore = when {
            isInsanity -> 1
            isNightmare -> 2
            else -> 3
        }

        var posScore = 0
        var negScore = 0

        evidenceHandler?.ruledEvidence?.value?.forEach { ruledEvidence ->
            var isContained = false
            for (normalEvidence in ghostModel.normalEvidenceList) {
                if (ruledEvidence.evidence == normalEvidence) {
                    isContained = true
                    break
                }
            }
            if (!isContained) {
                if (ruledEvidence.isRuling(RuledEvidence2.Ruling2.POSITIVE)) { return -5 } }
        }

        ghostModel.evidence.normalEvidenceList.forEachIndexed { index, normalEvidence ->

            evidenceHandler?.getRuledEvidence(normalEvidence)?.ruling?.value?.let {
                    ruling: RuledEvidence2.Ruling2 ->

                when (ruling) {
                    RuledEvidence2.Ruling2.POSITIVE -> { if (index < 3) { posScore++ } }
                    RuledEvidence2.Ruling2.NEGATIVE -> {
                        if (!(isNightmare || isInsanity)) return -6
                        negScore++
                        if (index >= 3) { return -7 }
                    }

                    RuledEvidence2.Ruling2.NEUTRAL -> {}
                }
            }

        }

        ghostModel.evidence.strictEvidenceList.forEach { strictEvidence ->
            evidenceHandler?.getRuledEvidence(strictEvidence)?.ruling?.value?.let {
                    ruling: RuledEvidence2.Ruling2 ->

                if (ruling == RuledEvidence2.Ruling2.NEGATIVE) {
                    return -8
                }
            }
        }

        if (posScore > maxPosScore) return -8
        if (negScore > 3 - maxPosScore) return -9

        if (!(isNightmare || isInsanity)) {
            return posScore - negScore
        }

        if (posScore == maxPosScore - (3 - ghostModel.evidence.normalEvidenceList.size)) {
            ghostModel.evidence.strictEvidenceList.forEach { strictEvidence ->

                evidenceHandler?.getRuledEvidence(strictEvidence)?.ruling?.value?.let {
                        ruling: RuledEvidence2.Ruling2 ->

                    if (ruling != RuledEvidence2.Ruling2.POSITIVE) {
                        return -10
                    }
                }
            }

        }

        return posScore
    }


    /** Resets the Ruling for each Evidence type  */
    fun reset() {
        setForcefullyRejected(false)
    }
}
