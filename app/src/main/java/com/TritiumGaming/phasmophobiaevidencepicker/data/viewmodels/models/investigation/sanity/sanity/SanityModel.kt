package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.sanity

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.sharedpreferences.InvestigationViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.timer.PhaseTimerModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.max
import kotlin.math.min

/**
 * SanityData class
 *
 * @author TritiumGamingStudios
 */
class SanityModel(
    private val investigationViewModel: InvestigationViewModel?
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
                if((investigationViewModel?.difficultyCarouselModel?.currentIndex ?: 0) == 4) 75f
                else MAX_SANITY )
            return value
        }

    /** The level can be between 0 and 100. Levels outside those extremes are constrained.
     * @return The sanity level that's missing. MAX_SANITY - insanityActual. */
    var insanityLevel: Float = 0f
        set(value) {
            field = max(min(MAX_SANITY, value), MIN_SANITY)
            updateSanityLevel()
        }
    fun timeRemainingToInsanityLevel() {
        val tickMultiplier = .001f
        val startTime = max(
            investigationViewModel?.timerModel?.startTime ?: PhaseTimerModel.TIME_DEFAULT,
            PhaseTimerModel.TIME_MIN)

        val drainMultiplier = drainModifier * tickMultiplier
        val timeDifference = startTime - System.currentTimeMillis()
        insanityLevel = MAX_SANITY - (timeDifference * drainMultiplier)
    }
    fun skipInsanity(newLevel: Float = HALF_SANITY) {
        insanityLevel = newLevel.coerceAtLeast(insanityLevel)
        progressToStartTime(insanityLevel)
    }

    /** the sanity level missing, in percent.**/
    private val _sanityLevel = MutableStateFlow(currentMaxSanity - insanityLevel)
    val sanityLevel: StateFlow<Float> = _sanityLevel.asStateFlow()
    private fun updateSanityLevel() {
        val level = (MAX_SANITY - insanityLevel)
        _sanityLevel.value = max(min(MAX_SANITY, level), MIN_SANITY)

        investigationViewModel?.timerModel?.updateCurrentPhase()
    }

    private val drainModifier: Float
        get() {
            val difficultyModifier =
                investigationViewModel?.difficultyCarouselModel?.currentModifier ?: 1f
            val mapModifier =
                investigationViewModel?.mapCarouselModel?.currentModifier ?: 1f
            return difficultyModifier * mapModifier
        }

    val isInsane: Boolean
        get() = sanityLevel.value < SAFE_MIN_BOUNDS

    init {
        reset()
    }

    /**
     * Reduces player sanity level each doTick. Sanity cannot drop below 50% if the clock still has
     * time remaining.
     */
    fun tick() {
        timeRemainingToInsanityLevel()
        investigationViewModel?.timerModel?.updateCurrentPhase()
    }

    /** @param progress specify the progress 0 - 100
     * Resets the Warning Indicator to start flashing again, if necessary
     * Sets the Start Time of the Sanity Drain, based on remaining time,
     * sanity, difficulty and map size. */
    fun progressToStartTime(progress: Float = insanityLevel) {
        investigationViewModel?.timerModel?.let { timerModel ->
            val progressOverride =
                MAX_SANITY - max(MIN_SANITY, min(MAX_SANITY, progress))

            val multiplier = .001f

            val timeAddition = (progressOverride / drainModifier / multiplier).toLong()
            val newStartTime = (System.currentTimeMillis() + timeAddition)

            timerModel.startTime = newStartTime

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

    /** Defaults all persistent data. */
    fun reset() {
        //TODO warnTriggered = false
        progressToStartTime(MAX_SANITY -
                (investigationViewModel?.difficultyCarouselModel?.currentStartSanity ?: 0f))
        tick()
    }

}
