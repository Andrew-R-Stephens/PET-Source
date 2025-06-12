package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel

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
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.CodexRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository.CodexRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostScore
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.RuledEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.RuledEvidence.Ruling
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchDifficultiesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchEvidencesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchGhostEvidencesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchGhostsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.carousels.MapCarouselHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.sanity.SanityRunnable
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.util.FormatterUtils.millisToTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.max
import kotlin.math.min

class InvestigationViewModel(
    private val fetchGhostsUseCase: FetchGhostsUseCase,
    private val fetchEvidenceUseCase: FetchEvidencesUseCase,
    private val fetchGhostEvidenceUseCase: FetchGhostEvidencesUseCase,
    private val fetchDifficultiesUseCase: FetchDifficultiesUseCase,

    mapRepository: SimpleMapRepository,
    private val codexRepository: CodexRepository
): ViewModel() {

    private val ghosts = fetchGhostsUseCase()
    private val evidences = fetchEvidenceUseCase()
    private val ghostEvidences = fetchGhostEvidenceUseCase()
    private val difficulties = fetchDifficultiesUseCase()

    /*
     * HANDLERS / CONTROLLERS
     */
    private var mapHandler: MapCarouselHandler =
        MapCarouselHandler(mapRepository)

    /*
     * UI STATES
     */

    /*
     * COROUTINES
     */
    var sanityRunnable: SanityRunnable? = null

    /*
     * GENERIC STATES
     */
    val currentMapName = mapHandler.currentName

    /*
     * FUNCTIONS
     */
    fun getGhostById(ghostId: String): GhostType? {
        return ghosts.find { it.id == ghostId }
    }
    fun getEvidenceById(evidenceId: String): EvidenceType? {
        return evidences.find { it.id == evidenceId }
    }

    fun incrementMapIndex() {
        mapHandler.incrementIndex()
    }
    fun decrementMapIndex() {
        mapHandler.decrementIndex()
    }

    fun reorderGhostScoreModel() {
        reorderGhostScores()
    }

    /*fun swapStatusInRejectedPile(index: Int) {
        investigationJournal.swapStatusInRejectedPile(index)
    }*/

    fun updatePhaseTimeElapsed() {
        updateTimeElapsed(isSanityInsane)
    }

    fun setAudioWarnTriggered(triggered: Boolean) {
        audioWarnTriggered = triggered
    }

    fun incrementDifficultyIndex() {
        incrementDifficultyIndex(mapHandler)
    }

    fun decrementDifficultyIndex() {
        decrementDifficultyIndex(mapHandler)
    }

    fun resetSanityHandler() {
        resetSanity(currentDifficultyStartSanity)
    }

    fun resetPhaseHandler() {
        resetPhaseHandler(isSanityInsane)
    }

    fun reset() {
        resetTimerHandler(currentDifficultyTime)
        resetInvestigationJournal()
        resetSanityHandler()
        resetPhaseHandler()
    }

    /*
     * InvestigationJournal
     */

    fun setGhostNegation(ghostModel: GhostType, isForceNegated: Boolean) {
        setForcedNegation(ghostModel, isForceNegated)
    }
    fun toggleGhostNegation(ghostModel: GhostType) {
        toggleForcedNegation(ghostModel)
    }

    private val _isInvestigationToolsDrawerCollapsed: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val isInvestigationToolsDrawerCollapsed = _isInvestigationToolsDrawerCollapsed.asStateFlow()
    fun setInvestigationToolsDrawerState(isCollapsed: Boolean) {
        _isInvestigationToolsDrawerCollapsed.update { isCollapsed }
    }
    fun toggleInvestigationToolsDrawerState() {
        _isInvestigationToolsDrawerCollapsed.update { !(isInvestigationToolsDrawerCollapsed.value) }
    }

    private val _investigationToolsCategory: MutableStateFlow<Int> = MutableStateFlow(TOOL_SANITY)
    val investigationToolsCategory = _investigationToolsCategory.asStateFlow()
    fun setInvestigationToolsCategory(categoryIndex: Int) {
        _investigationToolsCategory.value = categoryIndex
    }

    fun reorderGhostScores() {
        reorder()
    }

    /** Resets the Ruling for each Evidence type */
    fun resetInvestigationJournal() {
        resetEvidenceRulingHandler()
        resetGhostScoreHandler()
    }

    /*
    * Ghost Score Handler
    */
    private val _ghostScores : MutableStateFlow<SnapshotStateList<GhostScore>> =
        MutableStateFlow(mutableStateListOf())
    val ghostScores = _ghostScores.asStateFlow()
    private fun initGhostScores() {
        _ghostScores.update { mutableStateListOf() }

        val str = StringBuilder()
        ghostEvidences.forEach {
            val ghostScore = GhostScore(it)
            str.append("${ghostScore.ghostEvidence.ghost.id} ${ghostScore.score}, ")
            _ghostScores.value.add(ghostScore)
        }
        Log.d("GhostScores", "Creating New:\n${str}")

        initOrder()
    }
    fun getGhostScores(ghostModel: GhostType): GhostScore? {
        return _ghostScores.value.find { it.ghostEvidence.ghost.id == ghostModel.id }
    }
    fun getGhostScores(index: Int): GhostScore? {
        return _ghostScores.value.getOrNull(index)
    }

    /** Order of Ghost IDs **/
    private val _ghostOrder: MutableStateFlow<SnapshotStateList<String>> =
        MutableStateFlow(mutableStateListOf())
    @Stable
    val ghostOrder = _ghostOrder.asStateFlow()
    fun initOrder() {
        _ghostOrder.update { mutableStateListOf() }

        val str = StringBuilder()
        ghostScores.value.forEachIndexed { index, it ->
            str.append("${it.ghostEvidence.ghost.id} ${it.score}, ")
            _ghostOrder.value.add(it.ghostEvidence.ghost.id)
        }
        Log.d("GhostOrder", "Creating New:\n${str}")

        reorder()
    }
    fun reorder() {
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

    fun getGhostScore(ghostModel: GhostType): StateFlow<Int> {
        return ghostScores.value.first { it.ghostEvidence.ghost.id == ghostModel.id }.score
    }

    fun setForcedNegation(ghostModel: GhostType, isForceNegated: Boolean){
        val ghostScore = ghostScores.value.first { it.ghostEvidence.ghost.id == ghostModel.id }
        ghostScore.setForcefullyRejected(isForceNegated)
    }
    fun toggleForcedNegation(ghostModel: GhostType){
        val ghostScore = ghostScores.value.first { it.ghostEvidence.ghost.id == ghostModel.id }
        ghostScore.toggleForcefullyRejected()
    }
    fun getGhostScorePoints(ghostModel: GhostType): StateFlow<Int>? {
        val ghostScore = ghostScores.value.find { it.ghostEvidence.ghost.id == ghostModel.id }
        return ghostScore?.score
    }

    fun resetGhostScoreHandler() {
        initGhostScores()
    }

    /*
    * Evidence Ruling Handler
    */

    private val _ruledEvidence = MutableStateFlow(SnapshotStateList<RuledEvidence>())
    val ruledEvidence = _ruledEvidence.asStateFlow()
    fun initRuledEvidence() {
        val list = evidences.map {
            RuledEvidence(it).apply { setRuling(Ruling.NEUTRAL) }
        }
        _ruledEvidence.update { list.toMutableStateList() }
    }
    fun setEvidenceRuling(evidenceIndex: Int, ruling: Ruling) {
        ruledEvidence.value[evidenceIndex].setRuling(ruling)
        reorderGhostScores()
    }
    fun setEvidenceRuling(evidence: EvidenceType, ruling: Ruling) {
        getRuledEvidence(evidence)?.setRuling(ruling)
        reorderGhostScores()
    }
    fun getRuledEvidence(evidenceModel: EvidenceType): RuledEvidence? {
        return ruledEvidence.value.find { it.isEvidence(evidenceModel) }
    }

    /** Resets the Ruling for each Evidence type */
    fun resetEvidenceRulingHandler() {
        //initRuledEvidence()
        ruledEvidence.value.forEach {
            it.setRuling( Ruling.NEUTRAL)
        }
    }

    override fun toString(): String {
        val str = StringBuilder()
        ruledEvidence.value.forEach {
            str.append(" [${it.evidence.id}:${it.ruling.value}] ")
        }
        return "$str"
    }


    /*
     * Difficulty Handler
     */


    /* Index */
    private val _currentDifficultyIndex: MutableStateFlow<Int> =
        MutableStateFlow(DifficultyType.AMATEUR.ordinal)
    val currentDifficultyIndex = _currentDifficultyIndex.asStateFlow()
    private fun setDifficultyIndex(
        mapHandler: MapCarouselHandler,
        index: Int
    ) {
        _currentDifficultyIndex.update { index }
        onUpdateDifficultyIndex(index, mapHandler)
    }

    private fun onUpdateDifficultyIndex(
        index: Int,
        mapHandler: MapCarouselHandler
    ) {
        updateCurrentDifficultyName()

        setTimeRemaining(currentDifficultyTime)
        resetTimer()

        updateCurrentMaxSanity(DifficultyType.entries[index])
        updateSanityDrainModifier(currentDifficultyModifier, mapHandler)

        updateDifficultyResponseTypeUi()
    }

    fun incrementDifficultyIndex(
        mapHandler: MapCarouselHandler
    ) {

        var i = currentDifficultyIndex.value + 1
        if (i >= difficulties.size) { i = 0 }

        setDifficultyIndex(mapHandler, i)
        audioWarnTriggered = false
    }
    fun decrementDifficultyIndex(
        mapHandler: MapCarouselHandler
    ) {

        var i = currentDifficultyIndex.value - 1
        if (i < 0) { i = difficulties.size - 1 }

        setDifficultyIndex(mapHandler, i)
        audioWarnTriggered = false
    }

    /* -- */

    val currentDifficulty: DifficultyType
        get() = DifficultyType.entries[currentDifficultyIndex.value]

    private val _currentDifficultyName = MutableStateFlow(
        difficulties[currentDifficultyIndex.value].name
    )
    val currentDifficultyName = _currentDifficultyName.asStateFlow()
    fun updateCurrentDifficultyName() {
        _currentDifficultyName.update { difficulties[currentDifficultyIndex.value].name }
    }

    fun getDifficultyNameAt(index: Int): Int {
        return difficulties[index].name
    }

    val currentDifficultyTime: Long
        get() = difficulties[currentDifficultyIndex.value].time

    val currentDifficultyStartSanity: Float
        get() = difficulties[currentDifficultyIndex.value].initialSanity

    private val _difficultyResponseTypeUi = MutableStateFlow(false)
    val difficultyResponseTypeUi = _difficultyResponseTypeUi.asStateFlow()
    fun updateDifficultyResponseTypeUi() {
        _difficultyResponseTypeUi.update { currentDifficultyIndex.value < DifficultyType.PROFESSIONAL.ordinal }
    }

    /** Defaults if the selected index is out of range of available indexes.
     * @return the difficulty rate multiplier. 1 - default. 0-2 Depending on Map Size. */
    val currentDifficultyModifier: Float
        get() {
            val diffIndex = currentDifficultyIndex.value
            if (diffIndex >= 0 && diffIndex < difficulties.size) {
                return difficulties[diffIndex].modifier
            }
            return 1f
        }

    init {
        reorderGhostScoreModel()
        initGhostScores()
        initRuledEvidence()
    }

    /*
     * Sanity Handler
     */

    private val _currentMaxSanity = MutableStateFlow(MAX_SANITY)
    val currentMaxSanity = _currentMaxSanity.asStateFlow()
    fun updateCurrentMaxSanity(difficulty: DifficultyType) {
        _currentMaxSanity.update {
            if(difficulty == DifficultyType.INSANITY) THREE_FOURTH_SANITY else MAX_SANITY
        }
    }

    /** The level can be between 0 and 100. Levels outside those extremes are constrained.
     * @return The sanity level that's missing. MAX_SANITY - insanityActual. */
    private val _insanityLevel = MutableStateFlow(0f)
    val insanityLevel = _insanityLevel.asStateFlow()
    fun setInsanityLevel(
        value: Float
    ) {

        _insanityLevel.update { (max(min(MAX_SANITY, value), MIN_SANITY)) }
        updateSanityLevel()
    }
    fun timeRemainingToInsanityLevel() {

        val tickMultiplier = .001f
        val startTime = max(
            startTime.value,
            TIME_MIN)

        val drainMultiplier = sanityDrainModifier.value * tickMultiplier
        val timeDifference = startTime - System.currentTimeMillis()
        setInsanityLevel(
            MAX_SANITY - (timeDifference * drainMultiplier)
        )
    }
    fun skipInsanity(
        newLevel: Float = HALF_SANITY
    ) {

        setInsanityLevel(newLevel.coerceAtLeast(insanityLevel.value))
        setSanityStartTimeByProgress(insanityLevel.value)
    }

    /** the sanity level missing, in percent.**/
    private val _sanityLevel = MutableStateFlow(currentMaxSanity.value - insanityLevel.value)
    val sanityLevel: StateFlow<Float> = _sanityLevel.asStateFlow()
    private fun updateSanityLevel() {

        val level = (MAX_SANITY - insanityLevel.value)
        _sanityLevel.value = max(min(MAX_SANITY, level), MIN_SANITY)

        updateCurrentTimerPhase(sanityLevel.value)
    }

    private val _sanityDrainModifier = MutableStateFlow(1f)
    val sanityDrainModifier = _sanityDrainModifier.asStateFlow()
    fun updateSanityDrainModifier(
        currentDifficultyModifier: Float,
        mapHandler: MapCarouselHandler
    ) {
        val mapModifier = mapHandler.getCurrentModifier(timeRemaining.value)
        _sanityDrainModifier.update { currentDifficultyModifier * mapModifier }
    }

    val isSanityInsane: Boolean
        get() = sanityLevel.value < SAFE_MIN_BOUNDS

    /**
     * Reduces player sanity level each doTick. Sanity cannot drop below 50% if the clock still has
     * time remaining.
     */
    fun tickSanity() {
        timeRemainingToInsanityLevel()
        updateCurrentTimerPhase(sanityLevel.value)
    }

    /** @param progress specify the progress 0 - 100
     * Resets the Warning Indicator to start flashing again, if necessary
     * Sets the Start Time of the Sanity Drain, based on remaining time,
     * sanity, difficulty and map size. */
    fun setSanityStartTimeByProgress(
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

        val input = String.format(Locale.US,"%3d", percentNum)

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
    fun resetSanity(
        currentDifficultyStartSanity: Float
    ) {
        //TODO warnTriggered = false
        setSanityStartTimeByProgress(
            MAX_SANITY - (currentDifficultyStartSanity))
        tickSanity()
    }


    /*
     * Timer Handler
     */

    private val _currentTimerPhase: MutableStateFlow<Phase> = MutableStateFlow(Phase.SETUP)
    val currentTimerPhase = _currentTimerPhase.asStateFlow()
    internal fun updateCurrentTimerPhase(sanityLevel: Float) {
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
    val startTime = _startTime.asStateFlow()
    fun setStartTime(time: Long) {
        _startTime.update { time }
    }
    private fun resetStartTime() {
        _startTime.update { TIME_DEFAULT }
    }

    private val _timeRemaining: MutableStateFlow<Long> = MutableStateFlow(TIME_DEFAULT)
    val timeRemaining = _timeRemaining.asStateFlow()
    fun setTimeRemaining(value: Long) {
        _timeRemaining.update { value }
    }

    fun displayTime(): String {
        val breakdown = timeRemaining.value / SECOND_IN_MILLIS
        return millisToTime("%s:%s", breakdown)
    }

    private var liveTimer: CountDownTimer? = null
    private fun setLiveTimer(
        millisInFuture: Long = timeRemaining.value, countDownInterval: Long = 100L
    ) {
        liveTimer = object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millis: Long) {
                setTimeRemaining(millis)
            }

            override fun onFinish() { /* TODO not needed */
            }
        }
    }

    private val _timerPaused = MutableStateFlow(true)
    val timerPaused = _timerPaused.asStateFlow()
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

    fun resetTimer() {
        pauseTimer()
        resetStartTime()
        timeRemainingToInsanityLevel()
        setLiveTimer()
    }

    fun fastForwardTimer(time: Long) {
        pauseTimer()
        setTimeRemaining(time)
        setLiveTimer()
        skipInsanity(HALF_SANITY)
        playTimer()
    }

    fun resetTimerHandler(
        currentDifficultyTime: Long
    ) {
        resetTimer()
        setTimeRemaining(currentDifficultyTime)
        resetStartTime()
    }

    /*
     * Phase Handler
     */

    /** If the warning is within the appropriate range and condition for activation */
    var audioWarnTriggered = false

    /** The starting flash time, in milliseconds, for the hunt warning */
    private var flashTimeStart: Long = DEFAULT
    var flashTimeMax: Long = FOREVER

    private val _timeElapsed: MutableStateFlow<Long> = MutableStateFlow(DEFAULT)
    private val timeElapsed = _timeElapsed.asStateFlow()
    fun updateTimeElapsed(isSanityInsane: Boolean) {
        _timeElapsed.update { System.currentTimeMillis() - flashTimeStart }
        updateCanFlash(isSanityInsane)
    }

    /** Allow the Warning indicator to flash either off or on if:
     * The player's sanity is less than 70%
     * either if the Flash Timeout is infinite
     * or if there is no time remaining on the countdown timer.
     * @return if the Warning indicator can flash */
    private val _canFlash: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val canFlash = _canFlash.asStateFlow()
    private fun updateCanFlash(isSanityInsane: Boolean) {

        if (flashTimeMax == FOREVER) {
            _canFlash.update { isSanityInsane }
            return
        }

        if (flashTimeStart == DEFAULT) {
            Log.d("Flash", "Start time is default.. now setting to current time")
            flashTimeStart = System.currentTimeMillis()
            updateTimeElapsed(isSanityInsane)
            return
        }

        _canFlash.update { timeElapsed.value <= flashTimeMax }
    }

    fun resetPhaseHandler(isSanityInsane: Boolean) {
        flashTimeStart = DEFAULT
        updateTimeElapsed(isSanityInsane)
    }


    /*
     * VIEWMODEL FACTORIES
     */
    class InvestigationFactory(
        private val fetchGhostsUseCase: FetchGhostsUseCase,
        private val fetchEvidenceUseCase: FetchEvidencesUseCase,
        private val fetchGhostEvidenceUseCase: FetchGhostEvidencesUseCase,
        private val fetchDifficultiesUseCase: FetchDifficultiesUseCase,
        private val simpleMapRepository: SimpleMapRepository,
        private val codexRepository: CodexRepositoryImpl
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InvestigationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return InvestigationViewModel(
                    fetchGhostsUseCase = fetchGhostsUseCase,
                    fetchEvidenceUseCase = fetchEvidenceUseCase,
                    fetchGhostEvidenceUseCase = fetchGhostEvidenceUseCase,
                    fetchDifficultiesUseCase = fetchDifficultiesUseCase,
                    mapRepository = simpleMapRepository,
                    codexRepository = codexRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appKeyContainer = (this[APPLICATION_KEY] as PETApplication).operationsContainer

                val fetchGhostsUseCase: FetchGhostsUseCase =
                    appKeyContainer.fetchGhostsUseCase
                val fetchEvidencesUseCase: FetchEvidencesUseCase =
                    appKeyContainer.fetchEvidencesUseCase
                val fetchGhostEvidencesUseCase: FetchGhostEvidencesUseCase =
                    appKeyContainer.fetchGhostEvidencesUseCase
                val fetchDifficultiesUseCase: FetchDifficultiesUseCase =
                    appKeyContainer.fetchDifficultiesUseCase
                val mapRepository: SimpleMapRepository =
                    appKeyContainer.simpleMapRepository
                val codexRepository: CodexRepository =
                    appKeyContainer.codexRepository

                InvestigationViewModel(
                    fetchGhostsUseCase = fetchGhostsUseCase,
                    fetchEvidenceUseCase = fetchEvidencesUseCase,
                    fetchGhostEvidenceUseCase = fetchGhostEvidencesUseCase,
                    fetchDifficultiesUseCase = fetchDifficultiesUseCase,
                    mapRepository = mapRepository,
                    codexRepository = codexRepository,
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
