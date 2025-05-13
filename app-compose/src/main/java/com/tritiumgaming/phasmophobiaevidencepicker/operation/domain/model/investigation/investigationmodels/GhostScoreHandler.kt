package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.EvidenceRulingHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.RuledEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.ghost.GhostType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.carousels.DifficultyCarouselHandler
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
    fun getScores(ghostModel: GhostType): GhostScore? {
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
        ghostModel: GhostType): Int {

        return getScores(ghostModel)
            ?.getEvidenceScore(
                evidenceHandler = evidenceHandler,
                currentDifficulty = difficultyCarouselHandler?.currentDifficulty
            ) ?: 1
    }

    fun getGhostScore(ghostModel: GhostType): GhostScore? {
        return scores.value.find { it.ghostModel.id == ghostModel.id }
    }
    fun setForcedNegation(ghostModel: GhostType, isForceNegated: Boolean){
        getGhostScore(ghostModel)?.setForcefullyRejected(isForceNegated)
    }
    fun toggleForcedNegation(ghostModel: GhostType){
        getGhostScore(ghostModel)?.toggleForcefullyRejected()
    }
    fun getGhostScorePoints(ghostModel: GhostType): StateFlow<Int>? {
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
            evidenceHandler: EvidenceRulingHandler? = null,
            currentDifficulty: DifficultyRepository.DifficultyType? =
                DifficultyRepository.DifficultyType.AMATEUR
        ): Int {

            //if (forcefullyRejected.value) { return -5 }

            val isNightmare = currentDifficulty == DifficultyRepository.DifficultyType.NIGHTMARE
            val isInsanity = currentDifficulty == DifficultyRepository.DifficultyType.INSANITY

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
                    if (ruledEvidence.isRuling(RuledEvidence.Ruling.POSITIVE)) { return -5 } }
            }

            ghostModel.evidence.normalEvidenceList.forEachIndexed { index, normalEvidence ->

                evidenceHandler?.getRuledEvidence(normalEvidence)?.ruling?.value?.let {
                        ruling: RuledEvidence.Ruling ->

                    when (ruling) {
                        RuledEvidence.Ruling.POSITIVE -> { if (index < 3) { posScore++ } }
                        RuledEvidence.Ruling.NEGATIVE -> {
                            if (!(isNightmare || isInsanity)) return -6
                            negScore++
                            if (index >= 3) { return -7 }
                        }

                        RuledEvidence.Ruling.NEUTRAL -> {}
                    }
                }

            }

            ghostModel.evidence.strictEvidenceList.forEach { strictEvidence ->
                evidenceHandler?.getRuledEvidence(strictEvidence)?.ruling?.value?.let {
                        ruling: RuledEvidence.Ruling ->

                    if (ruling == RuledEvidence.Ruling.NEGATIVE) {
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

                        if (ruling != RuledEvidence.Ruling.POSITIVE) {
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

}

/*
class GhostScoreHandler(
    val evidenceRepository: EvidenceRepository,
    private val ghostRepository: GhostRepository,
    val mapCarouselModel: MapCarouselHandler?,
    val difficultyCarouselModel: DifficultyCarouselHandler?
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

        */
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
         *//*

        fun getEvidenceScore(
            evidenceRepository: EvidenceRepository,
            mapCarouselModel: MapCarouselHandler?,
            difficultyCarouselModel: DifficultyCarouselHandler?): Int {

                if (forcefullyRejected) { return -5 }

                val currentDifficulty =
                    difficultyCarouselModel?.currentDifficulty

                val isNightmare = currentDifficulty == DifficultyRepository.DifficultyTitle.NIGHTMARE
                val isInsanity = currentDifficulty == DifficultyRepository.DifficultyTitle.INSANITY

                var posScore = 0
                var negScore = 0

                for (e in evidenceRepository.evidenceList) {
                    var isContained = false
                    for (eThis in  ghostModel.normalEvidenceList) {
                        if (e == eThis) {
                            isContained = true
                            break
                        }
                    }
                    if (!isContained) { if (e.ruling == POSITIVE) { return -5 } }
                }

                for (i in ghostModel.normalEvidenceList.indices) {
                    val e = ghostModel.normalEvidenceList[i]
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

                for (e in ghostModel.strictEvidenceList) {
                    if (e.ruling == NEGATIVE) { return -8 }
                }

                val maxPosScore = when {
                    isInsanity -> 1
                    isNightmare -> 2
                    else -> 3
                }

                if (posScore > maxPosScore) return -8
                if (negScore > 3 - maxPosScore) return -9

                if (!(isNightmare || isInsanity)) {
                    return posScore - negScore
                }

                if (posScore == maxPosScore - (3 - ghostModel.normalEvidenceList.size)) {
                    for (e in ghostModel.strictEvidenceList) {
                        if (e.ruling != POSITIVE) {
                            return -10
                        }
                    }
                }

                return posScore
            }


        */
/** Resets the Ruling for each Evidence type  *//*

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
        return ghostScores.value.find { it.ghostModel.id == ghostModel.id }
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
        ghostRepository.ghostList.forEach {
            addGhostScore(GhostScore(it, 0))
        }
    }

    */
/**
     * createOrder
     * Initializes both current and previous order of ghosts to default order
     *//*

    private fun createOrder() {
        createPrevOrder()
        createCurrOrder()
    }

    */
/**
     * createPrevOrder
     * Initializes both current and previous order of ghosts to default order
     *//*

    private fun createPrevOrder() {
        prevOrder = IntArray(ghostRepository.count)

        prevOrder?.let { prevOrder ->
            for (i in prevOrder.indices) {
                prevOrder[i] = i
            }
        }
    }

    */
/**
     * createCurrOrder
     * Initializes both current and previous order of ghosts to default order
     *//*

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
*/
