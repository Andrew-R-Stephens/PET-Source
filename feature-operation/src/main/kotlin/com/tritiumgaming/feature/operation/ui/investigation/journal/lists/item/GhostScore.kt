package com.tritiumgaming.feature.operation.ui.investigation.journal.lists.item

import com.tritiumgaming.shared.operation.domain.difficulty.mapper.DifficultyResources
import com.tritiumgaming.shared.operation.domain.evidence.model.RuledEvidence
import com.tritiumgaming.shared.operation.domain.journal.model.GhostEvidence
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GhostScore(
    val ghostEvidence: GhostEvidence,
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
        ruledEvidence: List<RuledEvidence> = emptyList(),
        currentDifficulty: DifficultyResources.DifficultyType? = DifficultyResources.DifficultyType.AMATEUR
    ): Int {

        val isNightmare = currentDifficulty == DifficultyResources.DifficultyType.NIGHTMARE
        val isInsanity = currentDifficulty == DifficultyResources.DifficultyType.INSANITY

        val maxPosScore = when {
            isInsanity -> 1
            isNightmare -> 2
            else -> 3
        }

        var posScore = 0
        var negScore = 0

        ruledEvidence.forEach { ruledEvidence ->
            var isContained = false
            for (normalEvidence in ghostEvidence.normalEvidenceList) {
                if (ruledEvidence.evidence == normalEvidence) {
                    isContained = true
                    break
                }
            }
            if (!isContained) {
                if (ruledEvidence.isRuling(RuledEvidence.Ruling.POSITIVE)) {
                    return NORMAL_EVIDENCE_NOT_FOUND } }
        }

        ghostEvidence.normalEvidenceList.forEachIndexed { index, normalEvidence ->

            val normalRuledEvidence = ruledEvidence.find { it.isEvidence(normalEvidence) }

            normalRuledEvidence?.ruling?.let { ruling: RuledEvidence.Ruling ->

                when (ruling) {
                    RuledEvidence.Ruling.POSITIVE -> { if (index < 3) { posScore++ } }
                    RuledEvidence.Ruling.NEGATIVE -> {
                        if (!(isNightmare || isInsanity)) return NORMAL_NEGATION_MINIMUM_REACHED
                        negScore++
                        if (index >= 3) { return NORMAL_NEGATION_MAXIMUM_REACHED }
                    }

                    RuledEvidence.Ruling.NEUTRAL -> {}
                }
            }

        }

        ghostEvidence.strictEvidenceList.forEach { strictEvidence ->

            val strictRuledEvidence = ruledEvidence.find { it.isEvidence(strictEvidence) }

            strictRuledEvidence?.ruling?.let { ruling: RuledEvidence.Ruling ->

                if (ruling == RuledEvidence.Ruling.NEGATIVE) { return STRICT_EVIDENCE_FOUND }
            }
        }

        if (posScore > maxPosScore) return POSITIVE_COUNT_OVER_MAXIMUM
        if (negScore > (3 - maxPosScore)) return NEGATIVE_COUNT_UNDER_MINIMUM

        if (!(isNightmare || isInsanity)) {
            return posScore - negScore
        }

        if (posScore == maxPosScore - (3 - ghostEvidence.normalEvidenceList.size)) {
            ghostEvidence.strictEvidenceList.forEach { strictEvidence ->

                val strictRuledEvidence = ruledEvidence.find {
                    it.isEvidence(strictEvidence) }

                strictRuledEvidence?.ruling?.let { ruling: RuledEvidence.Ruling ->

                    if (ruling != RuledEvidence.Ruling.POSITIVE) { return STRICT_EVIDENCE_NOT_FOUND }
                }
            }

        }

        return posScore
    }


    /** Resets the Ruling for each Evidence type  */
    fun resetGhostScore() {
        setForcefullyRejected(false)
    }

    private companion object {
        private const val NORMAL_EVIDENCE_NOT_FOUND = -5
        private const val NORMAL_NEGATION_MINIMUM_REACHED = -6
        private const val NORMAL_NEGATION_MAXIMUM_REACHED = -7
        private const val STRICT_EVIDENCE_FOUND = -8
        private const val POSITIVE_COUNT_OVER_MAXIMUM = -9
        private const val NEGATIVE_COUNT_UNDER_MINIMUM = -10
        private const val STRICT_EVIDENCE_NOT_FOUND = -11
    }
}