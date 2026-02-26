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
import com.tritiumgaming.feature.investigation.ui.section.configs.DifficultyConfigUiState
import com.tritiumgaming.feature.investigation.ui.section.configs.MapConfigUiState
import com.tritiumgaming.feature.investigation.ui.section.footstep.BpmToolUiState
import com.tritiumgaming.feature.investigation.ui.section.footstep.visualizer.VisualizerMeasurementType
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiState
import com.tritiumgaming.shared.data.codex.usecase.FetchAchievementTypesUseCase
import com.tritiumgaming.shared.data.codex.usecase.FetchEquipmentTypesUseCase
import com.tritiumgaming.shared.data.codex.usecase.FetchPossessionTypesUseCase
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType
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
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.shared.data.map.modifier.usecase.FetchSimpleMapModifiersUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetSimpleMapModifierUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetSimpleMapNormalModifierUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetSimpleMapSetupModifierUseCase
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources.MapTitle
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
import com.tritiumgaming.shared.data.preferences.usecase.InitFlowUserPreferencesUseCase
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
    initFlowUserPreferencesUseCase: InitFlowUserPreferencesUseCase = preferencesUseCaseBundle.initFlowUserPreferencesUseCase,
) : ViewModel() {

    data class InvestigationScreenUserPreferences(
        val enableRTL: Boolean = false,
        val enableGhostReorder: Boolean = false,
        val maxHuntWarnFlashTime: Long = 0L,
        val allowHuntWarnAudio: Boolean = false
    )

    private val _preferences: StateFlow<InvestigationScreenUserPreferences> =
        initFlowUserPreferencesUseCase()
        .map {
            InvestigationScreenUserPreferences(
                enableRTL = it.enableRTL,
                enableGhostReorder = it.enableGhostReorder,
                maxHuntWarnFlashTime = it.maxHuntWarnFlashTime,
                allowHuntWarnAudio = it.allowHuntWarnAudio
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = InvestigationScreenUserPreferences()
        )
    val preferences = _preferences

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
     * UI STATES
     */
    private data class MapState(
        val index: Int = 0,
        val name: MapTitle = MapTitle.BLEASDALE_FARMHOUSE,
        val size: MapSize = MapSize.SMALL,
        val setupModifier: Float = 1f,
        val normalModifier: Float = 1f,
    )

    data class DifficultyState(
        val index: Int = 0,
        val type: DifficultyType = DifficultyType.entries[index],
        val name: DifficultyTitle = DifficultyTitle.AMATEUR,
        val modifier: Float = 0f,
        val time: Long = 0L,
        val initialSanity: Float = 0f,
        val responseType: DifficultyResponseType = DifficultyResponseType.KNOWN
    )

    private val _mapState = MutableStateFlow(MapState())

    private val _difficultyState = MutableStateFlow(DifficultyState())
    val difficultyState = _difficultyState

    private val _mapConfigUiState: StateFlow<MapConfigUiState> = _mapState.map { state ->
        val name = getSimpleMapNameUseCase(state.index).getOrThrow()

        MapConfigUiState(
            name = name,
            allMaps = maps.map { map -> map.mapName }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = MapConfigUiState(allMaps = maps.map { it.mapName })
    )
    internal val mapConfigUiState = _mapConfigUiState

    private val _difficultyConfigUiState : StateFlow<DifficultyConfigUiState> = _difficultyState.map { state ->
        val name = getDifficultyNameUseCase(state.index).getOrThrow()

        DifficultyConfigUiState(
            name = name,
            allDifficulties = difficulties.map { difficulty -> difficulty.name }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = DifficultyConfigUiState(allDifficulties = difficulties.map { it.name })
    )
    internal val difficultyConfigUiState = _difficultyConfigUiState

    private val _timerUiState = MutableStateFlow(TimerUiState())
    internal val timerUiState = _timerUiState.asStateFlow()

    private val _phaseUiState = MutableStateFlow(PhaseUiState())
    internal val phaseUiState = _phaseUiState.asStateFlow()

    private val _playerSanityUiState = MutableStateFlow(PlayerSanityUiState())
    internal val playerSanityUiState = _playerSanityUiState.asStateFlow()

    private val _toolbarUiState = MutableStateFlow(
        ToolbarUiState(
            isCollapsed = false,
            category = ToolbarUiState.Category.TOOL_CONFIG,
            openWidth = 0.5f
        )
    )
    internal val toolbarUiState = _toolbarUiState.asStateFlow()

    private val _popupUiState = MutableStateFlow(JournalPopupUiState())
    internal val popupUiState = _popupUiState.asStateFlow()

    private val _bpmToolUiState = MutableStateFlow(BpmToolUiState())
    internal val bpmToolUiState = _bpmToolUiState.asStateFlow()

    private val _operationSanityUiState = combine(
        _mapState,
        _difficultyState,
        phaseUiState
    ) { mapState, difficultyState, phaseUiState ->
        val mapModifier = try {
            getSimpleMapModifierUseCase(
                mapState.size.ordinal,
                phaseUiState.currentPhase
            ).getOrThrow()
        } catch (e: Exception) {
            e.printStackTrace()
            1f
        }

        OperationSanityUiState(
            sanityMax = _difficultyState.value.initialSanity,
            drainModifier = mapModifier * _difficultyState.value.modifier
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = OperationSanityUiState()
    )
    internal val operationSanityUiState = _operationSanityUiState

    private val _evidenceStates: MutableStateFlow<List<EvidenceState>> =
        MutableStateFlow(emptyList())
    internal val evidenceStates = _evidenceStates.asStateFlow()
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
    internal val ghostStates = _ghostStates.asStateFlow()
    private fun resetGhostStates() {
        _ghostStates.update {
            ghostEvidences.map { GhostState(it) }
        }
    }
    private fun updateGhostScores() {

        val ruledEvidence = evidenceStates.value
        val difficulty = _difficultyState.value.type
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
        combine(
            ghostStates, preferences
        ) { ghostScores, preferences ->
            val scores = if(!preferences.enableGhostReorder) {
                ghostScores
            } else {
                ghostScores
                    .sortedByDescending { it.bpmIsValid }
                    .sortedByDescending { it.score }
            }

            scores.map { it.ghostEvidence.ghost.id }
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
    internal val sortedGhosts = _sortedGhosts

    private val _operationDetailsUiState: StateFlow<OperationDetailsUiState> =
        combine(_mapState, _difficultyState) { mapState, difficultyState ->
            OperationDetailsUiState (
                mapDetails = OperationDetailsUiState.MapDetails(
                    name = mapState.name,
                    size = mapState.size,
                    modifiers = OperationDetailsUiState.MapDetails.MapModifiers(
                        normal = mapState.normalModifier,
                        setup = mapState.setupModifier
                    )
                ),
                difficultyDetails = OperationDetailsUiState.DifficultyDetails(
                    type = difficultyState.type,
                    name = difficultyState.name,
                    modifier = difficultyState.modifier,
                    setupTime = difficultyState.time,
                    initialSanity = difficultyState.initialSanity,
                    responseType = difficultyState.responseType,
                )
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = OperationDetailsUiState()
        )
    internal val operationDetailsUiState = _operationDetailsUiState

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
                remainingTime = _difficultyState.value.time,
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
    internal fun toggleToolbarState() {
        _toolbarUiState.update {
            it.copy(
                isCollapsed = !it.isCollapsed
            )
        }
    }

    internal fun setToolbarCategory(category: ToolbarUiState.Category) {
        if(toolbarUiState.value.category == category) {
            _toolbarUiState.update {
                val isCollapsed = !it.isCollapsed

                it.copy(
                    isCollapsed = isCollapsed,
                    category = if(!isCollapsed)
                        category
                    else
                        ToolbarUiState.Category.TOOL_NONE
                )
            }
        } else {
            _toolbarUiState.update {
                it.copy(
                    isCollapsed = false,
                    category = category
                )
            }
        }
    }

    /*
     * Popup Ui Functions
     */
    internal fun clearPopup() {
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

    internal fun setPopup(
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

    internal fun setPopup(
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
                sanityBounds = ghost.sanityBounds,
                huntCooldown = ghost.huntCooldown
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
    internal fun getGhostById(ghostId: GhostResources.GhostIdentifier): GhostType? {
        return try { getGhostTypeByIdUseCase(ghostId).getOrThrow() }
        catch (e: Exception) { e.printStackTrace(); null }
    }

    internal fun reset() {
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
    internal fun resetJournal() {
        resetEvidenceStates()
        resetGhostStates()
    }

    /*
    * Ghost Score ---------------------------
    */

    internal fun toggleGhostNegation(
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
    internal fun setEvidenceRuling(
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

    internal fun getRuledEvidence(
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
    internal fun setPlayerSanity(
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
        setStartTimeByProgress(SanityLevel.MAX_SANITY - _difficultyState.value.initialSanity)
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
                    if(it.remainingTime == TIME_DEFAULT) _difficultyState.value.time
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

    internal fun toggleTimer() {
        if (timerUiState.value.paused) {
            playTimer()
        } else {
            pauseTimer()
        }
    }

    internal fun fastForwardTimer(time: Long) {
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
    internal fun incrementDifficultyIndex() =
        incrementDifficultyIndexUseCase(_difficultyState.value.index)
            .getOrNull()?.let { index ->
                setDifficultyIndex(index)
            }

    internal fun decrementDifficultyIndex() =
        decrementDifficultyIndexUseCase(_difficultyState.value.index)
            .getOrNull()?.let { index ->
                setDifficultyIndex(index)
            }

    internal fun setDifficultyIndex(newIndex: Int) {
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
            val type = DifficultyType.entries[index]
            val modifier = getDifficultyModifierUseCase(index).getOrThrow()
            val time = getDifficultyTimeUseCase(index).getOrThrow()
            val initialSanity = getDifficultyInitialSanityUseCase(index).getOrThrow()
            val responseType = getDifficultyResponseTypeUseCase(index).getOrThrow()

            _difficultyState.update {
                it.copy(
                    index = index,
                    type = type,
                    name = name,
                    modifier = modifier,
                    time = time,
                    initialSanity = initialSanity,
                    responseType = responseType
                )
            }

            _playerSanityUiState.update {
                it.copy(
                    sanityLevel = _difficultyState.value.initialSanity,
                    insanityLevel = 1f - _difficultyState.value.initialSanity
                )
            }

            _phaseUiState.update {
                it.copy(
                    canAlertAudio = false
                )
            }

            updateGhostScores()

            setTimeRemaining(_difficultyState.value.time)
            resetTimer()

        } catch (e: Exception) {
            e.printStackTrace()

            Log.e("InvestigationViewModel", "Update DifficultyUiState failed.")
        }

        Log.d("InvestigationViewModel", "DifficultyUiState:" +
                "\n\tindex: ${_difficultyState.value.index}" +
                "\n\tname: ${_difficultyState.value.name}" +
                "\n\tmodifier: ${_difficultyState.value.modifier}" +
                "\n\ttime: ${_difficultyState.value.time}" +
                "\n\tinitialSanity: ${_difficultyState.value.initialSanity}" +
                "\n\tresponseType: ${_difficultyState.value.responseType}")

    }

    /*
     * Map ---------------------------
     */

    internal fun incrementMapIndex() {
        try {
            val newIndex = incrementSimpleMapIndexUseCase(
                _mapState.value.index).getOrThrow()
            updateMap(newIndex)
        } catch (e: Exception) { e.printStackTrace() }
    }

    internal fun decrementMapIndex() {
        try {
            val newIndex = decrementSimpleMapIndexUseCase(
                _mapState.value.index).getOrThrow()
            updateMap(newIndex)
        } catch (e: Exception) { e.printStackTrace() }
    }

    internal fun setMapIndex(index: Int) {
        //TODO

        updateMap(index)
    }

    internal fun updateMap(
        index: Int = 0
    ) {
        try {
            val name = getSimpleMapNameUseCase(index).getOrThrow()
            val size = getSimpleMapSizeUseCase(index).getOrThrow()
            val modifier = fetchSimpleMapModifiersUseCase(size).getOrThrow()

            _mapState.update {
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
                "\n\tindex: ${_mapState.value.index}" +
                "\n\tname: ${_mapConfigUiState.value.name}" +
                "\n\tsize: ${_mapState.value.size}" +
                "\n\tsetupModifier: ${_mapState.value.setupModifier}" +
                "\n\tnormalModifier: ${_mapState.value.normalModifier}")
    }

    /*
     * Settings
     */


    /*
    * Footstep Visualizer
    */

    internal fun setBpmData(data: RealtimeUiState<GraphPoint>) {
        _bpmToolUiState.update {
            it.copy(realtimeState = data)
        }
        updateGhostScores()
    }

    internal fun setBpmMeasurementType(type: VisualizerMeasurementType) {
        _bpmToolUiState.update {
            it.copy(measurementType = type)
        }
        updateGhostScores()
    }

    internal fun toggleApplyBpmMeasurement() {
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
