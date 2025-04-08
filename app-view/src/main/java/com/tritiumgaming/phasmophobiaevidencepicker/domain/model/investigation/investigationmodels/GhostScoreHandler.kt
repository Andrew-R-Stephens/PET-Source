package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.ghost.GhostModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.carousels.DifficultyCarouselHandler
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.RuledEvidence.Ruling.POSITIVE
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.RuledEvidence.Ruling.NEGATIVE
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.RuledEvidence.Ruling.NEUTRAL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GhostScoreHandler(
    private val ghostRepository: GhostRepository
) {

    private val _scores : MutableStateFlow<SnapshotStateList<GhostScore>> =
        MutableStateFlow(mutableStateListOf())
    val scores = _scores.asStateFlow()
    private fun initScores(
        evidenceRulingHandler: EvidenceRulingHandler? = null,
        difficultyCarouselHandler: DifficultyCarouselHandler? = null
    ) {
        _scores.update { mutableStateListOf() }

        val str = StringBuilder()
        ghostRepository.ghosts.forEach {
            val ghostScore = GhostScore(it)
            str.append("${ghostScore.ghostModel.id} ${ghostScore.score}, ")
            _scores.value.add(ghostScore)
        }
        Log.d("GhostScores", "Creating New:\n${str}")

        initOrder(evidenceRulingHandler, difficultyCarouselHandler)
    }
    fun getScores(ghostModel: GhostModel): GhostScore? {
        return _scores.value.find { it.ghostModel.id == ghostModel.id }
    }
    fun getScores(index: Int): GhostScore? {
        return _scores.value.getOrNull(index)
    }

    /** Order of Ghost IDs **/
    private val _order: MutableStateFlow<SnapshotStateList<Int>> =
        MutableStateFlow(mutableStateListOf())
    @Stable
    val order = _order.asStateFlow()
    fun initOrder(
        evidenceRulingHandler: EvidenceRulingHandler? = null,
        difficultyCarouselHandler: DifficultyCarouselHandler? = null
    ) {
        _order.update { mutableStateListOf() }

        val str = StringBuilder()
        scores.value.forEachIndexed { index, it ->
            str.append("${it.ghostModel.id} ${it.score}, ")
            _order.value.add(it.ghostModel.id)
        }
        Log.d("GhostOrder", "Creating New:\n${str}")

        reorder(evidenceRulingHandler, difficultyCarouselHandler)
    }
    fun reorder(
        evidenceRulingHandler: EvidenceRulingHandler? = null,
        difficultyCarouselHandler: DifficultyCarouselHandler? = null
    ) {
        val orderedScores = mutableListOf<GhostScore>()
        scores.value.forEach {
            it.setScore ( getEvidenceScore(
                evidenceRulingHandler,
                difficultyCarouselHandler,
                it.ghostModel
            ))
            orderedScores.add(it)
        }
        orderedScores.sortByDescending { it.score.value }
        val orderedTemp = orderedScores.map { it.ghostModel.id }.toMutableStateList()

        _order.update { orderedTemp }

        val str2 = StringBuilder()
        order.value.forEachIndexed { index, orderModel ->
            str2.append("[$orderModel: " + "${scores.value.find { scoreModel -> 
                scoreModel.ghostModel.id ==  orderModel}?.score}] ")
        }
        Log.d("GhostOrder", "Reordered to:$str2")

    }

    private fun getEvidenceScore(
        evidenceHandler: EvidenceRulingHandler?,
        difficultyCarouselHandler: DifficultyCarouselHandler?,
        ghostModel: GhostModel): Int {

        return getScores(ghostModel)
            ?.getEvidenceScore(
                evidenceHandler = evidenceHandler,
                currentDifficulty = difficultyCarouselHandler?.currentDifficulty
            ) ?: 1
    }

    fun getGhostScore(ghostModel: GhostModel): StateFlow<Int>? {
        val ghostScore = scores.value.find { it.ghostModel.id == ghostModel.id }
        return ghostScore?.score
    }

    fun reset(
        evidenceRulingHandler: EvidenceRulingHandler,
        difficultyCarouselHandler: DifficultyCarouselHandler
    ) {
        initScores(evidenceRulingHandler, difficultyCarouselHandler)
        //initOrder(difficultyCarouselHandler)
    }

    init {
        initScores()
    }

    data class GhostScore(
        val ghostModel: GhostModel,
    ) {
        private val _score = MutableStateFlow(0)
        val score = _score.asStateFlow()
        fun setScore(newScore: Int) {
            _score.update { newScore }
        }

        var forcefullyRejected: Boolean = false

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
            evidenceHandler: EvidenceRulingHandler? = null,
            currentDifficulty: DifficultyRepository.DifficultyTitle? =
                DifficultyRepository.DifficultyTitle.AMATEUR
        ): Int {
            /*Log.d("GhostScore", "Evidence Handler: ${evidenceHandler?.toString()}")
            Log.d("GhostScore", "Difficulty: $currentDifficulty")
            Log.d("GhostScore", "Current Evidence: ${ghostModel.evidence}")*/

            if (forcefullyRejected) { return -5 }

            val isNightmare = currentDifficulty == DifficultyRepository.DifficultyTitle.NIGHTMARE
            val isInsanity = currentDifficulty == DifficultyRepository.DifficultyTitle.INSANITY

            val maxPosScore = when {
                isInsanity -> 1
                isNightmare -> 2
                else -> 3
            }

            var posScore = 0
            var negScore = 0

            evidenceHandler?.ruledEvidence?.value?.forEach { ruledEvidence ->
                var isContained = false
                for (normalEvidence in ghostModel.evidence.normalEvidenceList) {
                    if (ruledEvidence.evidence == normalEvidence) {
                        isContained = true
                        break
                    }
                }
                if (!isContained) {
                    if (ruledEvidence.isRuling(POSITIVE)) { return -5 } }
            }

            ghostModel.evidence.normalEvidenceList.forEachIndexed { index, normalEvidence ->

                evidenceHandler?.getRuledEvidence(normalEvidence)?.ruling?.value?.let {
                        ruling: RuledEvidence.Ruling ->

                    when (ruling) {
                        POSITIVE -> { if (index < 3) { posScore++ } }
                        NEGATIVE -> {
                            if (!(isNightmare || isInsanity)) return -6
                            negScore++
                            if (index >= 3) { return -7 }
                        }

                        NEUTRAL -> {}
                    }
                }

            }

            ghostModel.evidence.strictEvidenceList.forEach { strictEvidence ->
                evidenceHandler?.getRuledEvidence(strictEvidence)?.ruling?.value?.let {
                        ruling: RuledEvidence.Ruling ->

                    if (ruling == NEGATIVE) {
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
                            ruling: RuledEvidence.Ruling ->

                        if (ruling != POSITIVE) {
                            return -10
                        }
                    }
                }

            }

            return posScore
        }


        /** Resets the Ruling for each Evidence type  */
        fun reset() {
            forcefullyRejected = false
        }
    }

}
