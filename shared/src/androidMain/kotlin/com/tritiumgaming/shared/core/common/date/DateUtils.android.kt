package com.tritiumgaming.shared.core.common.date

import java.util.Calendar
import java.util.TimeZone

actual fun getWeekOfYear(): Int {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        firstDayOfWeek = Calendar.MONDAY
        minimalDaysInFirstWeek = 4
    }
    return calendar.get(Calendar.WEEK_OF_YEAR)
}