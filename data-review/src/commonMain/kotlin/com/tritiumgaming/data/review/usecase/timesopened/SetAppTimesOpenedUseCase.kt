package com.tritiumgaming.data.review.usecase.timesopened

import com.tritiumgaming.data.review.repository.ReviewTrackerRepository

class SetAppTimesOpenedUseCase(
    private val repository: ReviewTrackerRepository
) {

    suspend operator fun invoke(count: Int) {
        repository.saveAppTimesOpened(count).getOrThrow()
    }

}