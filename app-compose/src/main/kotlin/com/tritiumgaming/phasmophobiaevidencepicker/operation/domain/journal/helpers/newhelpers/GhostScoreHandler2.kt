package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.helpers.newhelpers

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.newmodel.GhostScore2
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.carousels.DifficultyCarouselHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GhostScoreHandler2(
    private val ghosts: List<GhostType>
) {

    private val _scores : MutableStateFlow<SnapshotStateList<GhostScore2>> =
        MutableStateFlow(mutableStateListOf())
    val scores = _scores.asStateFlow()
    private fun initScores(
        evidenceRulingHandler: EvidenceRulingHandler2? = null,
        difficultyCarouselHandler: DifficultyCarouselHandler? = null
    ) {
        _scores.update { mutableStateListOf() }

        val str = StringBuilder()
        ghosts.forEach {
            val ghostScore = GhostScore2(it)
            str.append("${ghostScore.ghostModel.id} ${ghostScore.score}, ")
            _scores.value.add(ghostScore)
        }
        Log.d("GhostScores", "Creating New:\n${str}")

        initOrder(evidenceRulingHandler, difficultyCarouselHandler)
    }
    fun getScores(ghostModel: GhostType): GhostScore2? {
        return _scores.value.find { it.ghostModel.id == ghostModel.id }
    }
    fun getScores(index: Int): GhostScore2? {
        return _scores.value.getOrNull(index)
    }

    /** Order of Ghost IDs **/
    private val _order: MutableStateFlow<SnapshotStateList<String>> =
        MutableStateFlow(mutableStateListOf())
    @Stable
    val order = _order.asStateFlow()
    fun initOrder(
        evidenceRulingHandler: EvidenceRulingHandler2? = null,
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
        evidenceRulingHandler: EvidenceRulingHandler2? = null,
        difficultyCarouselHandler: DifficultyCarouselHandler? = null
    ) {
        val orderedScores = mutableListOf<GhostScore2>()
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
        evidenceHandler: EvidenceRulingHandler2?,
        difficultyCarouselHandler: DifficultyCarouselHandler?,
        ghostModel: GhostType): Int {

        return getScores(ghostModel)
            ?.getEvidenceScore(
                evidenceHandler = evidenceHandler,
                currentDifficulty = difficultyCarouselHandler?.currentDifficulty
            ) ?: 1
    }

    fun getGhostScore(ghostModel: GhostType): GhostScore2? {
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
        evidenceRulingHandler: EvidenceRulingHandler2,
        difficultyCarouselHandler: DifficultyCarouselHandler
    ) {
        initScores(evidenceRulingHandler, difficultyCarouselHandler)
    }

    init {
        initScores()
    }

}
