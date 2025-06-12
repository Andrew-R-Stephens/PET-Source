package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.CodexRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository.CodexRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostScore
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.RuledEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.RuledEvidence.Ruling
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchDifficultiesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchEvidencesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchGhostEvidencesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchGhostsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.carousels.DifficultyCarouselHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.carousels.MapCarouselHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.sanity.SanityHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.sanity.SanityRunnable
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.timer.TimerHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.warning.PhaseHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InvestigationViewModel(
    private val fetchGhostsUseCase: FetchGhostsUseCase,
    private val fetchEvidenceUseCase: FetchEvidencesUseCase,
    private val fetchGhostEvidenceUseCase: FetchGhostEvidencesUseCase,
    private val fetchDifficultiesUseCase: FetchDifficultiesUseCase,

    mapRepository: SimpleMapRepository,
    private val codexRepository: CodexRepository
): ViewModel() {

    private val ghosts = fetchGhostsUseCase()
    private val evidences = fetchEvidenceUseCase()
    private val ghostEvidences = fetchGhostEvidenceUseCase()
    private val difficulties = fetchDifficultiesUseCase()

    /*
     * HANDLERS / CONTROLLERS
     */
    private var mapHandler: MapCarouselHandler =
        MapCarouselHandler(mapRepository)
    private var difficultyHandler: DifficultyCarouselHandler =
        DifficultyCarouselHandler(difficulties)

    private var timerHandler: TimerHandler = TimerHandler()
    private var phaseHandler: PhaseHandler = PhaseHandler()

    private var sanityHandler: SanityHandler = SanityHandler()

    /*
     * UI STATES
     */
    val responseTypeUi = difficultyHandler.responseTypeUi

    /*
     * COROUTINES
     */
    var sanityRunnable: SanityRunnable? = null

    /*
     * GENERIC STATES
     */
    val sanityLevel = sanityHandler.sanityLevel
    val currentDifficultyIndex = difficultyHandler.currentIndex
    val currentDifficultyName = difficultyHandler.currentName
    val currentMapIndex = mapHandler.currentIndex
    val currentMapName = mapHandler.currentName
    val currentPhase = timerHandler.currentPhase
    val canFlash = phaseHandler.canFlash
    val isTimerPaused = timerHandler.paused
    val timeRemaining = timerHandler.timeRemaining

    /*
     * FUNCTIONS
     */
    fun getGhostById(ghostId: String): GhostType? {
        return ghosts.find { it.id == ghostId }
    }
    fun getEvidenceById(evidenceId: String): EvidenceType? {
        return evidences.find { it.id == evidenceId }
    }

    fun incrementMapIndex() {
        mapHandler.incrementIndex()
    }
    fun decrementMapIndex() {
        mapHandler.decrementIndex()
    }

    fun displaySanityAsPercent(): String {
        return sanityHandler.displaySanityAsPercent()
    }
    fun displayTime(): String {
        return timerHandler.displayTime()
    }

    fun reorderGhostScoreModel() {
        reorderGhostScores(difficultyHandler)
    }

    /*fun swapStatusInRejectedPile(index: Int) {
        investigationJournal.swapStatusInRejectedPile(index)
    }*/

    fun setSanityStartTimeByProgress(progress: Float) {
        sanityHandler.setStartTimeByProgress(timerHandler, progress.toFloat())
    }

    fun updatePhaseTimeElapsed() {
        phaseHandler.updateTimeElapsed(sanityHandler)
    }

    fun setAudioWarnTriggered(triggered: Boolean) {
        phaseHandler.audioWarnTriggered = triggered
    }

    fun tickSanityHandler() {
        sanityHandler.tick(timerHandler)
    }

    val timerPaused = timerHandler.paused
    fun toggleTimer() {
        timerHandler.toggleTimer(sanityHandler)
    }

    fun fastForwardTimer(time: Long) {
        timerHandler.fastForwardTimer(sanityHandler, time)
    }

    fun incrementDifficultyIndex() {
        difficultyHandler.incrementIndex(sanityHandler, mapHandler, timerHandler, phaseHandler)
    }

    fun decrementDifficultyIndex() {
        difficultyHandler.decrementIndex(sanityHandler, mapHandler, timerHandler, phaseHandler)
    }

    fun resetTimerHandler() {
        timerHandler.resetTimerHandler(sanityHandler, difficultyHandler)
    }

    fun resetSanityHandler() {
        sanityHandler.reset(difficultyHandler, timerHandler)
    }

    fun resetInvestigationJournal() {
        resetInvestigationJournal(difficultyHandler)
    }

    fun resetPhaseHandler() {
        phaseHandler.reset(sanityHandler)
    }

    fun reset() {
        resetTimerHandler()
        resetInvestigationJournal()
        resetSanityHandler()
        resetPhaseHandler()
    }

    init {
        reorderGhostScoreModel()
    }

    /*
     * InvestigationJournal
     */

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

    fun getGhostScore(ghostModel: GhostType): StateFlow<Int> {
        return ghostScores.value.first { it.ghostEvidence.ghost.id == ghostModel.id }.score
    }

    fun setForcedNegation(ghostModel: GhostType, isForceNegated: Boolean){
        val ghostScore = ghostScores.value.first { it.ghostEvidence.ghost.id == ghostModel.id }
        ghostScore.setForcefullyRejected(isForceNegated)
    }
    fun toggleForcedNegation(ghostModel: GhostType){
        val ghostScore = ghostScores.value.first { it.ghostEvidence.ghost.id == ghostModel.id }
        ghostScore.toggleForcefullyRejected()
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
            RuledEvidence(it).apply { setRuling(Ruling.NEUTRAL) }
        }
        _ruledEvidence.update { list.toMutableStateList() }
    }
    fun setEvidenceRuling(evidenceIndex: Int, ruling: Ruling) {
        ruledEvidence.value[evidenceIndex].setRuling(ruling)
        reorderGhostScores(difficultyHandler)
    }
    fun setEvidenceRuling(evidence: EvidenceType, ruling: Ruling) {
        getRuledEvidence(evidence)?.setRuling(ruling)
        reorderGhostScores(difficultyHandler)
    }
    fun getRuledEvidence(evidenceModel: EvidenceType): RuledEvidence? {
        return ruledEvidence.value.find { it.isEvidence(evidenceModel) }
    }

    /** Resets the Ruling for each Evidence type */
    fun resetEvidenceRulingHandler() {
        //initRuledEvidence()
        ruledEvidence.value.forEach {
            it.setRuling( Ruling.NEUTRAL)
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

    /*
     * Difficulty Handler
     */



    /*
     * VIEWMODEL FACTORIES
     */
    class InvestigationFactory(
        private val fetchGhostsUseCase: FetchGhostsUseCase,
        private val fetchEvidenceUseCase: FetchEvidencesUseCase,
        private val fetchGhostEvidenceUseCase: FetchGhostEvidencesUseCase,
        private val fetchDifficultiesUseCase: FetchDifficultiesUseCase,
        private val simpleMapRepository: SimpleMapRepository,
        private val codexRepository: CodexRepositoryImpl
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InvestigationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return InvestigationViewModel(
                    fetchGhostsUseCase = fetchGhostsUseCase,
                    fetchEvidenceUseCase = fetchEvidenceUseCase,
                    fetchGhostEvidenceUseCase = fetchGhostEvidenceUseCase,
                    fetchDifficultiesUseCase = fetchDifficultiesUseCase,
                    mapRepository = simpleMapRepository,
                    codexRepository = codexRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appKeyContainer = (this[APPLICATION_KEY] as PETApplication).operationsContainer

                val fetchGhostsUseCase: FetchGhostsUseCase =
                    appKeyContainer.fetchGhostsUseCase
                val fetchEvidencesUseCase: FetchEvidencesUseCase =
                    appKeyContainer.fetchEvidencesUseCase
                val fetchGhostEvidencesUseCase: FetchGhostEvidencesUseCase =
                    appKeyContainer.fetchGhostEvidencesUseCase
                val fetchDifficultiesUseCase: FetchDifficultiesUseCase =
                    appKeyContainer.fetchDifficultiesUseCase
                val mapRepository: SimpleMapRepository =
                    appKeyContainer.simpleMapRepository
                val codexRepository: CodexRepository =
                    appKeyContainer.codexRepository

                InvestigationViewModel(
                    fetchGhostsUseCase = fetchGhostsUseCase,
                    fetchEvidenceUseCase = fetchEvidencesUseCase,
                    fetchGhostEvidenceUseCase = fetchGhostEvidencesUseCase,
                    fetchDifficultiesUseCase = fetchDifficultiesUseCase,
                    mapRepository = mapRepository,
                    codexRepository = codexRepository,
                )
            }
        }

        const val TOOL_SANITY = 0
        const val TOOL_MODIFIER_DETAILS = 1

    }
}
