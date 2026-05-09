package com.tritiumgaming.feature.investigation.ui.journal.ghost.item

import android.util.Log
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.GhostSpeed
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.difficultysetting.mapper.toFloat
import com.tritiumgaming.shared.data.difficultysetting.mapper.toInt
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.shared.data.ghost.mapper.toHasLosMultiplierBoolean
import com.tritiumgaming.shared.data.ghost.mapper.toMaximumAsInt
import com.tritiumgaming.shared.data.ghost.mapper.toMinimumAsInt
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitState
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitWeight
import com.tritiumgaming.shared.data.ghosttrait.model.GhostTrait
import com.tritiumgaming.shared.data.investigation.model.DifficultyOverridesData.Companion.FuseBoxFlag
import com.tritiumgaming.shared.data.investigation.model.EvidenceState
import com.tritiumgaming.shared.data.investigation.model.EvidenceValidationType
import com.tritiumgaming.shared.data.journal.model.GhostEvidence

internal data class TraitScore(
    val confirm: Int = 0,
    val probableConfirm: Int = 0,
    val reject: Int = 0,
    val probableReject: Int = 0,
)

internal data class GhostState(
    val ghostEvidence: GhostEvidence,
    val score: Int = 0,
    val manualRejection: Boolean = false,
    val bpmIsValid: Boolean = false,
    val traitScore: TraitScore = TraitScore(),
    val traits: Set<GhostTrait> = emptySet()
) {

    fun updateTraits(
        traits: Set<GhostTrait>
    ): GhostState {

        Log.d("Trait", "${ghostEvidence.ghost.id}")

        var confirmedCount = 0
        var probableConfirmCount = 0
        var rejectedCount = 0
        var probableRejectedCount = 0

        traits.forEach { (_, _, state, weight) ->
            when(state) {
                TraitState.CONFIRM -> {
                    when(weight) {
                        TraitWeight.PROBABLE -> probableConfirmCount++
                        TraitWeight.DEFINITIVE -> confirmedCount++
                    }
                }
                TraitState.REJECT -> {
                    when(weight) {
                        TraitWeight.PROBABLE -> probableRejectedCount++
                        TraitWeight.DEFINITIVE -> rejectedCount++
                    }
                }
            }
        }

        Log.d("Trait", "${ghostEvidence.ghost.id}: C $confirmedCount - " +
                "PC $probableConfirmCount - R $rejectedCount - PR $probableRejectedCount")

        val copy = this.copy(
            traits = traits,
            traitScore = TraitScore(
                confirm = confirmedCount,
                probableConfirm = probableConfirmCount,
                reject = rejectedCount,
                probableReject = probableRejectedCount
            )
        )

        return copy
    }

    fun updateScore(
        evidenceState: List<EvidenceState>,
        evidenceGiven: DifficultySettingResources.EvidenceGiven
    ): GhostState {
        return setScore(score = calculateEvidenceScore(
            evidenceState, evidenceGiven.toInt()))
    }

    fun updateBpmValidation(
        targetBpm: Float,
        ghostSpeed: GhostSpeed = GhostSpeed.SPEED_100,
        weather: Weather = Weather.RANDOM,
        fuseBox: FuseBoxFlag = FuseBoxFlag.FUSEBOX_ENABLED
    ): GhostState {
        return setBpmValidation(validateBpm(targetBpm, ghostSpeed, weather, fuseBox))
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

    private fun validateBpm(
        targetBpm: Float,
        ghostSpeed: GhostSpeed,
        weather: Weather,
        fuseBox: FuseBoxFlag
    ): Boolean {
        val minBase = ghostEvidence.ghost.speed.toMinimumAsInt().toFloat()
        var maxBase = ghostEvidence.ghost.speed.toMaximumAsInt().toFloat()
        val losMultiplier = ghostEvidence.ghost.speed.toHasLosMultiplierBoolean()

        if(maxBase == -1f) maxBase = minBase

        // Handle Jinn Fuse Box ability
        if (ghostEvidence.ghost.id == GhostIdentifier.JINN &&
            fuseBox == FuseBoxFlag.FUSEBOX_DISABLED) {
            maxBase = minBase
        }

        val difficultyMultiplier = ghostSpeed.toFloat()
        val weatherMultiplier = if (weather == Weather.BLOOD_MOON) 1.15f else 1f
        val fuseBoxMultiplier = 1f // Placeholder for any general multiplier if needed

        val min = minBase * difficultyMultiplier * weatherMultiplier * fuseBoxMultiplier
        var max = maxBase * difficultyMultiplier * weatherMultiplier * fuseBoxMultiplier

        if(losMultiplier) { max *= 1.65f }

        return targetBpm in min..max
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
        evidenceLimit: Int
    ): Int {

        if(evidenceLimit == 0) return ZERO_EVIDENCE

        val isHardcore = evidenceLimit < 3

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

        if (posScore > evidenceLimit) return POSITIVE_COUNT_OVER_MAXIMUM
        if (negScore > (3 - evidenceLimit)) return NEGATIVE_COUNT_UNDER_MINIMUM

        if (!isHardcore) return posScore - negScore

        val expectedPosScore = evidenceLimit - (3 - normalEvidence.size)
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
        const val NORMAL_AFFIRM_MINIMUM_REACHED = 3
        const val ZERO_EVIDENCE = 0
        const val NORMAL_EVIDENCE_NOT_FOUND = -5
        const val NORMAL_NEGATION_MINIMUM_REACHED = -6
        const val NORMAL_NEGATION_MAXIMUM_REACHED = -7
        const val STRICT_EVIDENCE_FOUND = -8
        const val POSITIVE_COUNT_OVER_MAXIMUM = -9
        const val NEGATIVE_COUNT_UNDER_MINIMUM = -10
        const val STRICT_EVIDENCE_NOT_FOUND = -11
    }
}