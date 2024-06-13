package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationmodels

import android.os.CountDownTimer
import android.util.Log
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.SanityModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FormatterUtils.millisToTime
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PhaseTimerModel(
    val evidenceViewModel: EvidenceViewModel
) {

    enum class Phase {
        SETUP, ACTION, HUNT
    }

    companion object TimerConstraints {
        const val TIME_MIN = 0L
        const val TIME_DEFAULT = -1L
        const val TIME_SECOND = 1000L
    }

    private val _currentPhase: MutableStateFlow<Phase> = MutableStateFlow(Phase.SETUP)
    val currentPhase = _currentPhase.asStateFlow()
    fun updateCurrentPhase() {
        _currentPhase.value =
            if (timeRemaining.value > TIME_MIN) {
                Phase.SETUP
            }
            else {
                if((evidenceViewModel.sanityModel?.sanityLevel?.value ?: 0f) <
                    SanityModel.SAFE_MIN_BOUNDS) {
                    Phase.HUNT
                }
                else {
                    Phase.ACTION
                }
            }
    }

    private val _timeRemaining: MutableStateFlow<Long> = MutableStateFlow(TIME_DEFAULT)
    val timeRemaining = _timeRemaining.asStateFlow()
    fun setTimeRemaining(value: Long) {
        _timeRemaining.value = value
    }

    private var liveTimer: CountDownTimer? = null
    private fun setLiveTimer(millisInFuture: Long = timeRemaining.value,
                             countDownInterval: Long = TIME_SECOND,
                             paused: Boolean = true) {

        liveTimer = object: CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millis: Long) { _timeRemaining.value = millis }
            override fun onFinish() { /* TODO not needed */ }
        }
    }

    private val _paused = MutableStateFlow(true)
    val paused = _paused.asStateFlow()
    private fun pauseTimer() {
        _paused.value = true
        liveTimer?.cancel()
    }
    private fun playTimer() {
        _paused.value = false

        setLiveTimer()
        liveTimer?.start()
    }
    fun toggleTimer() {
        if(paused.value) playTimer()
        else pauseTimer()
    }
    fun resetTimer() {
        pauseTimer()
        resetStartTime()
        setLiveTimer()
    }

    /** The Sanity Drain starting time, whenever the play button is activated.
     * @return The Sanity drain start time. */
    var startTime: Long = -1L
        set(value) {
            field = value
            Log.d("StartTime", "Setting $field")
        }

    /** */
    val isNewCycle: Boolean = (startTime == -1L)

    val displayTime: String
        get() {
            val breakdown = timeRemaining.value / 1000L
            return millisToTime("%s:%s", breakdown)
        }

    private fun resetStartTime() {
        startTime = -1
    }

    fun initStartTime() {
        startTime = System.currentTimeMillis()
    }

    init {
        reset()
    }

    fun hasTimeRemaining(): Boolean {
        return timeRemaining.value < TIME_MIN
    }

    fun reset() {
        resetTimer()
        _timeRemaining.value =
            evidenceViewModel.difficultyCarouselData?.currentDifficultyTime ?: 0L
        resetStartTime()
    }

}
