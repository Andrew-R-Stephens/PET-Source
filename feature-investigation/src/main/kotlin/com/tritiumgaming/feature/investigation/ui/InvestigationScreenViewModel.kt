package com.tritiumgaming.feature.investigation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.GraphPoint
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.RealtimeUiState
import com.tritiumgaming.feature.investigation.app.container.CodexUseCaseBundle
import com.tritiumgaming.feature.investigation.app.container.DifficultyUseCaseBundle
import com.tritiumgaming.feature.investigation.app.container.InvestigationContainerProvider
import com.tritiumgaming.feature.investigation.app.container.JournalUseCaseBundle
import com.tritiumgaming.feature.investigation.app.container.PreferencesUseCaseBundle
import com.tritiumgaming.feature.investigation.app.container.SimpleMapUseCaseBundle
import com.tritiumgaming.feature.investigation.ui.TimerUiState.Companion.DEFAULT
import com.tritiumgaming.feature.investigation.ui.TimerUiState.Companion.DURATION_30_SECONDS
import com.tritiumgaming.feature.investigation.ui.TimerUiState.Companion.TIME_DEFAULT
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item.GhostState
import com.tritiumgaming.feature.investigation.ui.popups.JournalPopupUiState
import com.tritiumgaming.feature.investigation.ui.section.footstep.BpmToolUiState
import com.tritiumgaming.feature.investigation.ui.section.footstep.visualizer.VisualizerMeasurementType
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiState
import com.tritiumgaming.shared.data.codex.usecase.FetchAchievementTypesUseCase
import com.tritiumgaming.shared.data.codex.usecase.FetchEquipmentTypesUseCase
import com.tritiumgaming.shared.data.codex.usecase.FetchPossessionTypesUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.DecrementDifficultyIndexUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.FetchDifficultiesUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.GetDifficultyInitialSanityUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.GetDifficultyModifierUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.GetDifficultyNameUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.GetDifficultyResponseTypeUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.GetDifficultyTimeUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.IncrementDifficultyIndexUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.SetDifficultyIndexUseCase
import com.tritiumgaming.shared.data.evidence.model.EvidenceState
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.evidence.model.EvidenceValidationType
import com.tritiumgaming.shared.data.evidence.usecase.GetEquipmentTypeByEvidenceTypeUseCase
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.ghost.model.GhostType
import com.tritiumgaming.shared.data.journal.usecase.FetchEvidenceTypesUseCase
import com.tritiumgaming.shared.data.journal.usecase.FetchGhostEvidencesUseCase
import com.tritiumgaming.shared.data.journal.usecase.FetchGhostTypesUseCase
import com.tritiumgaming.shared.data.journal.usecase.GetEvidenceTypeByIdUseCase
import com.tritiumgaming.shared.data.journal.usecase.GetEvidenceUseCase
import com.tritiumgaming.shared.data.journal.usecase.GetGhostTypeByIdUseCase
import com.tritiumgaming.shared.data.journal.usecase.GetGhostUseCase
import com.tritiumgaming.shared.data.journal.usecase.InitRuledEvidenceUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.FetchSimpleMapModifiersUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetSimpleMapModifierUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetSimpleMapNormalModifierUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetSimpleMapSetupModifierUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.DecrementSimpleMapFloorIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.DecrementSimpleMapIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.FetchMapThumbnailsUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapIdUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapNameUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapSizeUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.IncrementSimpleMapFloorIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.IncrementSimpleMapIndexUseCase
import com.tritiumgaming.shared.data.phase.model.Phase
import com.tritiumgaming.shared.data.popup.model.EvidencePopupRecord
import com.tritiumgaming.shared.data.popup.model.GhostPopupRecord
import com.tritiumgaming.shared.data.preferences.usecase.GetAllowHuntWarnAudioUseCase
import com.tritiumgaming.shared.data.preferences.usecase.GetEnableGhostReorderUseCase
import com.tritiumgaming.shared.data.preferences.usecase.GetEnableRTLUseCase
import com.tritiumgaming.shared.data.preferences.usecase.GetMaxHuntWarnFlashTimeUseCase
import com.tritiumgaming.shared.data.sanity.model.SanityLevel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

