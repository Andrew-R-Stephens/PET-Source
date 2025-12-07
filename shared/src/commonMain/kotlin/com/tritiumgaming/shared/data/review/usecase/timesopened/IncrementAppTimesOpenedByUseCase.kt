package com.tritiumgaming.shared.data.review.usecase.timesopened

import com.tritiumgaming.shared.data.review.repository.ReviewTrackerRepository

class IncrementAppTimesOpenedByUseCase(
    private val repository: ReviewTrackerRepository
) {

    operator fun invoke(count: Int, incrementBy: Int): Result<Int> {
        repository.canIncrementAppTimesOpened().getOrThrow()

        return Result.success(count + incrementBy)
    }

}