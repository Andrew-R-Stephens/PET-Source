package com.tritiumgaming.data.review.usecase.setup

import com.tritiumgaming.data.review.repository.ReviewTrackerRepository

class InitFlowReviewTrackerUseCase(
    private val repository: ReviewTrackerRepository
) {

    operator fun invoke() = repository.initDatastoreFlow()

}
    