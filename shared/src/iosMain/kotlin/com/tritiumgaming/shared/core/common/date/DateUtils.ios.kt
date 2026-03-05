package com.tritiumgaming.shared.core.common.date

import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarIdentifierISO8601
import platform.Foundation.NSCalendarUnitWeekOfYear
import platform.Foundation.NSDate

actual fun getWeekOfYear(): Int {
    val calendar = NSCalendar.calendarWithIdentifier(NSCalendarIdentifierISO8601)
    val components = calendar?.components(NSCalendarUnitWeekOfYear, fromDate = NSDate())
    return components?.weekOfYear?.toInt() ?: 1
}