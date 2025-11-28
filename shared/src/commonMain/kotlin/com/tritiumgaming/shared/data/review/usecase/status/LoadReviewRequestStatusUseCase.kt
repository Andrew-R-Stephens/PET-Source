package com.tritiumgaming.shared.data.review.usecase.status

class LoadReviewRequestStatusUseCase(
    private val repository: com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository
) {

    suspend operator fun invoke() = repository.loadWasRequestedStatus()

}
