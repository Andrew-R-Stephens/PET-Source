package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.CodexRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository.CodexRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.helpers.InvestigationJournal
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.RuledEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.RuledEvidence.Ruling2
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
import kotlinx.coroutines.flow.StateFlow

class InvestigationViewModel(
    private val fetchGhostsUseCase: FetchGhostsUseCase,
    private val fetchEvidenceUseCase: FetchEvidencesUseCase,
    private val fetchGhostEvidenceUseCase: FetchGhostEvidencesUseCase,
    difficultyRepository: DifficultyRepository,
    mapRepository: SimpleMapRepository,
    private val codexRepository: CodexRepository
): ViewModel() {

    private val ghosts = fetchGhostsUseCase()
    private val evidences = fetchEvidenceUseCase()
    private val ghostEvidences = fetchGhostEvidenceUseCase()

    /*
     * HANDLERS / CONTROLLERS
     */
    private var mapHandler: MapCarouselHandler =
        MapCarouselHandler(mapRepository)
    private var difficultyHandler: DifficultyCarouselHandler =
        DifficultyCarouselHandler(difficultyRepository)
    private var investigationJournal: InvestigationJournal =
        InvestigationJournal(ghosts, evidences, ghostEvidences)

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
    val ruledEvidence = investigationJournal.ruledEvidence
    val ghostScores = investigationJournal.ghostScores
    val ghostOrder = investigationJournal.ghostOrder
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

    fun getRuledEvidence(evidenceModel: EvidenceType): RuledEvidence? {
        return investigationJournal.getRuledEvidence(evidenceModel)
    }
    fun setEvidenceRuling(evidenceIndex: Int, ruling: Ruling2) {
        investigationJournal.setEvidenceRuling(evidenceIndex, ruling)
        investigationJournal.reorderGhostScores(difficultyHandler)
    }
    fun setEvidenceRuling(evidence: EvidenceType, ruling: Ruling2) {
        investigationJournal.setEvidenceRuling(evidence, ruling)
        investigationJournal.reorderGhostScores(difficultyHandler)
    }
    fun setGhostNegation(ghostModel: GhostType, isForceNegated: Boolean) {
        investigationJournal.setGhostNegation(ghostModel, isForceNegated)
    }
    fun toggleGhostNegation(ghostModel: GhostType) {
        investigationJournal.toggleGhostNegation(ghostModel)
    }

    fun incrementMapIndex() {
        mapHandler.incrementIndex()
    }
    fun decrementMapIndex() {
        mapHandler.decrementIndex()
    }

    val isInvestigationToolsDrawerCollapsed = investigationJournal.isInvestigationToolsDrawerCollapsed
    fun setInvestigationToolsDrawerState(state: Boolean) {
        investigationJournal.setInvestigationToolsDrawerState(state)
    }
    fun toggleInvestigationToolsDrawerState() {
        investigationJournal.toggleInvestigationToolsDrawerState()
    }

    fun displaySanityAsPercent(): String {
        return sanityHandler.displaySanityAsPercent()
    }
    fun displayTime(): String {
        return timerHandler.displayTime()
    }

    fun reorderGhostScoreModel() {
        investigationJournal.reorderGhostScores(difficultyHandler)
    }
    fun getGhostScore(ghostModel: GhostType): StateFlow<Int> {
        return investigationJournal.getGhostScore(ghostModel).score
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
        timerHandler.reset(sanityHandler, difficultyHandler)
    }

    fun resetSanityHandler() {
        sanityHandler.reset(difficultyHandler, timerHandler)
    }

    fun resetInvestigationJournal() {
        investigationJournal.resetInvestigationJournal(difficultyHandler)
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

    /* InvestigationJournal
     *
     */



    /*
     * VIEWMODEL FACTORIES
     */
    class InvestigationFactory(
        private val fetchGhostsUseCase: FetchGhostsUseCase,
        private val fetchEvidenceUseCase: FetchEvidencesUseCase,
        private val fetchGhostEvidenceUseCase: FetchGhostEvidencesUseCase,
        private val difficultyRepository: DifficultyRepository,
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
                    difficultyRepository = difficultyRepository,
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
                val difficultyRepository: DifficultyRepository =
                    appKeyContainer.difficultyRepository
                val mapRepository: SimpleMapRepository =
                    appKeyContainer.simpleMapRepository
                val codexRepository: CodexRepository =
                    appKeyContainer.codexRepository

                InvestigationViewModel(
                    fetchGhostsUseCase = fetchGhostsUseCase,
                    fetchEvidenceUseCase = fetchEvidencesUseCase,
                    fetchGhostEvidenceUseCase = fetchGhostEvidencesUseCase,
                    difficultyRepository = difficultyRepository,
                    mapRepository = mapRepository,
                    codexRepository = codexRepository,
                )
            }
        }
    }
}
