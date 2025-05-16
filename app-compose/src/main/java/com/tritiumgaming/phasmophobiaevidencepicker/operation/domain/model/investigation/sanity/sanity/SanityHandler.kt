package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.sanity

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.source.local.DifficultyLocalDataSource.DifficultyType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.carousels.DifficultyCarouselHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.carousels.MapCarouselHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.timer.TimerHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.max
import kotlin.math.min

/**
 * SanityData class
 *
 * @author TritiumGamingStudios
 */

class SanityHandler {

    companion object SanityConstants {
        const val MIN_SANITY = 0f
        const val HALF_SANITY = 50f
        const val THREE_FOURTH_SANITY = 75f
        const val MAX_SANITY = 100f

        const val SAFE_MIN_BOUNDS = 70f
    }

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
        timerHandler: TimerHandler, sanityHandler: SanityHandler, value: Float) {

        _insanityLevel.update { (max(min(MAX_SANITY, value), MIN_SANITY)) }
        updateSanityLevel(timerHandler, sanityHandler)
    }
    fun timeRemainingToInsanityLevel(
        timerHandler: TimerHandler
    ) {

        val tickMultiplier = .001f
        val startTime = max(
            timerHandler.startTime.value,
            TimerHandler.TIME_MIN)

        val drainMultiplier = drainModifier.value * tickMultiplier
        val timeDifference = startTime - System.currentTimeMillis()
        setInsanityLevel(timerHandler, this,
            MAX_SANITY - (timeDifference * drainMultiplier))
    }
    fun skipInsanity(
        timerHandler: TimerHandler, newLevel: Float = HALF_SANITY) {

        setInsanityLevel(timerHandler, this, newLevel.coerceAtLeast(insanityLevel.value))
        setStartTimeByProgress(timerHandler, insanityLevel.value)
    }

    /** the sanity level missing, in percent.**/
    private val _sanityLevel = MutableStateFlow(currentMaxSanity.value - insanityLevel.value)
    val sanityLevel: StateFlow<Float> = _sanityLevel.asStateFlow()
    private fun updateSanityLevel(timerHandler: TimerHandler, sanityHandler: SanityHandler) {

        val level = (MAX_SANITY - insanityLevel.value)
        _sanityLevel.value = max(min(MAX_SANITY, level), MIN_SANITY)

        timerHandler.updateCurrentPhase(sanityHandler)
    }

    private val _drainModifier = MutableStateFlow(1f)
    val drainModifier = _drainModifier.asStateFlow()
    fun updateDrainModifier(
        difficultyHandler: DifficultyCarouselHandler,
        mapHandler: MapCarouselHandler,
        timerHandler: TimerHandler
    ) {
        val difficultyModifier = difficultyHandler.currentModifier
        val mapModifier = mapHandler.getCurrentModifier(timerHandler.timeRemaining.value)
        _drainModifier.update { difficultyModifier * mapModifier }
    }

    val isInsane: Boolean
        get() = sanityLevel.value < SAFE_MIN_BOUNDS

    /**
     * Reduces player sanity level each doTick. Sanity cannot drop below 50% if the clock still has
     * time remaining.
     */
    fun tick(timerHandler: TimerHandler) {
        timeRemainingToInsanityLevel(timerHandler)
        timerHandler.updateCurrentPhase(this)
    }

    /** @param progress specify the progress 0 - 100
     * Resets the Warning Indicator to start flashing again, if necessary
     * Sets the Start Time of the Sanity Drain, based on remaining time,
     * sanity, difficulty and map size. */
    fun setStartTimeByProgress(timerHandler: TimerHandler, progress: Float = insanityLevel.value) {
        val progressOverride =
            MAX_SANITY - max(MIN_SANITY, min(MAX_SANITY, progress))

        val multiplier = .001f

        val timeAddition = (progressOverride / drainModifier.value / multiplier).toLong()
        val newStartTime = (System.currentTimeMillis() + timeAddition)

        timerHandler.setStartTime(newStartTime)
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
    fun reset(
        difficultyHandler: DifficultyCarouselHandler,
        timerHandler: TimerHandler
    ) {
        //TODO warnTriggered = false
        setStartTimeByProgress(
            timerHandler, MAX_SANITY - (difficultyHandler.currentStartSanity))
        tick(timerHandler)
    }

}


