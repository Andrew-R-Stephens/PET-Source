package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.CodexRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.SimpleMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.achievevments.CodexAchievementsRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.equipment.CodexEquipmentRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.possessions.CodexPossessionsRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.InvestigationJournal
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.RuledEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.RuledEvidence.Ruling
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.ghost.GhostType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.carousels.DifficultyCarouselHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.carousels.MapCarouselHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.sanity.SanityHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.sanity.SanityRunnable
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.timer.TimerHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.warning.PhaseHandler
import kotlinx.coroutines.flow.StateFlow

class InvestigationViewModel(
    val evidenceRepository: EvidenceRepository,
    val ghostRepository: GhostRepository,
    difficultyRepository: DifficultyRepository,
    mapRepository: SimpleMapRepository,
    private val codexRepository: CodexRepository
): ViewModel() {

    /*
     * HANDLERS / CONTROLLERS
     */
    private var mapHandler: MapCarouselHandler = MapCarouselHandler(mapRepository)
    private var difficultyHandler: DifficultyCarouselHandler = DifficultyCarouselHandler(difficultyRepository)
    private var investigationJournal: InvestigationJournal = InvestigationJournal(ghostRepository, evidenceRepository)

    private var timerHandler: TimerHandler = TimerHandler()
    private var phaseHandler: PhaseHandler = PhaseHandler()

    private var sanityHandler: SanityHandler = SanityHandler()

    val equipmentStoreModel: CodexEquipmentRepository
        get() = codexRepository.equipment
    val possessionsStoreModel: CodexPossessionsRepository
        get() = codexRepository.possessions
    val achievementsStoreModel: CodexAchievementsRepository
        get() = codexRepository.achievements

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
    val ghostScores = investigationJournal.scores
    val ghostOrder = investigationJournal.order
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
    fun getRuledEvidence(evidenceModel: EvidenceType): RuledEvidence? {
        return investigationJournal.getRuledEvidence(evidenceModel)
    }
    fun setEvidenceRuling(evidenceIndex: Int, ruling: Ruling) {
        investigationJournal.setEvidenceRuling(evidenceIndex, ruling)
        investigationJournal.reorderGhostScores(difficultyHandler)
    }
    fun setEvidenceRuling(evidence: EvidenceType, ruling: Ruling) {
        investigationJournal.setEvidenceRuling(evidence, ruling)
        investigationJournal.reorderGhostScores(difficultyHandler)
    }
    fun setGhostNegation(ghostModel: GhostType, isForceNegated: Boolean) {
        investigationJournal.setGhostNegation(ghostModel, isForceNegated)
        investigationJournal.reorderGhostScores(difficultyHandler)
    }
    fun toggleGhostNegation(ghostModel: GhostType) {
        investigationJournal.toggleGhostNegation(ghostModel)
        investigationJournal.reorderGhostScores(difficultyHandler)
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
    fun getGhostScore(ghostModel: GhostType): StateFlow<Int>? {
        return investigationJournal.getGhostScore(ghostModel)
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
        investigationJournal.reset(difficultyHandler)
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
     * VIEWMODEL FACTORIES
     */
    class InvestigationFactory(
        private val evidenceRepository: EvidenceRepository,
        private val ghostRepository: GhostRepository,
        private val difficultyRepository: DifficultyRepository,
        private val simpleMapRepository: SimpleMapRepository,
        private val codexRepository: CodexRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InvestigationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return InvestigationViewModel(
                    evidenceRepository,
                    ghostRepository,
                    difficultyRepository,
                    simpleMapRepository,
                    codexRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appKeyContainer = (this[APPLICATION_KEY] as PETApplication).operationsContainer

                val evidenceRepository: EvidenceRepository = appKeyContainer.evidenceRepository
                val ghostRepository: GhostRepository = appKeyContainer.ghostRepository
                val difficultyRepository: DifficultyRepository = appKeyContainer.difficultyRepository
                val mapRepository: SimpleMapRepository = appKeyContainer.simpleMapRepository
                val codexRepository: CodexRepository = appKeyContainer.codexRepository

                InvestigationViewModel(
                    evidenceRepository = evidenceRepository,
                    ghostRepository = ghostRepository,
                    difficultyRepository = difficultyRepository,
                    mapRepository = mapRepository,
                    codexRepository = codexRepository,
                )
            }
        }
    }
}
