package com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timealive

import com.tritiumgaming.shared.core.domain.reviewtracker.repository.ReviewTrackerRepository

class LoadAppTimeAliveUseCase(
    private val repository: ReviewTrackerRepository
) {

    suspend operator fun invoke() = repository.loadAppTimeAlive()

}