class InvestigationScreenViewModel private constructor(
    journalUseCaseBundle: JournalUseCaseBundle,
    private val getEvidenceUseCase: GetEvidenceUseCase = journalUseCaseBundle.getEvidenceUseCase,
    private val fetchEvidenceTypesUseCase: FetchEvidenceTypesUseCase = journalUseCaseBundle.fetchEvidenceTypesUseCase,
    private val getEvidenceTypeByIdUseCase: GetEvidenceTypeByIdUseCase = journalUseCaseBundle.getEvidenceTypeByIdUseCase,
    private val getGhostUseCase: GetGhostUseCase = journalUseCaseBundle.getGhostUseCase,
    private val fetchGhostTypesUseCase: FetchGhostTypesUseCase = journalUseCaseBundle.fetchGhostTypesUseCase,
    private val getGhostTypeByIdUseCase: GetGhostTypeByIdUseCase = journalUseCaseBundle.getGhostTypeByIdUseCase,
    private val initEvidenceStateUseCase: InitRuledEvidenceUseCase = journalUseCaseBundle.initRuledEvidenceUseCase,
    private val fetchGhostEvidencesUseCase: FetchGhostEvidencesUseCase = journalUseCaseBundle.fetchGhostEvidencesUseCase,
    difficultyUseCaseBundle: DifficultyUseCaseBundle,
    private val fetchDifficultiesUseCase: FetchDifficultiesUseCase = difficultyUseCaseBundle.fetchDifficultiesUseCase,
    private val getDifficultyNameUseCase: GetDifficultyNameUseCase = difficultyUseCaseBundle.getDifficultyNameUseCase,
    private val getDifficultyModifierUseCase: GetDifficultyModifierUseCase = difficultyUseCaseBundle.getDifficultyModifierUseCase,
    private val getDifficultyTimeUseCase: GetDifficultyTimeUseCase = difficultyUseCaseBundle.getDifficultyTimeUseCase,
    private val getDifficultyResponseTypeUseCase: GetDifficultyResponseTypeUseCase = difficultyUseCaseBundle.getDifficultyResponseTypeUseCase,
    private val getDifficultyInitialSanityUseCase: GetDifficultyInitialSanityUseCase = difficultyUseCaseBundle.getDifficultyInitialSanityUseCase,
    private val incrementDifficultyIndexUseCase: IncrementDifficultyIndexUseCase = difficultyUseCaseBundle.incrementDifficultyIndexUseCase,
    private val decrementDifficultyIndexUseCase: DecrementDifficultyIndexUseCase = difficultyUseCaseBundle.decrementDifficultyIndexUseCase,
    private val setDifficultyIndexUseCase: SetDifficultyIndexUseCase = difficultyUseCaseBundle.setDifficultyIndexUseCase,
    simpleMapUseCaseBundle: SimpleMapUseCaseBundle,
    private val fetchSimpleMapsUseCase: FetchSimpleMapsUseCase = simpleMapUseCaseBundle.fetchSimpleMapsUseCase,
    private val incrementSimpleMapIndexUseCase: IncrementSimpleMapIndexUseCase = simpleMapUseCaseBundle.incrementSimpleMapIndexUseCase,
    private val decrementSimpleMapIndexUseCase: DecrementSimpleMapIndexUseCase = simpleMapUseCaseBundle.decrementSimpleMapIndexUseCase,
    private val getSimpleMapIdUseCase: GetSimpleMapIdUseCase = simpleMapUseCaseBundle.getSimpleMapIdUseCase,
    private val getSimpleMapNameUseCase: GetSimpleMapNameUseCase = simpleMapUseCaseBundle.getSimpleMapNameUseCase,
    private val getSimpleMapSizeUseCase: GetSimpleMapSizeUseCase = simpleMapUseCaseBundle.getSimpleMapSizeUseCase,
    private val getSimpleMapSetupModifierUseCase: GetSimpleMapSetupModifierUseCase = simpleMapUseCaseBundle.getSimpleMapSetupModifierUseCase,
    private val getSimpleMapNormalModifierUseCase: GetSimpleMapNormalModifierUseCase = simpleMapUseCaseBundle.getSimpleMapNormalModifierUseCase,
    private val getSimpleMapModifierUseCase: GetSimpleMapModifierUseCase = simpleMapUseCaseBundle.getSimpleMapModifierUseCase,
    private val fetchSimpleMapModifiersUseCase: FetchSimpleMapModifiersUseCase = simpleMapUseCaseBundle.fetchSimpleMapModifiersUseCase,
    private val incrementSimpleMapFloorIndexUseCase: IncrementSimpleMapFloorIndexUseCase = simpleMapUseCaseBundle.incrementSimpleMapFloorIndexUseCase,
    private val decrementSimpleMapFloorIndexUseCase: DecrementSimpleMapFloorIndexUseCase = simpleMapUseCaseBundle.decrementSimpleMapFloorIndexUseCase,
    private val fetchMapThumbnailsUseCase: FetchMapThumbnailsUseCase = simpleMapUseCaseBundle.fetchMapThumbnailsUseCase,
    codexUseCaseBundle: CodexUseCaseBundle,
    private val fetchCodexAchievementsUseCase: FetchAchievementTypesUseCase = codexUseCaseBundle.fetchCodexAchievementsUseCase,
    private val fetchCodexPossessionsUseCase: FetchPossessionTypesUseCase = codexUseCaseBundle.fetchCodexPossessionsUseCase,
    private val fetchCodexEquipmentUseCase: FetchEquipmentTypesUseCase = codexUseCaseBundle.fetchCodexEquipmentUseCase,
    private val getEquipmentTypeByEvidenceTypeUseCase: GetEquipmentTypeByEvidenceTypeUseCase = codexUseCaseBundle.getEquipmentTypeByEvidenceTypeUseCase,
    preferencesUseCaseBundle: PreferencesUseCaseBundle,
    private val getAllowHuntWarnAudioUseCase: GetAllowHuntWarnAudioUseCase = preferencesUseCaseBundle.getAllowHuntWarnAudioUseCase,
    private val getEnableGhostReorderUseCase: GetEnableGhostReorderUseCase = preferencesUseCaseBundle.getEnableGhostReorderUseCase,
    private val getEnableRTLUseCase: GetEnableRTLUseCase = preferencesUseCaseBundle.getEnableRTLUseCase,
    private val getMaxHuntWarnFlashTimeUseCase: GetMaxHuntWarnFlashTimeUseCase = preferencesUseCaseBundle.getMaxHuntWarnFlashTimeUseCase,
) : ViewModel() {

    private val ghostEvidences = fetchGhostEvidencesUseCase().let {
        it.exceptionOrNull()?.printStackTrace()
        try { it.getOrThrow() }
        catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private val maps = fetchSimpleMapsUseCase().let {
        it.exceptionOrNull()?.printStackTrace()
        try { it.getOrThrow() }
        catch (_: Exception) {
            emptyList()
        }
    }

    private val difficulties = fetchDifficultiesUseCase().let {
        it.exceptionOrNull()?.printStackTrace()
        try { it.getOrThrow() }
        catch (_: Exception) {
            emptyList()
        }
    }

    /*
     * Routines
     */
    private var sanityJob: Job? = null
    private var timerJob: Job? = null

    /*
     * Settings
     */
    val rTLPreference = getEnableRTLUseCase()
    private val ghostReorderPreference = getEnableGhostReorderUseCase()
    val huntWarnDurationPreference = getMaxHuntWarnFlashTimeUseCase()
    val allowHuntWarnAudioPreference = getAllowHuntWarnAudioUseCase()

    /*
     * UI STATES
     */
    private val _mapUiState = MutableStateFlow(
        MapUiState(allMaps = maps.map { it.mapName })
    )
    val mapUiState = _mapUiState.asStateFlow()

    private val _difficultyUiState = MutableStateFlow(
        DifficultyUiState(allDifficulties = difficulties.map { it.name })
    )
    val difficultyUiState = _difficultyUiState.asStateFlow()

    private val _timerUiState = MutableStateFlow(TimerUiState())
    val timerUiState = _timerUiState.asStateFlow()

    private val _phaseUiState = MutableStateFlow(PhaseUiState())
    val phaseUiState = _phaseUiState.asStateFlow()

    private val _playerSanityUiState = MutableStateFlow(PlayerSanityUiState())
    val playerSanityUiState = _playerSanityUiState.asStateFlow()

    private val _toolbarUiState = MutableStateFlow(
        ToolbarUiState(
            isCollapsed = false,
            category = ToolbarUiState.Category.TOOL_CONFIG,
            openWidth = 0.5f
        )
    )
    val toolbarUiState = _toolbarUiState.asStateFlow()

    private val _popupUiState = MutableStateFlow(JournalPopupUiState())
    val popupUiState = _popupUiState.asStateFlow()

    private val _bpmToolUiState = MutableStateFlow(BpmToolUiState())
    val bpmToolUiState = _bpmToolUiState.asStateFlow()

    private val _operationSanityUiState = combine(
        mapUiState,
        difficultyUiState,
        phaseUiState
    ) { mapUiState, difficultyUiState, phaseUiState ->
        val mapModifier = try {
            getSimpleMapModifierUseCase(
                mapUiState.size.ordinal,
                phaseUiState.currentPhase
            ).getOrThrow()
        } catch (e: Exception) {
            e.printStackTrace()
            1f
        }

        OperationSanityUiState(
            sanityMax = difficultyUiState.initialSanity,
            drainModifier = mapModifier * difficultyUiState.modifier
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = OperationSanityUiState()
    )
    val operationSanityUiState = _operationSanityUiState

    private val _evidenceStates: MutableStateFlow<List<EvidenceState>> =
        MutableStateFlow(emptyList())
    val evidenceStates = _evidenceStates.asStateFlow()
    private fun resetEvidenceStates() {
        _evidenceStates.update {
            try {
                fetchEvidenceTypesUseCase().getOrThrow()
                    .map { evidenceType ->
                        EvidenceState(
                            evidence = evidenceType,
                            state = EvidenceValidationType.NEUTRAL
                        )
                    }

            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
        updateGhostScores()
    }
    private val _ghostStates = MutableStateFlow(
        ghostEvidences.map { GhostState(it) }
    )
    val ghostStates = _ghostStates.asStateFlow()
    private fun resetGhostStates() {
        _ghostStates.update {
            ghostEvidences.map { GhostState(it) }
        }
    }
    private fun updateGhostScores() {

        val ruledEvidence = evidenceStates.value
        val difficulty = difficultyUiState.value.type
        val bpmToolUiState = bpmToolUiState.value

        _ghostStates.update { currentScores ->
            currentScores.map { ghostScore ->

                val updatedScore = ghostScore.updateScore(
                    evidenceState = ruledEvidence,
                    currentDifficulty = difficulty
                )

                if (bpmToolUiState.applyMeasurement) {
                    val bpmValue = when (bpmToolUiState.measurementType) {
                        VisualizerMeasurementType.INSTANT -> bpmToolUiState.realtimeState.smoothed
                        VisualizerMeasurementType.AVERAGED -> bpmToolUiState.realtimeState.average
                        VisualizerMeasurementType.WEIGHTED -> bpmToolUiState.realtimeState.weightedAverage
                    }
                    updatedScore.updateBpmValidation(bpmValue)
                } else {
                    updatedScore.resetBpmValidation()
                }
            }
        }
    }

    /** Order of Ghost IDs **/
    private val _sortedGhosts: StateFlow<List<GhostResources.GhostIdentifier>> =
        ghostStates.map { ghostScores ->
            ghostScores
                .sortedByDescending { it.bpmIsValid }
                .sortedByDescending { it.score }
                .map { it.ghostEvidence.ghost.id }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ghostEvidences.map { it.ghost.id }
        )
    /*private val _ghostOrder: StateFlow<List<GhostResources.GhostIdentifier>> =
        combine(ghostScores, ghostReorderPreference) { scores, isReorderEnabled ->
            if (isReorderEnabled) {
                scores.sortedWith(
                    compareByDescending<GhostScore> { it.bpmIsValid }
                        .thenByDescending { it.score }
                ).map { it.ghostEvidence.ghost.id }
            } else {
                // Keep original order (as defined in ghostEvidences) if reordering is disabled
                scores.map { it.ghostEvidence.ghost.id }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ghostEvidences.map { it.ghost.id }
        )*/
    val sortedGhosts = _sortedGhosts

    /*
     * COROUTINES
     */

    /*
     * Timer Ui Functions
     */
    private fun initTimerUiState() {
        _timerUiState.update {
            TimerUiState(
                startTime = TIME_DEFAULT,
                remainingTime = difficultyUiState.value.time,
                paused = true
            )
        }
    }

    /*
     * Phase Ui Functions
     */
    private fun initPhaseUiState() {
        _phaseUiState.update {
            it.copy(
                elapsedFlashTime = 0L,
                startFlashTime = DEFAULT,
                maxFlashTime = DURATION_30_SECONDS,
                currentPhase = Phase.SETUP,
                canFlash = true,
                canAlertAudio = false
            )
        }
    }

    /*
     * Player Sanity Ui Functions
     */
    private fun initPlayerSanityUiState() {
        _playerSanityUiState.update {
            PlayerSanityUiState(
                sanityLevel = SanityLevel.MAX_SANITY,
                insanityLevel = 0f
            )
        }
    }

    /*
     * Toolbar Ui Functions
     */
    fun toggleToolbarState() {
        _toolbarUiState.update {
            it.copy(
                isCollapsed = !it.isCollapsed
            )
        }
    }

    fun setToolbarCategory(category: ToolbarUiState.Category) {
        _toolbarUiState.update {
            it.copy(
                isCollapsed = false,
                category = category
            )
        }
    }

    /*
     * Popup Ui Functions
     */
    fun clearPopup() {
        try {
            _popupUiState.update {
                it.copy(
                    isShown = false,
                    evidencePopupRecord = null,
                    ghostPopupRecord = null
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setPopup(
        evidenceType: EvidenceType
    ) {

        try {
            val evidence = getEvidenceUseCase(evidenceType).getOrThrow()
            val equipmentList = fetchCodexEquipmentUseCase().getOrThrow()

            val equipmentId = getEquipmentTypeByEvidenceTypeUseCase(evidenceType)
            val equipmentType = equipmentList.first {
                it.id == equipmentId
            }

            val popupRecord = EvidencePopupRecord(
                id = evidence.id,
                name = evidence.name,
                description = evidence.description,
                icon = evidence.icon,
                animation = evidence.animation,
                equipmentTierAnimations = evidence.tiers.map { it },
                equipmentType = equipmentType
            )

            _popupUiState.update {
                it.copy(
                    isShown = true,
                    evidencePopupRecord = popupRecord
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setPopup(
        ghostType: GhostType
    ) {
        try {
            val ghost = getGhostUseCase(ghostType).getOrThrow()

            val popupRecord = GhostPopupRecord(
                id = ghost.id,
                name = ghost.name,
                icon = ghost.icon,
                info = ghost.info,
                strengthData = ghost.strengthData,
                weaknessData = ghost.weaknessData,
                huntData = ghost.huntData,
            )

            _popupUiState.update {
                it.copy(
                    isShown = true,
                    ghostPopupRecord = popupRecord
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun launchPlayerSanityJob() {
        sanityJob = viewModelScope.launch {
            while(!timerUiState.value.paused) {
                tickSanity()
                delay(1000)
            }
        }
    }

    private fun stopPlayerSanityJob() {
        sanityJob?.cancel("Player Sanity Job Cancelled")
    }

    /*
     * FUNCTIONS
     */
    fun getGhostById(ghostId: GhostResources.GhostIdentifier): GhostType? {
        return try { getGhostTypeByIdUseCase(ghostId).getOrThrow() }
        catch (e: Exception) { e.printStackTrace(); null }
    }

    fun reset() {
        resetJournal()

        resetTimer()
        resetPhase()
        resetSanity()
        resetTimer()
    }

    /*
     * Journal ------------------------
     */

    /** Resets the Ruling for each Evidence type
     * Depends on the difficulty configuration
     * */
    fun resetJournal() {
        resetEvidenceStates()
        resetGhostStates()
    }

    /*
    * Ghost Score ---------------------------
    */

    fun toggleGhostNegation(
        ghostModel: GhostType
    ) {
        val scores = ghostStates.value.map {
            if(it.ghostEvidence.ghost.id == ghostModel.id)
                it.toggleManualRejection()
            else it
        }

        _ghostStates.update {
            scores
        }
    }

    /*
    * Evidence Ruling Handler ---------------------------
    */
    fun setEvidenceRuling(
        evidence: EvidenceType,
        evidenceValidationType: EvidenceValidationType
    ) {
        _evidenceStates.update {
            it.map { e ->
                if (evidence.id == e.evidence.id)
                    e.copy(state = evidenceValidationType)
                else e
            }
        }
        updateGhostScores()
    }

    fun getRuledEvidence(
        evidenceModel: EvidenceType
    ): EvidenceState? {
        return evidenceStates.value.find { it.isEvidence(evidenceModel) }
    }

    /*
     * Player Sanity ---------------------------
     */

    private fun calculateSanityDrain(): Float {
        val currentTime = System.currentTimeMillis()
        val startTime = timerUiState.value.startTime

        val timeElapsed = currentTime - startTime
        val drainModifier = operationSanityUiState.value.drainModifier
        val multiplier = 0.00001f

        val calculatedDrain = timeElapsed * drainModifier * multiplier

        return calculatedDrain
    }

    /** The level can be between 0f and 1f. Levels outside those extremes are constrained. */
    fun setPlayerSanity(
        value: Float
    ) {
        val maxSanity = operationSanityUiState.value.sanityMax

        _playerSanityUiState.update {
            it.copy(
                insanityLevel = value.coerceIn(SanityLevel.MIN_SANITY, maxSanity),
                sanityLevel = (maxSanity - value).coerceIn(SanityLevel.MIN_SANITY, maxSanity)
            )
        }

    }

    private fun skipInsanity(
        newLevel: Float = SanityLevel.HALF_SANITY
    ) {
        val currentLevel = playerSanityUiState.value.insanityLevel

        val normalizedLevel = newLevel.coerceAtLeast(currentLevel)

        setPlayerSanity(normalizedLevel)

        setStartTimeByProgress(normalizedLevel)
    }

    /**
     * Reduces player sanity level each doTick. Sanity cannot drop below 50% if the clock still has
     * time remaining.
     */
    private fun tickSanity() {
        val drain = calculateSanityDrain()
        setPlayerSanity(drain)

        updatePhase()
    }

    /** @param progress specify the progress 0 - 100
     * Resets the Warning Indicator to start flashing again, if necessary
     * Sets the Start Time of the Sanity Drain, based on remaining time,
     * sanity, difficulty and map size. */
    private fun setStartTimeByProgress(progress: Float) {

        val maxSanity = operationSanityUiState.value.sanityMax

        val upperBound = min(maxSanity, progress)
        val normal = max(SanityLevel.MIN_SANITY, upperBound)

        val progressOverride = maxSanity - normal
        val drainModifier = operationSanityUiState.value.drainModifier
        val multiplier = .0001f

        val timeAddition = (progressOverride / drainModifier / multiplier).toLong()
        val newStartTime = (System.currentTimeMillis() + timeAddition)

        _timerUiState.update {
            it.copy(
                startTime = newStartTime
            )
        }
    }

    private fun getDurationByProgress(progress: Float): Long {
        val drainRatePerMs = 0.00001f

        val timeElapsed = (progress / drainRatePerMs / operationSanityUiState.value.drainModifier)

        return timeElapsed.toLong()
    }

    /** Defaults all persistent data. */
    private fun resetSanity() {
        //TODO warnTriggered = false
        setStartTimeByProgress(SanityLevel.MAX_SANITY - difficultyUiState.value.initialSanity)
        tickSanity()
    }

    /*
     * Phase ---------------------------
     */

    /** Allow the Warning indicator to flash either off or on if:
     * The player's sanity is less than 70%
     * either if the Flash Timeout is infinite
     * or if there is no time remaining on the countdown timer.
     * @return if the Warning indicator can flash */
    private fun updateCanFlash() {

        if (phaseUiState.value.maxFlashTime == DURATION_30_SECONDS) {
            _phaseUiState.update {
                it.copy(
                    canFlash = playerSanityUiState.value.isInsane
                )
            }
            return
        }

        if (phaseUiState.value.startFlashTime == DEFAULT) {
            Log.d("Flash", "Start time is default.. now setting to current time")
            _phaseUiState.update {
                it.copy(
                    startFlashTime = System.currentTimeMillis(),
                    elapsedFlashTime = System.currentTimeMillis() - it.startFlashTime
                )
            }

            updateCanFlash()

            return
        }

        _phaseUiState.update {
            it.copy(
                canFlash = phaseUiState.value.elapsedFlashTime <= phaseUiState.value.maxFlashTime
            )
        }

    }

    private fun updatePhase() {
        _phaseUiState.update {
            it.copy(
                currentPhase =
                    if (timerUiState.value.remainingTime > TIME_MIN) {
                        Phase.SETUP
                    } else {
                        if (playerSanityUiState.value.sanityLevel < SanityLevel.SAFE_MIN_BOUNDS) {
                            Phase.HUNT
                        } else {
                            Phase.ACTION
                        }
                    }
            )
        }
    }

    private fun resetPhase() {
        _phaseUiState.update {
            it.copy(
                startFlashTime = DEFAULT,
                elapsedFlashTime = System.currentTimeMillis() - it.startFlashTime
            )
        }
        updateCanFlash()
    }

    /*
     * Timer ---------------------------
     */

    private fun launchTimerJob() {
        timerJob = viewModelScope.launch {

            _timerUiState.update {

                val startTime =
                    if(it.startTime == TIME_DEFAULT) System.currentTimeMillis()
                    else System.currentTimeMillis() -
                            getDurationByProgress(playerSanityUiState.value.insanityLevel)

                val remainingTime =
                    if(it.remainingTime == TIME_DEFAULT) difficultyUiState.value.time
                    else it.remainingTime

                it.copy(
                    startTime = startTime,
                    remainingTime = remainingTime,
                    paused = false
                )
            }

            while (!timerUiState.value.paused) {

                val delay = 100L
                val preDelay = System.currentTimeMillis()
                delay(delay)
                val postDelay = System.currentTimeMillis()
                val actualDelay = postDelay - preDelay

                val remaining = timerUiState.value.remainingTime
                setTimeRemaining((remaining - actualDelay).coerceAtLeast(0L))

                updatePhase()

            }
        }
    }

    private fun stopTimerJob() {
        _timerUiState.update {
            it.copy(
                paused = true
            )
        }
        timerJob?.cancel("Timer Job Cancelled")
    }

    /*
    /** The Sanity Drain starting time, whenever the play button is activated.
     * @return The Sanity drain start time. */
    private fun resetStartTime() =
        _timerUiState.update {
            it.copy(
                startTime = TIME_DEFAULT
            )
        }*/

    private fun setTimeRemaining(value: Long) {
        _timerUiState.update {
            it.copy(
                remainingTime = value
            )
        }
    }

    private fun playTimer() {
        stopPlayerSanityJob()
        stopTimerJob()

        launchTimerJob()
        launchPlayerSanityJob()
    }

    private fun pauseTimer() {
        stopTimerJob()
        stopPlayerSanityJob()
    }

    fun toggleTimer() {
        if (timerUiState.value.paused) {
            playTimer()
        } else {
            pauseTimer()
        }
    }

    fun fastForwardTimer(time: Long) {
        pauseTimer()
        setTimeRemaining(time)
        skipInsanity(SanityLevel.HALF_SANITY)
        playTimer()
    }

    private fun resetTimer() {
        stopTimerJob()
        initTimerUiState()

        calculateSanityDrain()
        updatePhase()
    }

    /*
     * Difficulty ---------------------------
     */
    fun incrementDifficultyIndex() =
        incrementDifficultyIndexUseCase(difficultyUiState.value.index)
            .getOrNull()?.let { index ->
                setDifficultyIndex(index)
            }

    fun decrementDifficultyIndex() =
        decrementDifficultyIndexUseCase(difficultyUiState.value.index)
            .getOrNull()?.let { index ->
                setDifficultyIndex(index)
            }

    fun setDifficultyIndex(newIndex: Int) {
        setDifficultyIndexUseCase(newIndex)
            .onSuccess {
                updateDifficulty(newIndex)
            }
            .onFailure {
                Log.e("InvestigationViewModel", "Set Difficulty Index failed.")
            }
    }

    private fun updateDifficulty(
        index: Int = 0
    ) {
        try {
            val name = getDifficultyNameUseCase(index).getOrThrow()
            val modifier = getDifficultyModifierUseCase(index).getOrThrow()
            val time = getDifficultyTimeUseCase(index).getOrThrow()
            val initialSanity = getDifficultyInitialSanityUseCase(index).getOrThrow()
            val responseType = getDifficultyResponseTypeUseCase(index).getOrThrow()

            _difficultyUiState.update {
                it.copy(
                    index = index,
                    name = name,
                    modifier = modifier,
                    time = time,
                    initialSanity = initialSanity,
                    responseType = responseType
                )
            }

            _playerSanityUiState.update {
                it.copy(
                    sanityLevel = difficultyUiState.value.initialSanity,
                    insanityLevel = 1f - difficultyUiState.value.initialSanity
                )
            }

            _phaseUiState.update {
                it.copy(
                    canAlertAudio = false
                )
            }

            updateGhostScores()

            setTimeRemaining(difficultyUiState.value.time)
            resetTimer()

        } catch (e: Exception) {
            e.printStackTrace()

            Log.e("InvestigationViewModel", "Update DifficultyUiState failed.")
        }

        Log.d("InvestigationViewModel", "DifficultyUiState:" +
                "\n\tindex: ${_difficultyUiState.value.index}" +
                "\n\tname: ${_difficultyUiState.value.name}" +
                "\n\tmodifier: ${_difficultyUiState.value.modifier}" +
                "\n\ttime: ${_difficultyUiState.value.time}" +
                "\n\tinitialSanity: ${_difficultyUiState.value.initialSanity}" +
                "\n\tresponseType: ${_difficultyUiState.value.responseType}")

    }

    /*
     * Map ---------------------------
     */

    fun incrementMapIndex() {
        try {
            val newIndex = incrementSimpleMapIndexUseCase(
                mapUiState.value.index).getOrThrow()
            updateMap(newIndex)
        } catch (e: Exception) { e.printStackTrace() }
    }

    fun decrementMapIndex() {
        try {
            val newIndex = decrementSimpleMapIndexUseCase(
                mapUiState.value.index).getOrThrow()
            updateMap(newIndex)
        } catch (e: Exception) { e.printStackTrace() }
    }

    fun setMapIndex(index: Int) {
        //TODO

        updateMap(index)
    }

    fun updateMap(
        index: Int = 0
    ) {
        try {
            val name = getSimpleMapNameUseCase(index).getOrThrow()
            val size = getSimpleMapSizeUseCase(index).getOrThrow()
            val modifier = fetchSimpleMapModifiersUseCase(size).getOrThrow()

            _mapUiState.update {
                it.copy(
                    index = index,
                    name = name,
                    size = size,
                    setupModifier = modifier.setupModifier,
                    normalModifier = modifier.normalModifier
                )
            }

            Log.e("InvestigationViewModel", "Set map index success")

        } catch (e: Exception) {
            Log.e("InvestigationViewModel", "Error setting map index")
            e.printStackTrace()
        }

        Log.d("InvestigationViewModel", "MapUiSate:" +
                "\n\tindex: ${mapUiState.value.index}" +
                "\n\tname: ${mapUiState.value.name}" +
                "\n\tsize: ${mapUiState.value.size}" +
                "\n\tsetupModifier: ${mapUiState.value.setupModifier}" +
                "\n\tnormalModifier: ${mapUiState.value.normalModifier}")
    }

    /*
     * Settings
     */


    /*
    * Footstep Visualizer
    */

    fun setBpmData(data: RealtimeUiState<GraphPoint>) {
        _bpmToolUiState.update {
            it.copy(realtimeState = data)
        }
        updateGhostScores()
    }

    fun setBpmMeasurementType(type: VisualizerMeasurementType) {
        _bpmToolUiState.update {
            it.copy(measurementType = type)
        }
        updateGhostScores()
    }

    fun toggleApplyBpmMeasurement() {
        _bpmToolUiState.update {
            it.copy(applyMeasurement = !it.applyMeasurement)
        }
        updateGhostScores()
    }

    init {
        updateMap(0)
        updateDifficulty(0)
        initPhaseUiState()
        initTimerUiState()

        initPlayerSanityUiState()

        resetEvidenceStates()
        updateGhostScores()
    }

    companion object {

        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                    val container = (application as InvestigationContainerProvider).provideInvestigationContainer()

                    val journalUseCaseBundle = container.journalUseCaseBundle
                    val difficultyUseCaseBundle = container.difficultyUseCaseBundle
                    val simpleMapUseCaseBundle = container.simpleMapUseCaseBundle
                    val codexUseCaseBundle = container.codexUseCaseBundle
                    val preferencesUseCaseBundle = container.preferencesUseCaseBundle

                    InvestigationScreenViewModel(
                        journalUseCaseBundle = journalUseCaseBundle,
                        difficultyUseCaseBundle = difficultyUseCaseBundle,
                        simpleMapUseCaseBundle = simpleMapUseCaseBundle,
                        codexUseCaseBundle = codexUseCaseBundle,
                        preferencesUseCaseBundle = preferencesUseCaseBundle
                    )
                }
            }

        const val TIME_MIN = 0L
        const val TIME_MAX = 300000L

    }

}
