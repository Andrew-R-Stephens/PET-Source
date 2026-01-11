package com.tritiumgaming.feature.investigation.ui

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.shared.data.preferences.usecase.GetAllowHuntWarnAudioUseCase
import com.tritiumgaming.shared.data.preferences.usecase.GetEnableGhostReorderUseCase
import com.tritiumgaming.shared.data.preferences.usecase.GetEnableRTLUseCase
import com.tritiumgaming.shared.data.preferences.usecase.GetMaxHuntWarnFlashTimeUseCase
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
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources
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
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.shared.data.map.modifier.usecase.FetchMapModifiersUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetMapModifierUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetSimpleMapNormalModifierUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetSimpleMapSetupModifierUseCase
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources.MapTitle
import com.tritiumgaming.shared.data.map.simple.usecase.DecrementMapFloorIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.DecrementMapIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.FetchMapThumbnailsUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapIdUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapNameUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapSizeUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.IncrementMapFloorIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.IncrementMapIndexUseCase
import com.tritiumgaming.shared.data.popup.model.EvidencePopupRecord
import com.tritiumgaming.shared.data.popup.model.GhostPopupRecord
import com.tritiumgaming.feature.investigation.app.container.InvestigationContainerProvider
import com.tritiumgaming.feature.investigation.ui.journal.lists.item.GhostScore
import com.tritiumgaming.feature.investigation.ui.popups.JournalPopupUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.operationconfig.difficulty.DifficultyUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.operationconfig.map.MapUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.operationconfig.operation.OperationSanityUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.phase.Phase
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.phase.PhaseUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.sanity.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.sanity.PlayerSanityUiState.Companion.HALF_SANITY
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.sanity.PlayerSanityUiState.Companion.MAX_SANITY
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.sanity.PlayerSanityUiState.Companion.MIN_SANITY
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.sanity.PlayerSanityUiState.Companion.SAFE_MIN_BOUNDS
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.timer.TimerUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.timer.TimerUiState.Companion.DEFAULT
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.timer.TimerUiState.Companion.FOREVER
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.timer.TimerUiState.Companion.TIME_DEFAULT
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.max
import kotlin.math.min

