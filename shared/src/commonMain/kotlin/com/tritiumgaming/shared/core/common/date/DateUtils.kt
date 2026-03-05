package com.tritiumgaming.shared.core.common.date

expect fun getWeekOfYear(): Int

fun calcCycleIndex(size: Int): Int {
    if (size <= 0) return 0
    return (getWeekOfYear() - 1) % size
}