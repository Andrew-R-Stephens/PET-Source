package com.tritiumgaming.shared.data.review.usecase.status

class GetReviewRequestStatusUseCase(
    private val repository: com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository
) {

    operator fun invoke(): Boolean = repository.getWasRequestedStatus()

}