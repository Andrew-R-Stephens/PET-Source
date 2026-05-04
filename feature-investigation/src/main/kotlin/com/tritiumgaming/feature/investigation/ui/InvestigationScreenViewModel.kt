package com.tritiumgaming.feature.investigation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.core.common.util.FormatterUtils
import com.tritiumgaming.core.common.util.FormatterUtils.roundMillisToDuration
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.GraphPoint
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.RealtimeUiState
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiState
import com.tritiumgaming.core.ui.widgets.progressbar.ProgressBarNotch
import com.tritiumgaming.core.ui.widgets.text.UiText
import com.tritiumgaming.feature.investigation.app.container.ChallengesUseCaseBundle
import com.tritiumgaming.feature.investigation.app.container.CodexUseCaseBundle
import com.tritiumgaming.feature.investigation.app.container.DifficultyUseCaseBundle
import com.tritiumgaming.feature.investigation.app.container.InvestigationContainerProvider
import com.tritiumgaming.feature.investigation.app.container.JournalUseCaseBundle
import com.tritiumgaming.feature.investigation.app.container.PreferencesUseCaseBundle
import com.tritiumgaming.feature.investigation.app.container.SimpleMapUseCaseBundle
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toStringResource
import com.tritiumgaming.feature.investigation.ui.tool.operationtimer.OperationTimerUiState.Companion.TIME_DEFAULT
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.journal.ghost.item.GhostState
import com.tritiumgaming.feature.investigation.ui.popups.JournalPopupUiState
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.configs.DifficultyConfigUiState
import com.tritiumgaming.feature.investigation.ui.tool.configs.MapConfigUiState
import com.tritiumgaming.feature.investigation.ui.tool.configs.WeatherUiState
import com.tritiumgaming.feature.investigation.ui.tool.footstep.BpmToolUiState
import com.tritiumgaming.feature.investigation.ui.tool.footstep.visualizer.VisualizerMeasurementType
import com.tritiumgaming.feature.investigation.ui.tool.phase.PhaseUiState
import com.tritiumgaming.feature.investigation.ui.tool.sanity.OperationSanityUiState
import com.tritiumgaming.feature.investigation.ui.tool.operationtimer.OperationTimerUiState
import com.tritiumgaming.feature.investigation.ui.tool.temperature.TemperatureUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbarUiState
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources
import com.tritiumgaming.shared.data.challenge.usecase.GetCurrentChallengeUseCase
import com.tritiumgaming.shared.data.codex.usecase.FetchEquipmentTypesUseCase
import com.tritiumgaming.shared.data.customdifficulty.usecase.GetCustomDifficultiesUseCase
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.shared.data.difficulty.usecase.DecrementDifficultyIndexUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.FetchDifficultiesUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.IncrementDifficultyIndexUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.SetDifficultyIndexUseCase
import com.tritiumgaming.shared.data.difficultysetting.dto.EquipmentPermission
import com.tritiumgaming.shared.data.difficultysetting.dto.EquipmentPermission.Permission
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FuseBoxAtStartOfContract
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.difficultysetting.mapper.toFloat
import com.tritiumgaming.shared.data.difficultysetting.mapper.toLong
import com.tritiumgaming.shared.data.difficultysetting.mapper.toTemperatureRange
import com.tritiumgaming.shared.data.evidence.mapper.toEquipmentIdentifier
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostTitle
import com.tritiumgaming.shared.data.ghost.model.Ghost
import com.tritiumgaming.shared.data.ghosttrait.model.GhostTrait
import com.tritiumgaming.shared.data.ghosttrait.usecase.GetAllGhostTraitsUseCase
import com.tritiumgaming.shared.data.investigation.model.CategoryOption
import com.tritiumgaming.shared.data.investigation.model.DifficultyData
import com.tritiumgaming.shared.data.investigation.model.EvidenceState
import com.tritiumgaming.shared.data.investigation.model.EvidenceValidationType
import com.tritiumgaming.shared.data.investigation.model.GhostTraitFilterOptions
import com.tritiumgaming.shared.data.investigation.model.GhostTraitFilterUiOptions
import com.tritiumgaming.shared.data.investigation.model.InvestigationScreenUserPreferences
import com.tritiumgaming.shared.data.investigation.model.MapData
import com.tritiumgaming.shared.data.investigation.model.DifficultyOverridesData
import com.tritiumgaming.shared.data.investigation.model.DifficultyOverridesData.Companion.FuseBoxFlag
import com.tritiumgaming.shared.data.investigation.model.PhaseData
import com.tritiumgaming.shared.data.investigation.model.PhaseData.Companion.DEFAULT
import com.tritiumgaming.shared.data.investigation.model.PhaseData.Companion.DURATION_30_SECONDS
import com.tritiumgaming.shared.data.investigation.model.SanityTimerData
import com.tritiumgaming.shared.data.investigation.model.SanityTimerData.Companion.TIME_MIN
import com.tritiumgaming.shared.data.investigation.model.StateOption
import com.tritiumgaming.shared.data.investigation.model.TagOption
import com.tritiumgaming.shared.data.investigation.model.TemperatureData
import com.tritiumgaming.shared.data.investigation.model.ToolTimerData
import com.tritiumgaming.shared.data.investigation.model.ToolTimerType
import com.tritiumgaming.shared.data.investigation.model.TraitFilter
import com.tritiumgaming.shared.data.investigation.model.TraitValidationType
import com.tritiumgaming.shared.data.investigation.model.ValidatedGhostTrait
import com.tritiumgaming.shared.data.investigation.model.WeightOption
import com.tritiumgaming.shared.data.investigation.usecase.GetInvestigationStateUseCase
import com.tritiumgaming.shared.data.investigation.usecase.InvestigationUseCaseBundle
import com.tritiumgaming.shared.data.investigation.usecase.UpdateInvestigationDifficultyUseCase
import com.tritiumgaming.shared.data.investigation.usecase.UpdateInvestigationMapUseCase
import com.tritiumgaming.shared.data.journal.usecase.FetchEvidenceTypesUseCase
import com.tritiumgaming.shared.data.journal.usecase.FetchGhostEvidencesUseCase
import com.tritiumgaming.shared.data.journal.usecase.GetEvidenceUseCase
import com.tritiumgaming.shared.data.journal.usecase.GetGhostUseCase
import com.tritiumgaming.shared.data.map.modifier.mappers.toFloat
import com.tritiumgaming.shared.data.map.modifier.usecase.FetchSimpleMapModifiersUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetSimpleMapModifierUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.DecrementSimpleMapIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapNameUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapSizeUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.IncrementSimpleMapIndexUseCase
import com.tritiumgaming.shared.data.phase.model.PhaseResources.PhaseIdentifier
import com.tritiumgaming.shared.data.popup.model.EvidencePopupRecord
import com.tritiumgaming.shared.data.popup.model.GhostPopupRecord
import com.tritiumgaming.shared.data.sanity.model.SanityLevel
import com.tritiumgaming.shared.data.sanity.model.SanityLevel.SAFE_MIN_BOUNDS
import com.tritiumgaming.shared.data.sanity.model.SanityLevel.SANITY_LOSS_ON_PLAYER_DEATH
import com.tritiumgaming.shared.data.weather.model.Temperature
import com.tritiumgaming.shared.data.weather.model.Temperature.TEMPERATURE_COOLING_RATE
import com.tritiumgaming.shared.data.weather.model.Temperature.TEMPERATURE_HEATING_RATE
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.minutes

