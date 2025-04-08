package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.CodexRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.SimpleMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.achievevments.CodexAchievements
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.equipment.CodexEquipment
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.possessions.CodexPossessions
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.GhostScoreHandler.GhostScore
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.InvestigationJournal
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.RuledEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.RuledEvidence.Ruling
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.ghost.GhostModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.carousels.DifficultyCarouselHandler
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.carousels.MapCarouselHandler
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.sanity.SanityHandler
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.sanity.SanityRunnable
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.timer.TimerHandler
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.warning.PhaseHandler
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class InvestigationViewModel(
    val evidenceRepository: EvidenceRepository,
    val ghostRepository: GhostRepository,
    difficultyRepository: DifficultyRepository,
    mapRepository: SimpleMapRepository,
    private val codexRepository: CodexRepository
): ViewModel() {

    private var mapHandler: MapCarouselHandler = MapCarouselHandler(mapRepository)
    private var difficultyHandler: DifficultyCarouselHandler = DifficultyCarouselHandler(difficultyRepository)
    private var investigationJournal: InvestigationJournal = InvestigationJournal(ghostRepository, evidenceRepository)

    private var timerHandler: TimerHandler = TimerHandler()
    private var phaseHandler: PhaseHandler = PhaseHandler()

    private var sanityHandler: SanityHandler = SanityHandler()

    var sanityRunnable: SanityRunnable? = null

    val equipmentStoreModel: CodexEquipment
        get() = codexRepository.equipment
    val possessionsStoreModel: CodexPossessions
        get() = codexRepository.possessions
    val achievementsStoreModel: CodexAchievements
        get() = codexRepository.achievements

    val evidenceRadioButtonUi = investigationJournal.evidenceRadioButtonUi
    fun checkEvidenceRadioButtonUi(evidenceIndex: Int, buttonIndex: Int) {
        investigationJournal.checkEvidenceRadioButtonUi(evidenceIndex, buttonIndex)
    }

    val rejectionPileUi = investigationJournal.rejectionPileUi
    val responseTypeUi = difficultyHandler.responseTypeUi

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

    fun getRuledEvidence(evidenceModel: EvidenceModel): RuledEvidence? {
        return investigationJournal.getRuledEvidence(evidenceModel)
    }
    fun setEvidenceRuling(evidenceIndex: Int, ruling: Ruling) {
        investigationJournal.setEvidenceRuling(evidenceIndex, ruling)
        investigationJournal.reorderGhostScores(difficultyHandler)
    }
    fun setEvidenceRuling(evidence: EvidenceModel, ruling: Ruling) {
        investigationJournal.setEvidenceRuling(evidence, ruling)
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
    fun getGhostScore(ghostModel: GhostModel): StateFlow<Int>? {
        return investigationJournal.getGhostScore(ghostModel)
    }
    fun swapStatusInRejectedPile(index: Int) {
        investigationJournal.swapStatusInRejectedPile(index)
    }

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

    class InvestigationFactory(
        private val evidenceRepository: EvidenceRepository,
        private val ghostRepository: GhostRepository,
        private val difficultyRepository: DifficultyRepository,
        private val mapRepository: SimpleMapRepository,
        private val codexRepository: CodexRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InvestigationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return InvestigationViewModel(
                    evidenceRepository,
                    ghostRepository,
                    difficultyRepository,
                    mapRepository,
                    codexRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appKeyContainer = (this[APPLICATION_KEY] as PETApplication).container

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