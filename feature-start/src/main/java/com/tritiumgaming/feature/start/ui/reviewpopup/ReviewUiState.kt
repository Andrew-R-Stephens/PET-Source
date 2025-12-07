package com.tritiumgaming.feature.start.ui.reviewpopup

data class ReviewUiState(
    val timesOpened: Int = 0,
    val canRequestReview: Boolean = false
) {
    override fun toString(): String {
        return "ReviewUiState(timesOpened=$timesOpened, canRequestReview=$canRequestReview)"
    }
}