class InvestigationScreenViewModel private constructor(
    journalUseCaseBundle: JournalUseCaseBundle,
    difficultyUseCaseBundle: DifficultyUseCaseBundle,
    simpleMapUseCaseBundle: SimpleMapUseCaseBundle,
    codexUseCaseBundle: CodexUseCaseBundle,
    investigationUseCaseBundle: InvestigationUseCaseBundle,
    challengesUseCaseBundle: ChallengesUseCaseBundle,
    preferencesUseCaseBundle: PreferencesUseCaseBundle,
) : ViewModel() {

    private val getEvidenceUseCase: GetEvidenceUseCase = journalUseCaseBundle.getEvidenceUseCase
    private val fetchEvidenceTypesUseCase: FetchEvidenceTypesUseCase = journalUseCaseBundle.fetchEvidenceTypesUseCase
    private val getGhostUseCase: GetGhostUseCase = journalUseCaseBundle.getGhostUseCase
    private val fetchGhostEvidencesUseCase: FetchGhostEvidencesUseCase = journalUseCaseBundle.fetchGhostEvidencesUseCase
    private val getAllGhostTraitsUseCase: GetAllGhostTraitsUseCase = journalUseCaseBundle.getAllGhostTraitsUseCase
    private val fetchDifficultiesUseCase: FetchDifficultiesUseCase = difficultyUseCaseBundle.fetchDifficultiesUseCase
    private val incrementDifficultyIndexUseCase: IncrementDifficultyIndexUseCase = difficultyUseCaseBundle.incrementDifficultyIndexUseCase
    private val decrementDifficultyIndexUseCase: DecrementDifficultyIndexUseCase = difficultyUseCaseBundle.decrementDifficultyIndexUseCase
    private val setDifficultyIndexUseCase: SetDifficultyIndexUseCase = difficultyUseCaseBundle.setDifficultyIndexUseCase
    private val fetchSimpleMapsUseCase: FetchSimpleMapsUseCase = simpleMapUseCaseBundle.fetchSimpleMapsUseCase
    private val incrementSimpleMapIndexUseCase: IncrementSimpleMapIndexUseCase = simpleMapUseCaseBundle.incrementSimpleMapIndexUseCase
    private val decrementSimpleMapIndexUseCase: DecrementSimpleMapIndexUseCase = simpleMapUseCaseBundle.decrementSimpleMapIndexUseCase
    private val getSimpleMapNameUseCase: GetSimpleMapNameUseCase = simpleMapUseCaseBundle.getSimpleMapNameUseCase
    private val getSimpleMapSizeUseCase: GetSimpleMapSizeUseCase = simpleMapUseCaseBundle.getSimpleMapSizeUseCase
    private val getSimpleMapModifierUseCase: GetSimpleMapModifierUseCase = simpleMapUseCaseBundle.getSimpleMapModifierUseCase
    private val fetchSimpleMapModifiersUseCase: FetchSimpleMapModifiersUseCase = simpleMapUseCaseBundle.fetchSimpleMapModifiersUseCase
    private val fetchCodexEquipmentUseCase: FetchEquipmentTypesUseCase = codexUseCaseBundle.fetchCodexEquipmentUseCase
    private val getInvestigationStateUseCase: GetInvestigationStateUseCase = investigationUseCaseBundle.getInvestigationStateUseCase
    private val updateInvestigationMapUseCase: UpdateInvestigationMapUseCase = investigationUseCaseBundle.updateInvestigationMapUseCase
    private val updateInvestigationDifficultyUseCase: UpdateInvestigationDifficultyUseCase = investigationUseCaseBundle.updateInvestigationDifficultyUseCase
    private val getCurrentChallengeUseCase: GetCurrentChallengeUseCase = challengesUseCaseBundle.getCurrentChallengeUseCase
    private val getCustomDifficultiesUseCase: GetCustomDifficultiesUseCase = investigationUseCaseBundle.getCustomDifficultiesUseCase

    private val preferences: StateFlow<InvestigationScreenUserPreferences> =
        preferencesUseCaseBundle.initFlowUserPreferencesUseCase()
        .map {
            InvestigationScreenUserPreferences(
                enableGhostReorder = it.enableGhostReorder,
                maxHuntWarnFlashTime = it.maxHuntWarnFlashTime,
                allowHuntWarnAudio = it.allowHuntWarnAudio
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = InvestigationScreenUserPreferences()
        )

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

    private val allGhostTraits: List<GhostTrait> = try {
        getAllGhostTraitsUseCase().getOrThrow()
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }

    private val defaultSelectedTraits
        get() = allGhostTraits.map { ValidatedGhostTrait(ghostTrait = it) }

    /*
     * Routines
     */
    private var operationControllerJob: Job? = null
    private var operationTimerJob: Job? = null

    private var toolTimersJob: Job? = null

    /*
     * Investigation Repository
     */
    private val _investigationState = getInvestigationStateUseCase()

    /*
     * Map
     */

    private val mapState = _investigationState.map { it.map }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = MapData()
        )

    private fun updateMap(
        index: Int = 0
    ) {
        try {
            val name = getSimpleMapNameUseCase(index).getOrThrow()
            val size = getSimpleMapSizeUseCase(index).getOrThrow()
            val modifier = fetchSimpleMapModifiersUseCase(size).getOrThrow()

            val mapState = MapData(
                index = index,
                name = name,
                size = size,
                setupModifier = modifier.setupModifier,
                actionModifier = modifier.actionModifier
            )

            updateInvestigationMapUseCase(mapState)

        } catch (e: Exception) {
            Log.e("InvestigationViewModel", "Error setting map index")
            e.printStackTrace()
        }

    }


    /*
     * Difficulty
     */

    private val difficultyState = _investigationState.map { it.difficulty }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = DifficultyData()
        )

    private fun updateDifficulty(difficultyIndex: Int = 0, customIndex: Int? = null) {
        viewModelScope.launch {
            try {
                val baseDifficulty = difficulties.getOrNull(difficultyIndex) ?: return@launch

                var settings = baseDifficulty.settingsModel
                var challengeTitle: ChallengeResources.ChallengeTitle? = null

                when (baseDifficulty.type) {
                    DifficultyType.CHALLENGE -> {
                        getCurrentChallengeUseCase().onSuccess { challenge ->
                            challengeTitle = challenge.challengeTitle
                            settings = challenge.settingsModel

                            // Sync map if required by challenge
                            maps.indexOfFirst { it.mapName == challenge.map }.let { mapIndex ->
                                if (mapIndex != -1) setMapIndex(mapIndex)
                            }
                        }
                    }
                    DifficultyType.CUSTOM -> {
                        val customDifficulties = getCustomDifficultiesUseCase().first()
                        val custom = customIndex?.let { customDifficulties.getOrNull(it) }
                            ?: customDifficulties.firstOrNull()
                        custom?.let {
                            settings = it.settings
                        }
                    }
                    else -> { /* Use base settings */ }
                }

                val newDifficultyState = DifficultyData(
                    index = difficultyIndex,
                    type = baseDifficulty.type,
                    title = baseDifficulty.difficultyTitle,
                    responseType = baseDifficulty.responseType,
                    challengeTitle = challengeTitle,
                    settings = settings,
                    customIndex = customIndex
                )

                updateInvestigationDifficultyUseCase(newDifficultyState)

            } catch (e: Exception) {
                Log.e("InvestigationViewModel", "Update Difficulty failed", e)
            }
        }
    }

    internal val customDifficultyConfigUiState: StateFlow<CustomDifficultyConfigUiState> = combine(
        difficultyState,
        getCustomDifficultiesUseCase()
    ) { difficulty, customDifficulties ->
        val customDifficultyIndex = difficulty.customIndex ?: 0

        CustomDifficultyConfigUiState(
            name = customDifficulties[customDifficultyIndex].name,
            options = customDifficulties.map { it.name },
            selectedIndex = difficulty.customIndex ?: 0,
            isVisible = difficulty.type == DifficultyType.CUSTOM
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CustomDifficultyConfigUiState()
    )

    /*
     * Tool Timers
     */

    private data class NotchedProgressBarData(
        val max: Long = 0,
        val origin: Long = 0,
        val remaining: Long = max,
        val notches: List<ProgressBarNotch> = listOf(),
        val running: Boolean = false
    )

    private val smudgeHuntTimerProgressBarNotches = listOf(
        ProgressBarNotch(
            UiText.StringResource(GhostTitle.SPIRIT.toStringResource()),
            (3).minutes.inWholeMilliseconds
        ),
        ProgressBarNotch(
            UiText.DynamicString("Standard"),
            (1.5).minutes.inWholeMilliseconds
        ),
        ProgressBarNotch(
            UiText.StringResource(GhostTitle.DEMON.toStringResource()),
            (1).minutes.inWholeMilliseconds
        ),
    )
    private val _smudgeHuntProtectionTimerState = MutableStateFlow(
        NotchedProgressBarData(
            max = 3.minutes.inWholeMilliseconds,
            origin = 0,
            notches = smudgeHuntTimerProgressBarNotches,
            running = false
        )
    )

    private val huntDurationTimerProgressBarNotches = listOf(
        ProgressBarNotch(
            UiText.DynamicString("Standard"),
            (1.5).minutes.inWholeMilliseconds
        ),
        ProgressBarNotch(
            UiText.DynamicString("Cursed"),
            (1.5).minutes.inWholeMilliseconds
        ),
    )
    private val _huntDurationTimerState = MutableStateFlow(
        NotchedProgressBarData(
            max = 1.minutes.inWholeMilliseconds,
            origin = 0,
            notches = huntDurationTimerProgressBarNotches,
            running = false
        )
    )

    private val huntCooldownTimerProgressBarNotches = listOf(
        ProgressBarNotch(
            UiText.DynamicString("Standard"),
            (1.5).minutes.inWholeMilliseconds
        )
    )
    private val _huntCooldownTimerState = MutableStateFlow(
        NotchedProgressBarData(
            max = 72000,
            origin = 0,
            notches = huntCooldownTimerProgressBarNotches,
            running = false
        )
    )

    private val _fingerprintTimerState = MutableStateFlow(
        NotchedProgressBarData(
            max = difficultyState.value.settings.fingerprintDuration.toLong(),
            origin = 0,
            notches = listOf(
                ProgressBarNotch(
                    UiText.StringResource(GhostTitle.OBAKE.toStringResource()),
                    (difficultyState.value.settings.fingerprintDuration.toLong() * .5f).toLong()
                ),
                ProgressBarNotch(
                    UiText.DynamicString("Normal"),
                    difficultyState.value.settings.fingerprintDuration.toLong()
                )
            ),
            running = false
        )
    )

    private val _toolsTimerState = MutableStateFlow(ToolTimerData())
    private fun MutableStateFlow<NotchedProgressBarData>.toggleTimer() {
        update { state ->
            if (!state.running) state.copy(running = true)
            else state.copy(remaining = state.max, running = false)
        }
    }

    /*
     * Operation Timer State
     */

    private val _operationTimerState = MutableStateFlow(
        SanityTimerData(
            startTime = TIME_DEFAULT,
            remainingTime = difficultyState.value.settings.setupTime.toLong(),
            paused = true
        )
    )
    private val operationTimerState = _operationTimerState.asStateFlow()

    /*
     * Override
     * */
    private val _difficultyOverridesState = MutableStateFlow(DifficultyOverridesData())
    val difficultyOverridesState = _difficultyOverridesState.asStateFlow()

    /*
     * Weather
     */
    private val _weatherState = combine(
        difficultyOverridesState,
        difficultyState
    ) { overrides, diff ->
        if (diff.settings.weather == Weather.RANDOM) overrides.weather
        else diff.settings.weather
    }.stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Weather.RANDOM
    )

    /* Temperature */
    private val _temperatureState = MutableStateFlow(TemperatureData())
    private fun updateTemperature(delta: Float, timeStamp: Long) {
        val weatherRange = getCurrentWeather().toTemperatureRange()
        val fuseBoxOn = _difficultyOverridesState.value.fuseBox == FuseBoxFlag.FUSEBOX_ENABLED

        val targetTemp = if (fuseBoxOn)
            Temperature.TEMPERATURE_START_FUSEBOX_ENABLED
        else
            weatherRange.low

        _temperatureState.update { state ->
            var nextTemp = state.current + delta
            if (delta > 0) { // Heating
                if (state.current <= targetTemp && nextTemp > targetTemp) nextTemp = targetTemp
            } else if (delta < 0) { // Cooling
                if (state.current >= targetTemp && nextTemp < targetTemp) nextTemp = targetTemp
            }

            state.copy(
                previous = state.current,
                current = nextTemp,
                lastUpdate = timeStamp
            )
        }
    }

    private fun resetTemperature() {
        val weather = getCurrentWeather()
        val weatherRange = weather.toTemperatureRange()
        val fuseBoxOn = _difficultyOverridesState.value.fuseBox == FuseBoxFlag.FUSEBOX_ENABLED

        val startTemp = if (fuseBoxOn) {
            Temperature.TEMPERATURE_START_FUSEBOX_ENABLED
        } else {
            weatherRange.high
        }

        _temperatureState.update {
            it.copy(
                current = startTemp,
                previous = startTemp,
                range = weatherRange,
                lastUpdate = System.currentTimeMillis()
            )
        }
    }

    private fun getCurrentWeather(): Weather {
        val diff = difficultyState.value
        val overrides = _difficultyOverridesState.value
        return if (diff.settings.weather == Weather.RANDOM) overrides.weather
        else diff.settings.weather
    }

    /*
     * Phase
     */
    private val _phaseState = MutableStateFlow(PhaseData())
    private val phaseState = _phaseState.asStateFlow()

    /*
     * Traits Data
     */
    private val _traitData = MutableStateFlow(allGhostTraits)
    private val traitData = _traitData.asStateFlow()

    /*
     * Trait Filter
     */
    private val _traitFilterOptions = traitData.map { traits ->
        val categories = traits.map {it.category }.distinct().sortedBy { it }
        val weights = traits.map { it.weight }.distinct().sortedBy { it }
        val states = traits.map { it.state }.distinct().sortedBy { it }
        val tags = traits.flatMap { it.tags }.distinct().sortedBy { it }

        GhostTraitFilterOptions(
            categories = categories,
            weights = weights,
            states = states,
            tags = tags
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GhostTraitFilterOptions()
    )

    /*
     * Traits Selected
     */
    private val _selectedTraits = MutableStateFlow(
        allGhostTraits.map { ValidatedGhostTrait(ghostTrait = it) }
    )
    val selectedTraits = _selectedTraits.asStateFlow()
    private fun resetTraitSelections() {
        _selectedTraits.update { defaultSelectedTraits }
    }

    /*
     * EvidenceStates
     */
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
    }

    /*
     * BPM Tool
     */
    private val _bpmToolState = MutableStateFlow(BpmToolUiState())

    /* Ghost Rejections */
    private val _explicitGhostRejects =
        MutableStateFlow<Set<GhostResources.GhostIdentifier>>(emptySet())
    private fun resetExplicitGhostRejects() {
        _explicitGhostRejects.update { emptySet() }
    }

    /* Ghost States */
    private val _ghostStates: StateFlow<List<GhostState>> = combine(
        evidenceStates,
        difficultyState,
        _bpmToolState,
        _explicitGhostRejects,
        selectedTraits
    ) { evidenceStates, difficultyState, bpmToolUiState, explicitRejections, selectedTraits ->
        ghostEvidences.map { ghostEvidence ->
            val isManuallyRejected = ghostEvidence.ghost.id in explicitRejections

            // Calculate the base state with score
            var state = GhostState(
                ghostEvidence = ghostEvidence,
                manualRejection = isManuallyRejected
            ).updateScore(
                evidenceState = evidenceStates,
                evidenceGiven = difficultyState.settings.evidenceGiven
            )

            // Apply BPM validation if enabled
            state = if (bpmToolUiState.applyMeasurement) {
                val bpmValue = when (bpmToolUiState.measurementType) {
                    VisualizerMeasurementType.INSTANT -> bpmToolUiState.realtimeState.smoothed
                    VisualizerMeasurementType.AVERAGED -> bpmToolUiState.realtimeState.average
                    VisualizerMeasurementType.WEIGHTED -> bpmToolUiState.realtimeState.weightedAverage
                }
                state.updateBpmValidation(bpmValue)
            } else {
                state.resetBpmValidation()
            }

            val traits = selectedTraits
                .filter { it.validationType == TraitValidationType.CONFIRMED }
                .filter { state.ghostEvidence.ghost.id in it.ghostTrait.affectedGhosts }
            state = state.updateTraits(traits.map { it.ghostTrait }.toSet())

            state
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = ghostEvidences.map { GhostState(it) }
    )
    private val ghostStates = _ghostStates

    private val _playerSanityState = MutableStateFlow(
        PlayerSanityData(
            sanityLevel = difficultyState.value.settings.startingSanity.toFloat(),
            insanityLevel = 1f - difficultyState.value.settings.startingSanity.toFloat()
        )
    )

    /**
     * UI States
     */
    private val _mapConfigUiState: StateFlow<MapConfigUiState> =
        combine(
            mapState,
            difficultyState
        ) { mapState, difficultyState ->
            val name = maps[mapState.index].mapName

            MapConfigUiState(
                name = name,
                enabled = difficultyState.type != DifficultyType.CHALLENGE,
                allMaps = maps.map { map -> map.mapName }
            )
        }
        .distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = MapConfigUiState(
                allMaps = maps.map { it.mapName }
            )
        )
    internal val mapConfigUiState = _mapConfigUiState

    private val _difficultyConfigUiState : StateFlow<DifficultyConfigUiState> = difficultyState
        .map { state ->
            val type = difficulties[state.index].type
            val name = difficulties[state.index].difficultyTitle

            DifficultyConfigUiState(
                type = type,
                name = name,
                allDifficulties = difficulties.map { difficulty -> difficulty.difficultyTitle }
            )
        }
        .distinctUntilChanged()
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = DifficultyConfigUiState(
            allDifficulties = difficulties.map { it.difficultyTitle }
        )
    )
    internal val difficultyConfigUiState = _difficultyConfigUiState

    private val _operationTimerUiState: StateFlow<OperationTimerUiState> = _operationTimerState.map {
        sanityTimerState ->
        OperationTimerUiState(
            remainingTime = FormatterUtils.formatMillisToTime(sanityTimerState.remainingTime),
            paused = sanityTimerState.paused
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OperationTimerUiState()
    )
    internal val operationTimerUiState = _operationTimerUiState

    val playerSanityUiState = _playerSanityState.map {
        val normalizedInsanity = (((it.insanityLevel * 1000).roundToInt()) / 1000f)
        val normalizedSanity = (((it.sanityLevel * 1000).roundToInt()) / 1000f)

        PlayerSanityUiState(
            normalizedInsanity,
            normalizedSanity
        )
    }.distinctUntilChanged { old, new ->
        old.insanityLevel == new.insanityLevel || old.sanityLevel == new.sanityLevel
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PlayerSanityUiState()
    )

    private val _phaseUiState = combine(
        phaseState, operationTimerState, _playerSanityState, preferences
    ) { phaseState, timerUiState, playerSanityState, preferences ->
        val type =
            when {
                timerUiState.remainingTime > TIME_MIN -> PhaseIdentifier.SETUP
                playerSanityState.sanityLevel < SAFE_MIN_BOUNDS -> PhaseIdentifier.HUNT
                else -> PhaseIdentifier.ACTION
            }

        val canFlash = playerSanityState.isInsane &&
                phaseState.elapsedFlashTime <= preferences.maxHuntWarnFlashTime

        PhaseUiState(
            type = type,
            canFlash = canFlash,
            canAlertAudio = phaseState.canAlertAudio,
            startFlashTime = phaseState.startFlashTime,
            elapsedFlashTime = phaseState.elapsedFlashTime
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PhaseUiState(
            elapsedFlashTime = 0L,
            startFlashTime = DEFAULT,
            maxFlashTime = DURATION_30_SECONDS,
            type = PhaseIdentifier.SETUP,
            canFlash = true,
            canAlertAudio = false
        )
    )
    internal val phaseUiState = _phaseUiState

    private val _operationToolbarUiState = MutableStateFlow(
        OperationToolbarUiState(
            isCollapsed = false,
            category = OperationToolbarUiState.Category.TOOL_CONFIG,
            openWidth = 0.5f
        )
    )
    internal val operationToolbarUiState = _operationToolbarUiState.asStateFlow()

    private val _popupUiState = MutableStateFlow(JournalPopupUiState())
    internal val popupUiState = _popupUiState.asStateFlow()

    /*
     * Bpm Tool
     */
    internal val bpmToolUiState = _bpmToolState.map {
        BpmToolUiState(
            realtimeState = it.realtimeState,
            measurementType = it.measurementType,
            applyMeasurement = it.applyMeasurement
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BpmToolUiState()
    )

    /*
     * Operation Sanity Ui
     */
    private val _operationSanityUiState = combine(
        mapState,
        difficultyState,
        phaseState
    ) { mapState, difficultyState, phaseState ->
        val mapModifier = try {
            getSimpleMapModifierUseCase(
                mapState.size.ordinal,
                phaseState.type
            ).getOrThrow().toFloat()
        } catch (e: Exception) {
            e.printStackTrace()
            1f
        }

        OperationSanityUiState(
            sanityMax = difficultyState.settings.startingSanity.toFloat(),
            drainModifier = mapModifier * difficultyState.settings.sanityDrainSpeed.toFloat()
        )
    }
    .distinctUntilChanged()
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = OperationSanityUiState()
    )
    internal val operationSanityUiState = _operationSanityUiState

    /*
     * Weather Ui
     */
    private val _weatherUiState = combine(
        _weatherState,
        difficultyState,
    ) { weatherState, difficultyState ->
        WeatherUiState(
            weather = weatherState,
            enabled = difficultyState.settings.weather == Weather.RANDOM
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WeatherUiState()
    )
    internal val weatherUiState = _weatherUiState

    /*
     * Traits Ui
     */
    private val _traitFilterUiState: MutableStateFlow<TraitFilter> = MutableStateFlow(
        TraitFilter()
    )
    val traitFilterUiState = _traitFilterUiState.asStateFlow()

    private val _traitFilterOptionsUiState: StateFlow<GhostTraitFilterUiOptions> = combine(
        traitFilterUiState,
        _traitFilterOptions
    ) { filter, options ->
        GhostTraitFilterUiOptions(
            category = options.categories.map {
                CategoryOption(it, filter.category == it) },
            weight = options.weights.map {
                WeightOption(it, filter.weight == it) },
            state = options.states.map {
                StateOption(it, filter.state == it) },
            tags = options.tags.let { it.ifEmpty { filter.tags } }
                .map { TagOption(it) },
            uniqueOnly = filter.uniqueOnly,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GhostTraitFilterUiOptions()
    )
    val traitFilterOptionsUiState = _traitFilterOptionsUiState

    private val _traitListUiState: StateFlow<List<ValidatedGhostTrait>> =
        combine(selectedTraits, traitFilterUiState) { traits, filter ->
            traits.asSequence().filter { (trait, _) ->
                val matchesCategory = filter.category == null || trait.category == filter.category
                val matchesWeight = filter.weight == null || trait.weight == filter.weight
                val matchesState = filter.state == null || trait.state == filter.state
                val matchesUnique = !filter.uniqueOnly || trait.isUnique

                val matchesTags = filter.tags.isEmpty() || trait.tags.any { it in filter.tags }

                matchesCategory && matchesWeight && matchesState && matchesUnique && matchesTags
            }.sortedWith (
                compareByDescending <ValidatedGhostTrait> { it.validationType }
                    .thenBy {
                        val ghosts = it.ghostTrait.affectedGhosts
                        when(ghosts.size) {
                            in 2 .. Integer.MAX_VALUE -> Integer.MAX_VALUE
                            else -> ghosts.first().ordinal
                        }
                    }
                    .thenBy { it.ghostTrait.weight }
                    .thenBy { it.ghostTrait.state }
                    .thenBy { it.ghostTrait.category }
                    .thenByDescending { it.ghostTrait.isUnique }
            ).toList()
    }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val traitListUiState = _traitListUiState

    private val _temperatureUiState = _temperatureState.map { temperatureState ->
        TemperatureUiState(
            range = temperatureState.range,
            current = temperatureState.current,
            temporalGradient = temperatureState.current - temperatureState.previous,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TemperatureUiState()
    )
    internal val temperatureUiState = _temperatureUiState

    private val _evidenceListUiState: StateFlow<List<EvidenceState>> = combine(
        evidenceStates, difficultyState
    ) { evidenceStates, difficultyState ->
        val settings = difficultyState.settings.equipmentPermission

        val revokedIdentifiers = settings
            .filter { it.permission == Permission.REVOKED && it.quantity == EquipmentPermission.ALL }
            .map { it.identifier }

        evidenceStates
            .map {
                val identifier = it.evidence.id.toEquipmentIdentifier()
                val enabled = identifier !in revokedIdentifiers
                it.copy(
                    state = if(!enabled) { EvidenceValidationType.NEUTRAL } else { it.state },
                    enabled = enabled
                )
            }.sortedBy { it.evidence.id.ordinal }
            .sortedByDescending { it.enabled }
    }
    .distinctUntilChanged()
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )
    val evidenceListUiState = _evidenceListUiState

    /* Ghost Order **/
    private val _ghostsSortedUiState: StateFlow<List<GhostState>> =
        combine(
            _ghostStates, preferences
        ) { ghostScores, preferences ->
            if(!preferences.enableGhostReorder) {
                ghostScores
            } else {
                ghostScores
                    .sortedWith(
                        compareByDescending<GhostState> { it.score }
                            .thenByDescending { it.bpmIsValid }
                            .thenByDescending {
                                val traitScore = it.traitScore

                                if(traitScore.reject > 0) {
                                    -traitScore.reject
                                } else {
                                    traitScore.confirm
                                }
                            }
                            .thenByDescending { it.traitScore.confirm }
                            .thenByDescending {
                                val traitScore = it.traitScore

                                if(traitScore.probableReject > 0) {
                                    -traitScore.probableReject
                                } else traitScore.probableConfirm
                            }
                    )

            }
        }
        .distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ghostEvidences.map { GhostState(ghostEvidence = it) }
        )
    internal val ghostsSortedUiState = _ghostsSortedUiState

    /* Operation Details */
    private val _operationDetailsUiState: StateFlow<OperationDetailsUiState> = combine(
        mapState,
        difficultyState,
        ghostStates,
        difficultyOverridesState
    ) {
            mapState, difficultyState, ghostStates, difficultyOverrideState ->

            OperationDetailsUiState(
                weatherDetails = OperationDetailsUiState.WeatherDetails(
                    weather = difficultyOverrideState.weather,
                ),
                mapDetails = OperationDetailsUiState.MapDetails(
                    name = mapState.name,
                    size = mapState.size,
                    modifiers = OperationDetailsUiState.MapDetails.MapModifiers(
                        action = mapState.actionModifier,
                        setup = mapState.setupModifier
                    )
                ),
                difficultyDetails = OperationDetailsUiState.DifficultyDetails(
                    type = difficultyState.type,
                    difficultyTitle = difficultyState.title,
                    responseType = difficultyState.responseType,
                    challengeTitle = difficultyState.challengeTitle,
                    settings = difficultyState.settings
                ),
                ghostDetails = OperationDetailsUiState.GhostDetails(
                    activeGhosts = ghostStates.let { ghostStates ->
                        val states = ghostStates.filter { it.score >= 0 && !it.manualRejection }
                        states.map { state ->
                            OperationDetailsUiState.GhostDetails.GhostDetail(
                                state = state
                            )
                        }
                    }
                )
            )
        }
        .distinctUntilChanged()
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = OperationDetailsUiState()
    )
    internal val operationDetailsUiState = _operationDetailsUiState

    internal val huntDurationTimerUiState = _huntDurationTimerState.map {
        val roundedRemaining = it.remaining.roundMillisToDuration(500L)
        NotchedProgressBarUiState(
            max = it.max,
            origin = it.origin,
            timeText = FormatterUtils.formatMillisToTime(roundedRemaining),
            remaining = roundedRemaining,
            notches = it.notches,
            running = it.running
        )
    }.distinctUntilChanged()
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = NotchedProgressBarUiState()
    )

    internal val huntCooldownTimerUiState = _huntCooldownTimerState.map {
        val roundedRemaining = it.remaining.roundMillisToDuration(500L)
        NotchedProgressBarUiState(
            max = it.max,
            origin = it.origin,
            timeText = FormatterUtils.formatMillisToTime(roundedRemaining),
            remaining = roundedRemaining,
            notches = it.notches,
            running = it.running
        )
    }.distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = NotchedProgressBarUiState()
        )

    internal val smudgeHuntProtectionTimerUiState = _smudgeHuntProtectionTimerState.map {
        val roundedRemaining = it.remaining.roundMillisToDuration(500L)
        NotchedProgressBarUiState(
            max = it.max,
            origin = it.origin,
            timeText = FormatterUtils.formatMillisToTime(roundedRemaining),
            remaining = roundedRemaining,
            notches = it.notches,
            running = it.running
        )
    }.distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = NotchedProgressBarUiState()
        )

    internal val fingerprintTimerUiState = _fingerprintTimerState.map {
        val roundedRemaining = it.remaining.roundMillisToDuration(500L)
        NotchedProgressBarUiState(
            max = it.max,
            origin = it.origin,
            timeText = FormatterUtils.formatMillisToTime(roundedRemaining),
            remaining = roundedRemaining,
            notches = it.notches,
            running = it.running
        )
    }.distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = NotchedProgressBarUiState()
        )

    /*
     * Timer Ui Functions
     */

    private fun launchOperationControllerJob() {
        _playerSanityState.update{
            it.copy(
                lastSanityTickTime = System.currentTimeMillis()
            )
        }
        _temperatureState.update{
            it.copy(
                lastUpdate = System.currentTimeMillis()
            )
        }
        operationControllerJob = viewModelScope.launch {
            while(!operationTimerState.value.paused) {
                tickSanity()
                tickTemperature()
                delay(1000)
            }
        }
    }

    private fun stopOperationControllerJob() {
        operationControllerJob?.cancel("Operation Controller Job Cancelled")
    }

    /*
     * Journal ------------------------
     */

    /** Resets the Ruling for each Evidence type
     * Depends on the difficulty configuration
     * */
    private fun resetJournal() {
        resetEvidenceStates()
        resetExplicitGhostRejects()
        resetTraitSelections()
    }

    /*
     * Player Sanity ---------------------------
     */

    /** The level can be between 0f and 1f. Levels outside those extremes are constrained. */
    private fun setPlayerSanity(
        value: Float
    ) {
        _playerSanityState.update {
            it.copy(
                insanityLevel = value.coerceIn(SanityLevel.MIN_SANITY, SanityLevel.MAX_SANITY),
                sanityLevel = (SanityLevel.MAX_SANITY - value)
                    .coerceIn(SanityLevel.MIN_SANITY, SanityLevel.MAX_SANITY)
            )
        }

    }
    private fun addPlayerSanity(
        value: Float
    ) {
        setPlayerSanity(_playerSanityState.value.insanityLevel - value)
    }
    private fun removePlayerSanity(
        value: Float
    ) {
        setPlayerSanity(_playerSanityState.value.insanityLevel + value)
    }

    private fun skipPlayerInsanity(
        newLevel: Float = SanityLevel.HALF_SANITY
    ) {
        val currentLevel = _playerSanityState.value.insanityLevel

        val normalizedLevel = newLevel.coerceAtLeast(currentLevel)

        setPlayerSanity(normalizedLevel)
    }

    private fun getBloodMoonMultiplier(): Float {
        return if (getCurrentWeather() == Weather.BLOOD_MOON) 2f else 1f
    }

    /**
     * Reduces player sanity level each doTick. Sanity cannot drop below 50% if the clock still has
     * time remaining.
     */
    private fun tickSanity() {
        val currentTime = System.currentTimeMillis()
        val deltaTime = if (_playerSanityState.value.lastSanityTickTime > 0)
            currentTime - _playerSanityState.value.lastSanityTickTime else 0L

        _playerSanityState.update {
            it.copy(
                lastSanityTickTime = currentTime
            )
        }

        if (deltaTime > 0) {
            val drainModifier = operationSanityUiState.value.drainModifier
            val multiplier = 0.00001f
            val bloodMoonMultiplier = getBloodMoonMultiplier()

            val deltaDrain = deltaTime * drainModifier * multiplier * bloodMoonMultiplier

            val currentInsanity = _playerSanityState.value.insanityLevel
            setPlayerSanity(currentInsanity + deltaDrain)
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
        val startingInsanity = SanityLevel.MAX_SANITY -
                difficultyState.value.settings.startingSanity.toFloat()
        setPlayerSanity(startingInsanity)
    }

    /*
     * Temperature
     */
    private fun tickTemperature() {
        val currentTime = System.currentTimeMillis()
        val lastTickTime = _temperatureState.value.lastUpdate
        val deltaTime = if (lastTickTime > 0)
            currentTime - lastTickTime else 0L

        if (deltaTime > 0) {
            val fuseBoxOn = _difficultyOverridesState.value.fuseBox == FuseBoxFlag.FUSEBOX_ENABLED
            val currentTemp = _temperatureState.value.current
            val weatherRange = getCurrentWeather().toTemperatureRange()

            val targetTemp = if (fuseBoxOn)
                Temperature.TEMPERATURE_START_FUSEBOX_ENABLED
            else
                weatherRange.low

            val isHeating = currentTemp < targetTemp
            val temperatureChangeModifier = if (isHeating)
                TEMPERATURE_HEATING_RATE
            else
                TEMPERATURE_COOLING_RATE

            val deltaDrain = (deltaTime / 1000f) * temperatureChangeModifier

            updateTemperature(deltaDrain, currentTime)
        }
    }

    /*
     * Phase ---------------------------
     */

    private fun resetPhase() {
        _phaseState.update {
            it.copy(
                startFlashTime = DEFAULT,
                elapsedFlashTime = System.currentTimeMillis() - it.startFlashTime
            )
        }
    }

    private fun resetWeatherOverride() {
        _difficultyOverridesState.update { it.copy(weather = Weather.RANDOM) }
    }

    private fun resetToolTimers() {
        _huntDurationTimerState.update { it.copy(remaining = it.max, running = false) }
        _huntCooldownTimerState.update { it.copy(remaining = it.max, running = false) }
        _smudgeHuntProtectionTimerState.update { it.copy(remaining = it.max, running = false) }
        _fingerprintTimerState.update { it.copy(remaining = it.max, running = false) }
    }

    /*
     * Tool Timers ---------------------------
     */

    private fun launchToolTimersJob() {
        toolTimersJob = viewModelScope.launch {

            while (!_toolsTimerState.value.paused) {

                val delay = 500L
                val preDelay = System.currentTimeMillis()
                delay(delay)
                val postDelay = System.currentTimeMillis()
                val actualDelay = postDelay - preDelay

                tickToolTimers(actualDelay)
            }
        }
    }

    private fun stopToolTimersJob() {
        _toolsTimerState.update {
            it.copy(
                paused = true
            )
        }
        toolTimersJob?.cancel("Timer Job Cancelled")
    }

    private fun tickToolTimers(delay: Long) {
        if(_huntDurationTimerState.value.running) {
            _huntDurationTimerState.update {
                it.copy(
                    remaining = (it.remaining - delay).coerceAtLeast(0L),
                    running = (it.remaining) >= 0L
                )
            }
        }
        if(_huntCooldownTimerState.value.running) {
            _huntCooldownTimerState.update {
                it.copy(
                    remaining = (it.remaining - delay).coerceAtLeast(0L),
                    running = (it.remaining) >= 0L
                )
            }
        }
        if(_smudgeHuntProtectionTimerState.value.running) {
            _smudgeHuntProtectionTimerState.update {
                it.copy(
                    remaining = (it.remaining - delay).coerceAtLeast(0L),
                    running = (it.remaining) >= 0L
                )
            }
        }
        if(_fingerprintTimerState.value.running) {
            _fingerprintTimerState.update {
                it.copy(
                    remaining = (it.remaining - delay).coerceAtLeast(0L),
                    running = (it.remaining) >= 0L
                )
            }
        }
    }

    /*
     * Operation Timer ---------------------------
     */

    private fun launchOperationTimerJob() {
        operationTimerJob = viewModelScope.launch {

            while (!operationTimerState.value.paused) {

                val delay = 100L
                val preDelay = System.currentTimeMillis()
                delay(delay)
                val postDelay = System.currentTimeMillis()
                val actualDelay = postDelay - preDelay

                val remaining = operationTimerState.value.remainingTime
                setOperationTimerRemainingTime((remaining - actualDelay)
                    .coerceAtLeast(0L))
            }
        }
    }

    private fun stopOperationTimerJob() {
        operationTimerJob?.cancel("Operation Timer Job Cancelled")
    }

    private fun setOperationTimerRemainingTime(value: Long) {
        _operationTimerState.update {
            it.copy(
                remainingTime = value
            )
        }
    }

    private fun playOperationTimer() {
        _operationTimerState.update {
            val startTime =
                if (it.startTime == TIME_DEFAULT) System.currentTimeMillis()
                else System.currentTimeMillis() -
                        getDurationByProgress(_playerSanityState.value.insanityLevel)

            val remainingTime =
                if (it.remainingTime == TIME_DEFAULT)
                    difficultyState.value.settings.setupTime.toLong()
                else it.remainingTime

            it.copy(
                startTime = startTime,
                remainingTime = remainingTime,
                paused = false
            )
        }
    }

    private fun pauseOperationTimer() {
        _operationTimerState.update {
            it.copy(
                paused = true
            )
        }
    }

    private fun resetOperationTimer() {
        _operationTimerState.update {
            SanityTimerData(
                startTime = TIME_DEFAULT,
                remainingTime = difficultyState.value.settings.setupTime.toLong(),
                paused = true
            )
        }
    }

    /**
     * MVI UI Events
     */

    private fun setBpmData(data: RealtimeUiState<GraphPoint>) {
        _bpmToolState.update {
            it.copy(realtimeState = data)
        }
    }

    private fun setBpmMeasurementType(type: VisualizerMeasurementType) {
        _bpmToolState.update {
            it.copy(measurementType = type)
        }
    }

    private fun toggleApplyBpmMeasurement() {
        _bpmToolState.update {
            it.copy(applyMeasurement = !it.applyMeasurement)
        }
    }

    private fun updateTraitFilter(filter: TraitFilter) {
        _traitFilterUiState.update {
            it.copy(
                category = if(it.category == filter.category) null else filter.category,
                weight = if(it.weight == filter.weight) null else filter.weight,
                state = if(it.state == filter.state) null else filter.state,
                tags = if(it.tags == filter.tags) emptyList() else filter.tags
            )
        }
    }

    private fun toggleUniqueOnly() {
        _traitFilterUiState.update {
            it.copy(
                uniqueOnly = !it.uniqueOnly
            )
        }
    }

    private fun setWeatherOverride(weather: Weather) {
        _difficultyOverridesState.update {
            it.copy (
                weather = weather
            )
        }
        resetTemperature()
    }
    private fun setFuseBoxOverride(state: FuseBoxFlag) {
        _difficultyOverridesState.update {
            it.copy(
                fuseBox =
                    when(difficultyState.value.settings.fuseBoxAtStartOfContract) {
                        FuseBoxAtStartOfContract.BROKEN -> FuseBoxFlag.FUSEBOX_DISABLED
                        else -> state
                    }
            )
        }
    }
    private fun toggleFuseBoxOverride() {
        _difficultyOverridesState.update {
            it.copy(
                fuseBox =
                    when(difficultyState.value.settings.fuseBoxAtStartOfContract) {
                        FuseBoxAtStartOfContract.BROKEN -> FuseBoxFlag.FUSEBOX_DISABLED
                        else -> {
                            when(it.fuseBox) {
                                FuseBoxFlag.FUSEBOX_ENABLED -> FuseBoxFlag.FUSEBOX_DISABLED
                                else -> FuseBoxFlag.FUSEBOX_ENABLED
                            }
                        }
                    }
            )
        }
    }

    private fun incrementMapIndex() {
        try {
            val newIndex = incrementSimpleMapIndexUseCase(
                mapState.value.index).getOrThrow()
            updateMap(newIndex)
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun decrementMapIndex() {
        try {
            val newIndex = decrementSimpleMapIndexUseCase(
                mapState.value.index).getOrThrow()
            updateMap(newIndex)
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun setMapIndex(index: Int) {
        updateMap(index)
    }

    private fun incrementDifficultyIndex() =
        incrementDifficultyIndexUseCase(difficultyState.value.index)
            .getOrNull()?.let { index ->
                setDifficultyIndex(index)
            }

    private fun decrementDifficultyIndex() =
        decrementDifficultyIndexUseCase(difficultyState.value.index)
            .getOrNull()?.let { index ->
                setDifficultyIndex(index)
            }

    private fun setDifficultyIndex(newIndex: Int) {
        setDifficultyIndexUseCase(newIndex)
            .onSuccess {
                updateDifficulty(newIndex)
            }
            .onFailure {
                Log.e("InvestigationViewModel", "Set Difficulty Index failed.")
            }
    }

    private fun setCustomDifficultyIndex(newIndex: Int) {
        updateDifficulty(
            difficultyIndex = difficultyState.value.index,
            customIndex = newIndex
        )
    }

    private fun incrementCustomDifficultyIndex() {
        val names = customDifficultyConfigUiState.value.options
        if (names.isNotEmpty()) {
            val currentIndex = customDifficultyConfigUiState.value.selectedIndex
            val nextIndex = (currentIndex + 1) % names.size
            setCustomDifficultyIndex(nextIndex)
        }
    }

    private fun decrementCustomDifficultyIndex() {
        val names = customDifficultyConfigUiState.value.options
        if (names.isNotEmpty()) {
            val currentIndex = customDifficultyConfigUiState.value.selectedIndex
            val nextIndex = if (currentIndex <= 0) names.size - 1 else currentIndex - 1
            setCustomDifficultyIndex(nextIndex)
        }
    }

    private fun setWeather(weather: Weather) {
        setWeatherOverride(weather)
    }

    private fun onPlayerDeath() {
        removePlayerSanity(SANITY_LOSS_ON_PLAYER_DEATH)
    }
    private fun onUseSanityMedication() {
        addPlayerSanity(difficultyState.value.settings.sanityPillRestoration.toFloat())
    }

    private fun setEvidenceRuling(
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
    }

    private fun toggleExplicitNegation(ghostModel: Ghost) {
        _explicitGhostRejects.update { rejections ->
            if (ghostModel.id in rejections) rejections - ghostModel.id
            else rejections + ghostModel.id
        }
    }

    private fun toggleTraitSelection(
        trait: ValidatedGhostTrait
    ) {
        val newTraits = selectedTraits.value.map {
            if(it.ghostTrait.id == trait.ghostTrait.id) {
                it.copy(
                    validationType = when(it.validationType) {
                        TraitValidationType.CONFIRMED -> TraitValidationType.NEUTRAL
                        TraitValidationType.NEUTRAL -> TraitValidationType.CONFIRMED
                    }
                )
            } else { it }
        }
        _selectedTraits.update {
            newTraits
        }
    }

    private fun toggleOperationTimer() {
        if (operationTimerState.value.paused) {
            playOperationTimer()
        } else {
            pauseOperationTimer()
        }
    }

    private fun skipOperationTimer() {

        val currentLevel = _playerSanityState.value.sanityLevel

        val startingSanity = difficultyState.value.settings.startingSanity.toFloat()
        val huntThreshold = SanityLevel.HALF_SANITY

        val target = min(startingSanity, huntThreshold)

        val newLevel = currentLevel.coerceAtMost(target)

        Log.d("InvestigationViewModel", "$startingSanity:$huntThreshold -> $target = $currentLevel -> $newLevel")

        playOperationTimer()
        skipPlayerInsanity(newLevel)
        _operationTimerState.update {
            it.copy(
                startTime = TIME_DEFAULT,
                remainingTime = TIME_DEFAULT,
                paused = false
            )
        }
    }

    private fun triggerToolTimer(type: ToolTimerType) {
        when(type) {
            ToolTimerType.HUNT_DURATION -> _huntDurationTimerState.toggleTimer()
            ToolTimerType.HUNT_COOLDOWN -> _huntCooldownTimerState.toggleTimer()
            ToolTimerType.SMUDGE_TIMER -> _smudgeHuntProtectionTimerState.toggleTimer()
            ToolTimerType.UV_EVIDENCE_DURATION -> _fingerprintTimerState.toggleTimer()
        }
    }

    private fun toggleToolbarState() {
        _operationToolbarUiState.update {
            it.copy(
                isCollapsed = !it.isCollapsed
            )
        }
    }

    private fun setToolbarCategory(category: OperationToolbarUiState.Category) {
        if(operationToolbarUiState.value.category == category) {
            _operationToolbarUiState.update {
                val isCollapsed = !it.isCollapsed

                it.copy(
                    isCollapsed = isCollapsed,
                    category = if(!isCollapsed)
                        category
                    else
                        OperationToolbarUiState.Category.TOOL_NONE
                )
            }
        } else {
            _operationToolbarUiState.update {
                it.copy(
                    isCollapsed = false,
                    category = category
                )
            }
        }
    }

    private fun setPopup(
        evidenceType: EvidenceType
    ) {

        try {
            val evidence = getEvidenceUseCase(evidenceType).getOrThrow()
            val equipmentList = fetchCodexEquipmentUseCase().getOrThrow()

            val equipmentId = evidence.id.toEquipmentIdentifier()
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

    private fun setPopup(
        ghostIdentifier: GhostResources.GhostIdentifier
    ) {
        try {
            val ghost = getGhostUseCase(ghostIdentifier).getOrThrow()

            val popupRecord = GhostPopupRecord(
                id = ghost.id,
                name = ghost.name,
                icon = ghost.icon,
                info = ghost.info,
                strengthData = ghost.strengthData,
                weaknessData = ghost.weaknessData,
                huntData = ghost.huntData,
                sanityBounds = ghost.huntSanityBounds,
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

    private fun clearPopup() {
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

    private fun reset() {
        resetJournal()
        resetOperationTimer()
        resetPhase()
        resetSanity()
        resetWeatherOverride()
        resetTemperature()
        resetToolTimers()
    }

    fun onEvent(event: InvestigationEvent) {
        when (event) {
            // Configuration
            is InvestigationEvent.IncrementMap -> incrementMapIndex()
            is InvestigationEvent.DecrementMap -> decrementMapIndex()
            is InvestigationEvent.SetMap -> setMapIndex(event.index)
            is InvestigationEvent.IncrementDifficulty -> incrementDifficultyIndex()
            is InvestigationEvent.DecrementDifficulty -> decrementDifficultyIndex()
            is InvestigationEvent.SetDifficulty -> setDifficultyIndex(event.index)
            is InvestigationEvent.IncrementCustomDifficulty -> incrementCustomDifficultyIndex()
            is InvestigationEvent.DecrementCustomDifficulty -> decrementCustomDifficultyIndex()
            is InvestigationEvent.SetCustomDifficulty -> setCustomDifficultyIndex(event.index)
            is InvestigationEvent.SetWeather -> setWeather(event.weather)
            is InvestigationEvent.SetWeatherOverride -> setWeatherOverride(event.weather)
            is InvestigationEvent.ToggleFuseBoxOverride -> toggleFuseBoxOverride()

            // Sanity Tracking
            is InvestigationEvent.PlayerDeath -> onPlayerDeath()
            is InvestigationEvent.UseSanityMedication -> onUseSanityMedication()
            is InvestigationEvent.SetPlayerSanity -> setPlayerSanity(event.value)
            is InvestigationEvent.SetEvidence -> setEvidenceRuling(event.type, event.state)
            is InvestigationEvent.ToggleGhostNegation -> toggleExplicitNegation(event.ghost)

            // Trait Logic
            is InvestigationEvent.ToggleTrait -> toggleTraitSelection(event.trait)
            is InvestigationEvent.SetTraitFilter -> updateTraitFilter(event.filter)
            is InvestigationEvent.ToggleUniqueTraitFilter -> toggleUniqueOnly()

            // BPM Ui
            is InvestigationEvent.SetBpmData -> setBpmData(event.data)
            is InvestigationEvent.SetBpmMeasurementType -> setBpmMeasurementType(event.type)
            is InvestigationEvent.ToggleApplyBpmMeasurement -> toggleApplyBpmMeasurement()

            // Timers
            is InvestigationEvent.ToggleOperationTimer -> toggleOperationTimer()
            is InvestigationEvent.SkipOperationTimer -> skipOperationTimer()
            is InvestigationEvent.TriggerToolTimer -> triggerToolTimer(event.type)

            // UI Navigation/Popups
            is InvestigationEvent.ToggleToolbar -> toggleToolbarState()
            is InvestigationEvent.SetToolbarCategory -> setToolbarCategory(event.category)
            is InvestigationEvent.ShowEvidencePopup -> setPopup(event.type)
            is InvestigationEvent.ShowGhostPopup -> setPopup(event.id)
            is InvestigationEvent.ClearPopup -> clearPopup()

            // Lifecycle
            is InvestigationEvent.ResetInvestigation -> reset()
        }
    }

    private fun observeOperationTimer() {
        _operationTimerState
            .map { it.paused }
            .distinctUntilChanged()
            .onEach { paused ->
                if (!paused) {
                    launchOperationTimerJob()
                    launchOperationControllerJob()
                } else {
                    stopOperationTimerJob()
                    stopOperationControllerJob()
                }
            }.launchIn(viewModelScope)
    }

    private fun observeToolTimers() {
        combine(
            listOf(
                _huntDurationTimerState,
                _huntCooldownTimerState,
                _smudgeHuntProtectionTimerState,
                _fingerprintTimerState
            )
        ) { states -> states.any { it.running } }
            .distinctUntilChanged()
            .onEach { shouldRun ->
                if (shouldRun) {
                    _toolsTimerState.update { it.copy(paused = false) }
                    launchToolTimersJob()
                } else stopToolTimersJob()
            }.launchIn(viewModelScope)
    }

    private fun observeToolTimerSettings() {
        combine(
            difficultyState,
            mapState
        ) { difficulty, map ->
            difficulty.settings to map.size
        }
            .distinctUntilChanged()
            .onEach { (settings, mapSize) ->
                val huntDuration = settings.huntDuration.toLong(mapSize)
                _huntDurationTimerState.update {
                    it.copy(
                        max = huntDuration,
                        remaining = huntDuration
                    )
                }

                val fingerprintDuration = settings.fingerprintDuration.toLong()
                _fingerprintTimerState.update {
                    it.copy(
                        max = fingerprintDuration,
                        remaining = fingerprintDuration,
                        notches = listOf(
                            ProgressBarNotch(
                                UiText.StringResource(GhostTitle.OBAKE.toStringResource()),
                                (fingerprintDuration * .5f).toLong()
                            ),
                            ProgressBarNotch(
                                UiText.DynamicString("Normal"),
                                fingerprintDuration
                            )
                        )
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun observeCustomDifficulty() {
        getCustomDifficultiesUseCase()
            .onEach { customDifficulties ->
                val currentDifficulty = difficultyState.value
                if (currentDifficulty.type == DifficultyType.CUSTOM) {
                    val custom = currentDifficulty.customIndex?.let { customDifficulties.getOrNull(it) }
                        ?: customDifficulties.firstOrNull()
                    custom?.let {
                        if (currentDifficulty.settings != it.settings) {
                            updateInvestigationDifficultyUseCase(
                                currentDifficulty.copy(settings = it.settings)
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeDifficulty() {
        difficultyState
            .onEach { difficulty ->

                _playerSanityState.update {
                    it.copy(
                        sanityLevel = difficulty.settings.startingSanity.toFloat(),
                        insanityLevel = 1f - difficulty.settings.startingSanity.toFloat()
                    )
                }

                _phaseState.update {
                    it.copy(
                        canAlertAudio = false
                    )
                }

                val fuseState = if (difficulty.settings.fuseBoxAtStartOfContract ==
                    FuseBoxAtStartOfContract.ON) FuseBoxFlag.FUSEBOX_ENABLED
                else FuseBoxFlag.FUSEBOX_DISABLED

                setFuseBoxOverride(fuseState)
                resetOperationTimer()
                resetTemperature()

            }.launchIn(viewModelScope)
    }

    private fun observeConfigOverrides() {
        // Handled by individual setters or other observers
    }

    init {
        updateMap()
        updateDifficulty()
        reset()

        observeCustomDifficulty()
        observeDifficulty()
        observeConfigOverrides()
        observeOperationTimer()
        observeToolTimers()
        observeToolTimerSettings()
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
                    val investigationUseCaseBundle = container.investigationUseCaseBundle
                    val preferencesUseCaseBundle = container.preferencesUseCaseBundle
                    val challengesUseCaseBundle = container.challengesUseCaseBundle

                    InvestigationScreenViewModel(
                        journalUseCaseBundle = journalUseCaseBundle,
                        difficultyUseCaseBundle = difficultyUseCaseBundle,
                        simpleMapUseCaseBundle = simpleMapUseCaseBundle,
                        codexUseCaseBundle = codexUseCaseBundle,
                        investigationUseCaseBundle = investigationUseCaseBundle,
                        preferencesUseCaseBundle = preferencesUseCaseBundle,
                        challengesUseCaseBundle = challengesUseCaseBundle
                    )
                }
            }
    }

    sealed class InvestigationEvent {
        // Configuration Events
        object IncrementMap : InvestigationEvent()
        object DecrementMap : InvestigationEvent()
        data class SetMap(val index: Int) : InvestigationEvent()
        object IncrementDifficulty : InvestigationEvent()
        object DecrementDifficulty : InvestigationEvent()
        data class SetDifficulty(val index: Int) : InvestigationEvent()
        object IncrementCustomDifficulty : InvestigationEvent()
        object DecrementCustomDifficulty : InvestigationEvent()
        data class SetCustomDifficulty(val index: Int) : InvestigationEvent()
        data class SetWeather(val weather: Weather) : InvestigationEvent()
        data class SetWeatherOverride(val weather: Weather) : InvestigationEvent()
        object ToggleFuseBoxOverride : InvestigationEvent()

        // Sanity Tracker Events
        object PlayerDeath : InvestigationEvent()
        object UseSanityMedication : InvestigationEvent()
        data class SetPlayerSanity(val value: Float) : InvestigationEvent()

        // Journal Events
        data class SetEvidence(val type: EvidenceType, val state: EvidenceValidationType) : InvestigationEvent()
        data class ToggleGhostNegation(val ghost: Ghost) : InvestigationEvent()

        // Trait Logic Events
        data class ToggleTrait(val trait: ValidatedGhostTrait) : InvestigationEvent()
        data class SetTraitFilter(val filter: TraitFilter) : InvestigationEvent()
        object ToggleUniqueTraitFilter : InvestigationEvent()

        data class SetBpmData(val data: RealtimeUiState<GraphPoint>) : InvestigationEvent()
        data class SetBpmMeasurementType(val type: VisualizerMeasurementType) : InvestigationEvent()
        object ToggleApplyBpmMeasurement : InvestigationEvent()

        // Timer Events
        object ToggleOperationTimer : InvestigationEvent()
        object SkipOperationTimer : InvestigationEvent()
        data class TriggerToolTimer(val type: ToolTimerType) : InvestigationEvent()

        // UI State Events
        object ToggleToolbar : InvestigationEvent()
        data class SetToolbarCategory(val category: OperationToolbarUiState.Category) : InvestigationEvent()
        data class ShowEvidencePopup(val type: EvidenceType) : InvestigationEvent()
        data class ShowGhostPopup(val id: GhostResources.GhostIdentifier) : InvestigationEvent()
        object ClearPopup : InvestigationEvent()

        // System Events
        object ResetInvestigation : InvestigationEvent()
    }

    private data class PlayerSanityData(
        val insanityLevel: Float = 0.0f,
        val sanityLevel: Float = 1.0f,
        val lastSanityTickTime: Long = 0L
    ) {
        val isInsane: Boolean
            get() = sanityLevel < SAFE_MIN_BOUNDS

    }

    internal data class CustomDifficultyConfigUiState(
        val name: String = "",
        val options: List<String> = emptyList(),
        val selectedIndex: Int = 0,
        val isVisible: Boolean = false
    )
}
