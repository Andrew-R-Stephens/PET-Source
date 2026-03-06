package com.tritiumgaming.shared.data.review.usecase.status

import com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository

class SetReviewRequestStatusUseCase(
    private val repository: ReviewTrackerRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(status: Boolean) {
        repository.saveWasRequestedStatus(status)
    }

}
