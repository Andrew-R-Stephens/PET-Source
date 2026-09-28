package com.tritiumgaming.data.review.usecase.timealive

import com.tritiumgaming.data.review.repository.ReviewTrackerRepository

class GetAppTimeAliveUseCase(
    private val repository: ReviewTrackerRepository
) {

    operator fun invoke() = repository.getAppTimeAlive()

}