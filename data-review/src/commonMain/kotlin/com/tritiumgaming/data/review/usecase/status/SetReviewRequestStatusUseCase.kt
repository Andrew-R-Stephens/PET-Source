package com.tritiumgaming.data.review.usecase.status

import com.tritiumgaming.data.review.repository.ReviewTrackerRepository

class SetReviewRequestStatusUseCase(
    private val repository: ReviewTrackerRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(status: Boolean) {
        repository.saveWasRequestedStatus(status)
    }

}
