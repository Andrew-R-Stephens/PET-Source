package com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item

import android.util.Log
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toHasLosMultiplierBoolean
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toMaximumAsInt
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toMinimumAsInt
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence
import com.tritiumgaming.shared.data.journal.model.GhostEvidence
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

    private val _generalRejection = MutableStateFlow(false)
    val generalRejection = _generalRejection.asStateFlow()
    fun setForcefullyRejected(reject: Boolean) {
        _generalRejection.update { reject }
    }
    fun toggleForcefullyRejected() {
        _generalRejection.update { !it }
    }

    private val _bpmIsValid = MutableStateFlow(false)
    val bpmIsValid = _bpmIsValid.asStateFlow()
    private fun setBpmState(state: Boolean) {
        _bpmIsValid.update { state }
    }

    fun updateEvidenceScore(
        ruledEvidence: List<RuledEvidence>,
        currentDifficulty: DifficultyResources.DifficultyType
    ) {
        setScore(calculateEvidenceScore(ruledEvidence, currentDifficulty))
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

    /*@Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated")
    fun getEvidenceScore(
        currentDifficulty: DifficultyResources.DifficultyType? = DifficultyResources.DifficultyType.AMATEUR,
        ruledEvidence: List<RuledEvidence> = emptyList(),
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
    }*/

    fun updateBpmScore(bpm: Float) {
        val min = ghostEvidence.speed.toMinimumAsInt().toFloat()
        var max = ghostEvidence.speed.toMaximumAsInt().toFloat()
        val losMultiplier = ghostEvidence.speed.toHasLosMultiplierBoolean()

        if(max == -1f) max = min

        if(losMultiplier) { max *= 1.65f }

        val isInRange = bpm in min..max

        Log.d("GhostScore", "setBpmState: ${ghostEvidence.ghost.id} -> $isInRange -> " +
                "(min / bpm / max) $min / $bpm / $max")

        setBpmState(isInRange)
    }

    /** Resets the Ruling for each Evidence type  */
    fun resetBpmScore() {
        setBpmState(false)
    }

    /** Resets the Ruling for each Evidence type  */
    fun resetNegateScore() {
        setForcefullyRejected(false)
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