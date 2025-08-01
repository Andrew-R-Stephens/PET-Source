package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.usecase.FetchCodexAchievementsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.usecase.FetchCodexEquipmentUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.usecase.FetchCodexPossessionsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase.DecrementDifficultyIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase.FetchDifficultiesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase.GetDifficultyInitialSanityUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase.GetDifficultyModifierUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase.GetDifficultyNameUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase.GetDifficultyResponseTypeUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase.GetDifficultyTimeUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase.IncrementDifficultyIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.RuledEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.RuledEvidence.Ruling
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchEvidencesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchGhostEvidencesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchGhostsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.GetEvidenceByIdUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.GetGhostByIdUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.InitRuledEvidenceUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.model.WorldMapModifier
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.usecase.FetchMapModifiersUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.usecase.GetMapModifierUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.usecase.GetSimpleMapNormalModifierUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.usecase.GetSimpleMapSetupModifierUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources.MapTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.DecrementMapFloorIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.DecrementMapIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.FetchMapThumbnailsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.GetSimpleMapNameUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.GetSimpleMapSizeUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.IncrementMapFloorIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.IncrementMapIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toStringResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal.lists.item.GhostScore
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.ToolbarUiState
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.operationconfig.difficulty.DifficultyUiState
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.operationconfig.map.MapUiState
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.phase.Phase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.phase.PhaseUiState
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.sanity.SanityUiState
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.sanity.SanityUiState.Companion.HALF_SANITY
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.sanity.SanityUiState.Companion.MAX_SANITY
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.sanity.SanityUiState.Companion.MIN_SANITY
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.sanity.SanityUiState.Companion.SAFE_MIN_BOUNDS
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.timer.TimerUiState
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.timer.TimerUiState.Companion.DEFAULT
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.timer.TimerUiState.Companion.FOREVER
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import kotlin.math.max
import kotlin.math.min

