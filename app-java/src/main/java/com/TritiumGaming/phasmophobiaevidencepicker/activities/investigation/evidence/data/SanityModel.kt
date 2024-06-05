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

    private val _insanityPercent = MutableStateFlow(1f) /** the sanity level missing, in percent.**/
    val insanityPercent = _insanityPercent.asStateFlow()

    private fun setInsanityPercent() {
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

    private val maxSanity = 100f

    private val difficultyRate: DoubleArray = doubleArrayOf(1.0, 1.5, 2.0)

    private val dropRate_setup = doubleArrayOf(.09, .05, .03)
    private val dropRate_normal = doubleArrayOf(.12, .08, .05)

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
    fun getDifficultyRate(): Double {
        val diffIndex =
            evidenceViewModel?.difficultyCarouselData?.difficultyIndex ?: return 1.0

        if (diffIndex >= 0 && diffIndex < difficultyRate.size) {
            return difficultyRate[diffIndex]
        }

        return 1.0
    }

    val dropRate: Double
        /**
         * Returns the drop rate multiplier.
         * Based on current map size (Small, Medium, Large) and the stage of the investigation (Setup
         * vs Hunt)
         * Defaults if the selected index is out of range of available indexes.
         * @return 1.0 - default.
         */
        get() {

            val currMapSize = evidenceViewModel?.mapCarouselData?.mapCurrentSize ?: return 1.0

            if ((evidenceViewModel.phaseTimerData?.timeRemaining ?: return 1.0) <= 0L) {
                return getNormalDrainRate(currMapSize)
            }

            return getSanityDrainRate(currMapSize)
        }

    fun getNormalDrainRate(mapSize: Int): Double {
        return dropRate_normal[mapSize]
    }

    fun getSanityDrainRate(mapSize: Int): Double {
        return dropRate_setup[mapSize]
    }

    fun resetStartTime() {
        startTime = -1
    }

    fun initStartTime() {
        startTime = System.currentTimeMillis()
    }

    fun setFlashTimeoutMax(flashTimeoutMax: Int) {
        this.flashTimeoutMax = flashTimeoutMax.toLong()
    }

    /** @param timeout - The moment when the Warning began to flash. */
    fun setFlashTimeoutStart(timeout: Long) {
        flashTimeoutStart = timeout
    }

    fun resetFlashTimeoutStart() {
        setFlashTimeoutStart(-1)
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
    fun getInsanityActual(): Long {
        val insanityActualTemp = (maxSanity - insanityActual).toInt().toLong()

        val maxSanity = 100L
        val minSanity = 0L
        return max(
            min(insanityActualTemp.toDouble(), maxSanity.toDouble()),
            minSanity.toDouble()
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
        val newStartTime =
            (System.currentTimeMillis() +
                    (maxSanity - progressOverride / getDifficultyRate() / dropRate / .001))
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
            setFlashTimeoutStart(System.currentTimeMillis())
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
        setInsanityPercent()

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

        return percentageFormat.format(insanityPercent.value.toDouble()).replace("%", "")
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
