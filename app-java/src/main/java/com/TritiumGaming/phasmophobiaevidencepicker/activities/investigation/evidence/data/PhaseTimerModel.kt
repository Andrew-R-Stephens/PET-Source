package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.carousels.DifficultyCarouselModel

class PhaseTimerModel(
    val difficultyCarouselData: DifficultyCarouselModel
) {
    var isPaused: Boolean = true

    private val TIME_DEFAULT = -1L
    private val TIME_MIN = 0L

    var timeRemaining: Long = TIME_DEFAULT

    val isSetupPhase: Boolean
        get() = timeRemaining > TIME_MIN

    init {
        reset()
    }

    fun hasTimeRemaining(): Boolean {
        return timeRemaining < TIME_MIN
    }

    fun reset() {
        isPaused = true
        timeRemaining = difficultyCarouselData.currentDifficultyTime
    }
}
