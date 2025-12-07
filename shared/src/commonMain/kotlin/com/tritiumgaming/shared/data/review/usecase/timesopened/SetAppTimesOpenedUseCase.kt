package com.tritiumgaming.shared.data.review.usecase.timesopened

import com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository

class SetAppTimesOpenedUseCase(
    private val repository: ReviewTrackerRepository
) {

    suspend operator fun invoke(count: Int) {
        repository.saveAppTimesOpened(count).getOrThrow()
    }

}