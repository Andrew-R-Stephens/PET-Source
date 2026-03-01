package com.tritiumgaming.core.common.util

import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

fun Date.calcCycleIndex(size: Int, period: Int): Int {

    val cycleSize = size.coerceAtLeast(1)
    val periodDays = period.coerceAtLeast(1)

    val today = Calendar.getInstance().apply { resetToMidnight() }
    val start = Calendar.getInstance().apply {
        time = this@calcCycleIndex
        resetToMidnight()
    }

    val diffInMillis = today.timeInMillis - start.timeInMillis
    val daysElapsed = TimeUnit.MILLISECONDS.toDays(diffInMillis)

    val periodsElapsed = daysElapsed / periodDays
    val index = ((periodsElapsed % cycleSize) + cycleSize) % cycleSize

    return index.toInt()
}

private fun Calendar.resetToMidnight() {
    this.set(Calendar.HOUR_OF_DAY, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
    this.set(Calendar.MILLISECOND, 0)
}