/*
class SanityHandler(
    private val difficultyCarouselModel: DifficultyCarouselHandler,
    private val mapCarouselModel: MapCarouselHandler,
    private val timerModel: PhaseTimerModel?
) {

    companion object SanityConstants {
        const val MIN_SANITY = 0f
        const val HALF_SANITY = 50f
        const val MAX_SANITY = 100f

        const val SAFE_MIN_BOUNDS = 70f
    }

    private val currentMaxSanity: Float
        get() {
            val value = (
                if(difficultyCarouselModel.currentIndex.value == 4) 75f
                else MAX_SANITY )
            return value
        }

    */
/** The level can be between 0 and 100. Levels outside those extremes are constrained.
     * @return The sanity level that's missing. MAX_SANITY - insanityActual. *//*

    private var insanityLevel: Float = 0f
        set(value) {
            field = max(min(MAX_SANITY, value), MIN_SANITY)
            updateSanityLevel()
        }
    fun timeRemainingToInsanityLevel() {
        val tickMultiplier = .001f
        val startTime = max(
            timerModel?.startTime ?: PhaseTimerModel.TIME_DEFAULT,
            PhaseTimerModel.TIME_MIN)

        val drainMultiplier = drainModifier * tickMultiplier
        val timeDifference = startTime - System.currentTimeMillis()
        insanityLevel = MAX_SANITY - (timeDifference * drainMultiplier)
    }
    fun skipInsanity(newLevel: Float = HALF_SANITY) {
        insanityLevel = newLevel.coerceAtLeast(insanityLevel)
        progressToStartTime(insanityLevel)
    }

    */
/** the sanity level missing, in percent.**//*

    private val _sanityLevel = MutableStateFlow(currentMaxSanity - insanityLevel)
    val sanityLevel: StateFlow<Float> = _sanityLevel.asStateFlow()
    private fun updateSanityLevel() {
        val level = (MAX_SANITY - insanityLevel)
        _sanityLevel.value = max(min(MAX_SANITY, level), MIN_SANITY)

        timerModel?.updateCurrentPhase()
    }

    private val drainModifier: Float
        get() {
            val difficultyModifier = difficultyCarouselModel.currentModifier
            val mapModifier = mapCarouselModel.currentModifier
            return difficultyModifier * mapModifier
        }

    val isInsane: Boolean
        get() = sanityLevel.value < SAFE_MIN_BOUNDS

    init {
        reset()
    }

    */
/**
     * Reduces player sanity level each doTick. Sanity cannot drop below 50% if the clock still has
     * time remaining.
     *//*

    fun tick() {
        timeRemainingToInsanityLevel()
        timerModel?.updateCurrentPhase()
    }

    */
/** @param progress specify the progress 0 - 100
     * Resets the Warning Indicator to start flashing again, if necessary
     * Sets the Start Time of the Sanity Drain, based on remaining time,
     * sanity, difficulty and map size. *//*

    fun progressToStartTime(progress: Float = insanityLevel) {
        timerModel.let { timerModel ->
            val progressOverride =
                MAX_SANITY - max(MIN_SANITY, min(MAX_SANITY, progress))

            val multiplier = .001f

            val timeAddition = (progressOverride / drainModifier / multiplier).toLong()
            val newStartTime = (System.currentTimeMillis() + timeAddition)

            timerModel?.startTime = newStartTime

            // TODO resetFlashTimeoutStart()
        }
    }

    fun sanityLevelAsPercent(): String {
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

    */
/** Defaults all persistent data. *//*

    fun reset() {
        //TODO warnTriggered = false
        progressToStartTime(
            MAX_SANITY - (difficultyCarouselModel.currentStartSanity))
        tick()
    }

}
*/
