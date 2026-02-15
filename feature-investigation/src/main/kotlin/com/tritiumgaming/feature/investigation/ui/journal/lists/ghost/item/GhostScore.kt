package com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item

import com.tritiumgaming.feature.investigation.app.mappers.ghost.toHasLosMultiplierBoolean
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toMaximumAsInt
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toMinimumAsInt
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence
import com.tritiumgaming.shared.data.journal.model.GhostEvidence

data class GhostScore(
    val ghostEvidence: GhostEvidence,
    val score: Int = 0,
    val manualRejection: Boolean = false,
    val bpmIsValid: Boolean = false
) {

    fun updateScore(
        ruledEvidence: List<RuledEvidence>,
        currentDifficulty: DifficultyResources.DifficultyType
    ): GhostScore {
        return setScore(score = calculateEvidenceScore(ruledEvidence, currentDifficulty))
    }

    fun updateBpmValidation(
        targetBpm: Float
    ): GhostScore {
        return setBpmValidation(validateBpm(targetBpm))
    }

    fun toggleManualRejection(): GhostScore {
        return this.copy(
            manualRejection = !manualRejection
        )
    }

    /** Resets the Ruling for each Evidence type  */
    fun resetBpmValidation(): GhostScore {
        return setBpmValidation(false)
    }

    /** Resets the Ruling for each Evidence type  */
    fun resetManualRejection(): GhostScore {
        return setManualRejection(false)
    }

    private fun setScore(score: Int): GhostScore {
        return this.copy(
            score = score
        )
    }

    private fun setManualRejection(state: Boolean): GhostScore {
        return this.copy(
            manualRejection = state
        )
    }

    private fun setBpmValidation(state: Boolean): GhostScore {
        return this.copy(
            bpmIsValid = state
        )
    }

    private fun validateBpm(targetBpm: Float): Boolean {
        val min = ghostEvidence.speed.toMinimumAsInt().toFloat()
        var max = ghostEvidence.speed.toMaximumAsInt().toFloat()
        val losMultiplier = ghostEvidence.speed.toHasLosMultiplierBoolean()

        if(max == -1f) max = min

        if(losMultiplier) { max *= 1.65f }

        val isInRange = targetBpm in min..max

        return isInRange
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
    private fun calculateEvidenceScore(
        ruledEvidence: List<RuledEvidence>,
        currentDifficulty: DifficultyResources.DifficultyType
    ): Int {

        val isNightmare = currentDifficulty == DifficultyResources.DifficultyType.NIGHTMARE
        val isInsanity = currentDifficulty == DifficultyResources.DifficultyType.INSANITY
        val isHardcore = isNightmare || isInsanity

        val maxPosScore = when {
            isInsanity -> 1
            isNightmare -> 2
            else -> 3
        }

        val rulings = ruledEvidence.associate { it.evidence to it.ruling }

        val (normalEvidence, strictEvidence) =
            ghostEvidence.normalEvidenceList to ghostEvidence.strictEvidenceList

        if (ruledEvidence.any {
            it.isRuling(RuledEvidence.Ruling.POSITIVE) &&
                    it.evidence !in normalEvidence }) { return NORMAL_EVIDENCE_NOT_FOUND }

        var posScore = 0
        var negScore = 0

        normalEvidence.forEachIndexed { index, evidence ->
            when (rulings[evidence]) {
                RuledEvidence.Ruling.POSITIVE -> if (index < 3) posScore++
                RuledEvidence.Ruling.NEGATIVE -> {
                    if (!isHardcore) return NORMAL_NEGATION_MINIMUM_REACHED
                    if (index < 3) negScore++
                    else return NORMAL_NEGATION_MAXIMUM_REACHED
                }
                else -> {}
            }
        }

        if (strictEvidence.any { rulings[it] == RuledEvidence.Ruling.NEGATIVE }) {
            return STRICT_EVIDENCE_FOUND
        }

        if (posScore > maxPosScore) return POSITIVE_COUNT_OVER_MAXIMUM
        if (negScore > (3 - maxPosScore)) return NEGATIVE_COUNT_UNDER_MINIMUM

        if (!isHardcore) return posScore - negScore

        val expectedPosScore = maxPosScore - (3 - normalEvidence.size)
        if (posScore == expectedPosScore) {
            val hasInvalidStrict = strictEvidence.any {
                val ruling = rulings[it]
                val result = ruling != null && ruling != RuledEvidence.Ruling.POSITIVE

                result
            }
            if (hasInvalidStrict) return STRICT_EVIDENCE_NOT_FOUND
        }

        return posScore
    }

    companion object {
        const val NORMAL_EVIDENCE_NOT_FOUND = -5
        const val NORMAL_NEGATION_MINIMUM_REACHED = -6
        const val NORMAL_NEGATION_MAXIMUM_REACHED = -7
        const val STRICT_EVIDENCE_FOUND = -8
        const val POSITIVE_COUNT_OVER_MAXIMUM = -9
        const val NEGATIVE_COUNT_UNDER_MINIMUM = -10
        const val STRICT_EVIDENCE_NOT_FOUND = -11
    }
}