package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PhaseTimerModel(
    val evidenceViewModel: EvidenceViewModel
) {
    enum class Phase {
        SETUP, ACTION, HUNT
    }

    var isPaused: Boolean = true

    private val timeDefault = -1L
    private val timeMin = 0L

    var timeRemaining: Long = timeDefault

    val isSetupPhase: Boolean
        get() = timeRemaining > timeMin


    /** @return If the countdown timer is paused. */
    private val _currentPhase: MutableStateFlow<Phase> = MutableStateFlow(Phase.SETUP)
    val currentPhase = _currentPhase.asStateFlow()
    fun updateCurrentPhase() {
        _currentPhase.value =
            if (timeRemaining > timeMin) { Phase.SETUP }
            else {
                if((evidenceViewModel.sanityData?.insanityPercent?.value ?: 0f) <
                    SanityModel.SAFE_MIN_BOUNDS) { Phase.HUNT }
                else { Phase.ACTION }
            }
    }

    init {
        reset()
    }

    fun hasTimeRemaining(): Boolean {
        return timeRemaining < timeMin
    }

    fun reset() {
        isPaused = true
        timeRemaining = evidenceViewModel.difficultyCarouselData?.currentDifficultyTime ?: 0L
    }
}
