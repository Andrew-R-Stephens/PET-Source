package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data

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

    private companion object SanityConstants {
        const val MAX_SANITY = 100f
        const val MIN_SANITY = 0f

        val DIFFICULTY_RATE = floatArrayOf(1.0f, 1.5f, 2.0f)

        val DROP_RATE_SETUP = floatArrayOf(.09f, .05f, .03f)
        val DROP_RATE_NORMAL = floatArrayOf(.12f, .08f, .05f)
    }

    private val _insanityPercent = MutableStateFlow(1f) /** the sanity level missing, in percent.**/
    val insanityPercent = _insanityPercent.asStateFlow()

    private fun updateInsanityPercent() {
        _insanityPercent.value = (getInsanityActual() * .01).toFloat()
    }

    val insanityDegree: Float
        get() = (insanityPercent.value * 360f) /** the sanity level missing, in degrees.**/

    val sanityActual: Long
        /** @return The Sanity level between 0 and 100. Levels outside those extremes are constrained.*/
        get() {
            val insanityActualTemp = insanityActual.toInt().toLong()
            return max(min(insanityActualTemp.toDouble(), 100.0), 0.0).toLong()
        }
    private var insanityActual = 0f

    /**
     * @param startTime - The Sanity Drain starting time, whenever the play button is activated.
     * @return The Sanity drain start time.
     */
    var startTime: Long = -1L

    val isNewCycle: Boolean
        get() = startTime == -1L


    var warningAudioAllowed = true
        get() {
            val temp = (evidenceViewModel?.sanityData?.insanityPercent?.value ?: 0f) < .7

            return field && temp
        }


    /** @return If the countdown timer is paused. */
    var isPaused: Boolean = false

    private var flashTimeoutMax: Long = -1
    private var flashTimeoutStart: Long = -1

    /**
     * Returns the difficulty rate multiplier.
     * Defaults if the selected index is out of range of available indexes.
     * @return 1 - default. 0-2 Depending on Map Size.
     */
    private fun getDifficultyRate(): Float {
        val diffIndex =
            evidenceViewModel?.difficultyCarouselData?.difficultyIndex ?: return 1f

        if (diffIndex >= 0 && diffIndex < DIFFICULTY_RATE.size) {
            return DIFFICULTY_RATE[diffIndex]
        }

        return 1f
    }

    private val dropRate: Float
        /** Returns the drop rate multiplier.
         * Based on current map size (Small, Medium, Large) and the stage of the investigation
         * (Setup vs Hunt)
         * Defaults if the selected index is out of range of available indexes. */
        get() {
            val currMapSize = evidenceViewModel?.mapCarouselData?.mapCurrentSize ?: return 1f
            if ((evidenceViewModel.phaseTimerData?.timeRemaining ?: return 1f) <= 0L) {
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

    private fun resetStartTime() {
        startTime = -1
    }

    fun initStartTime() {
        startTime = System.currentTimeMillis()
    }

    fun setFlashTimeoutMax(flashTimeoutMax: Long) {
        this.flashTimeoutMax = flashTimeoutMax
    }

    private fun resetFlashTimeoutStart() {
        flashTimeoutStart = -1
    }

    /** @param insanityActual - The decimal form of the sanity level. */
    fun setInsanityActual(insanityActual: Float) {
        val maxSanity = 100L
        this.insanityActual = min(insanityActual.toDouble(), maxSanity.toDouble()).toFloat()
    }

    /**
     * The level can be between 0 and 100. Levels outside those extremes are constrained.
     * @return The sanity level that's missing. MAX_SANITY - insanityActual.
     */
    private fun getInsanityActual(): Long {
        val insanityActualTemp = (MAX_SANITY - insanityActual).toInt().toFloat()

        return max(
            min(insanityActualTemp, MAX_SANITY), MIN_SANITY
        ).toLong()
    }

    /**
     * Sets the progress based on preexisting sanity levels
     * Resets the Warning Indicator to start flashing again, if necessary
     * Used upon fragment re-entry, continuing with preexisting data.
     */
    fun setProgressManually() {
        setProgressManually(insanityActual.toLong())
        resetFlashTimeoutStart()
    }

    /**
     * @param progressOverride Accepts a passed value to specify the progress, either done
     * locally or through views
     * Resets the Warning Indicator to start flashing again, if necessary
     * Sets the Start Time of the Sanity Drain, based on remaining time,
     * sanity, difficulty and map size.
     */
    fun setProgressManually(progressOverride: Long) {
        val multiplier = .001f
        val newStartTime =
            (System.currentTimeMillis() +
                    (MAX_SANITY - progressOverride / getDifficultyRate() / dropRate / multiplier))
        startTime = newStartTime.toLong()
        resetFlashTimeoutStart()
    }

    /**
     * Allow the Warning indicator to flash either off or on if:
     * The player's sanity is less than 70%
     * either if the Flash Timeout is infinite
     * or if there is no time remaining on the countdown timer.
     *
     * @return if the Warning indicator can flash
     */
    fun canFlashWarning(): Boolean {
        val temp = (evidenceViewModel?.sanityData?.insanityPercent?.value ?: 0f) < .7

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
        val startTime = (if (startTime == -1L) System.currentTimeMillis() else startTime)

        setInsanityActual(
            (((System.currentTimeMillis() -
                    (startTime)) * multiplier * this.dropRate) * getDifficultyRate()).toFloat()
        )
        updateInsanityPercent()

        /*
        If the Countdown timer still has time, and the player's sanity is less than or
        equal to halfway gone, set the remaining sanity to half.
        */
        if (insanityPercent.value <= percentHalf &&
            evidenceViewModel?.phaseTimerData?.hasTimeRemaining() == true
        ) {
            setProgressManually(sanityHalf)
        }
    }

    /**
     * @return the percent format of sanity level
     */
    fun toPercentString(): String {
        val percentageFormat = NumberFormat.getPercentInstance()
        percentageFormat.minimumFractionDigits = 0

        return percentageFormat.format(
            insanityPercent.value.toDouble()).replace("%", "")
    }

    /**
     * Defaults all persistent data.
     */
    fun reset() {
        resetStartTime()
        resetFlashTimeoutStart()
        warningAudioAllowed = true
        tick()
        val sanityQuarter = 25f
        val sanityEmpty = 0L
        setInsanityActual(
            if ((evidenceViewModel?.difficultyCarouselData?.difficultyIndex ?: 0) == 4)
                sanityQuarter
            else
                sanityEmpty.toFloat()
        )
    }
}
