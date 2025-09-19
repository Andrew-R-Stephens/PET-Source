package com.tritiumgaming.shared.core.domain.reviewtracker.usecase.setup

import com.tritiumgaming.shared.core.domain.reviewtracker.repository.ReviewTrackerRepository

class SetupReviewTrackerUseCase(
    private val repository: ReviewTrackerRepository
) {
    @Suppress("unused")
    operator fun invoke() = repository.initializeDatastoreLiveData()
}