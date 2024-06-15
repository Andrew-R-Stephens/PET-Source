package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity

import android.annotation.SuppressLint
import android.util.Log
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

        const val SAFE_MIN_BOUNDS = 70f

        val DIFFICULTY_MODIFIER = floatArrayOf(1.0f, 1.5f, 2.0f)

        val DROP_MODIFIER_NORMAL = floatArrayOf(.12f, .08f, .05f)
        val DROP_MODIFIER_SETUP = floatArrayOf(.09f, .05f, .03f)
    }

    private val currentMaxSanity: Float
        get() {
            val value = (
                if((evidenceViewModel?.difficultyCarouselData?.currentIndex ?: 0) == 4) 75f
                else MAX_SANITY )
            Log.d("CurrentMaxSanity", "$value")
            return value
        }

    /** The level can be between 0 and 100. Levels outside those extremes are constrained.
     * @return The sanity level that's missing. MAX_SANITY - insanityActual. */
    private var insanityLevel: Float = 0f
        set(value) {
            field = max(min(MAX_SANITY, value), MIN_SANITY)
            Log.d("InsanityLevel", "$insanityLevel")
            updateSanityLevel()
        }

    /** the sanity level missing, in percent.**/
    private val _sanityLevel = MutableStateFlow(currentMaxSanity - insanityLevel)
    val sanityLevel: StateFlow<Float> = _sanityLevel.asStateFlow()
    private fun updateSanityLevel() {
        val level = (MAX_SANITY - insanityLevel)
        _sanityLevel.value = max(min(MAX_SANITY, level), MIN_SANITY)

        evidenceViewModel?.timerModel?.updateCurrentPhase()

        Log.d("SanityLevel", "${sanityLevel.value}")

    }

    /** */
    var warnTriggered = false
        get() {
            return field && (sanityLevel.value < SAFE_MIN_BOUNDS)
        }

    /** */
    var flashTimeoutMax: Long = -1
    /** */
    private var flashTimeoutStart: Long = -1

    /** Defaults if the selected index is out of range of available indexes.
     * @return the difficulty rate multiplier. 1 - default. 0-2 Depending on Map Size. */
    private val currentDifficultyModifier: Float
        get() {
            val diffIndex =
                evidenceViewModel?.difficultyCarouselData?.currentIndex?.value ?: return 1f
            if (diffIndex >= 0 && diffIndex < DIFFICULTY_MODIFIER.size) {
                return DIFFICULTY_MODIFIER[diffIndex]
            }
            return 1f
        }

    /** Based on current map size (Small, Medium, Large) and the stage of the investigation
     * (Setup vs Hunt)
     * Defaults if the selected index is out of range of available indexes.
     * @returns the drop rate multiplier. */
    private val currentMapSizeModifier: Float
        get() {
            val currMapSize = evidenceViewModel?.mapCarouselData?.currentMapSize ?: return 1f
            if ((evidenceViewModel.timerModel?.timeRemaining?.value ?: return 1f) <= 0L) {
                return getNormalDrainRate(currMapSize)
            }
            return getSanityDrainRate(currMapSize)
        }

    private fun getNormalDrainRate(mapSize: Int): Float {
        return DROP_MODIFIER_NORMAL[mapSize]
    }

    private fun getSanityDrainRate(mapSize: Int): Float {
        return DROP_MODIFIER_SETUP[mapSize]
    }

    private fun resetFlashTimeoutStart() {
        flashTimeoutStart = -1
    }

    private val drainModifier: Float
        get() {
            val difficultyModifier =
                evidenceViewModel?.difficultyCarouselData?.currentModifier ?: 1f
            val mapModifier = difficultyModifier / currentMapSizeModifier
            val modifier = difficultyModifier * mapModifier

            return modifier
        }

    /** @param progress specify the progress 0 - 100
     * Resets the Warning Indicator to start flashing again, if necessary
     * Sets the Start Time of the Sanity Drain, based on remaining time,
     * sanity, difficulty and map size. */
    fun setStartTimeFromProgress(progress: Int) {
        val progressOverride =
            MAX_SANITY - max(MIN_SANITY.toInt(), min(MAX_SANITY.toInt(), progress)).toFloat()
        val multiplier = .001f

        val timeAddition = (progressOverride / drainModifier / multiplier).toLong()
        val oldStartTime = evidenceViewModel?.timerModel?.startTime
        val newStartTime = (System.currentTimeMillis() + timeAddition)

        Log.d("RateModifier",
                    "ProgressOverride: $progressOverride" +
                    " TimeAddition: $timeAddition" +
                    "[ StartTime: $oldStartTime -> $newStartTime ]" +
                    " DifficultyRate: $currentDifficultyModifier " +
                    " MapRate: $currentMapSizeModifier" +
                    " Modifier: $drainModifier")

        evidenceViewModel?.timerModel?.startTime = newStartTime
        evidenceViewModel?.sanityModel?.tick()

        resetFlashTimeoutStart()
    }

    /** Allow the Warning indicator to flash either off or on if:
     * The player's sanity is less than 70%
     * either if the Flash Timeout is infinite
     * or if there is no time remaining on the countdown timer.
     * @return if the Warning indicator can flash */
    fun canFlashWarning(): Boolean {
        val temp = sanityLevel.value < SAFE_MIN_BOUNDS

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
        val multiplier = .001f
        val sanityHalf = 50

        /*
        Multiplier which drops the rate to appropriate levels
        Algorithm which mimics in-game sanity drop, based on map size, difficulty level and
        investigation phase.
        */
        /*val startTime = (
                if (evidenceViewModel?.timerModel?.isNewCycle == true) System.currentTimeMillis()
                else evidenceViewModel?.timerModel?.startTime ?: -1L
        )*/
        val startTime = max(evidenceViewModel?.timerModel?.startTime ?: 0L, 0L)

        val drainMultiplier = drainModifier * multiplier
        val timeDifference = startTime - System.currentTimeMillis()
        //Log.d("Tick", "$drainMultiplier $timeDifference ${sanityLevel.value}")
        insanityLevel = MAX_SANITY - (timeDifference * drainMultiplier)

        /*
        If the Countdown timer still has time, and the player's sanity is less than or
        equal to halfway gone, set the remaining sanity to half.
        */
        /*
        if (sanityLevel.value <= sanityHalf &&
            evidenceViewModel?.timerModel?.hasTimeRemaining() == true
        ) {
            setStartTimeFromProgress(sanityHalf)
        }
        */

        //evidenceViewModel?.timerModel?.updateCurrentPhase()
    }

    @SuppressLint("DefaultLocale")
    fun toPercentString(): String {
        val percentageFormat = NumberFormat.getPercentInstance()
        percentageFormat.minimumFractionDigits = 0
        val percent = sanityLevel.value * .01f

        val formattedPercent =
            percentageFormat.format(percent).replace("%", "")

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

    init {
        insanityLevel = 0f
    }

    /** Defaults all persistent data. */
    fun reset() {
        warnTriggered = false
        setStartTimeFromProgress((MAX_SANITY - currentMaxSanity).toInt())
        tick()
    }
}
