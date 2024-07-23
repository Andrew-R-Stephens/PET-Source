package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.reviews

/**
 * ReviewTrackingData class
 *
 * @author TritiumGamingStudios
 */
class ReviewTrackingModel(wasRequested: Boolean, timeActive: Long, timesOpened: Int) {

    companion object {
        private const val MAX_TIMES_OPENED_TARGET: Int = 5
    }

    // Checks if the Review was already requested
    var wasRequested: Boolean = false

    // Current time app was alive / target time to trigger review request
    var timeActive: Long = 0

    // Count of times app was opened / target count to trigger review request
    var timesOpened: Int = 0

    init {
        this.wasRequested = wasRequested
        this.timeActive = timeActive
        this.timesOpened = timesOpened
    }

    fun incrementTimesOpened() {
        this.timesOpened += 1
    }

    fun canRequestReview(): Boolean {
        return (!wasRequested) && (timesOpened >= MAX_TIMES_OPENED_TARGET)
    }

    fun canShowReviewButton(): Boolean {
        return (wasRequested) && (timesOpened >= MAX_TIMES_OPENED_TARGET)
    }

    override fun toString(): String {
        return "Time Active: " + timeActive + "; Times Opened: " + timesOpened +
                "; Can Request Review: " + canRequestReview()
    }
}
