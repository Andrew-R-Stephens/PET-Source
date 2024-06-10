package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.timer

import android.os.CountDownTimer
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
            if (timeRemaining > TIME_MIN) { Phase.SETUP }
            else {
                if((evidenceViewModel.sanityData?.insanityPercent?.value ?: 0f) <
                    SanityModel.SAFE_MIN_BOUNDS) { Phase.HUNT }
                else { Phase.ACTION }
            }
    }

    var timeRemaining: Long = TIME_DEFAULT
    var liveTimer: CountDownTimer? = null
    fun resetLiveTimer() {
        liveTimer = object: CountDownTimer(timeRemaining, TIME_SECOND) {
            override fun onTick(millis: Long) { timeRemaining = millis }
            override fun onFinish() { /* TODO not needed */ }
        }
    }

    private val _paused = MutableStateFlow(true)
    val paused = _paused.asStateFlow()
    private fun pause() {
        _paused.value = true
    }
    private fun play() {
        _paused.value = false
    }
    fun toggle() {
        if(paused.value) play() else pause()
    }

    val displayText: String
        get() {
            val breakdown = timeRemaining / 1000L
            return millisToTime("%s:%s", breakdown)
        }

    init {
        reset()
    }
    
    fun hasTimeRemaining(): Boolean {
        return timeRemaining < TIME_MIN
    }

    fun reset() {
        pause()
        timeRemaining = evidenceViewModel.difficultyCarouselData?.currentDifficultyTime ?: 0L
    }

}
