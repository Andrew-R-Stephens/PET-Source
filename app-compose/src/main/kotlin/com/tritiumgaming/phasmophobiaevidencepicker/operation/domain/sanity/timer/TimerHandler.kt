package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.timer

import android.os.CountDownTimer
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.carousels.DifficultyCarouselHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.sanity.SanityHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.sanity.SanityHandler.SanityConstants.HALF_SANITY
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.sanity.SanityHandler.SanityConstants.SAFE_MIN_BOUNDS
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.util.FormatterUtils.millisToTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class TimerHandler {

    private val _currentPhase: MutableStateFlow<Phase> = MutableStateFlow(Phase.SETUP)
    val currentPhase = _currentPhase.asStateFlow()
    internal fun updateCurrentPhase(sanityHandler: SanityHandler) {
        _currentPhase.update {
            if (timeRemaining.value > TIME_MIN) { Phase.SETUP }
            else {
                if (sanityHandler.sanityLevel.value < SAFE_MIN_BOUNDS) { Phase.HUNT }
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

    private val _paused = MutableStateFlow(true)
    val paused = _paused.asStateFlow()
    private fun pauseTimer() {
        _paused.update { true }
        liveTimer?.cancel()
    }
    private fun playTimer() {
        _paused.update { false }
        setLiveTimer()
        liveTimer?.start()
    }
    fun toggleTimer(sanityHandler: SanityHandler) {
        if (paused.value) {
            playTimer()
            sanityHandler.setStartTimeByProgress(this)
        } else {
            pauseTimer()
        }
    }

    fun resetTimer(sanityHandler: SanityHandler) {
        pauseTimer()
        resetStartTime()
        sanityHandler.timeRemainingToInsanityLevel(this)
        setLiveTimer()
    }

    fun fastForwardTimer(sanityHandler: SanityHandler, time: Long) {
        pauseTimer()
        setTimeRemaining(time)
        setLiveTimer()
        sanityHandler.skipInsanity(this, HALF_SANITY)
        playTimer()
    }

    fun resetTimerHandler(
        sanityHandler: SanityHandler,
        difficultyHandler: DifficultyCarouselHandler
    ) {
        resetTimer(sanityHandler)
        setTimeRemaining(difficultyHandler.currentTime)
        resetStartTime()
    }

    enum class Phase {
        SETUP, ACTION, HUNT
    }

    companion object TimerConstraint {
        const val TIME_MIN = 0L
        const val SECOND_IN_MILLIS = 1000L
        const val TIME_DEFAULT = -1L
    }

}
