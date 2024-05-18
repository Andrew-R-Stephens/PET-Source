package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data

class PhaseTimerData(
    @JvmField
    val difficultyCarouselData: DifficultyCarouselData
) {
    @JvmField
    var isPaused: Boolean = true

    private val TIME_DEFAULT = -1L
    private val TIME_MIN = 0L
    @JvmField
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
