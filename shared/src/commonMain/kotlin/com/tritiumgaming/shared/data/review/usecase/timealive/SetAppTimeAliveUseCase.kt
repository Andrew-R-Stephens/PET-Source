package com.tritiumgaming.shared.data.review.usecase.timealive

import com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository

class SetAppTimeAliveUseCase(
    private val repository: ReviewTrackerRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(time: Long) {
        repository.saveAppTimeAlive(time)
    }

}