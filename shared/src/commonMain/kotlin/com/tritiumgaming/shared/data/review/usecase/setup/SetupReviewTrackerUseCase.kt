package com.tritiumgaming.shared.data.review.usecase.setup

import com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository

class SetupReviewTrackerUseCase(
    private val repository: ReviewTrackerRepository
) {
    @Suppress("unused")
    operator fun invoke() = repository.initializeDatastoreLiveData()
}