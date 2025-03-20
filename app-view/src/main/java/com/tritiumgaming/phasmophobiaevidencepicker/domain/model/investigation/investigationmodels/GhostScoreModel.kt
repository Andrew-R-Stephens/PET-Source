package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel.Ruling.NEGATIVE
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel.Ruling.NEUTRAL
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel.Ruling.POSITIVE
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.ghost.GhostModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.carousels.DifficultyCarouselModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.carousels.MapCarouselModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GhostScoreModel(
    val evidenceRepository: EvidenceRepository,
    private val ghostRepository: GhostRepository,
    val mapCarouselModel: MapCarouselModel?,
    val difficultyCarouselModel: DifficultyCarouselModel?
) {

    fun getEvidenceScore(ghostModel: GhostModel): Int {
        return getGhostScore(ghostModel)
            ?.getEvidenceScore(
                evidenceRepository,
                mapCarouselModel,
                difficultyCarouselModel
            ) ?: 0
    }

    data class GhostScore(
        val ghostModel: GhostModel,
        var score: Int
    ) {

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
            evidenceRepository: EvidenceRepository,
            mapCarouselModel: MapCarouselModel?,
            difficultyCarouselModel: DifficultyCarouselModel?): Int {

            if (forcefullyRejected) { return -5 }

            val currentDifficulty =
                difficultyCarouselModel?.currentDifficulty

            val isNightmare = currentDifficulty == DifficultyRepository.DifficultyTitle.NIGHTMARE
            val isInsanity = currentDifficulty == DifficultyRepository.DifficultyTitle.INSANITY

            val maxPosScore = when {
                isInsanity -> 1
                isNightmare -> 2
                else -> 3
            }

            var posScore = 0
            var negScore = 0

            for (e in evidenceRepository.evidenceList) {
                var isContained = false
                for (eThis in  ghostModel.evidence.normalEvidenceList) {
                    if (e == eThis) {
                        isContained = true
                        break
                    }
                }
                if (!isContained) { if (e.ruling == POSITIVE) { return -5 } }
            }

            for (i in ghostModel.evidence.normalEvidenceList.indices) {
                val e = ghostModel.evidence.normalEvidenceList[i]
                when (e.ruling) {
                    POSITIVE -> { if (i < 3) { posScore++ } }
                    NEGATIVE -> {
                        if (!(isNightmare || isInsanity)) return -6
                        negScore++
                        if (i >= 3) { return -7 }
                    }
                    NEUTRAL -> {}
                }
            }

            for (e in ghostModel.evidence.strictEvidenceList) {
                if (e.ruling == NEGATIVE) { return -8 }
            }

            if (posScore > maxPosScore) return -8
            if (negScore > 3 - maxPosScore) return -9

            if (!(isNightmare || isInsanity)) {
                return posScore - negScore
            }

            if (posScore == maxPosScore - (3 - ghostModel.evidence.normalEvidenceList.size)) {
                for (e in ghostModel.evidence.strictEvidenceList) {
                    if (e.ruling != POSITIVE) {
                        return -10
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

    private val _ghostScores : MutableStateFlow<SnapshotStateList<GhostScore>> =
        MutableStateFlow(mutableStateListOf())
    val ghostScores = _ghostScores.asStateFlow()
    private fun addGhostScore(ghostScore: GhostScore) {
        _ghostScores.value.add(ghostScore)
    }
    fun getGhostScore(ghostModel: GhostModel): GhostScore? {
        return _ghostScores.value.find { it.ghostModel.id == ghostModel.id }
    }
    fun getGhostScore(index: Int): GhostScore {
        return _ghostScores.value[index]
    }
    fun updateGhostScore(ghostModel: GhostModel, score: Int) {
        _ghostScores.value.find { it.ghostModel.id == ghostModel.id }?.score = score
    }

    private var prevOrder: IntArray? = null
    var currOrder: IntArray? = null
        get() {
            field ?: createCurrOrder()
            return field
        }

    init {
        ghostRepository.ghosts.forEach {
            addGhostScore(GhostScore(it, 0))
        }
    }

    /**
     * createOrder
     * Initializes both current and previous order of ghosts to default order
     */
    private fun createOrder() {
        createPrevOrder()
        createCurrOrder()
    }

    /**
     * createPrevOrder
     * Initializes both current and previous order of ghosts to default order
     */
    private fun createPrevOrder() {
        prevOrder = IntArray(ghostRepository.count)

        prevOrder?.let { prevOrder ->
            for (i in prevOrder.indices) {
                prevOrder[i] = i
            }
        }
    }

    /**
     * createCurrOrder
     * Initializes both current and previous order of ghosts to default order
     */
    private fun createCurrOrder() {
        currOrder = IntArray(_ghostScores.value.size)

        currOrder?.let { currOrder ->
            for (i in currOrder.indices) {
                currOrder[i] = i
            }
        }

    }

    fun updateOrder() {
        val newOrder = IntArray(_ghostScores.value.size)

        // Replace previous with current
        if (currOrder == null) {
            createCurrOrder()
        }
        if (prevOrder == null) {
            createPrevOrder()
        }
        //System.arraycopy(currOrder, 0, prevOrder, 0, currOrder?.size ?: 0)
        prevOrder = IntArray(currOrder?.size ?: 0)
        currOrder?.forEachIndexed { item, index ->
            prevOrder?.set(index, item)
        }

        // Set default numbers into placeholder array
        for (i in newOrder.indices) {
            newOrder[i] = i
        }

        // Order placeholder array based on scores
        var i = 0
        while (i < newOrder.size - 1) {
            val ratingA = ghostScores.value[newOrder[i]].score
            val ratingB = ghostScores.value[newOrder[i + 1]].score

            if (ratingA < ratingB) {
                val t = newOrder[i + 1]
                newOrder[i + 1] = newOrder[i]
                newOrder[i] = t

                if (i > 0) { i-- }
            } else { i++ }
        }

        // Replace current with placeholders
        currOrder = newOrder
    }

    fun hasChanges(): Boolean {
        if (prevOrder == null) {
            createPrevOrder()
        }
        if (currOrder == null) {
            createCurrOrder()
        }

        currOrder?.let { currOrder ->
            prevOrder?.let { prevOrder ->
                for (i in currOrder.indices) {
                    if (currOrder[i] != prevOrder[i]) return true
                }
            }
        }

        return false
    }

    fun reset() {
        createOrder()
    }


}
