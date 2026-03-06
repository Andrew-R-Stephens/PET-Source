package com.tritiumgaming.shared.data.review.usecase.timealive

import com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository

class GetAppTimeAliveUseCase(
    private val repository: ReviewTrackerRepository
) {

    operator fun invoke() = repository.getAppTimeAlive()

}