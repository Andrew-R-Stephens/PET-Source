package com.tritiumgaming.shared.core.domain.reviewtracker.usecase.status

import com.tritiumgaming.shared.core.domain.reviewtracker.repository.ReviewTrackerRepository

class LoadReviewRequestStatusUseCase(
    private val repository: ReviewTrackerRepository
) {

    suspend operator fun invoke() = repository.loadWasRequestedStatus()

}
