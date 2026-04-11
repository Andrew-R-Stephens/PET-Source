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
import com.tritiumgaming.feature.investigation.ui.TimerUiState.Companion.TIME_DEFAULT
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.journal.ghost.item.GhostState
import com.tritiumgaming.feature.investigation.ui.popups.JournalPopupUiState
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.configs.DifficultyConfigUiState
import com.tritiumgaming.feature.investigation.ui.tool.configs.MapConfigUiState
import com.tritiumgaming.feature.investigation.ui.tool.footstep.BpmToolUiState
import com.tritiumgaming.feature.investigation.ui.tool.footstep.visualizer.VisualizerMeasurementType
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbarUiState
import com.tritiumgaming.shared.data.challenge.usecase.GetCurrentChallengeUseCase
import com.tritiumgaming.shared.data.codex.usecase.FetchEquipmentTypesUseCase
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.shared.data.difficulty.usecase.DecrementDifficultyIndexUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.FetchDifficultiesUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.IncrementDifficultyIndexUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.SetDifficultyIndexUseCase
import com.tritiumgaming.shared.data.difficultysetting.dto.EquipmentPermission
import com.tritiumgaming.shared.data.difficultysetting.dto.EquipmentPermission.Permission
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.difficultysetting.mapper.toFloat
import com.tritiumgaming.shared.data.difficultysetting.mapper.toLong
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.evidence.usecase.GetEquipmentTypeByEvidenceTypeUseCase
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.ghost.model.Ghost
import com.tritiumgaming.shared.data.ghost.model.GhostType
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
import com.tritiumgaming.shared.data.investigation.model.OperationConditionsData
import com.tritiumgaming.shared.data.investigation.model.PhaseData
import com.tritiumgaming.shared.data.investigation.model.PhaseData.Companion.DEFAULT
import com.tritiumgaming.shared.data.investigation.model.PhaseData.Companion.DURATION_30_SECONDS
import com.tritiumgaming.shared.data.investigation.model.SanityTimerData
import com.tritiumgaming.shared.data.investigation.model.SanityTimerData.Companion.TIME_MIN
import com.tritiumgaming.shared.data.investigation.model.StateOption
import com.tritiumgaming.shared.data.investigation.model.TagOption
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
import com.tritiumgaming.shared.data.journal.usecase.GetGhostTypeByIdUseCase
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.min

