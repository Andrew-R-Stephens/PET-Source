package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data

import android.annotation.SuppressLint
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.NumberFormat
import kotlin.math.max
import kotlin.math.min

/**
 * SanityData class
 *
 * @author TritiumGamingStudios
 */
class SanityModel(
    private val evidenceViewModel: EvidenceViewModel?
) {

    companion object SanityConstants {
        const val MIN_SANITY = 0f
        const val MAX_SANITY = 100f

        const val SAFE_MIN_BOUNDS = .7f

        val DIFFICULTY_RATE = floatArrayOf(1.0f, 1.5f, 2.0f)

        val DROP_RATE_SETUP = floatArrayOf(.09f, .05f, .03f)
        val DROP_RATE_NORMAL = floatArrayOf(.12f, .08f, .05f)
    }

    private val currentMaxSanity: Float
        get() {
            return (
                if((evidenceViewModel?.difficultyCarouselData?.difficultyIndex ?: 0) == 4) 75f
                else MAX_SANITY )
        }

    /** The level can be between 0 and 100. Levels outside those extremes are constrained.
     * @return The sanity level that's missing. MAX_SANITY - insanityActual. */
    private var insanityActual: Float = 0f
        set(value) {
            field = max(min(currentMaxSanity, value), MIN_SANITY)
            updateInsanityPercent()
        }

    private val _insanityPercent = MutableStateFlow(0f) /** the sanity level missing, in percent.**/
    val insanityPercent = _insanityPercent.asStateFlow()
    private fun updateInsanityPercent() {
        _insanityPercent.value = ((currentMaxSanity - insanityActual) * .01f)
        evidenceViewModel?.timerModel?.updateCurrentPhase()
    }

    /** @return The Sanity level between 0 and 100. Levels outside those extremes are constrained.*/
    val sanityActual: Long
        get() {
            val insanityActualTemp = insanityActual.toInt().toLong()
            return max(min(currentMaxSanity, insanityActualTemp.toFloat()), MIN_SANITY).toLong()
        }

    /** */
    var warningAudioAllowed = true
        get() {
            return field && (insanityPercent.value < SAFE_MIN_BOUNDS)
        }

    /** */
    var flashTimeoutMax: Long = -1
    /** */
    private var flashTimeoutStart: Long = -1

    /** Defaults if the selected index is out of range of available indexes.
     * @return the difficulty rate multiplier. 1 - default. 0-2 Depending on Map Size. */
    private val currentDifficultyRate: Float
        get() {
            val diffIndex =
                evidenceViewModel?.difficultyCarouselData?.difficultyIndex?.value ?: return 1f
            if (diffIndex >= 0 && diffIndex < DIFFICULTY_RATE.size) {
                return DIFFICULTY_RATE[diffIndex]
            }
            return 1f
        }

    /** Based on current map size (Small, Medium, Large) and the stage of the investigation
     * (Setup vs Hunt)
     * Defaults if the selected index is out of range of available indexes.
     * @returns the drop rate multiplier. */
    private val dropRate: Float
        get() {
            val currMapSize = evidenceViewModel?.mapCarouselData?.mapCurrentSize ?: return 1f
            if ((evidenceViewModel.timerModel?.timeRemaining?.value ?: return 1f) <= 0L) {
                return getNormalDrainRate(currMapSize)
            }
            return getSanityDrainRate(currMapSize)
        }

    private fun getNormalDrainRate(mapSize: Int): Float {
        return DROP_RATE_NORMAL[mapSize]
    }

    private fun getSanityDrainRate(mapSize: Int): Float {
        return DROP_RATE_SETUP[mapSize]
    }

    private fun resetFlashTimeoutStart() {
        flashTimeoutStart = -1
    }

    /** @param progressOverride specify the progress 0 - 100
     * Resets the Warning Indicator to start flashing again, if necessary
     * Sets the Start Time of the Sanity Drain, based on remaining time,
     * sanity, difficulty and map size. */
    fun setProgressManually(progressOverride: Long) {
        val multiplier = .001f
        val newStartTime = (System.currentTimeMillis() +
                (currentMaxSanity - progressOverride / currentDifficultyRate / dropRate / multiplier))
        evidenceViewModel?.timerModel?.startTime = newStartTime.toLong()
        resetFlashTimeoutStart()
    }

    /** Allow the Warning indicator to flash either off or on if:
     * The player's sanity is less than 70%
     * either if the Flash Timeout is infinite
     * or if there is no time remaining on the countdown timer.
     * @return if the Warning indicator can flash */
    fun canFlashWarning(): Boolean {
        val temp = insanityPercent.value < SAFE_MIN_BOUNDS

        if (temp && flashTimeoutMax == -1L) {
            return true
        }

        if (temp && flashTimeoutStart == -1L) {
            flashTimeoutStart = System.currentTimeMillis()
        }

        return (System.currentTimeMillis() - flashTimeoutStart) < flashTimeoutMax
    }

    /**
     * Reduces player sanity level each doTick. Sanity cannot drop below 50% if the clock still has
     * time remaining.
     */
    fun tick() {
        val multiplier = .001
        val percentHalf = .5f
        val sanityHalf = 50L

        /*
        Multiplier which drops the rate to appropriate levels
        Algorithm which mimics in-game sanity drop, based on map size, difficulty level and
        investigation phase.
        */
        val startTime = (
                if (evidenceViewModel?.timerModel?.startTime == -1L) System.currentTimeMillis()
                else evidenceViewModel?.timerModel?.startTime
        ) ?: -1L

        insanityActual = (
            (((System.currentTimeMillis() -
                    (startTime)) * multiplier * dropRate) * currentDifficultyRate).toFloat()
        )
        //updateInsanityPercent()

        /*
        If the Countdown timer still has time, and the player's sanity is less than or
        equal to halfway gone, set the remaining sanity to half.
        */
        if (insanityPercent.value <= percentHalf &&
            evidenceViewModel?.timerModel?.hasTimeRemaining() == true
        ) {
            setProgressManually(sanityHalf)
        }

        //evidenceViewModel?.timerModel?.updateCurrentPhase()
    }

    @SuppressLint("DefaultLocale")
    fun toPercentString(): String {
        val percentageFormat = NumberFormat.getPercentInstance()
        percentageFormat.minimumFractionDigits = 0

        val formattedPercent =
            percentageFormat.format(insanityPercent.value.toDouble())
                .replace("%", "")

        val nbsp = "\u00A0"

        var percentStr = formattedPercent
        percentStr = percentStr.replace(nbsp, "").trim { it <= ' ' }

        var percentNum = 100
        try {
            percentNum = percentStr.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }

        val input = String.format("%3d", percentNum)

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
    fun reset() {
        resetFlashTimeoutStart()
        warningAudioAllowed = true
        tick()
    }
}
