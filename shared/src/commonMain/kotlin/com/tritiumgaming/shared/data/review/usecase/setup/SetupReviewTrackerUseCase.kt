package com.tritiumgaming.shared.data.review.usecase.setup

class SetupReviewTrackerUseCase(
    private val repository: com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository
) {
    @Suppress("unused")
    operator fun invoke() = repository.initializeDatastoreLiveData()
}