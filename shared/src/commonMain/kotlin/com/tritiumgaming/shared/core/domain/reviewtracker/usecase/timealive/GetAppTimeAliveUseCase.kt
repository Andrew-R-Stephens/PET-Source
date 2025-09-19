package com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timealive

import com.tritiumgaming.shared.core.domain.reviewtracker.repository.ReviewTrackerRepository

class GetAppTimeAliveUseCase(
    private val repository: ReviewTrackerRepository
) {

    operator fun invoke() = repository.getAppTimeAlive()

}