package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.warning

class PhaseHandler(
) {
    /*companion object Time {
        const val MIN_TIME = 0L
        const val MAX_TIME = 300000L

        const val NEVER = MIN_TIME
        const val FOREVER = MAX_TIME
        const val DEFAULT = FOREVER

        fun percentAsTime(percent: Float, maxTime: Long = MAX_TIME): Long {
            return (percent * maxTime).toLong()
        }

        fun timeAsPercent(time: Long, maxTime: Long = MAX_TIME): Float {
            return time.toFloat() / maxTime.toFloat()
        }
    }

    *//** If the warning is within the appropriate range and condition for activation *//*
    var audioWarnTriggered = false

    *//** The starting flash time, in milliseconds, for the hunt warning *//*
    private var flashTimeStart: Long = DEFAULT
    var flashTimeMax: Long = FOREVER

    private val _timeElapsed: MutableStateFlow<Long> = MutableStateFlow(DEFAULT)
    private val timeElapsed = _timeElapsed.asStateFlow()
    fun updateTimeElapsed(isSanityInsane: Boolean) {
        _timeElapsed.update { System.currentTimeMillis() - flashTimeStart }
        updateCanFlash(isSanityInsane)
    }

    *//** Allow the Warning indicator to flash either off or on if:
     * The player's sanity is less than 70%
     * either if the Flash Timeout is infinite
     * or if there is no time remaining on the countdown timer.
     * @return if the Warning indicator can flash *//*
    private val _canFlash: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val canFlash = _canFlash.asStateFlow()
    private fun updateCanFlash(isSanityInsane: Boolean) {

        if (flashTimeMax == FOREVER) {
            _canFlash.update { isSanityInsane }
            return
        }

        if (flashTimeStart == DEFAULT) {
            Log.d("Flash", "Start time is default.. now setting to current time")
            flashTimeStart = System.currentTimeMillis()
            updateTimeElapsed(isSanityInsane)
            return
        }

        _canFlash.update { timeElapsed.value <= flashTimeMax }
    }

    fun resetPhaseHandler(isSanityInsane: Boolean) {
        flashTimeStart = DEFAULT
        updateTimeElapsed(isSanityInsane)
    }*/

}
