package com.tritiumgaming.shared.data.review.usecase.setup

import com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository

class InitFlowReviewTrackerUseCase(
    private val repository: ReviewTrackerRepository
) {

    operator fun invoke() = repository.initDatastoreFlow()

}
    