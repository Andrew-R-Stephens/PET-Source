package com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item

import com.tritiumgaming.feature.investigation.app.mappers.ghost.toHasLosMultiplierBoolean
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toMaximumAsInt
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toMinimumAsInt
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.shared.data.evidence.model.EvidenceValidationType
import com.tritiumgaming.shared.data.evidence.model.EvidenceState
import com.tritiumgaming.shared.data.journal.model.GhostEvidence

data class GhostState(
    val ghostEvidence: GhostEvidence,
    val score: Int = 0,
    val manualRejection: Boolean = false,
    val bpmIsValid: Boolean = false
) {

    fun updateScore(
        evidenceState: List<EvidenceState>,
        currentDifficulty: DifficultyResources.DifficultyType
    ): GhostState {
        return setScore(score = calculateEvidenceScore(evidenceState, currentDifficulty))
    }

    fun updateBpmValidation(
        targetBpm: Float
    ): GhostState {
        return setBpmValidation(validateBpm(targetBpm))
    }

    fun toggleManualRejection(): GhostState {
        return this.copy(
            manualRejection = !manualRejection
        )
    }

    /** Resets the Ruling for each Evidence type  */
    fun resetBpmValidation(): GhostState {
        return setBpmValidation(false)
    }

    /** Resets the Ruling for each Evidence type  */
    fun resetManualRejection(): GhostState {
        return setManualRejection(false)
    }

    private fun setScore(score: Int): GhostState {
        return this.copy(
            score = score
        )
    }

    private fun setManualRejection(state: Boolean): GhostState {
        return this.copy(
            manualRejection = state
        )
    }

    private fun setBpmValidation(state: Boolean): GhostState {
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
        evidenceState: List<EvidenceState>,
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

        val rulings = evidenceState.associate { it.evidence to it.state }

        val (normalEvidence, strictEvidence) =
            ghostEvidence.normalEvidenceList to ghostEvidence.strictEvidenceList

        if (evidenceState.any {
            it.isRuling(EvidenceValidationType.POSITIVE) &&
                    it.evidence !in normalEvidence }) { return NORMAL_EVIDENCE_NOT_FOUND }

        var posScore = 0
        var negScore = 0

        normalEvidence.forEachIndexed { index, evidence ->
            when (rulings[evidence]) {
                EvidenceValidationType.POSITIVE -> if (index < 3) posScore++
                EvidenceValidationType.NEGATIVE -> {
                    if (!isHardcore) return NORMAL_NEGATION_MINIMUM_REACHED
                    if (index < 3) negScore++
                    else return NORMAL_NEGATION_MAXIMUM_REACHED
                }
                else -> {}
            }
        }

        if (strictEvidence.any { rulings[it] == EvidenceValidationType.NEGATIVE }) {
            return STRICT_EVIDENCE_FOUND
        }

        if (posScore > maxPosScore) return POSITIVE_COUNT_OVER_MAXIMUM
        if (negScore > (3 - maxPosScore)) return NEGATIVE_COUNT_UNDER_MINIMUM

        if (!isHardcore) return posScore - negScore

        val expectedPosScore = maxPosScore - (3 - normalEvidence.size)
        if (posScore == expectedPosScore) {
            val hasInvalidStrict = strictEvidence.any {
                val ruling = rulings[it]
                val result = ruling != null && ruling != EvidenceValidationType.POSITIVE

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