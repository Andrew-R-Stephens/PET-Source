package com.TritiumGaming.phasmophobiaevidencepicker.data.model.investigation.sanity.timer

import android.os.CountDownTimer
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.investigation.sanity.carousels.DifficultyCarouselModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.investigation.sanity.sanity.SanityModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.investigation.sanity.sanity.SanityModel.SanityConstants.HALF_SANITY
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.investigation.sanity.sanity.SanityModel.SanityConstants.MIN_SANITY
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.investigation.sanity.sanity.SanityModel.SanityConstants.SAFE_MIN_BOUNDS
import com.TritiumGaming.phasmophobiaevidencepicker.utils.FormatterUtils.millisToTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PhaseTimerModel(
    private val sanityModel: SanityModel?,
    private val difficultyCarouselModel: DifficultyCarouselModel?
) {

    enum class Phase {
        SETUP, ACTION, HUNT
    }

    companion object TimerConstraint {
        const val TIME_MIN = 0L
        const val SECOND_IN_MILLIS = 1000L
        const val TIME_DEFAULT = -1L
    }

    private val _currentPhase: MutableStateFlow<Phase> = MutableStateFlow(Phase.SETUP)
    val currentPhase = _currentPhase.asStateFlow()
    fun updateCurrentPhase() {
        _currentPhase.value =
            if (timeRemaining.value > TIME_MIN) {
                Phase.SETUP
            }
            else {
                if((sanityModel?.sanityLevel?.value ?: MIN_SANITY) <
                    SAFE_MIN_BOUNDS) {
                    Phase.HUNT
                }
                else {
                    Phase.ACTION
                }
            }
    }

    /** The Sanity Drain starting time, whenever the play button is activated.
     * @return The Sanity drain start time. */
    var startTime: Long = TIME_DEFAULT
    private fun resetStartTime() { startTime = TIME_DEFAULT }

    private val _timeRemaining: MutableStateFlow<Long> = MutableStateFlow(TIME_DEFAULT)
    val timeRemaining = _timeRemaining.asStateFlow()
    fun setTimeRemaining(value: Long) {
        _timeRemaining.value = value
    }

    val displayTime: String
        get() {
            val breakdown = timeRemaining.value / SECOND_IN_MILLIS
            return millisToTime("%s:%s", breakdown)
        }

    private var liveTimer: CountDownTimer? = null
    private fun setLiveTimer(
        millisInFuture: Long = timeRemaining.value, countDownInterval: Long = 100L) {
        liveTimer = object: CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millis: Long) { setTimeRemaining(millis) }
            override fun onFinish() { /* TODO not needed */ }
        }
    }

    private val _paused = MutableStateFlow(true)
    val paused = _paused.asStateFlow()
    private fun pauseTimer() {
        _paused.value = true
        liveTimer?.cancel()
    }
    fun playTimer() {
        _paused.value = false
        setLiveTimer()
        liveTimer?.start()
    }
    fun toggleTimer() {
        if(paused.value) {
            playTimer()
            sanityModel?.progressToStartTime()
        }
        else { pauseTimer() }
    }
    fun resetTimer() {
        pauseTimer()
        resetStartTime()
        sanityModel?.timeRemainingToInsanityLevel()
        setLiveTimer()
    }
    fun fastForwardTimer(time: Long){
        pauseTimer()
        setTimeRemaining(time)
        setLiveTimer()
        //investigationViewModel.sanityModel?.timeRemainingToInsanityLevel()
        sanityModel?.skipInsanity(HALF_SANITY)
        playTimer()
    }

    init {
        reset()
    }

    fun reset() {
        resetTimer()
        difficultyCarouselModel?.currentTime?.let { setTimeRemaining(it) }
        resetStartTime()
    }

}
