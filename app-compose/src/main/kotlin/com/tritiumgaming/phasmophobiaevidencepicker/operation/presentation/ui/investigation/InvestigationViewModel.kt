package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase.FetchDifficultiesUseCase
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
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.usecase.FetchMapModifiersUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.usecase.GetMapModifierUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.usecase.GetSimpleMapNormalModifierUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.usecase.GetSimpleMapSetupModifierUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.DecrementMapFloorIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.DecrementMapIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.FetchMapThumbnailsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.GetSimpleMapNameUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.GetSimpleMapSizeUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.IncrementMapFloorIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.IncrementMapIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toStringResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.model.SanityRunnable
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal.lists.item.GhostScore
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.util.FormatterUtils
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
    private val difficulties = fetchDifficultiesUseCase().also { it: List<DifficultyModel> ->
        Log.d("InvestigationViewModel", "DifficultyModel")
        it.forEach { Log.d("InvestigationViewModel", "\tDifficultyModel: $it") } }
    private val codexAchievements = fetchCodexAchievementsUseCase()
    private val codexPossessions = fetchCodexPossessionsUseCase()
    private val codexEquipment = fetchCodexEquipmentUseCase()

    /*
     * UI STATES
     */

    /*
     * COROUTINES
     */
    var sanityRunnable: SanityRunnable? = null

    /*
     * FUNCTIONS
     */
    fun getGhostById(ghostId: String): GhostType? = getGhostByIdUseCase(ghostId)

    fun getEvidenceById(evidenceId: String): EvidenceType? = getEvidenceByIdUseCase(evidenceId)

    fun reset() {
        resetTimer(currentDifficultyTime)
        resetInvestigationJournal()
        resetSanity()
        resetTimerPhase()
    }

    /*
     * InvestigationJournal ---------------------------
     */

    private val _isInvestigationToolsDrawerCollapsed: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val isInvestigationToolsDrawerCollapsed = _isInvestigationToolsDrawerCollapsed.asStateFlow()
    fun setInvestigationToolsDrawerState(
        isCollapsed: Boolean
    ) {
        _isInvestigationToolsDrawerCollapsed.update { isCollapsed }
    }
    fun toggleInvestigationToolsDrawerState() {
        setInvestigationToolsDrawerState(!isInvestigationToolsDrawerCollapsed.value)
    }

    private val _investigationToolsCategory: MutableStateFlow<Int> =
        MutableStateFlow(TOOL_SANITY)
    val investigationToolsCategory = _investigationToolsCategory.asStateFlow()
    fun setInvestigationToolsCategory(categoryIndex: Int) {
        _investigationToolsCategory.value = categoryIndex
    }

    /** Resets the Ruling for each Evidence type */
    fun resetInvestigationJournal() {
        resetEvidenceRulingHandler()
        resetGhostScoreHandler()
    }

    /*
    * Ghost Score Handler ---------------------------
    */
    private val _ghostScores : MutableStateFlow<List<GhostScore>> =
        MutableStateFlow(listOf())
    val ghostScores = _ghostScores.asStateFlow()
    private fun initGhostScores() {
        _ghostScores.update {
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
    fun getGhostScores(
        index: Int
    ): GhostScore? {
        return _ghostScores.value.getOrNull(index)
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
        orderedScores.sortByDescending { it.score.value }
        val orderedTemp = orderedScores.map { it.ghostEvidence.ghost.id }.toMutableStateList()

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
                currentDifficulty = currentDifficulty
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

    private fun resetGhostScoreHandler() {
        initGhostScores()
    }

    /*
    * Evidence Ruling Handler ---------------------------
    */

    private val _ruledEvidence =
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
            it.map { e -> if(evidence.id == e.evidence.id) e.copy(ruling = ruling) else e }
        }
        reorderGhostScores()
    }
    fun getRuledEvidence(
        evidenceModel: EvidenceType
    ): RuledEvidence? {
        return ruledEvidence.value.find { it.isEvidence(evidenceModel) }
    }

    /** Resets the Ruling for each Evidence type */
    private fun resetEvidenceRulingHandler() {
        initRuledEvidence()
    }

    /*override fun toString(): String {
        val str = StringBuilder()
        ruledEvidence.value.forEach {
            str.append(" [${it.evidence.id}:${it.ruling.value}] ")
        }
        return "$str"
    }*/


    /*
     * Difficulty Handler ---------------------------
     */

    /* Index */
    private val _currentDifficultyIndex: MutableStateFlow<Int> =
        MutableStateFlow(DifficultyType.AMATEUR.ordinal)
    private val currentDifficultyIndex = _currentDifficultyIndex.asStateFlow()
    private fun setDifficultyIndex(
        index: Int
    ) {
        _currentDifficultyIndex.update { index }

        updateCurrentDifficultyName()

        setTimeRemaining(currentDifficultyTime)
        resetTimer()

        updateCurrentMaxSanity(DifficultyType.entries[index])
        updateSanityDrainModifier(currentDifficultyModifier)

        updateDifficultyResponseTypeUi()
    }

    fun incrementDifficultyIndex() {

        var i = currentDifficultyIndex.value + 1
        if (i >= difficulties.size) { i = 0 }

        setDifficultyIndex(i)
        audioWarnTriggered = false
    }
    fun decrementDifficultyIndex() {

        var i = currentDifficultyIndex.value - 1
        if (i < 0) { i = difficulties.size - 1 }

        setDifficultyIndex(i)
        audioWarnTriggered = false
    }

    private val currentDifficulty: DifficultyType
        get() = DifficultyType.entries[currentDifficultyIndex.value]

    private val _currentDifficultyName = MutableStateFlow(
        difficulties[currentDifficultyIndex.value].name
    )
    val currentDifficultyName = _currentDifficultyName.asStateFlow()
    private fun updateCurrentDifficultyName() {
        _currentDifficultyName.update { difficulties[currentDifficultyIndex.value].name }
    }

    fun getDifficultyNameAt(
        index: Int
    ): Int {
        return difficulties[index].name.toStringResource()
    }

    private val currentDifficultyTime: Long
        get() = difficulties[currentDifficultyIndex.value].time

    private val currentDifficultyStartSanity: Float
        get() = difficulties[currentDifficultyIndex.value].initialSanity

    private val _difficultyResponseTypeUi = MutableStateFlow(false)
    val difficultyResponseTypeUi = _difficultyResponseTypeUi.asStateFlow()
    private fun updateDifficultyResponseTypeUi() {
        _difficultyResponseTypeUi.update { currentDifficultyIndex.value < DifficultyType.PROFESSIONAL.ordinal }
    }

    /** Defaults if the selected index is out of range of available indexes.
     * @return the difficulty rate multiplier. 1 - default. 0-2 Depending on Map Size. */
    private val currentDifficultyModifier: Float
        get() {
            val diffIndex = currentDifficultyIndex.value
            if (diffIndex >= 0 && diffIndex < difficulties.size) {
                return difficulties[diffIndex].modifier
            }
            return 1f
        }

    /*
     * Sanity Handler ---------------------------
     */

    private val _currentMaxSanity = MutableStateFlow(MAX_SANITY)
    private val currentMaxSanity = _currentMaxSanity.asStateFlow()
    private fun updateCurrentMaxSanity(
        difficulty: DifficultyType
    ) {
        _currentMaxSanity.update {
            if(difficulty == DifficultyType.INSANITY) THREE_FOURTH_SANITY else MAX_SANITY
        }
    }

    /** The level can be between 0 and 100. Levels outside those extremes are constrained.
     * @return The sanity level that's missing. MAX_SANITY - insanityActual. */
    private val _insanityLevel = MutableStateFlow(0f)
    private val insanityLevel = _insanityLevel.asStateFlow()
    fun setInsanityLevel(
        value: Float
    ) {

        _insanityLevel.update { (max(min(MAX_SANITY, value), MIN_SANITY)) }
        updateSanityLevel()
    }
    private fun timeRemainingToInsanityLevel() {

        val tickMultiplier = .001f
        val startTime = max(
            startTime.value,
            TIME_MIN
        )

        val drainMultiplier = sanityDrainModifier.value * tickMultiplier
        val timeDifference = startTime - System.currentTimeMillis()
        setInsanityLevel(
            MAX_SANITY - (timeDifference * drainMultiplier)
        )
    }
    private fun skipInsanity(
        newLevel: Float = HALF_SANITY
    ) {

        setInsanityLevel(newLevel.coerceAtLeast(insanityLevel.value))
        setSanityStartTimeByProgress(insanityLevel.value)
    }

    /** the sanity level missing, in percent.**/
    private val _sanityLevel =
        MutableStateFlow(currentMaxSanity.value - insanityLevel.value)
    val sanityLevel: StateFlow<Float> = _sanityLevel.asStateFlow()
    private fun updateSanityLevel() {

        val level = (MAX_SANITY - insanityLevel.value)
        _sanityLevel.value = max(min(MAX_SANITY, level), MIN_SANITY)

        updateCurrentTimerPhase(sanityLevel.value)
    }

    private val _sanityDrainModifier = MutableStateFlow(1f)
    private val sanityDrainModifier = _sanityDrainModifier.asStateFlow()
    private fun updateSanityDrainModifier(
        currentDifficultyModifier: Float
    ) {
        val mapModifier = getCurrentMapModifier(timeRemaining.value)
        _sanityDrainModifier.update { currentDifficultyModifier * mapModifier }
    }

    private val isSanityInsane: Boolean
        get() = sanityLevel.value < SAFE_MIN_BOUNDS

    /**
     * Reduces player sanity level each doTick. Sanity cannot drop below 50% if the clock still has
     * time remaining.
     */
    private fun tickSanity() {
        timeRemainingToInsanityLevel()
        updateCurrentTimerPhase(sanityLevel.value)
    }

    /** @param progress specify the progress 0 - 100
     * Resets the Warning Indicator to start flashing again, if necessary
     * Sets the Start Time of the Sanity Drain, based on remaining time,
     * sanity, difficulty and map size. */
    private fun setSanityStartTimeByProgress(
        progress: Float = insanityLevel.value
    ) {
        val progressOverride =
            MAX_SANITY - max(MIN_SANITY, min(MAX_SANITY, progress))

        val multiplier = .001f

        val timeAddition = (progressOverride / sanityDrainModifier.value / multiplier).toLong()
        val newStartTime = (System.currentTimeMillis() + timeAddition)

        setStartTime(newStartTime)
    }

    fun displaySanityAsPercent(): String {
        val percentageFormat = NumberFormat.getPercentInstance()
        percentageFormat.minimumFractionDigits = 0
        val percent = sanityLevel.value * .01f

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
        setSanityStartTimeByProgress(
            MAX_SANITY - (currentDifficultyStartSanity))
        tickSanity()
    }


    /*
     * Timer Handler ---------------------------
     */

    private val _currentTimerPhase: MutableStateFlow<Phase> =
        MutableStateFlow(Phase.SETUP)
    val currentTimerPhase = _currentTimerPhase.asStateFlow()
    private  fun updateCurrentTimerPhase(
        sanityLevel: Float
    ) {
        _currentTimerPhase.update {
            if (timeRemaining.value > TIME_MIN) { Phase.SETUP }
            else {
                if (sanityLevel < SAFE_MIN_BOUNDS) { Phase.HUNT }
                else { Phase.ACTION }
            }
        }
    }

    /** The Sanity Drain starting time, whenever the play button is activated.
     * @return The Sanity drain start time. */
    private val _startTime = MutableStateFlow(TIME_DEFAULT)
    private val startTime = _startTime.asStateFlow()
    private fun setStartTime(
        time: Long
    ) {
        _startTime.update { time }
    }
    private fun resetStartTime() {
        _startTime.update { TIME_DEFAULT }
    }

    private val _timeRemaining: MutableStateFlow<Long> =
        MutableStateFlow(TIME_DEFAULT)
    private val timeRemaining = _timeRemaining.asStateFlow()
    private fun setTimeRemaining(
        value: Long
    ) {
        _timeRemaining.update { value }
    }

    fun displayTime(): String {
        val breakdown = timeRemaining.value / SECOND_IN_MILLIS
        return FormatterUtils.millisToTime(
            "%s:%s",
            breakdown
        )
    }

    private var liveTimer: CountDownTimer? = null
    private fun setLiveTimer(
        millisInFuture: Long = timeRemaining.value,
        countDownInterval: Long = 100L
    ) {
        liveTimer = object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millis: Long) {
                setTimeRemaining(millis)
            }

            override fun onFinish() { /* TODO not needed */ }
        }
    }

    private val _timerPaused = MutableStateFlow(true)
    private val timerPaused = _timerPaused.asStateFlow()
    private fun pauseTimer() {
        _timerPaused.update { true }
        liveTimer?.cancel()
    }
    private fun playTimer() {
        _timerPaused.update { false }
        setLiveTimer()
        liveTimer?.start()
    }
    fun toggleTimer() {
        if (timerPaused.value) {
            playTimer()
            setSanityStartTimeByProgress()
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

    /** If the warning is within the appropriate range and condition for activation */
    private var audioWarnTriggered = false

    /** The starting flash time, in milliseconds, for the hunt warning */
    private var flashTimeStart: Long = DEFAULT
    private var flashTimeMax: Long = FOREVER

    private val _timeElapsed: MutableStateFlow<Long> =
        MutableStateFlow(DEFAULT)
    private val timeElapsed = _timeElapsed.asStateFlow()
    private fun updateTimeElapsed() {
        _timeElapsed.update { System.currentTimeMillis() - flashTimeStart }
        updateCanFlash()
    }

    /** Allow the Warning indicator to flash either off or on if:
     * The player's sanity is less than 70%
     * either if the Flash Timeout is infinite
     * or if there is no time remaining on the countdown timer.
     * @return if the Warning indicator can flash */
    private val _canFlash: MutableStateFlow<Boolean> =
        MutableStateFlow(true)
    val canFlash = _canFlash.asStateFlow()
    private fun updateCanFlash() {

        if (flashTimeMax == FOREVER) {
            _canFlash.update { isSanityInsane }
            return
        }

        if (flashTimeStart == DEFAULT) {
            Log.d("Flash", "Start time is default.. now setting to current time")
            flashTimeStart = System.currentTimeMillis()
            updateTimeElapsed()
            return
        }

        _canFlash.update { timeElapsed.value <= flashTimeMax }
    }

    private fun resetTimerPhase() {
        flashTimeStart = DEFAULT
        updateTimeElapsed()
    }

    /*
     * MapCarouselHandler ---------------------------
     */

    /* Index */
    private val _currentMapIndex: MutableStateFlow<Int> =
        MutableStateFlow(0)
    private val currentMapIndex = _currentMapIndex.asStateFlow()
    private fun setCurrentMapIndex(
        index: Int
    ) {
        _currentMapIndex.value = index

        setCurrentMapName()
        setCurrentMapSize()
    }

    fun incrementMapIndex() {
        setCurrentMapIndex(incrementMapIndexUseCase(currentMapIndex.value))
    }

    fun decrementMapIndex() {
        setCurrentMapIndex(decrementMapIndexUseCase(currentMapIndex.value))
    }

    private val _currentMapName = MutableStateFlow<Int>(
        getSimpleMapNameUseCase(currentMapIndex.value)?.toStringResource() ?: 0
    )
    val currentMapName = _currentMapName.asStateFlow()
    private fun setCurrentMapName() {
        _currentMapName.update {
            getSimpleMapNameUseCase(currentMapIndex.value)?.toStringResource() ?: 0
        }
    }

    private val _currentMapSize = MutableStateFlow<Int>(
        getSimpleMapSizeUseCase(currentMapIndex.value)?.toStringResource() ?: 0
    )
    private val currentMapSize = _currentMapSize.asStateFlow()
    private fun setCurrentMapSize() {
        _currentMapSize.update {
            getSimpleMapSizeUseCase(currentMapIndex.value)?.toStringResource() ?: 0
        }
    }

    /** Based on current map size (Small, Medium, Large) and the stage of the investigation
     * (Setup vs Hunt)
     * Defaults if the selected index is out of range of available indexes.
     * @returns the drop rate multiplier. */
    private fun getCurrentMapModifier(
        timeRemaining: Long = 0L
    ): Float = getMapModifierUseCase(currentMapSize.value, timeRemaining)

    init {
        Log.d("InvestigationViewModel", "init")
        Log.d("InvestigationViewModel", "collapsed: ${isInvestigationToolsDrawerCollapsed.value}")
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
                    val fetchSimpleMapsUseCase = container.fetchSimpleMapsUseCase
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

                    InvestigationViewModel(
                        fetchEvidencesUseCase = fetchEvidencesUseCase,
                        getEvidenceByIdUseCase = getEvidenceByIdUseCase,
                        fetchGhostsUseCase = fetchGhostsUseCase,
                        getGhostByIdUseCase = getGhostByIdUseCase,
                        initRuledEvidenceUseCase = initRuledEvidenceUseCase,
                        fetchGhostEvidencesUseCase = fetchGhostEvidencesUseCase,
                        fetchDifficultiesUseCase = fetchDifficultiesUseCase,
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

        const val TOOL_SANITY = 0
        const val TOOL_MODIFIER_DETAILS = 1

        const val MIN_SANITY = 0f
        const val HALF_SANITY = 50f
        const val THREE_FOURTH_SANITY = 75f
        const val MAX_SANITY = 100f

        const val SAFE_MIN_BOUNDS = 70f

        const val TIME_MIN = 0L
        const val SECOND_IN_MILLIS = 1000L
        const val TIME_DEFAULT = -1L

        const val MIN_TIME = 0L
        const val MAX_TIME = 300000L

        const val NEVER = MIN_TIME
        const val FOREVER = MAX_TIME
        const val DEFAULT = FOREVER

        fun percentAsTime(percent: Float, maxTime: Long = MAX_TIME): Long {
            return (percent * maxTime).toLong()
        }

        fun timeAsPercent(time: Long, maxTime: Long = MAX_TIME): Float {
            return time.toFloat() / maxTime.toFloat()
        }
    }

    enum class Phase {
        SETUP, ACTION, HUNT
    }

}