class InvestigationScreenViewModel(
    private val getEvidenceUseCase: GetEvidenceUseCase,
    private val fetchEvidenceTypesUseCase: FetchEvidenceTypesUseCase,
    private val getEvidenceTypeByIdUseCase: GetEvidenceTypeByIdUseCase,
    private val getGhostUseCase: GetGhostUseCase,
    private val fetchGhostTypesUseCase: FetchGhostTypesUseCase,
    private val getGhostTypeByIdUseCase: GetGhostTypeByIdUseCase,
    private val initRuledEvidenceUseCase: InitRuledEvidenceUseCase,
    private val fetchGhostEvidencesUseCase: FetchGhostEvidencesUseCase,
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
        try {
            it.getOrThrow()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

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

    private val _operationSanityUiState = MutableStateFlow(OperationSanityUiState())
    val operationSanityUiState = _operationSanityUiState.asStateFlow()

    private val _toolbarUiState = MutableStateFlow(ToolbarUiState())
    val toolbarUiState = _toolbarUiState.asStateFlow()

    private val _popupUiState = MutableStateFlow(JournalPopupUiState())
    val popupUiState = _popupUiState.asStateFlow()

    /*
     * COROUTINES
     */
    var sanityJob: Job? = null

    /*
     * Map Ui Functions
     */
    private fun initMapUiState() {
        _mapUiState.update {
            MapUiState(
                index = 0,
                name = getSimpleMapNameUseCase(0)
                    .getOrDefault(MapTitle.BLEASDALE_FARMHOUSE),
                size = getSimpleMapSizeUseCase(0)
                    .getOrDefault(MapSize.SMALL),
                setupModifier = getSimpleMapSetupModifierUseCase(0)
                    .getOrDefault(it.setupModifier),
                normalModifier = getSimpleMapNormalModifierUseCase(0)
                    .getOrDefault(it.normalModifier)
            )
        }
    }

    /*
     * Difficulty Ui Functions
     */
    private fun initDifficultyUiState() {
        _difficultyUiState.update {
            DifficultyUiState(
                index = 0,
                name = getDifficultyNameUseCase(0)
                    .getOrDefault(DifficultyTitle.AMATEUR),
                modifier = getDifficultyModifierUseCase(0)
                    .getOrDefault(0f),
                time = getDifficultyTimeUseCase(0)
                    .getOrDefault(TIME_DEFAULT),
                initialSanity = getDifficultyInitialSanityUseCase(0)
                    .getOrDefault(MAX_SANITY * .01f) * 100f,
                responseType = getDifficultyResponseTypeUseCase(0)
                    .getOrDefault(DifficultyResponseType.KNOWN)
            )
        }
    }

    /*
     * Timer Ui Functions
     */
    private fun initTimerUiState() {
        _timerUiState.update {
            TimerUiState(
                startTime = TIME_DEFAULT,
                remainingTime = _difficultyUiState.value.time,
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
                maxFlashTime = FOREVER,
                currentPhase = Phase.SETUP,
                canFlash = true,
                canAlertAudio = false
            )
        }
    }

    /*
     * Operation Sanity Ui Functions
     */
    private fun initOperationSanityUiState() {
        _operationSanityUiState.update {
            OperationSanityUiState(
                sanityMax = MAX_SANITY,
                drainModifier = it.getDrainModifier(
                    difficultyModifier = difficultyUiState.value.modifier,
                    mapModifier = 1f
                )
            )
        }
    }

    /*
     * Player Sanity Ui Functions
     */
    private fun initPlayerSanityUiState() {
        _playerSanityUiState.update {
            PlayerSanityUiState(
                sanityLevel = MAX_SANITY,
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

    private fun startPlayerSanityJob() {
        sanityJob = viewModelScope.launch {
            while(!timerUiState.value.paused) {
                Log.d("TickSanity",
                    "--------------------------- Set Sanity-----------------------------")
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
    fun getGhostById(ghostId: GhostResources.GhostIdentifier): GhostType? =
        getGhostTypeByIdUseCase(ghostId)

    fun getEvidenceById(evidenceId: EvidenceResources.EvidenceIdentifier): EvidenceType? =
        getEvidenceTypeByIdUseCase(evidenceId)

    fun reset() {
        resetJournal()

        resetTimer(
            currentDifficultyTime = difficultyUiState.value.time
        )
        resetPhase()
        resetSanity()
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
    private val _ghostScores: MutableStateFlow<List<GhostScore>> =
        MutableStateFlow(listOf())
    val ghostScores = _ghostScores.asStateFlow()
    private fun initGhostScores() {
        _ghostScores.update { _ ->
            ghostEvidences.map { GhostScore(it) }
        }
        Log.d("GhostScores", "Creating New")

        initGhostOrder()
    }

    private fun getGhostScores(
        ghostModel: GhostType
    ): GhostScore? {
        return _ghostScores.value.find { it.ghostEvidence.ghost.id == ghostModel.id }
    }

    /** Order of Ghost IDs **/
    private val _ghostOrder: MutableStateFlow<List<GhostResources.GhostIdentifier>> =
        MutableStateFlow(mutableStateListOf())

    @Stable
    val ghostOrder = _ghostOrder.asStateFlow()
    private fun initGhostOrder() {
        _ghostOrder.update {
            ghostEvidences.map { it.ghost.id }
        }
        Log.d("GhostOrder", "Creating New")

        reorderGhostScores()
    }

    private fun reorderGhostScores() {
        val orderedScores = mutableListOf<GhostScore>()

        ghostScores.value.forEach {
            it.setScore(
                getEvidenceScore(
                    it.ghostEvidence.ghost
                )
            )
            orderedScores.add(it)
        }
        val orderedTemp = orderedScores
            .sortedByDescending { it.score.value }
            .map { it.ghostEvidence.ghost.id }.toMutableStateList()

        _ghostOrder.update { orderedTemp }

        val str2 = StringBuilder()
        ghostOrder.value.forEachIndexed { index, orderModel ->
            str2.append(
                "[$orderModel: " + "${
                    ghostScores.value.find { scoreModel ->
                        scoreModel.ghostEvidence.ghost.id == orderModel
                    }?.score
                }] "
            )
        }
        Log.d("GhostOrder", "Reordered to:$str2")

    }

    private fun getEvidenceScore(
        ghostModel: GhostType
    ): Int {

        return getGhostScores(ghostModel)
            ?.getEvidenceScore(
                ruledEvidence = ruledEvidence.value,
                currentDifficulty = DifficultyType.entries[difficultyUiState.value.index]
            ) ?: 1
    }

    fun getGhostScore(
        ghostModel: GhostType
    ): StateFlow<Int> {
        return ghostScores.value.first { it.ghostEvidence.ghost.id == ghostModel.id }.score
    }

    fun setGhostNegation(
        ghostModel: GhostType,
        isForceNegated: Boolean
    ) {
        val ghostScore = ghostScores.value.first { it.ghostEvidence.ghost.id == ghostModel.id }
        ghostScore.setForcefullyRejected(isForceNegated)
    }

    fun toggleGhostNegation(
        ghostModel: GhostType
    ) {
        val ghostScore = ghostScores.value.first { it.ghostEvidence.ghost.id == ghostModel.id }
        ghostScore.toggleForcefullyRejected()
    }

    fun getGhostScorePoints(
        ghostModel: GhostType
    ): StateFlow<Int>? {
        val ghostScore = ghostScores.value.find { it.ghostEvidence.ghost.id == ghostModel.id }
        return ghostScore?.score
    }

    /*
    * Evidence Ruling Handler ---------------------------
    */

    private val _ruledEvidence: MutableStateFlow<List<RuledEvidence>> =
        MutableStateFlow(emptyList())
    val ruledEvidence = _ruledEvidence.asStateFlow()
    private fun initRuledEvidence() {
        try {
            val evidence = initRuledEvidenceUseCase().getOrThrow()
            _ruledEvidence.update { evidence }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setEvidenceRuling(
        evidenceIndex: Int,
        ruling: Ruling
    ) {
        _ruledEvidence.update {
            it.apply { it[evidenceIndex].copy(ruling = ruling) }
        }
        reorderGhostScores()
    }

    fun setEvidenceRuling(
        evidence: EvidenceType,
        ruling: Ruling
    ) {
        _ruledEvidence.update {
            ruledEvidence.value.map { e ->
                if (evidence.id == e.evidence.id)
                    e.copy(ruling = ruling)
                else e
            }
        }
        reorderGhostScores()
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

        _operationSanityUiState.update {
            it.copy(
                sanityMax = difficultyUiState.value.initialSanity,
                drainModifier = it.getDrainModifier(
                    difficultyModifier = difficultyUiState.value.modifier,
                    mapModifier = 0f
                )
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

        reorderGhostScores()
    }

    private fun updateDifficulty(index: Int) {
        _difficultyUiState.update {
            it.copy(
                index = index,
                name = getDifficultyNameUseCase(index)
                    .getOrDefault(it.name),
                modifier = getDifficultyModifierUseCase(index)
                    .getOrDefault(it.modifier),
                time = getDifficultyTimeUseCase(index)
                    .getOrDefault(it.time),
                initialSanity = getDifficultyInitialSanityUseCase(index)
                    .getOrDefault(it.initialSanity),
                responseType = getDifficultyResponseTypeUseCase(index)
                    .getOrDefault(it.responseType)
            )
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
     * Operation Sanity ---------------------------
     */
    /*fun setOperationSanity() {
        _operationSanityUiState.update {
            it.copy(
                sanityMax = difficultyUiState.value.initialSanity,
                drainModifier = it.getDrainModifier(
                    difficultyModifier = difficultyUiState.value.modifier,
                    mapModifier = getCurrentMapModifier()
                )
            )
        }
    }*/

    /*
     * Player Sanity ---------------------------
     */

    /** The level can be between 0 and 100. Levels outside those extremes are constrained.
     * @return The sanity level that's missing. MAX_SANITY - insanityActual. */
    fun setInsanityLevel(
        value: Float
    ) {
        _playerSanityUiState.update {
            it.copy(
                insanityLevel = max(
                    min(MAX_SANITY, value),
                    MIN_SANITY)
            )
        }

        Log.d("Sanity", "Set insanityLevel: " +
                "\n\t${playerSanityUiState.value.insanityLevel}")

        updateSanityLevel()

    }

    private fun timeRemainingToInsanityLevel() {

        //val tickMultiplier = 100f
        val startTime = max(
            timerUiState.value.startTime,
            TIME_MIN
        )

        val drainModifier = operationSanityUiState.value.drainModifier
        val drainMultiplier = drainModifier // * tickMultiplier
        val timeRemaining = startTime - System.currentTimeMillis()

        setInsanityLevel(
            MAX_SANITY - (timeRemaining * drainMultiplier) * .001f
        )

        Log.d("Sanity", "Modifiers:" +
                "\n\tDrain Modifier: ${getCurrentMapModifier()} * ${difficultyUiState.value.modifier} = $drainModifier" +
                "\n\tDrain Multiplier: $drainMultiplier" +
                "\n\tTime Remaining: $startTime - ${System.currentTimeMillis()} = $timeRemaining" +
                "\n\tSet Insanity Level: $MAX_SANITY - ($timeRemaining * $drainMultiplier) -> " +
                "${MAX_SANITY - (timeRemaining * drainMultiplier) * .001f}"
        )

    }

    private fun skipInsanity(
        newLevel: Float = HALF_SANITY
    ) {
        val insanityLevel = playerSanityUiState.value.insanityLevel

        setInsanityLevel(newLevel.coerceAtLeast(insanityLevel))
        setStartTimeByProgress(insanityLevel)
    }

    /** the sanity level missing, in percent.**/
    private fun updateSanityLevel() {

        _playerSanityUiState.update {
            it.copy(
                sanityLevel = max(
                    min(
                        MAX_SANITY,
                        MAX_SANITY - it.insanityLevel
                    ),
                    MIN_SANITY
                )
            )
        }

        Log.d("Sanity", "Updated:" +
                "\n\tinsanityLevel: ${playerSanityUiState.value.insanityLevel}" +
                "\n\tsanityLevel: ${playerSanityUiState.value.sanityLevel}"
        )

        updatePhase()
    }

    /**
     * Reduces player sanity level each doTick. Sanity cannot drop below 50% if the clock still has
     * time remaining.
     */
    private fun tickSanity() {
        timeRemainingToInsanityLevel()
        updatePhase()
    }

    /** @param progress specify the progress 0 - 100
     * Resets the Warning Indicator to start flashing again, if necessary
     * Sets the Start Time of the Sanity Drain, based on remaining time,
     * sanity, difficulty and map size. */
    private fun setStartTimeByProgress(progress: Float) {
        val drainModifier = operationSanityUiState.value.drainModifier

        val progressOverride =
            MAX_SANITY - max(MIN_SANITY, min(MAX_SANITY, progress))

        val multiplier = .001f

        val timeAddition = (progressOverride / drainModifier / multiplier).toLong()
        val newStartTime = (System.currentTimeMillis() + timeAddition)

        _timerUiState.update {
            it.copy(
                startTime = newStartTime
            )
        }
    }

    fun displaySanityAsPercent(): String {
        val percentageFormat = NumberFormat.getPercentInstance()
        percentageFormat.minimumFractionDigits = 0

        val percent = playerSanityUiState.value.sanityLevel * .01f

        val formattedPercent = percentageFormat.format(percent).replace("%", "")

        val nbsp = "\u00A0"

        var percentStr = formattedPercent
        percentStr = percentStr.replace(nbsp, "").trim { it <= ' ' }

        var percentNum = 100
        try {
            percentNum = percentStr.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }

        val input = String.format(Locale.US, "%3d", percentNum)

        val output = StringBuilder()
        var i = 0
        while (i < input.length) {
            if (input[i] != '0') {
                break
            }
            output.append(nbsp)
            i++
        }
        while (i < input.length) {
            output.append(input[i])
            i++
        }
        output.append("%")

        return output.toString()

    }

    /** Defaults all persistent data. */
    private fun resetSanity() {
        //TODO warnTriggered = false
        setStartTimeByProgress(MAX_SANITY - difficultyUiState.value.initialSanity)
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

        if (phaseUiState.value.maxFlashTime == FOREVER) {
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

    private fun resetPhase() {
        _phaseUiState.update {
            it.copy(
                startFlashTime = DEFAULT,
                elapsedFlashTime = System.currentTimeMillis() - it.startFlashTime
            )
        }
        updateCanFlash()
    }

    private fun updatePhase() =
        _phaseUiState.update {
            it.copy(
                currentPhase =
                    if (timerUiState.value.remainingTime > TIME_MIN) {
                        Phase.SETUP
                    } else {
                        if (playerSanityUiState.value.sanityLevel < SAFE_MIN_BOUNDS) {
                            Phase.HUNT
                        } else {
                            Phase.ACTION
                        }
                    }
            )
        }

    /*
     * Timer ---------------------------
     */

    private var liveTimer: CountDownTimer? = null
    private fun setLiveTimer(
        millisInFuture: Long = timerUiState.value.remainingTime,
        countDownInterval: Long = 100L
    ) {
        liveTimer = object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millis: Long) {
                setTimeRemaining(millis)
            }

            override fun onFinish() { /* TODO not needed */
            }
        }
    }

    /** The Sanity Drain starting time, whenever the play button is activated.
     * @return The Sanity drain start time. */
    private fun resetStartTime() =
        _timerUiState.update {
            it.copy(
                startTime = TIME_DEFAULT
            )
        }

    private fun setTimeRemaining(value: Long) {
        _timerUiState.update { it.copy(remainingTime = value) }
    }

    private fun pauseTimer() {
        _timerUiState.update {
            it.copy(
                paused = true
            )
        }
        liveTimer?.cancel()

        stopPlayerSanityJob()
    }

    private fun playTimer() {
        _timerUiState.update {
            it.copy(
                paused = false
            )
        }
        setLiveTimer()
        liveTimer?.start()

        startPlayerSanityJob()
    }

    fun toggleTimer() {
        if (timerUiState.value.paused) {
            playTimer()
            setStartTimeByProgress(progress = playerSanityUiState.value.insanityLevel)
        } else {
            pauseTimer()
        }
    }

    private fun resetTimer() {
        pauseTimer()
        resetStartTime()
        timeRemainingToInsanityLevel()
        setLiveTimer()
    }

    private fun resetTimer(
        currentDifficultyTime: Long
    ) {
        resetTimer()
        setTimeRemaining(currentDifficultyTime)
        resetStartTime()
    }

    fun fastForwardTimer(time: Long) {
        pauseTimer()
        setTimeRemaining(time)
        setLiveTimer()
        skipInsanity(HALF_SANITY)
        playTimer()
    }

    /*
     * MapCarouselHandler ---------------------------
     */

    private fun setCurrentMapIndex(
        index: Int
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

            Log.d("InvestigationViewModel", "MapUiSate:" +
                    "\n\tindex: ${mapUiState.value.index}" +
                    "\n\tname: ${mapUiState.value.name}" +
                    "\n\tsize: ${mapUiState.value.size}" +
                    "\n\tsetupModifier: ${mapUiState.value.setupModifier}" +
                    "\n\tnormalModifier: ${mapUiState.value.normalModifier}")

        } catch (e: Exception) {
            Log.e("InvestigationViewModel", "Error setting map index")
            e.printStackTrace()
        }
    }

    fun incrementMapIndex() {
        try {
            val newIndex = incrementMapIndexUseCase(
                mapUiState.value.index).getOrThrow()
            setCurrentMapIndex(newIndex)
        } catch (e: Exception) { e.printStackTrace() }
    }

    fun decrementMapIndex() {
        try {
            val newIndex = decrementMapIndexUseCase(
                mapUiState.value.index).getOrThrow()
            setCurrentMapIndex(newIndex)
        } catch (e: Exception) { e.printStackTrace() }
    }

    /** Based on current map size (Small, Medium, Large) and the stage of the investigation
     * (Setup vs Hunt)
     * Defaults to Setup/Small if the selected index is out of range of available indexes.
     * @returns the drop rate multiplier. */
    private fun getCurrentMapModifier(): Float {
        val modifier = getMapModifierUseCase(
            _mapUiState.value.size.ordinal,
            timerUiState.value.remainingTime
        )
        modifier.exceptionOrNull()?.let {
            Log.e("InvestigationViewModel", "Error getting map modifier")
            it.printStackTrace()
        }

        return modifier.getOrDefault(1f)
    }

    /*
     * Settings
     */

    val rTLPreference = getEnableRTLUseCase()
    val ghostReorderPreference = getEnableGhostReorderUseCase()
    val huntWarnDurationPreference = getMaxHuntWarnFlashTimeUseCase()
    val allowHuntWarnAudioPreference = getAllowHuntWarnAudioUseCase()

    init {
        initMapUiState()
        initDifficultyUiState()
        initTimerUiState()
        initPhaseUiState()
        initOperationSanityUiState()
        initPlayerSanityUiState()
        initToolbarUiState()

        initPopupUiState()

        initGhostScores()
        initRuledEvidence()
        reorderGhostScores()
    }

    companion object {

        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                    val container = (application as InvestigationContainerProvider).provideInvestigationContainer()

                    val getEvidenceUseCase = container.getEvidenceUseCase
                    val fetchEvidenceTypesUseCase = container.fetchEvidenceTypesUseCase
                    val getEvidenceByIdUseCase = container.getEvidenceTypeByIdUseCase
                    val getGhostUseCase = container.getGhostUseCase
                    val fetchGhostTypesUseCase = container.fetchGhostTypesUseCase
                    val getGhostByIdUseCase = container.getGhostTypeByIdUseCase
                    val fetchGhostEvidencesUseCase = container.fetchGhostEvidencesUseCase
                    val initRuledEvidenceUseCase = container.initRuledEvidenceUseCase
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
                        getEvidenceUseCase = getEvidenceUseCase,
                        fetchEvidenceTypesUseCase = fetchEvidenceTypesUseCase,
                        getEvidenceTypeByIdUseCase = getEvidenceByIdUseCase,
                        getGhostUseCase = getGhostUseCase,
                        fetchGhostTypesUseCase = fetchGhostTypesUseCase,
                        getGhostTypeByIdUseCase = getGhostByIdUseCase,
                        initRuledEvidenceUseCase = initRuledEvidenceUseCase,
                        fetchGhostEvidencesUseCase = fetchGhostEvidencesUseCase,
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
