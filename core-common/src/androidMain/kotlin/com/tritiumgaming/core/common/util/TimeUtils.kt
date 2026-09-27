package com.tritiumgaming.core.common.util

object TimeUtils {

    fun percentAsTime(percent: Float, maxTime: Long): Long {
        return (percent * maxTime).toLong()
    }

    fun timeAsPercent(time: Long, maxTime: Long): Float {
        return time.toFloat() / maxTime.toFloat()
    }

}