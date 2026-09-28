package com.tritiumgaming.data.review.usecase.status

import com.tritiumgaming.data.review.repository.ReviewTrackerRepository

class GetReviewRequestStatusUseCase(
    private val repository: ReviewTrackerRepository
) {

    operator fun invoke(): Boolean = repository.getWasRequestedStatus()

}