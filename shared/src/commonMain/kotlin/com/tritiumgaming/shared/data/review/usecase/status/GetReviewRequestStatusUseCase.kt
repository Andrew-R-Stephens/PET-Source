package com.tritiumgaming.shared.data.review.usecase.status

import com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository

class GetReviewRequestStatusUseCase(
    private val repository: ReviewTrackerRepository
) {

    operator fun invoke(): Boolean = repository.getWasRequestedStatus()

}