class InvestigationScreenViewModel private constructor(
    journalUseCaseBundle: JournalUseCaseBundle,
    private val getEvidenceUseCase: GetEvidenceUseCase = journalUseCaseBundle.getEvidenceUseCase,
    private val fetchEvidenceTypesUseCase: FetchEvidenceTypesUseCase = journalUseCaseBundle.fetchEvidenceTypesUseCase,
    private val getGhostUseCase: GetGhostUseCase = journalUseCaseBundle.getGhostUseCase,
    private val getGhostTypeByIdUseCase: GetGhostTypeByIdUseCase = journalUseCaseBundle.getGhostTypeByIdUseCase,
    private val fetchGhostEvidencesUseCase: FetchGhostEvidencesUseCase = journalUseCaseBundle.fetchGhostEvidencesUseCase,
    private val getAllGhostTraitsUseCase: GetAllGhostTraitsUseCase = journalUseCaseBundle.getAllGhostTraitsUseCase,
    difficultyUseCaseBundle: DifficultyUseCaseBundle,
    private val fetchDifficultiesUseCase: FetchDifficultiesUseCase = difficultyUseCaseBundle.fetchDifficultiesUseCase,
    private val incrementDifficultyIndexUseCase: IncrementDifficultyIndexUseCase = difficultyUseCaseBundle.incrementDifficultyIndexUseCase,
    private val decrementDifficultyIndexUseCase: DecrementDifficultyIndexUseCase = difficultyUseCaseBundle.decrementDifficultyIndexUseCase,
    private val setDifficultyIndexUseCase: SetDifficultyIndexUseCase = difficultyUseCaseBundle.setDifficultyIndexUseCase,
    simpleMapUseCaseBundle: SimpleMapUseCaseBundle,
    private val fetchSimpleMapsUseCase: FetchSimpleMapsUseCase = simpleMapUseCaseBundle.fetchSimpleMapsUseCase,
    private val incrementSimpleMapIndexUseCase: IncrementSimpleMapIndexUseCase = simpleMapUseCaseBundle.incrementSimpleMapIndexUseCase,
    private val decrementSimpleMapIndexUseCase: DecrementSimpleMapIndexUseCase = simpleMapUseCaseBundle.decrementSimpleMapIndexUseCase,
    private val getSimpleMapNameUseCase: GetSimpleMapNameUseCase = simpleMapUseCaseBundle.getSimpleMapNameUseCase,
    private val getSimpleMapSizeUseCase: GetSimpleMapSizeUseCase = simpleMapUseCaseBundle.getSimpleMapSizeUseCase,
    private val getSimpleMapModifierUseCase: GetSimpleMapModifierUseCase = simpleMapUseCaseBundle.getSimpleMapModifierUseCase,
    private val fetchSimpleMapModifiersUseCase: FetchSimpleMapModifiersUseCase = simpleMapUseCaseBundle.fetchSimpleMapModifiersUseCase,
    codexUseCaseBundle: CodexUseCaseBundle,
    private val fetchCodexEquipmentUseCase: FetchEquipmentTypesUseCase = codexUseCaseBundle.fetchCodexEquipmentUseCase,
    private val getEquipmentTypeByEvidenceTypeUseCase: GetEquipmentTypeByEvidenceTypeUseCase = codexUseCaseBundle.getEquipmentTypeByEvidenceTypeUseCase,
    investigationUseCaseBundle: InvestigationUseCaseBundle,
    private val getInvestigationStateUseCase: GetInvestigationStateUseCase = investigationUseCaseBundle.getInvestigationStateUseCase,
    private val updateInvestigationMapUseCase: UpdateInvestigationMapUseCase = investigationUseCaseBundle.updateInvestigationMapUseCase,
    private val updateInvestigationDifficultyUseCase: UpdateInvestigationDifficultyUseCase = investigationUseCaseBundle.updateInvestigationDifficultyUseCase,
    preferencesUseCaseBundle: PreferencesUseCaseBundle,
    initFlowUserPreferencesUseCase: InitFlowUserPreferencesUseCase = preferencesUseCaseBundle.initFlowUserPreferencesUseCase,
    private val getCurrentChallengeUseCase: GetCurrentChallengeUseCase
) : ViewModel() {

    private val _preferences: StateFlow<InvestigationScreenUserPreferences> =
        initFlowUserPreferencesUseCase()
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
    private val preferences = _preferences

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

    /*private val weather = fetchWeatherUseCase().let {
        it.exceptionOrNull()?.printStackTrace()
        try { it.getOrThrow() }
        catch (_: Exception) {
            emptyList()
        }
    }*/

    /*
     * Routines
     */
    private var sanityJob: Job? = null
    private var timerJob: Job? = null

    /**
     * ViewModel States
     */

    private val _investigationState = getInvestigationStateUseCase()

    private val _mapState = _investigationState.map { it.map }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = MapData()
        )
    private val mapState = _mapState

    private val _difficultyState = _investigationState.map { it.difficulty }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = DifficultyData()
        )
    val difficultyState = _difficultyState

    private val _operationTimerState = MutableStateFlow(SanityTimerData())
    private val operationTimerState = _operationTimerState.asStateFlow()

    private val _operationConditionsState = MutableStateFlow(OperationConditionsData())
    private val operationConditionsState = _operationConditionsState.asStateFlow()
    internal fun setWeatherOverride(weather: Weather) {
        _operationConditionsState.update {
            it.copy (
                weatherOverride = weather
            )
        }
    }

    data class WeatherUiState(
        val weather: Weather = Weather.RANDOM
    )

    private val _weatherUiState = combine(
        operationConditionsState,
        difficultyState
    ) { operationConditionsState, difficultyState ->
        val difficultyWeather = difficultyState.settings.weather
        val weatherOverride = operationConditionsState.weatherOverride

        val weather = if(difficultyWeather == Weather.RANDOM) { weatherOverride }
            else { difficultyWeather }

        WeatherUiState(
            weather = weather
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WeatherUiState()
    )
    internal val weatherUiState = _weatherUiState

    private val _phaseState = MutableStateFlow(PhaseData())
    private val phaseState = _phaseState.asStateFlow()

    /* Traits */
    private val _traitData: MutableStateFlow<List<GhostTrait>> =
        MutableStateFlow(
            try {
                getAllGhostTraitsUseCase().getOrThrow().map { it }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        )
    private val traitData = _traitData.asStateFlow()

    private val _selectedTraits = MutableStateFlow<List<ValidatedGhostTrait>>(emptyList())
    val selectedTraits = _selectedTraits.asStateFlow()
    fun toggleTraitSelection(
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
    private fun resetTraitSelections() {
        _selectedTraits.update {
            try {
                getAllGhostTraitsUseCase().getOrThrow().map {
                    ValidatedGhostTrait(ghostTrait = it) }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

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

    private val _operationConditionsUiState = _operationConditionsState.map {
        OperationDetailsUiState.WeatherDetails(
            weatherOverride = it.weatherOverride
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OperationDetailsUiState.WeatherDetails()
    )
    internal val operationConditionsUiState = _operationConditionsUiState

    private val _difficultyConfigUiState : StateFlow<DifficultyConfigUiState> = _difficultyState
        .map { state ->
            val name = difficulties[state.index].difficultyTitle

            DifficultyConfigUiState(
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

    private val _sanityTimerUiState: StateFlow<TimerUiState> = _operationTimerState.map {
        sanityTimerState ->
        TimerUiState(
            remainingTime = sanityTimerState.remainingTime,
            paused = sanityTimerState.paused
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TimerUiState()
    )
    internal val sanityTimerUiState = _sanityTimerUiState

    private val _playerSanityUiState = MutableStateFlow(PlayerSanityUiState())
    internal val playerSanityUiState = _playerSanityUiState.asStateFlow()

    fun useSanityMedication() {
        addPlayerSanity(difficultyState.value.settings.sanityPillRestoration.toFloat())
    }

    private val _phaseUiState = combine(
        phaseState, operationTimerState, playerSanityUiState, preferences
    ) { phaseState, timerUiState, playerSanityUiState, preferences ->
        val type =
            when {
                timerUiState.remainingTime > TIME_MIN -> PhaseIdentifier.SETUP
                playerSanityUiState.sanityLevel < SanityLevel.SAFE_MIN_BOUNDS -> PhaseIdentifier.HUNT
                else -> PhaseIdentifier.ACTION
            }

        val canFlash = playerSanityUiState.isInsane &&
                phaseState.elapsedFlashTime <= preferences.maxHuntWarnFlashTime

        OperationDetailsUiState.PhaseDetails(
            type = type,
            canFlash = canFlash,
            canAlertAudio = phaseState.canAlertAudio,
            startFlashTime = phaseState.startFlashTime,
            elapsedFlashTime = phaseState.elapsedFlashTime
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OperationDetailsUiState.PhaseDetails(
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
    internal val primaryToolbarUiState = _operationToolbarUiState.asStateFlow()

    private val _popupUiState = MutableStateFlow(JournalPopupUiState())
    internal val popupUiState = _popupUiState.asStateFlow()

    private val _bpmToolUiState = MutableStateFlow(BpmToolUiState())
    internal val bpmToolUiState = _bpmToolUiState.asStateFlow()

    private val _operationSanityUiState = combine(
        mapState,
        difficultyState,
        phaseUiState
    ) { mapState, difficultyState, phaseUiState ->
        val mapModifier = try {
            getSimpleMapModifierUseCase(
                mapState.size.ordinal,
                phaseUiState.type
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


    /* Trait States */
    private val _traitFilterUiState: MutableStateFlow<TraitFilter> = MutableStateFlow(
        TraitFilter()
    )
    val traitFilterUiState = _traitFilterUiState.asStateFlow()
    fun updateTraitFilter(filter: TraitFilter) {
        _traitFilterUiState.update {
            it.copy(
                category = if(it.category == filter.category) null else filter.category,
                weight = if(it.weight == filter.weight) null else filter.weight,
                state = if(it.state == filter.state) null else filter.state,
                tags = if(it.tags == filter.tags) emptyList() else filter.tags
            )
        }
    }
    fun toggleUniqueOnly() {
        _traitFilterUiState.update {
            it.copy(
                uniqueOnly = !it.uniqueOnly
            )
        }
    }

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

    /* EvidenceStates */
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

    private val _evidenceListUiState: StateFlow<List<EvidenceState>> = combine(
        evidenceStates, difficultyState
    ) { evidenceStates, difficultyState ->
        val settings = difficultyState.settings.equipmentPermission

        val revokedIdentifiers = settings
            .filter { it.permission == Permission.REVOKED && it.quantity == EquipmentPermission.ALL }
            .map { it.identifier }

        evidenceStates
            .map {
                val identifier = getEquipmentTypeByEvidenceTypeUseCase(it.evidence)
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

    /* Ghost States */
    private val _explicitRejections =
        MutableStateFlow<Set<GhostResources.GhostIdentifier>>(emptySet())
    internal fun toggleExplicitNegation(ghostModel: Ghost) {
        _explicitRejections.update { rejections ->
            if (ghostModel.id in rejections) rejections - ghostModel.id
            else rejections + ghostModel.id
        }
    }
    fun resetExplicitNegations() {
        _explicitRejections.update { emptySet() }
    }

    private val _ghostStates: StateFlow<List<GhostState>> = combine(
        evidenceStates,
        difficultyState,
        bpmToolUiState,
        _explicitRejections,
        selectedTraits
    ) { evidenceStates, difficultyState, bpmToolUiState, manualRejections, selectedTraits ->
        ghostEvidences.map { ghostEvidence ->
            val isManuallyRejected = ghostEvidence.ghost.id in manualRejections

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
        operationConditionsState
    ) {
            mapState, difficultyState, ghostStates, operationConditionsState ->

            OperationDetailsUiState(
                weatherDetails = OperationDetailsUiState.WeatherDetails(
                    weatherOverride = operationConditionsState.weatherOverride,
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

    /*
     * Timer Ui Functions
     */
    private fun initTimerUiState() {
        _operationTimerState.update {
            SanityTimerData(
                startTime = TIME_DEFAULT,
                remainingTime = difficultyState.value.settings.setupTime.toLong(),
                paused = true
            )
        }
    }

    /*
     * Player Sanity Ui Functions
     */
    private fun initPlayerSanityUiState() {
        _playerSanityUiState.update {
            PlayerSanityUiState(
                sanityLevel = difficultyState.value.settings.startingSanity.toFloat(),
                insanityLevel = 1f - difficultyState.value.settings.startingSanity.toFloat()
            )
        }
    }

    /*
     * Toolbar Ui Functions
     */
    internal fun toggleToolbarState() {
        _operationToolbarUiState.update {
            it.copy(
                isCollapsed = !it.isCollapsed
            )
        }
    }

    internal fun setToolbarCategory(category: OperationToolbarUiState.Category) {
        if(primaryToolbarUiState.value.category == category) {
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

    private fun launchPlayerSanityJob() {
        _playerSanityUiState.update{
            it.copy(
                lastSanityTickTime = System.currentTimeMillis()
            )
        }
        sanityJob = viewModelScope.launch {
            while(!operationTimerState.value.paused) {
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
        //resetGhostStates()
        resetExplicitNegations()
        resetTraitSelections()
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
        //updateGhostStates()
    }

    internal fun getRuledEvidence(
        evidenceModel: EvidenceType
    ): EvidenceState? {
        return evidenceStates.value.find { it.isEvidence(evidenceModel) }
    }

    /*
     * Player Sanity ---------------------------
     */

    /** The level can be between 0f and 1f. Levels outside those extremes are constrained. */
    internal fun setPlayerSanity(
        value: Float
    ) {
        _playerSanityUiState.update {
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
        setPlayerSanity(playerSanityUiState.value.insanityLevel - value)
    }
    private fun removePlayerSanity(
        value: Float
    ) {
        setPlayerSanity(playerSanityUiState.value.insanityLevel + value)
    }

    private fun skipPlayerInsanity(
        newLevel: Float = SanityLevel.HALF_SANITY
    ) {
        val currentLevel = playerSanityUiState.value.insanityLevel

        val normalizedLevel = newLevel.coerceAtLeast(currentLevel)

        setPlayerSanity(normalizedLevel)
    }

    /**
     * Reduces player sanity level each doTick. Sanity cannot drop below 50% if the clock still has
     * time remaining.
     */
    private fun tickSanity() {
        val currentTime = System.currentTimeMillis()
        val deltaTime = if (playerSanityUiState.value.lastSanityTickTime > 0)
            currentTime - playerSanityUiState.value.lastSanityTickTime else 0L

        _playerSanityUiState.update {
            it.copy(
                lastSanityTickTime = currentTime
            )
        }

        if (deltaTime > 0) {
            val drainModifier = operationSanityUiState.value.drainModifier
            val multiplier = 0.00001f
            val bloodMoonMultiplier =
                if(difficultyState.value.settings.weather == Weather.BLOOD_MOON ||
                    operationConditionsState.value.weatherOverride == Weather.BLOOD_MOON) 2f else 1f

            val deltaDrain = deltaTime * drainModifier * multiplier * bloodMoonMultiplier

            val currentInsanity = playerSanityUiState.value.insanityLevel
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

    /*
     * Timer ---------------------------
     */

    private fun launchTimerJob() {
        timerJob = viewModelScope.launch {

            while (!operationTimerState.value.paused) {

                val delay = 100L
                val preDelay = System.currentTimeMillis()
                delay(delay)
                val postDelay = System.currentTimeMillis()
                val actualDelay = postDelay - preDelay

                val remaining = operationTimerState.value.remainingTime
                setTimeRemaining((remaining - actualDelay).coerceAtLeast(0L))
            }
        }
    }

    private fun stopTimerJob() {
        _operationTimerState.update {
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
        _operationTimerState.update {
            it.copy(
                remainingTime = value
            )
        }
    }

    private fun initializeNewTimer() {
        _operationTimerState.update {
            val startTime =
                if (it.startTime == TIME_DEFAULT) System.currentTimeMillis()
                else System.currentTimeMillis() -
                        getDurationByProgress(playerSanityUiState.value.insanityLevel)

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

    private fun playTimer() {
        stopPlayerSanityJob()
        stopTimerJob()

        initializeNewTimer()

        launchTimerJob()
        launchPlayerSanityJob()
    }

    private fun pauseTimer() {
        stopTimerJob()
        stopPlayerSanityJob()
    }

    internal fun toggleTimer() {
        if (operationTimerState.value.paused) {
            playTimer()
        } else {
            pauseTimer()
        }
    }

    internal fun fastForwardTimer(time: Long) {
        pauseTimer()
        setTimeRemaining(time)
        skipPlayerInsanity(SanityLevel.HALF_SANITY)
        playTimer()
    }

    internal fun skipTimer() {

        val currentLevel = playerSanityUiState.value.sanityLevel

        val startingSanity = difficultyState.value.settings.startingSanity.toFloat()
        val huntThreshold = SanityLevel.HALF_SANITY

        val target = min(startingSanity, huntThreshold)

        val newLevel = currentLevel.coerceAtMost(target)

        Log.d("InvestigationViewModel", "$startingSanity:$huntThreshold -> $target = $currentLevel -> $newLevel")

        playTimer()
        skipPlayerInsanity(newLevel)
        _operationTimerState.update {
            it.copy(
                startTime = TIME_DEFAULT,
                remainingTime = TIME_DEFAULT,
                paused = false
            )
        }
    }

    private fun resetTimer() {
        stopTimerJob()
        initTimerUiState()
    }

    /*
     * Weather ---------------------------
     */
    internal fun incrementWeatherIndex() {
        /*incrementWeatherIndexUseCase(_difficultyState.value.index)
            .getOrNull()?.let { index ->
                setWeatherIndex(index)
            }*/
    }

    internal fun decrementWeatherIndex() {
        /*decrementWeatherIndexUseCase(_weatherState.value.index)
            .getOrNull()?.let { index ->
                setWeatherIndex(index)
            }*/
    }

    internal fun setWeather(weather: Weather) {
        _operationConditionsState.update {
            it.copy(
                weatherOverride = weather
            )
        }
    }

    private fun updateWeather(
        index: Int = 0
    ) {

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
            val difficulty = difficulties[index]
            val type = difficulty.type
            val difficultyTitle = difficulty.difficultyTitle
            val responseType = difficulty.responseType

            val settings = difficulty.settingsModel

            var difficultyState = DifficultyData(
                index = index,
                type = type,
                title = difficultyTitle,
                responseType = responseType,
                settings = settings
            )

            if(type == DifficultyType.CHALLENGE) {
                getCurrentChallengeUseCase().onSuccess {
                    val challengeTitle = it.challengeTitle
                    difficultyState = difficultyState.copy(
                        challengeTitle = challengeTitle,
                        settings = it.settingsModel
                    )

                    maps.indexOfFirst { map -> map.mapName == it.map }.let { index ->
                        if(index != -1) setMapIndex(index)
                    }

                }
            }

            updateInvestigationDifficultyUseCase(difficultyState)

            _playerSanityUiState.update {
                it.copy(
                    sanityLevel = _difficultyState.value.settings.startingSanity.toFloat(),
                    insanityLevel = 1f - _difficultyState.value.settings.startingSanity.toFloat()
                )
            }

            _phaseState.update {
                it.copy(
                    canAlertAudio = false
                )
            }

            setTimeRemaining(_difficultyState.value.settings.setupTime.toLong())
            resetTimer()

        } catch (e: Exception) {
            e.printStackTrace()

            Log.e("InvestigationViewModel", "Update DifficultyUiState failed.")
        }

        Log.d("InvestigationViewModel", "DifficultyUiState:" +
                "\n\tindex: ${_difficultyState.value.index}" +
                "\n\tname: ${_difficultyState.value.title}" +
                "\n\tmodifier: ${_difficultyState.value.settings.sanityDrainSpeed.toFloat()}" +
                "\n\ttime: ${_difficultyState.value.settings.setupTime.toLong()}" +
                "\n\tinitialSanity: ${_difficultyState.value.settings.startingSanity.toFloat()}" +
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

            val mapState = MapData(
                index = index,
                name = name,
                size = size,
                setupModifier = modifier.setupModifier,
                actionModifier = modifier.actionModifier
            )

            updateInvestigationMapUseCase(mapState)

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
                "\n\tnormalModifier: ${_mapState.value.actionModifier}")
    }

    /*
    * Footstep Visualizer
    */
    internal fun setBpmData(data: RealtimeUiState<GraphPoint>) {
        _bpmToolUiState.update {
            it.copy(realtimeState = data)
        }
    }

    internal fun setBpmMeasurementType(type: VisualizerMeasurementType) {
        _bpmToolUiState.update {
            it.copy(measurementType = type)
        }
    }

    internal fun toggleApplyBpmMeasurement() {
        _bpmToolUiState.update {
            it.copy(applyMeasurement = !it.applyMeasurement)
        }
    }

    init {
        updateMap(0)
        updateDifficulty(0)

        initTimerUiState()

        initPlayerSanityUiState()

        resetEvidenceStates()
        resetTraitSelections()
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
                    val getCurrentChallengeUseCase = container.getCurrentChallengeUseCase

                    InvestigationScreenViewModel(
                        journalUseCaseBundle = journalUseCaseBundle,
                        difficultyUseCaseBundle = difficultyUseCaseBundle,
                        simpleMapUseCaseBundle = simpleMapUseCaseBundle,
                        codexUseCaseBundle = codexUseCaseBundle,
                        investigationUseCaseBundle = investigationUseCaseBundle,
                        preferencesUseCaseBundle = preferencesUseCaseBundle,
                        getCurrentChallengeUseCase = getCurrentChallengeUseCase
                    )
                }
            }

    }

}
