package com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timealive

import com.tritiumgaming.shared.core.domain.reviewtracker.repository.ReviewTrackerRepository

class SetAppTimeAliveUseCase(
    private val repository: ReviewTrackerRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(time: Long) {
        repository.saveAppTimeAlive(time)
    }

}