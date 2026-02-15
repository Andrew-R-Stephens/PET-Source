package com.tritiumgaming.feature.investigation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.PointRecord
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.RealtimeUiState
import com.tritiumgaming.feature.investigation.app.container.InvestigationContainerProvider
import com.tritiumgaming.feature.investigation.app.container.JournalUseCaseBundle
import com.tritiumgaming.feature.investigation.ui.TimerUiState.Companion.DEFAULT
import com.tritiumgaming.feature.investigation.ui.TimerUiState.Companion.DURATION_30_SECONDS
import com.tritiumgaming.feature.investigation.ui.TimerUiState.Companion.TIME_DEFAULT
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item.GhostScore
import com.tritiumgaming.feature.investigation.ui.popups.JournalPopupUiState
import com.tritiumgaming.feature.investigation.ui.section.footstep.ToolbarSectionBpmVisualizerUiState
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
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence.Ruling
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
import com.tritiumgaming.shared.data.map.modifier.usecase.FetchMapModifiersUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetMapModifierUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetSimpleMapNormalModifierUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetSimpleMapSetupModifierUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.DecrementMapFloorIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.DecrementMapIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.FetchMapThumbnailsUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapIdUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapNameUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapSizeUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.IncrementMapFloorIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.IncrementMapIndexUseCase
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
    private val initRuledEvidenceUseCase: InitRuledEvidenceUseCase = journalUseCaseBundle.initRuledEvidenceUseCase,
    private val fetchGhostEvidencesUseCase: FetchGhostEvidencesUseCase = journalUseCaseBundle.fetchGhostEvidencesUseCase,
    private val fetchDifficultiesUseCase: FetchDifficultiesUseCase,
    private val getDifficultyNameUseCase: GetDifficultyNameUseCase,
    private val getDifficultyModifierUseCase: GetDifficultyModifierUseCase,
    private val getDifficultyTimeUseCase: GetDifficultyTimeUseCase,
    private val getDifficultyResponseTypeUseCase: GetDifficultyResponseTypeUseCase,
    private val getDifficultyInitialSanityUseCase: GetDifficultyInitialSanityUseCase,
    private val incrementDifficultyIndexUseCase: IncrementDifficultyIndexUseCase,
    private val decrementDifficultyIndexUseCase: DecrementDifficultyIndexUseCase,
    private val fetchSimpleMapsUseCase: FetchSimpleMapsUseCase,
    private val incrementMapIndexUseCase: IncrementMapIndexUseCase,
    private val decrementMapIndexUseCase: DecrementMapIndexUseCase,
    private val incrementMapFloorIndexUseCase: IncrementMapFloorIndexUseCase,
    private val decrementMapFloorIndexUseCase: DecrementMapFloorIndexUseCase,
    private val getSimpleMapIdUseCase: GetSimpleMapIdUseCase,
    private val getSimpleMapNameUseCase: GetSimpleMapNameUseCase,
    private val getSimpleMapSizeUseCase: GetSimpleMapSizeUseCase,
    private val getSimpleMapSetupModifierUseCase: GetSimpleMapSetupModifierUseCase,
    private val getSimpleMapNormalModifierUseCase: GetSimpleMapNormalModifierUseCase,
    private val getMapModifierUseCase: GetMapModifierUseCase,
    private val fetchMapModifiersUseCase: FetchMapModifiersUseCase,
    private val fetchMapThumbnailsUseCase: FetchMapThumbnailsUseCase,
    private val fetchCodexAchievementsUseCase: FetchAchievementTypesUseCase,
    private val fetchCodexPossessionsUseCase: FetchPossessionTypesUseCase,
    private val fetchCodexEquipmentUseCase: FetchEquipmentTypesUseCase,
    private val getAllowHuntWarnAudioUseCase: GetAllowHuntWarnAudioUseCase,
    private val getEnableGhostReorderUseCase: GetEnableGhostReorderUseCase,
    private val getEnableRTLUseCase: GetEnableRTLUseCase,
    private val getMaxHuntWarnFlashTimeUseCase: GetMaxHuntWarnFlashTimeUseCase,
    private val getEquipmentTypeByEvidenceTypeUseCase: GetEquipmentTypeByEvidenceTypeUseCase,
) : ViewModel() {

    private val ghostEvidences = fetchGhostEvidencesUseCase().let {
        it.exceptionOrNull()?.printStackTrace()
        try { it.getOrThrow() }
        catch (e: Exception) {
            e.printStackTrace()
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
    private val _mapUiState = MutableStateFlow(MapUiState())
    val mapUiState = _mapUiState.asStateFlow()

    private val _difficultyUiState = MutableStateFlow(DifficultyUiState())
    val difficultyUiState = _difficultyUiState.asStateFlow()

    private val _timerUiState = MutableStateFlow(TimerUiState())
    val timerUiState = _timerUiState.asStateFlow()

    private val _phaseUiState = MutableStateFlow(PhaseUiState())
    val phaseUiState = _phaseUiState.asStateFlow()

    private val _playerSanityUiState = MutableStateFlow(PlayerSanityUiState())
    val playerSanityUiState = _playerSanityUiState.asStateFlow()

    private val _toolbarUiState = MutableStateFlow(ToolbarUiState())
    val toolbarUiState = _toolbarUiState.asStateFlow()

    private val _popupUiState = MutableStateFlow(JournalPopupUiState())
    val popupUiState = _popupUiState.asStateFlow()

    private val _footstepVisualizerUiState = MutableStateFlow(
        ToolbarSectionBpmVisualizerUiState())
    val footstepVisualizerUiState = _footstepVisualizerUiState.asStateFlow()

    private val _operationSanityUiState = combine(
        mapUiState,
        difficultyUiState,
        phaseUiState
    ) { mapUiState, difficultyUiState, phaseUiState ->
        val mapModifier = try {
            getMapModifierUseCase(
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

    private val _ruledEvidence: MutableStateFlow<List<RuledEvidence>> =
        MutableStateFlow(emptyList())
    val ruledEvidence = _ruledEvidence.asStateFlow()
    private fun initRuledEvidence() {
        try {
            val evidence = initRuledEvidenceUseCase().getOrThrow()
            _ruledEvidence.update { evidence }
            updateGhostScores()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    /*private fun initRuledEvidence() {
        initRuledEvidenceUseCase()
            .onSuccess {
                _ruledEvidence.update { it }
                updateGhostScores()
            }
            .onFailure {
                it.printStackTrace()
            }
    }*/

    private val _ghostScores = MutableStateFlow(
        ghostEvidences.map { GhostScore(it) }
    )
    val ghostScores = _ghostScores.asStateFlow()
    private fun initGhostScores() {
        _ghostScores.update {
            ghostEvidences.map { GhostScore(it) }
        }
    }
    private fun updateGhostScores() {

        val currentRuledEvidence = ruledEvidence.value
        val currentDifficulty = difficultyUiState.value.type
        val visualizerState = footstepVisualizerUiState.value

        _ghostScores.update { currentScores ->
            currentScores.map { ghostScore ->

                val updatedScore = ghostScore.updateScore(
                    ruledEvidence = currentRuledEvidence,
                    currentDifficulty = currentDifficulty
                )

                if (visualizerState.applyMeasurement) {
                    val bpmValue = when (visualizerState.measurementType) {
                        VisualizerMeasurementType.INSTANT -> visualizerState.state.smoothed
                        VisualizerMeasurementType.AVERAGED -> visualizerState.state.average
                        VisualizerMeasurementType.WEIGHTED -> visualizerState.state.weightedAverage
                    }
                    updatedScore.updateBpmValidation(bpmValue)
                } else {
                    updatedScore.resetBpmValidation()
                }
            }
        }
    }

    /** Order of Ghost IDs **/
    private val _ghostOrder: StateFlow<List<GhostResources.GhostIdentifier>> =
        ghostScores.map { ghostScores ->
            ghostScores
                .sortedByDescending { it.bpmIsValid }
                .sortedByDescending { it.score }
                .map { it.ghostEvidence.ghost.id }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ghostEvidences.map { it.ghost.id }
        )
    val ghostOrder = _ghostOrder

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
    private fun initToolbarUiState() {
        _toolbarUiState.update {
            ToolbarUiState(
                isCollapsed = false,
                category = ToolbarUiState.Category.TOOL_CONFIG,
                openWidth = 0.5f
            )
        }
    }

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
    private fun initPopupUiState() {
        _popupUiState.update {
            JournalPopupUiState()
        }
    }

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
        initRuledEvidence()
        initGhostScores()
    }

    /*
    * Ghost Score ---------------------------
    */

    fun toggleGhostNegation(
        ghostModel: GhostType
    ) {
        val scores = ghostScores.value.map {
            if(it.ghostEvidence.ghost.id == ghostModel.id)
                it.toggleManualRejection()
            else it
        }

        _ghostScores.update {
            scores
        }
    }

    /*
    * Evidence Ruling Handler ---------------------------
    */
    fun setEvidenceRuling(
        evidence: EvidenceType,
        ruling: Ruling
    ) {
        _ruledEvidence.update {
            it.map { e ->
                if (evidence.id == e.evidence.id)
                    e.copy(ruling = ruling)
                else e
            }
        }
        updateGhostScores()
    }

    fun getRuledEvidence(
        evidenceModel: EvidenceType
    ): RuledEvidence? {
        return ruledEvidence.value.find { it.isEvidence(evidenceModel) }
    }

    /*
     * Difficulty Handler ---------------------------
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

    private fun setDifficultyIndex(
        index: Int
    ) {
        updateDifficulty(index)

        setTimeRemaining(difficultyUiState.value.time)
        resetTimer()
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
    private fun resetTimer(
        currentDifficultyTime: Long
    ) {
        resetTimer()
        setTimeRemaining(currentDifficultyTime)
        resetStartTime()
    }
    */

    /*
     * MapCarouselHandler ---------------------------
     */

    private fun setMapIndex(
        index: Int = 0
    ) {
        try {
            val name = getSimpleMapNameUseCase(index).getOrThrow()
            val size = getSimpleMapSizeUseCase(index).getOrThrow()
            val modifier = fetchMapModifiersUseCase(size).getOrThrow()

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

    fun incrementMapIndex() {
        try {
            val newIndex = incrementMapIndexUseCase(
                mapUiState.value.index).getOrThrow()
            setMapIndex(newIndex)
        } catch (e: Exception) { e.printStackTrace() }
    }

    fun decrementMapIndex() {
        try {
            val newIndex = decrementMapIndexUseCase(
                mapUiState.value.index).getOrThrow()
            setMapIndex(newIndex)
        } catch (e: Exception) { e.printStackTrace() }
    }

    /*
     * Settings
     */


    /*
    * Footstep Visualizer
    */

    fun setBpmData(data: RealtimeUiState<PointRecord>) {
        _footstepVisualizerUiState.update {
            it.copy(state = data)
        }
        updateGhostScores()
    }

    fun setBpmMeasurementType(type: VisualizerMeasurementType) {
        _footstepVisualizerUiState.update {
            it.copy(measurementType = type)
        }
        updateGhostScores()
    }

    fun toggleApplyBpmMeasurement() {
        _footstepVisualizerUiState.update {
            it.copy(applyMeasurement = !it.applyMeasurement)
        }
        updateGhostScores()
    }

    init {
        initToolbarUiState()
        initPopupUiState()

        setMapIndex(0)
        updateDifficulty(0)
        initPhaseUiState()
        initTimerUiState()

        initPlayerSanityUiState()

        //initGhostScores()
        initRuledEvidence()
        updateGhostScores()
    }

    companion object {

        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                    val container = (application as InvestigationContainerProvider).provideInvestigationContainer()

                    val journalUseCaseBundle = container.journalUseCaseBundle
                    val fetchDifficultiesUseCase = container.fetchDifficultiesUseCase
                    val getDifficultyNameUseCase = container.getDifficultyNameUseCase
                    val getDifficultyModifierUseCase = container.getDifficultyModifierUseCase
                    val getDifficultyTimeUseCase = container.getDifficultyTimeUseCase
                    val getDifficultyResponseTypeUseCase =
                        container.getDifficultyResponseTypeUseCase
                    val getDifficultyInitialSanityUseCase =
                        container.getDifficultyInitialSanityUseCase
                    val incrementDifficultyIndexUseCase =
                        container.incrementDifficultyIndexUseCase
                    val decrementDifficultyIndexUseCase =
                        container.decrementDifficultyIndexUseCase
                    val fetchSimpleMapsUseCase = container.fetchSimpleMapsUseCase
                    val getSimpleMapIdUseCase = container.getSimpleMapIdUseCase
                    val getSimpleMapNameUseCase = container.getSimpleMapNameUseCase
                    val getSimpleMapSizeUseCase = container.getSimpleMapSizeUseCase
                    val getSimpleMapSetupModifierUseCase =
                        container.getSimpleMapSetupModifierUseCase
                    val getSimpleMapNormalModifierUseCase =
                        container.getSimpleMapNormalModifierUseCase
                    val getMapModifierUseCase = container.getMapModifierUseCase
                    val incrementMapIndexUseCase = container.incrementMapIndexUseCase
                    val decrementMapIndexUseCase = container.decrementMapIndexUseCase
                    val incrementMapFloorIndexUseCase = container.incrementMapFloorIndexUseCase
                    val decrementMapFloorIndexUseCase = container.decrementMapFloorIndexUseCase
                    val fetchMapModifiersUseCase = container.fetchMapModifiersUseCase
                    val fetchMapThumbnailsUseCase = container.fetchMapThumbnailsUseCase
                    val fetchCodexAchievementsUseCase = container.fetchCodexAchievementsUseCase
                    val fetchCodexPossessionsUseCase = container.fetchCodexPossessionsUseCase
                    val fetchCodexEquipmentUseCase = container.fetchCodexEquipmentUseCase
                    val getAllowHuntWarnAudioUseCase = container.getAllowHuntWarnAudioUseCase
                    val getEnableGhostReorderUseCase = container.getEnableGhostReorderUseCase
                    val getEnableRTLUseCase = container.getEnableRTLUseCase
                    val getMaxHuntWarnFlashTimeUseCase = container.getMaxHuntWarnFlashTimeUseCase
                    val getEquipmentTypeByEvidenceTypeUseCase =
                        container.getEquipmentTypeByEvidenceTypeUseCase

                    InvestigationScreenViewModel(
                        journalUseCaseBundle = journalUseCaseBundle,
                        fetchDifficultiesUseCase = fetchDifficultiesUseCase,
                        getDifficultyNameUseCase = getDifficultyNameUseCase,
                        getDifficultyModifierUseCase = getDifficultyModifierUseCase,
                        getDifficultyTimeUseCase = getDifficultyTimeUseCase,
                        getDifficultyResponseTypeUseCase = getDifficultyResponseTypeUseCase,
                        getDifficultyInitialSanityUseCase = getDifficultyInitialSanityUseCase,
                        incrementDifficultyIndexUseCase = incrementDifficultyIndexUseCase,
                        decrementDifficultyIndexUseCase = decrementDifficultyIndexUseCase,
                        fetchSimpleMapsUseCase = fetchSimpleMapsUseCase,
                        getSimpleMapIdUseCase = getSimpleMapIdUseCase,
                        getSimpleMapNameUseCase = getSimpleMapNameUseCase,
                        getSimpleMapSizeUseCase = getSimpleMapSizeUseCase,
                        getSimpleMapSetupModifierUseCase = getSimpleMapSetupModifierUseCase,
                        getSimpleMapNormalModifierUseCase = getSimpleMapNormalModifierUseCase,
                        getMapModifierUseCase = getMapModifierUseCase,
                        incrementMapIndexUseCase = incrementMapIndexUseCase,
                        decrementMapIndexUseCase = decrementMapIndexUseCase,
                        incrementMapFloorIndexUseCase = incrementMapFloorIndexUseCase,
                        decrementMapFloorIndexUseCase = decrementMapFloorIndexUseCase,
                        fetchMapModifiersUseCase = fetchMapModifiersUseCase,
                        fetchMapThumbnailsUseCase = fetchMapThumbnailsUseCase,
                        fetchCodexAchievementsUseCase = fetchCodexAchievementsUseCase,
                        fetchCodexPossessionsUseCase = fetchCodexPossessionsUseCase,
                        fetchCodexEquipmentUseCase = fetchCodexEquipmentUseCase,
                        getAllowHuntWarnAudioUseCase = getAllowHuntWarnAudioUseCase,
                        getEnableGhostReorderUseCase = getEnableGhostReorderUseCase,
                        getEnableRTLUseCase = getEnableRTLUseCase,
                        getMaxHuntWarnFlashTimeUseCase = getMaxHuntWarnFlashTimeUseCase,
                        getEquipmentTypeByEvidenceTypeUseCase = getEquipmentTypeByEvidenceTypeUseCase
                    )
                }
            }

        const val TIME_MIN = 0L
        const val TIME_MAX = 300000L

    }

}
