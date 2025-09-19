package com.tritiumgaming.shared.core.domain.reviewtracker.usecase.status

import com.tritiumgaming.shared.core.domain.reviewtracker.repository.ReviewTrackerRepository

class GetReviewRequestStatusUseCase(
    private val repository: ReviewTrackerRepository
) {

    operator fun invoke(): Boolean = repository.getWasRequestedStatus()

}