class InvestigationViewModel(
    private val fetchGhostsUseCase: FetchGhostsUseCase,
    private val fetchEvidencesUseCase: FetchEvidencesUseCase,
    private val getEvidenceByIdUseCase: GetEvidenceByIdUseCase,
    private val initRuledEvidenceUseCase: InitRuledEvidenceUseCase,
    private val fetchGhostEvidencesUseCase: FetchGhostEvidencesUseCase,
    private val getGhostByIdUseCase: GetGhostByIdUseCase,
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
    private val getSimpleMapNameUseCase: GetSimpleMapNameUseCase,
    private val getSimpleMapSizeUseCase: GetSimpleMapSizeUseCase,
    private val getSimpleMapSetupModifierUseCase: GetSimpleMapSetupModifierUseCase,
    private val getSimpleMapNormalModifierUseCase: GetSimpleMapNormalModifierUseCase,
    private val getMapModifierUseCase: GetMapModifierUseCase,
    private val fetchMapModifiersUseCase: FetchMapModifiersUseCase,
    private val fetchMapThumbnailsUseCase: FetchMapThumbnailsUseCase,
    private val fetchCodexAchievementsUseCase: FetchCodexAchievementsUseCase,
    private val fetchCodexPossessionsUseCase: FetchCodexPossessionsUseCase,
    private val fetchCodexEquipmentUseCase: FetchCodexEquipmentUseCase
): ViewModel() {

    private val ghostEvidences = fetchGhostEvidencesUseCase().let {
        it.exceptionOrNull()?.printStackTrace()
        it.getOrNull()?.let {
            Log.d("InvestigationViewModel", "GhostEvidence")
            it.forEach { Log.d("InvestigationViewModel", "	GhostEvidence: $it") }
        }
        it.getOrDefault(emptyList())
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

    private val _sanityUiState = MutableStateFlow(SanityUiState())
    val sanityUiState = _sanityUiState.asStateFlow()

    private val _toolbarUiState: MutableStateFlow<ToolbarUiState> =
        MutableStateFlow(ToolbarUiState())
    val toolbarUiState = _toolbarUiState.asStateFlow()

    /*
     * Map Ui Functions
     */
    fun initMapUiState() {
        _mapUiState.update {
            it.copy(
                index = 0,
                name = getSimpleMapNameUseCase(0).getOrDefault(MapTitle.BLEASDALE_FARMHOUSE),
                size = getSimpleMapSizeUseCase(0).getOrDefault(MapSize.SMALL),
                modifier = fetchMapModifiersUseCase(0).getOrDefault(
                    WorldMapModifier(
                        name = MapSize.SMALL,
                        setupModifier = getSimpleMapSetupModifierUseCase(0).getOrDefault(0f),
                        normalModifier = getSimpleMapNormalModifierUseCase(0).getOrDefault(0f)
                    )
                )
            )
        }
    }

    /*
     * Difficulty Ui Functions
     */
    fun initDifficultyUiState() {
        _difficultyUiState.update {
            it.copy(
                index = 0,
                name = getDifficultyNameUseCase(0).getOrDefault(DifficultyTitle.AMATEUR),
                modifier = getDifficultyModifierUseCase(0).getOrDefault(0f),
                time = getDifficultyTimeUseCase(0).getOrDefault(TIME_DEFAULT),
                initialSanity = getDifficultyInitialSanityUseCase(0).getOrDefault(MAX_SANITY * .01f) * 100f,
                responseType = getDifficultyResponseTypeUseCase(0).getOrDefault(DifficultyResponseType.KNOWN)
            )
        }
    }

    /*
     * Timer Ui Functions
     */
    fun initTimerUiState() {
        _timerUiState.update {
            it.copy(
                startTime = TIME_DEFAULT,
                elapsedTime = 0L,
                remainingTime = _difficultyUiState.value.time,
                startFlashTime = DEFAULT,
                maxFlashTime = FOREVER,
                paused = true
            )
        }
    }

    /*
     * Phase Ui Functions
     */
    fun initPhaseUiState() {
        _phaseUiState.update {
            it.copy(
                currentPhase = Phase.SETUP,
                canFlash = true,
                canAlertAudio = false
            )
        }
    }

    /*
     * Sanity Ui Functions
     */
    fun initSanityUiState() {
        _sanityUiState.update {
            it.copy(
                sanityLevel = MAX_SANITY,
                sanityMax = MAX_SANITY,
                drainModifier = 1f,
                insanityLevel = 0f,
                isInsane = false
            )
        }
    }

    /*
     * Toolbar Ui Functions
     */
    fun initToolbarUiState() {
        _toolbarUiState.update {
            it.copy(
                isCollapsed = false,
                category = ToolbarUiState.Category.TOOL_CONFIG,
                openWidth = 0.5f
            )
        }
    }
    fun toggleToolbarState() {
        _toolbarUiState.update {
            it.copy(isCollapsed = !it.isCollapsed) }
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
     * COROUTINES
     */
    var sanityJob: Job? = null

    /*
     * FUNCTIONS
     */
    fun getGhostById(ghostId: String): GhostType? = getGhostByIdUseCase(ghostId)

    fun getEvidenceById(evidenceId: String): EvidenceType? = getEvidenceByIdUseCase(evidenceId)

    fun reset() {
        resetTimer(difficultyUiState.value.time)
        resetJournal()
        resetTimerPhase()
        resetSanity()
    }

    /*
     * Journal ------------------------
     */

    /** Resets the Ruling for each Evidence type */
    fun resetJournal() {
        initRuledEvidence()
        initGhostScores()
    }

    /*
    * Ghost Score ---------------------------
    */
    private val _ghostScores : MutableStateFlow<List<GhostScore>> =
        MutableStateFlow(listOf())
    val ghostScores = _ghostScores.asStateFlow()
    private fun initGhostScores() {
        _ghostScores.update { scores ->
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
    private val _ghostOrder: MutableStateFlow<List<String>> =
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
            it.setScore ( getEvidenceScore(
                it.ghostEvidence.ghost
            ))
            orderedScores.add(it)
        }
        val orderedTemp = orderedScores
            .sortedByDescending { it.score.value }
            .map { it.ghostEvidence.ghost.id }.toMutableStateList()

        _ghostOrder.update { orderedTemp }

        val str2 = StringBuilder()
        ghostOrder.value.forEachIndexed { index, orderModel ->
            str2.append("[$orderModel: " + "${ghostScores.value.find { scoreModel ->
                scoreModel.ghostEvidence.ghost.id ==  orderModel}?.score}] ")
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
    ){
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
        MutableStateFlow(listOf<RuledEvidence>())
    val ruledEvidence = _ruledEvidence.asStateFlow()
    private fun initRuledEvidence() {
        _ruledEvidence.update { initRuledEvidenceUseCase() }
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

    /* Index */

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

        _sanityUiState.update {
            it.copy(
                sanityMax = difficultyUiState.value.initialSanity,
                drainModifier = difficultyUiState.value.modifier *
                        getCurrentMapModifier(timerUiState.value.remainingTime),
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
                name = getDifficultyNameUseCase(index).getOrDefault(it.name),
                modifier = getDifficultyModifierUseCase(index).getOrDefault(it.modifier),
                time = getDifficultyTimeUseCase(index).getOrDefault(it.time),
                initialSanity = getDifficultyInitialSanityUseCase(index).getOrDefault(it.initialSanity),
                responseType = getDifficultyResponseTypeUseCase(index).getOrDefault(it.responseType)
            )
        }
    }

    /*
     * Sanity Handler ---------------------------
     */

    /** The level can be between 0 and 100. Levels outside those extremes are constrained.
     * @return The sanity level that's missing. MAX_SANITY - insanityActual. */
    fun setInsanityLevel(
        value: Float
    ) {
        _sanityUiState.update {
            it.copy(
                insanityLevel = max(min(MAX_SANITY, value), MIN_SANITY)
            )
        }

        updateSanityLevel()
    }
    private fun timeRemainingToInsanityLevel() {

        val tickMultiplier = .001f
        val startTime = max(
            timerUiState.value.startTime,
            TIME_MIN
        )

        val drainMultiplier = sanityUiState.value.drainModifier * tickMultiplier
        val timeDifference = startTime - System.currentTimeMillis()
        setInsanityLevel(
            MAX_SANITY - (timeDifference * drainMultiplier)
        )
    }
    private fun skipInsanity(
        newLevel: Float = HALF_SANITY
    ) {
        setInsanityLevel(newLevel.coerceAtLeast(sanityUiState.value.insanityLevel))
        setSanityStartTimeByProgress(sanityUiState.value.insanityLevel)
    }

    /** the sanity level missing, in percent.**/
    private fun updateSanityLevel() {

        _sanityUiState.update {
            it.copy(
                sanityLevel = max(
                    min(
                        MAX_SANITY,
                        MAX_SANITY - sanityUiState.value.insanityLevel),
                    MIN_SANITY)
            )
        }

        updateCurrentTimerPhase()
    }

    /**
     * Reduces player sanity level each doTick. Sanity cannot drop below 50% if the clock still has
     * time remaining.
     */
    private fun tickSanity() {
        timeRemainingToInsanityLevel()
        updateCurrentTimerPhase()
    }

    /** @param progress specify the progress 0 - 100
     * Resets the Warning Indicator to start flashing again, if necessary
     * Sets the Start Time of the Sanity Drain, based on remaining time,
     * sanity, difficulty and map size. */
    private fun setSanityStartTimeByProgress(progress: Float) {
        val progressOverride =
            MAX_SANITY - max(MIN_SANITY, min(MAX_SANITY, progress))

        val multiplier = .001f

        val timeAddition = (progressOverride / sanityUiState.value.drainModifier / multiplier).toLong()
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
        val percent = sanityUiState.value.sanityLevel * .01f

        val formattedPercent = percentageFormat.format(percent).replace("%", "")

        val nbsp = "\u00A0"

        var percentStr = formattedPercent
        percentStr = percentStr.replace(nbsp, "").trim { it <= ' ' }

        var percentNum = 100
        try { percentNum = percentStr.toInt() }
        catch (e: NumberFormatException) { e.printStackTrace() }

        val input = String.Companion.format(java.util.Locale.US,"%3d", percentNum)

        val output = StringBuilder()
        var i = 0
        while (i < input.length) {
            if (input[i] != '0') { break }
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
        setSanityStartTimeByProgress(MAX_SANITY - difficultyUiState.value.initialSanity)
        tickSanity()
    }

    /*
     * Timer Handler ---------------------------
     */
    private fun updateCurrentTimerPhase() =
        _phaseUiState.update {
            it.copy(
                currentPhase =
                    if (timerUiState.value.remainingTime > TIME_MIN) { Phase.SETUP }
                    else {
                        if (sanityUiState.value.sanityLevel < SAFE_MIN_BOUNDS) { Phase.HUNT }
                        else { Phase.ACTION }
                    }
            )
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

    private var liveTimer: CountDownTimer? = null
    private fun setLiveTimer(
        millisInFuture: Long = timerUiState.value.remainingTime,
        countDownInterval: Long = 100L
    ) {
        liveTimer = object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millis: Long) {
                setTimeRemaining(millis)
            }

            override fun onFinish() { /* TODO not needed */ }
        }
    }

    private fun pauseTimer() {
        _timerUiState.update {
            it.copy(
                paused = true
            )
        }
        liveTimer?.cancel()
    }
    private fun playTimer() {
        _timerUiState.update {
            it.copy(
                paused = false
            )
        }
        setLiveTimer()
        liveTimer?.start()
    }
    fun toggleTimer() {
        if (timerUiState.value.paused) {
            playTimer()
            setSanityStartTimeByProgress(progress = sanityUiState.value.insanityLevel)
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
     * Phase Handler ---------------------------
     */

    /** Allow the Warning indicator to flash either off or on if:
     * The player's sanity is less than 70%
     * either if the Flash Timeout is infinite
     * or if there is no time remaining on the countdown timer.
     * @return if the Warning indicator can flash */
    private fun updateCanFlash() {

        if (timerUiState.value.maxFlashTime == FOREVER) {
            _phaseUiState.update {
                it.copy(
                    canFlash = sanityUiState.value.isInsane
                )
            }
            return
        }

        if (timerUiState.value.startFlashTime == DEFAULT) {
            Log.d("Flash", "Start time is default.. now setting to current time")
            _timerUiState.update {
                it.copy(
                    startFlashTime = System.currentTimeMillis(),
                    elapsedTime = System.currentTimeMillis() - it.startFlashTime
                )
            }

            updateCanFlash()

            return
        }

        _phaseUiState.update {
            it.copy(
                canFlash = timerUiState.value.elapsedTime <=  timerUiState.value.maxFlashTime
            )
        }

    }

    private fun resetTimerPhase() {
        _timerUiState.update {
            it.copy(
                startFlashTime = DEFAULT,
                elapsedTime = System.currentTimeMillis() - it.startFlashTime
            )
        }
        updateCanFlash()
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
            val modifier = fetchMapModifiersUseCase(size.ordinal).getOrThrow()

            _mapUiState.update {
                it.copy(
                    index = index,
                    name = name,
                    size = size,
                    modifier = modifier
                )
            }
        } catch (e: Exception) {
            Log.e("InvestigationViewModel", "Error setting map index")
            e.printStackTrace() }
    }
    fun incrementMapIndex() {
        setCurrentMapIndex(incrementMapIndexUseCase(mapUiState.value.index))
    }
    fun decrementMapIndex() =
        setCurrentMapIndex(decrementMapIndexUseCase(mapUiState.value.index))

    /** Based on current map size (Small, Medium, Large) and the stage of the investigation
     * (Setup vs Hunt)
     * Defaults if the selected index is out of range of available indexes.
     * @returns the drop rate multiplier. */
    private fun getCurrentMapModifier(
        timeRemaining: Long = 0L
    ): Float {
        val modifier = getMapModifierUseCase(
            _mapUiState.value.size.toStringResource(),
            timeRemaining
        )
        modifier.exceptionOrNull()?.let {
            Log.e("InvestigationViewModel", "Error getting map modifier")
            it.printStackTrace()
        }

        return modifier.getOrDefault(1f)
    }

    init {
        initMapUiState()
        initDifficultyUiState()
        initTimerUiState()
        initPhaseUiState()
        initSanityUiState()
        initToolbarUiState()

        initGhostScores()
        initRuledEvidence()
        reorderGhostScores()
    }

    /*
     * VIEWMODEL FACTORIES
     */
    class InvestigationFactory(
        private val fetchEvidencesUseCase: FetchEvidencesUseCase,
        private val fetchGhostsUseCase: FetchGhostsUseCase,
        private val getEvidenceByIdUseCase: GetEvidenceByIdUseCase,
        private val fetchGhostEvidencesUseCase: FetchGhostEvidencesUseCase,
        private val getGhostByIdUseCase: GetGhostByIdUseCase,
        private val initRuledEvidenceUseCase: InitRuledEvidenceUseCase,
        private val fetchDifficultiesUseCase: FetchDifficultiesUseCase,
        private val getDifficultyNameUseCase: GetDifficultyNameUseCase,
        private val getDifficultyModifierUseCase: GetDifficultyModifierUseCase,
        private val getDifficultyTimeUseCase: GetDifficultyTimeUseCase,
        private val getDifficultyResponseTypeUseCase: GetDifficultyResponseTypeUseCase,
        private val getDifficultyInitialSanityUseCase: GetDifficultyInitialSanityUseCase,
        private val incrementDifficultyIndexUseCase: IncrementDifficultyIndexUseCase,
        private val decrementDifficultyIndexUseCase: DecrementDifficultyIndexUseCase,
        private val fetchSimpleMapsUseCase: FetchSimpleMapsUseCase,
        private val getSimpleMapNameUseCase: GetSimpleMapNameUseCase,
        private val getSimpleMapSizeUseCase: GetSimpleMapSizeUseCase,
        private val getSimpleMapSetupModifierUseCase: GetSimpleMapSetupModifierUseCase,
        private val getSimpleMapNormalModifierUseCase: GetSimpleMapNormalModifierUseCase,
        private val getMapModifierUseCase: GetMapModifierUseCase,
        private val incrementMapIndexUseCase: IncrementMapIndexUseCase,
        private val decrementMapIndexUseCase: DecrementMapIndexUseCase,
        private val incrementMapFloorIndexUseCase: IncrementMapFloorIndexUseCase,
        private val decrementMapFloorIndexUseCase: DecrementMapFloorIndexUseCase,
        private val fetchMapModifiersUseCase: FetchMapModifiersUseCase,
        private val fetchMapThumbnailsUseCase: FetchMapThumbnailsUseCase,
        private val fetchCodexAchievementsUseCase: FetchCodexAchievementsUseCase,
        private val fetchCodexPossessionsUseCase: FetchCodexPossessionsUseCase,
        private val fetchCodexEquipmentUseCase: FetchCodexEquipmentUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InvestigationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return InvestigationViewModel(
                    fetchEvidencesUseCase = fetchEvidencesUseCase,
                    getEvidenceByIdUseCase = getEvidenceByIdUseCase,
                    fetchGhostsUseCase = fetchGhostsUseCase,
                    fetchGhostEvidencesUseCase = fetchGhostEvidencesUseCase,
                    getGhostByIdUseCase = getGhostByIdUseCase,
                    initRuledEvidenceUseCase = initRuledEvidenceUseCase,
                    fetchDifficultiesUseCase = fetchDifficultiesUseCase,
                    incrementDifficultyIndexUseCase = incrementDifficultyIndexUseCase,
                    decrementDifficultyIndexUseCase = decrementDifficultyIndexUseCase,
                    getDifficultyNameUseCase = getDifficultyNameUseCase,
                    getDifficultyModifierUseCase = getDifficultyModifierUseCase,
                    getDifficultyTimeUseCase = getDifficultyTimeUseCase,
                    getDifficultyResponseTypeUseCase = getDifficultyResponseTypeUseCase,
                    getDifficultyInitialSanityUseCase = getDifficultyInitialSanityUseCase,
                    fetchSimpleMapsUseCase = fetchSimpleMapsUseCase,
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
                    fetchCodexEquipmentUseCase = fetchCodexEquipmentUseCase
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val container =
                        (this[APPLICATION_KEY] as PETApplication).operationsContainer

                    val fetchEvidencesUseCase = container.fetchEvidencesUseCase
                    val getEvidenceByIdUseCase = container.getEvidenceByIdUseCase
                    val fetchGhostsUseCase = container.fetchGhostsUseCase
                    val getGhostByIdUseCase = container.getGhostByIdUseCase
                    val fetchGhostEvidencesUseCase = container.fetchGhostEvidencesUseCase
                    val initRuledEvidenceUseCase = container.initRuledEvidenceUseCase
                    val fetchDifficultiesUseCase = container.fetchDifficultiesUseCase
                    val getDifficultyNameUseCase = container.getDifficultyNamesUseCase
                    val getDifficultyModifierUseCase = container.getDifficultyModifierUseCase
                    val getDifficultyTimeUseCase = container.getDifficultyTimeUseCase
                    val getDifficultyResponseTypeUseCase = container.getDifficultyResponseTypeUseCase
                    val getDifficultyInitialSanityUseCase = container.getDifficultyInitialSanityUseCase
                    val incrementDifficultyIndexUseCase = container.incrementDifficultyIndexUseCase
                    val decrementDifficultyIndexUseCase = container.decrementDifficultyIndexUseCase
                    val fetchSimpleMapsUseCase = container.fetchSimpleMapsUseCase
                    val getSimpleMapNameUseCase = container.getSimpleMapNameUseCase
                    val getSimpleMapSizeUseCase = container.getSimpleMapSizeUseCase
                    val getSimpleMapSetupModifierUseCase = container.getSimpleMapSetupModifierUseCase
                    val getSimpleMapNormalModifierUseCase = container.getSimpleMapNormalModifierUseCase
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

                    InvestigationViewModel(
                        fetchEvidencesUseCase = fetchEvidencesUseCase,
                        getEvidenceByIdUseCase = getEvidenceByIdUseCase,
                        fetchGhostsUseCase = fetchGhostsUseCase,
                        getGhostByIdUseCase = getGhostByIdUseCase,
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
                        fetchCodexEquipmentUseCase = fetchCodexEquipmentUseCase
                    )
                }
            }


        private const val TIME_MIN = 0L
        private const val TIME_DEFAULT = 0L

        private const val MAX_TIME = 300000L

        fun percentAsTime(percent: Float, maxTime: Long = MAX_TIME): Long {
            return (percent * maxTime).toLong()
        }

        fun timeAsPercent(time: Long, maxTime: Long = MAX_TIME): Float {
            return time.toFloat() / maxTime.toFloat()
        }
    }

}
