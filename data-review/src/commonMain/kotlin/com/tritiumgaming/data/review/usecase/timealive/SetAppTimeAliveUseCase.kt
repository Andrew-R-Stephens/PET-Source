package com.tritiumgaming.data.review.usecase.timealive

import com.tritiumgaming.data.review.repository.ReviewTrackerRepository

class SetAppTimeAliveUseCase(
    private val repository: ReviewTrackerRepository
) {

    @Suppress("unused")
    suspend operator fun invoke(time: Long) {
        repository.saveAppTimeAlive(time)
    }

}