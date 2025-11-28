package com.tritiumgaming.shared.data.review.usecase.status

class SetReviewRequestStatusUseCase(
    private val repository: com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(status: Boolean) {
        repository.saveWasRequestedStatus(status)
    }

}
