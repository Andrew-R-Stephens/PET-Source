package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.helpers

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostScore
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.RuledEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.carousels.DifficultyCarouselHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * InvestigationData class
 *
 * @author TritiumGamingStudios
 */
class InvestigationJournal(
    private val ghosts: List<GhostType>,
    private val evidences: List<EvidenceType>,
    private val ghostEvidences: List<GhostEvidence>
) {

    fun setGhostNegation(ghostModel: GhostType, isForceNegated: Boolean) {
        setForcedNegation(ghostModel, isForceNegated)
    }
    fun toggleGhostNegation(ghostModel: GhostType) {
        toggleForcedNegation(ghostModel)
    }

    private val _isInvestigationToolsDrawerCollapsed: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val isInvestigationToolsDrawerCollapsed = _isInvestigationToolsDrawerCollapsed.asStateFlow()
    fun setInvestigationToolsDrawerState(isCollapsed: Boolean) {
        _isInvestigationToolsDrawerCollapsed.update { isCollapsed }
    }
    fun toggleInvestigationToolsDrawerState() {
        _isInvestigationToolsDrawerCollapsed.update { !(isInvestigationToolsDrawerCollapsed.value) }
    }

    private val _investigationToolsCategory: MutableStateFlow<Int> = MutableStateFlow(TOOL_SANITY)
    val investigationToolsCategory = _investigationToolsCategory.asStateFlow()
    fun setInvestigationToolsCategory(categoryIndex: Int) {
        _investigationToolsCategory.value = categoryIndex
    }

    fun reorderGhostScores(difficultyCarouselHandler: DifficultyCarouselHandler) {
        reorder(difficultyCarouselHandler)
    }

    /** Resets the Ruling for each Evidence type */
    fun resetInvestigationJournal(difficultyCarouselHandler: DifficultyCarouselHandler) {
        resetEvidenceRulingHandler()
        resetGhostScoreHandler(difficultyCarouselHandler)
    }

    companion object {
        const val TOOL_SANITY = 0
        const val TOOL_MODIFIER_DETAILS = 1
    }

    /*
    * Ghost Score Handler
    */
    private val _ghostScores : MutableStateFlow<SnapshotStateList<GhostScore>> =
        MutableStateFlow(mutableStateListOf())
    val ghostScores = _ghostScores.asStateFlow()
    private fun initGhostScores(
        difficultyCarouselHandler: DifficultyCarouselHandler? = null
    ) {
        _ghostScores.update { mutableStateListOf() }

        val str = StringBuilder()
        ghostEvidences.forEach {
            val ghostScore = GhostScore(it)
            str.append("${ghostScore.ghostEvidence.ghost.id} ${ghostScore.score}, ")
            _ghostScores.value.add(ghostScore)
        }
        Log.d("GhostScores", "Creating New:\n${str}")

        initOrder(difficultyCarouselHandler)
    }
    fun getGhostScores(ghostModel: GhostType): GhostScore? {
        return _ghostScores.value.find { it.ghostEvidence.ghost.id == ghostModel.id }
    }
    fun getGhostScores(index: Int): GhostScore? {
        return _ghostScores.value.getOrNull(index)
    }

    /** Order of Ghost IDs **/
    private val _ghostOrder: MutableStateFlow<SnapshotStateList<String>> =
        MutableStateFlow(mutableStateListOf())
    @Stable
    val ghostOrder = _ghostOrder.asStateFlow()
    fun initOrder(
        difficultyCarouselHandler: DifficultyCarouselHandler? = null
    ) {
        _ghostOrder.update { mutableStateListOf() }

        val str = StringBuilder()
        ghostScores.value.forEachIndexed { index, it ->
            str.append("${it.ghostEvidence.ghost.id} ${it.score}, ")
            _ghostOrder.value.add(it.ghostEvidence.ghost.id)
        }
        Log.d("GhostOrder", "Creating New:\n${str}")

        reorder(difficultyCarouselHandler)
    }
    fun reorder(
        difficultyCarouselHandler: DifficultyCarouselHandler? = null
    ) {
        val orderedScores = mutableListOf<GhostScore>()
        ghostScores.value.forEach {
            it.setScore ( getEvidenceScore(
                difficultyCarouselHandler,
                it.ghostEvidence.ghost
            ))
            orderedScores.add(it)
        }
        orderedScores.sortByDescending { it.score.value }
        val orderedTemp = orderedScores.map { it.ghostEvidence.ghost.id }.toMutableStateList()

        _ghostOrder.update { orderedTemp }

        val str2 = StringBuilder()
        ghostOrder.value.forEachIndexed { index, orderModel ->
            str2.append("[$orderModel: " + "${ghostScores.value.find { scoreModel ->
                scoreModel.ghostEvidence.ghost.id ==  orderModel}?.score}] ")
        }
        Log.d("GhostOrder", "Reordered to:$str2")

    }

    private fun getEvidenceScore(
        difficultyCarouselHandler: DifficultyCarouselHandler?,
        ghostModel: GhostType
    ): Int {

        return getGhostScores(ghostModel)
            ?.getEvidenceScore(
                ruledEvidence = ruledEvidence.value,
                currentDifficulty = difficultyCarouselHandler?.currentDifficulty
            ) ?: 1
    }

    fun getGhostScore(ghostModel: GhostType): GhostScore {
        return ghostScores.value.first { it.ghostEvidence.ghost.id == ghostModel.id }
    }
    fun setForcedNegation(ghostModel: GhostType, isForceNegated: Boolean){
        getGhostScore(ghostModel).setForcefullyRejected(isForceNegated)
    }
    fun toggleForcedNegation(ghostModel: GhostType){
        getGhostScore(ghostModel).toggleForcefullyRejected()
    }
    fun getGhostScorePoints(ghostModel: GhostType): StateFlow<Int>? {
        val ghostScore = ghostScores.value.find { it.ghostEvidence.ghost.id == ghostModel.id }
        return ghostScore?.score
    }

    fun resetGhostScoreHandler(
        difficultyCarouselHandler: DifficultyCarouselHandler
    ) {
        initGhostScores(difficultyCarouselHandler)
    }

    /*
    * Evidence Ruling Handler
    */

    private val _ruledEvidence = MutableStateFlow(SnapshotStateList<RuledEvidence>())
    val ruledEvidence = _ruledEvidence.asStateFlow()
    fun initRuledEvidence() {
        val list = evidences.map {
            RuledEvidence(it).apply { setRuling(RuledEvidence.Ruling2.NEUTRAL) }
        }
        _ruledEvidence.update { list.toMutableStateList() }
    }
    fun setEvidenceRuling(evidenceIndex: Int, ruling: RuledEvidence.Ruling2) {
        ruledEvidence.value[evidenceIndex].setRuling(ruling)
    }
    fun setEvidenceRuling(evidence: EvidenceType, ruling: RuledEvidence.Ruling2) {
        getRuledEvidence(evidence)?.setRuling(ruling)
    }

    fun getRuledEvidence(evidenceModel: EvidenceType): RuledEvidence? {
        return ruledEvidence.value.find { it.isEvidence(evidenceModel) }
    }

    /** Resets the Ruling for each Evidence type */
    fun resetEvidenceRulingHandler() {
        //initRuledEvidence()
        ruledEvidence.value.forEach {
            it.setRuling( RuledEvidence.Ruling2.NEUTRAL)
        }
    }

    override fun toString(): String {
        val str = StringBuilder()
        ruledEvidence.value.forEach {
            str.append(" [${it.evidence.id}:${it.ruling.value}] ")
        }
        return "$str"
    }

    init {
        initGhostScores()
        initRuledEvidence()
    }
}