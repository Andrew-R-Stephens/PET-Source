package com.tritiumgaming.shared.core.domain.reviewtracker.usecase.status

import com.tritiumgaming.shared.core.domain.reviewtracker.repository.ReviewTrackerRepository

class SetReviewRequestStatusUseCase(
    private val repository: ReviewTrackerRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(status: Boolean) {
        repository.saveWasRequestedStatus(status)
    }

}
